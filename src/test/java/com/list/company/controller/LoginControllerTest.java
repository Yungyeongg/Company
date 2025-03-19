package com.list.company.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.list.company.service.UsersService;
import com.list.company.service.UsersServiceImplTest;
@ExtendWith(MockitoExtension.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc; //偽の要請を送られるMockMvcオブジェクト
	
	@MockBean
	private UsersService usersService; //Mockオブジェクトで生成
	
	// testをする際にはコンストラクタを明示的に作らない
	
	@Test
	public void loginPageRenderingテスト() throws Exception {
		
		System.out.println("login page rendering test start");
		
		mockMvc.perform(get("/login"))
			   .andExpect(status().isOk()) //HTTP200応答確認
			   .andExpect(view().name("login")) //返還されたviewが"login"か確認
			   .andExpect(model().attributeExists("users")); //モデルに"users"が存在するか確認
		
		System.out.println("login page rendering test 完了");
	}
	
	@Test
	public void ログイン成功テスト() throws Exception {
		
		System.out.println("ログイン成功テスト start");
		
		when(usersService.authenticate("testUser", "password123")).thenReturn(true);
		
		mockMvc.perform(post("/login/check")
				.param("userId", "testUser")
				.param("password", "password123"))
		       .andExpect(status().is3xxRedirection()) //成功時redirect302
		       .andExpect(redirectedUrl("/success")); //"/success"でredirectされるか確認  
	
		System.out.println("ログイン成功テスト 完了");
	}
	
	@Test
	public void ログイン失敗テスト() throws Exception {
		
		System.out.println("ログイン失敗テスト start");
		
		when(usersService.authenticate("wrongUser", "wrongPass")).thenReturn(false);
		
		mockMvc.perform(post("/login/check")
			    .param("userId", "wrongUser")
			    .param("password", "wrongPassword"))
		       .andExpect(status().isOk()) //login失敗時またloginページに移動
		       .andExpect(view().name("login"))
		       .andExpect(model().attributeExists("diffError")) //"IDまたはパスワードが違います。" メッセージ存否確認
		       .andExpect(model().attribute("diffError", "IDまたはパスワードが違います。"));
		
		System.out.println("ログイン失敗テスト 完了");
	}
	
	@Test
	public void id空白テスト() throws Exception {
		
		System.out.println("id空白テスト start");
		
		mockMvc.perform(post("/login/check")
				 .param("userId", "")
				 .param("password", "password123"))
				.andExpect(status().isOk())
				.andExpect(view().name("login"))
				.andExpect(model().attributeExists("idError"))
				.andExpect(model().attribute("idError", "IDを入力してください。"));
	
		System.out.println("id空白テスト 完了");
	}
	
	@Test
	public void password空白テスト() throws Exception {
		
		System.out.println("password空白テスト start");
		
		mockMvc.perform(post("/login/check")
				 .param("userId", "testUser")
				 .param("password", ""))
		       .andExpect(status().isOk())
		       .andExpect(view().name("login"))
		       .andExpect(model().attributeExists("passwordError"))
		       .andExpect(model().attribute("passwordError","パスワードを入力してください。"));
		
		System.out.println("password空白テスト 完了");
	}
}
