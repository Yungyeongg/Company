package com.list.company.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.list.company.DTO.EmployeeDTO;
import com.list.company.model.Employee;
import com.list.company.service.EmployeeService;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

	@InjectMocks
	private EmployeeController employeeController;
	
	@Mock
	private Model model;
	
	@Mock
	private RedirectAttributes redirectAttributes;
	
	@Mock
	private EmployeeService mockEmployeeService;
	
	private EmployeeDTO mockEmployeeDTO;
	
	@BeforeEach
	void setUp() {
		mockEmployeeDTO = new EmployeeDTO();
	}
	
	@Test
	void enrollPageRendering() {
		
		String viewName = employeeController.showEnrollPage(model, mockEmployeeDTO);
		
		verify(model).addAttribute("employeeDTO", mockEmployeeDTO);
		assertThat(viewName).isEqualTo("enroll");
    }
	
	@Test
	void isBlankTest() {
		assertTrue(employeeController.isBlank(""));
		assertTrue(employeeController.isBlank(" "));
		assertTrue(employeeController.isBlank("a b"));
		assertTrue(employeeController.isBlank(" a"));
		assertTrue(employeeController.isBlank("　Email"));
		assertTrue(employeeController.isBlank(null));
	}
	
	@Test
	void 正しくない１設定値を入力する() {
		//given
		mockEmployeeDTO.setDepartment(" ");
		mockEmployeeDTO.setRights("役員");
		mockEmployeeDTO.setName("ひらがなカタカナ");
		mockEmployeeDTO.setEmail("yun234@naver.com");
		mockEmployeeDTO.setPhone("01012345678");
		mockEmployeeDTO.setId("abcd1234");
		mockEmployeeDTO.setPassword("Kimyun124#");
		
		//when
		String returnName = employeeController.enrollCheck(mockEmployeeDTO, model, redirectAttributes);
		
		//then
		System.out.println("actual return name: " + returnName);
		verify(redirectAttributes).addFlashAttribute("errors", Map.of("department", "部署を選択してください。"));
	}
	
	@Test
	void 正しい設定値を入力する() {
		//given
		mockEmployeeDTO.setDepartment("人事部");
		mockEmployeeDTO.setRights("役員");
		mockEmployeeDTO.setName("ひらがなカタカナ");
		mockEmployeeDTO.setEmail("yun234@naver.com");
		mockEmployeeDTO.setPhone("01012345678");
		mockEmployeeDTO.setId("abcd1234");
		mockEmployeeDTO.setPassword("Kimyun124#");
		 
		//when
		String returnName = employeeController.enrollCheck(mockEmployeeDTO, model, redirectAttributes);
		
		//then
		verify(mockEmployeeService).saveEmployee(mockEmployeeDTO);
		assertThat(returnName).isEqualTo("redirect:/enroll/success");	
	}
	
	@Test
	void enrollSeccess画面遷移Test() {
		String viewName = employeeController.enrollSuccess();
		assertThat(viewName).isEqualTo("dashboard");
	}
	
	@Test
	void 空白設定値を入力する() {
	    // given
	    mockEmployeeDTO.setDepartment("");
	    mockEmployeeDTO.setRights("");
	    mockEmployeeDTO.setName("");
	    mockEmployeeDTO.setEmail("");
	    mockEmployeeDTO.setPhone("");
	    mockEmployeeDTO.setId("");
	    mockEmployeeDTO.setPassword("");

	    // when
	    String returnName = employeeController.enrollCheck(mockEmployeeDTO, model, redirectAttributes);

	    // then
	    assertThat(returnName).isEqualTo("redirect:/enroll");
	    verify(redirectAttributes).addFlashAttribute("errors", Map.of(
	        "department", "部署を選択してください。",
	        "rights", "権限を選択してください。",
	        "name", "名前を入力してください。",
	        "email", "メールを入力してください",
	        "phone", "電話番号を入力してください。",
	        "id", "ユーザーIDを入力してください。",
	        "password", "パスワードを入力してください。"
	    ));
	}
	
	@Test
	void 空白文字設定値を入力する() {
	    // given
	    mockEmployeeDTO.setDepartment("　");
	    mockEmployeeDTO.setRights("　");
	    mockEmployeeDTO.setName("　");
	    mockEmployeeDTO.setEmail("　");
	    mockEmployeeDTO.setPhone("　");
	    mockEmployeeDTO.setId("　");
	    mockEmployeeDTO.setPassword("　");

	    // when
	    String returnName = employeeController.enrollCheck(mockEmployeeDTO, model, redirectAttributes);
	    System.out.println(returnName);
	    assertThat(returnName).isEqualTo("redirect:/enroll");
	    // then
	    verify(redirectAttributes).addFlashAttribute("errors", Map.of(
		        "department", "部署を選択してください。",
		        "rights", "権限を選択してください。",
		        "name", "名前を入力してください。",
		        "email", "メールを入力してください",
		        "phone", "電話番号を入力してください。",
		        "id", "ユーザーIDを入力してください。",
		        "password", "パスワードを入力してください。"
		    ));
	}
	
	@Test
	void null設定値を入力する() {
	    // given
	    mockEmployeeDTO.setDepartment(null);
	    mockEmployeeDTO.setRights(null);
	    mockEmployeeDTO.setName(null);
	    mockEmployeeDTO.setEmail(null);
	    mockEmployeeDTO.setPhone(null);
	    mockEmployeeDTO.setId(null);
	    mockEmployeeDTO.setPassword(null);

	    // when
	    String returnName = employeeController.enrollCheck(mockEmployeeDTO, model, redirectAttributes);

	    // then
	    verify(redirectAttributes).addFlashAttribute("errors", Map.of(
		        "department", "部署を選択してください。",
		        "rights", "権限を選択してください。",
		        "name", "名前を入力してください。",
		        "email", "メールを入力してください",
		        "phone", "電話番号を入力してください。",
		        "id", "ユーザーIDを入力してください。",
		        "password", "パスワードを入力してください。"
		    ));
	    assertThat(returnName).isEqualTo("redirect:/enroll");
	}
	
	@Test
	void 空白含める文字設定値を入力する() {
	    // given
	    mockEmployeeDTO.setDepartment("　Depar tment　");
	    mockEmployeeDTO.setRights("　Rig hts ");
	    mockEmployeeDTO.setName("Na me　");
	    mockEmployeeDTO.setEmail("　Email");
	    mockEmployeeDTO.setPhone("　P hone");
	    mockEmployeeDTO.setId("I d　");
	    mockEmployeeDTO.setPassword("Pass word");

	    // when
	    String returnName = employeeController.enrollCheck(mockEmployeeDTO, model, redirectAttributes);
	    assertThat(returnName).isEqualTo("redirect:/enroll");
	    // then
	    Map expected = Map.of(
		        "department", "部署を選択してください。",
		        "rights", "権限を選択してください。",
		        "name", "名前を入力してください。",
		        "email", "メールを入力してください",
		        "phone", "電話番号を入力してください。",
		        "id", "ユーザーIDを入力してください。",
		        "password", "パスワードを入力してください。"
		    );
	    System.out.println("expected: " + expected);
	    verify(redirectAttributes).addFlashAttribute("errors", expected);
//	    verify(redirectAttributes).addFlashAttribute("errors", Map.of(
//	        "department", "部署を選択してください。",
//	        "rights", "権限を選択してください。",
//	        "name", "名前を入力してください。",
//	        "email", "メールを入力してください",
//	        "phone", "電話番号を入力してください。",
//	        "id", "ユーザーIDを入力してください。",
//	        "password", "パスワードを入力してください。"
//	    ));
	    
	}
	
	@Test
	void validation以外の設定値を入力する() {
	    // given
		mockEmployeeDTO.setDepartment("システム開発部");
	    mockEmployeeDTO.setRights("社員");
	    mockEmployeeDTO.setName("englishname");
	    mockEmployeeDTO.setEmail("ABCD@.COM");
	    mockEmployeeDTO.setPhone("070-1234-2344");
	    mockEmployeeDTO.setId("ABC123");
	    mockEmployeeDTO.setPassword("Password");

	    // when
	    String returnName = employeeController.enrollCheck(mockEmployeeDTO, model, redirectAttributes);

	    // then
	    verify(redirectAttributes).addFlashAttribute("errors", Map.of(
	        "name", "ひらがな、カタカナ、漢字を入力してください",
	        "email", "英語の小文字, 数字を含めて入力してください",
	        "phone", "数字を入力してください",
	        "id", "英語の小文字,数字だけ入力してください",
	        "password", "英語の大文字·小文字·数字·特殊記号! @ # $ % & * _を含めて入力してください"
	    ));
	    assertThat(returnName).isEqualTo("redirect:/enroll"); 
	} 
}
