package com.list.company.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.list.CompanyApplication;
import com.list.company.model.Users;
import com.list.company.repository.UsersRepository;
import com.list.company.service.UsersService;
import com.list.company.service.UsersServiceImpl;
import com.list.company.service.UsersServiceImplTest;

import jakarta.servlet.http.HttpSession;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest { 

	@InjectMocks
	private LoginController loginController;
	
	@InjectMocks
	private CustomErrorController errorController;
	
	@Mock
	private BindingResult bindingResult;
	
	@Mock
	private Model model;
	
	@Mock
	private HttpSession session;
	
	@Mock
	private UsersServiceImpl usersService;
	
	@Mock
	private UsersRepository usersRepository;
	
	private Users mockUsers;
	
	@BeforeEach
	void setUp() {
		mockUsers = new Users();
		mockUsers.setUserId("testId");
		mockUsers.setPassword("testPassword");
	}
	
	@Test
    void contextLoads() {
		CompanyApplication.main(new String[] {}); 
        // application contextが正常的にロードされるか確認
    }
	
	@Test
	void errorPage() {
		
		String viewName = errorController.handleError();
		
		assertThat(viewName).isEqualTo("error");
	}
	
	@Test
	void loginFormRendering() {
		
		String viewName = loginController.loginForm(model, mockUsers);
		
		verify(model).addAttribute("users", mockUsers);
		assertThat(viewName).isEqualTo("login");
	}
	
	@Test
	void idBlankCheck() {
		//given
		mockUsers.setUserId("");
		mockUsers.setPassword("testPassword");
		
		//when
		String viewName = loginController.loginCheck(mockUsers, bindingResult, model, session);
		
		//then
		assertThat(viewName).isEqualTo("login");
		verify(model).addAttribute("idError", "IDを入力してください。"); 
		//verifyはｍodelのメソッドが特定因子値と呼び出しされたか確認
	}
	
	@Test
	void passwordBlankCheck() {
		//given
		mockUsers.setUserId("testId");
		mockUsers.setPassword("");
		
		//when
		String viewName = loginController.loginCheck(mockUsers, bindingResult, model, session);
		
		//then
		assertThat(viewName).isEqualTo("login");
		verify(model).addAttribute("passwordError", "パスワードを入力してください。");
	}
	
	@Test
	void idとパスワード存在sessionにidあり() {
		mockUsers.setUserId("testId");
		mockUsers.setPassword("testPassword");
		
		when(usersService.authenticate("testId", "testPassword")).thenReturn(true);
		
		String returnName = loginController.loginCheck(mockUsers, bindingResult, model, session);
		
		 ArgumentCaptor<String> sessionKeyCaptor = ArgumentCaptor.forClass(String.class);
	     ArgumentCaptor<String> sessionValueCaptor = ArgumentCaptor.forClass(String.class);
	     verify(session).setAttribute(sessionKeyCaptor.capture(), sessionValueCaptor.capture());

	        assertThat(sessionKeyCaptor.getValue()).isEqualTo("userId");
	        assertThat(sessionValueCaptor.getValue()).isEqualTo("testId");
	        assertThat(returnName).isEqualTo("redirect:/success");
	    }
	
	
	@Test
	void id存在なしパスワード存在() {
		//given
		mockUsers.setUserId("wrongId");
		mockUsers.setPassword("testPassword");
		
		//when
		String viewName = loginController.loginCheck(mockUsers, bindingResult, model, session);
		
		//then
		assertThat(viewName).isEqualTo("login");
		verify(model).addAttribute("diffError", "IDまたはパスワードが違います。");
	}
	
	@Test
	void id存在パスワード存在なし() {
		//given
		mockUsers.setUserId("testPassword");
		mockUsers.setPassword("wrongId");
		
		//when
		String viewName = loginController.loginCheck(mockUsers, bindingResult, model, session);
		
		//then
		assertThat(viewName).isEqualTo("login");
		verify(model).addAttribute("diffError", "IDまたはパスワードが違います。");
	}
	
	@Test
	void id存在なしパスワード存在なし() {
		//given
		mockUsers.setUserId("wrongId");
		mockUsers.setPassword("wrongPassword");
		
		//when 
		String viewName = loginController.loginCheck(mockUsers, bindingResult, model, session);
		
		//then
		assertThat(viewName).isEqualTo("login");
		verify(model).addAttribute("diffError", "IDまたはパスワードが違います。");
	}
	
	@Test 
	 void loginSuccessDashboard遷移() {
        // given
        String expectedUserId = "testUser";
        when(session.getAttribute("userId")).thenReturn(expectedUserId); 

        // when
        String viewName = loginController.loginSuccess(session, mockUsers); 

        // then
        assertThat(viewName).isEqualTo("dashboard");
        verify(session).getAttribute("userId"); 
    }
}
