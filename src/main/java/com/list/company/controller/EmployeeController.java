package com.list.company.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.list.company.DTO.EmployeeDTO;
import com.list.company.model.Users;
import com.list.company.service.EmployeeService;

import jakarta.validation.Valid;

@Controller
public class EmployeeController {
	  
	private final EmployeeService employeeService;
	
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/enroll")
	public String showEnrollPage(Model model, EmployeeDTO employeeDTO) {
		model.addAttribute("employeeDTO", employeeDTO);
		return "enroll";
	}
	
	@PostMapping("/enrollEmployeeCheck")
	public String enrollCheck(@ModelAttribute EmployeeDTO employeeDTO, Model model) {
		//@Validation  BindingResult result
		System.out.println("enrollCheck method 呼び出しされる");
		
		String department = employeeDTO.getDepartment();
		String rights = employeeDTO.getRights();
		String name = employeeDTO.getName();
		String email = employeeDTO.getEmail();
		String phone = employeeDTO.getPhone();
		String id = employeeDTO.getId();
		String password = employeeDTO.getPassword();
		
		//if (result.hasErrors()) {
	    //    return "enroll";
	   // }
		// 不足な部分　ヌル処理　null、 space、 設定値なし
		if(department == null || department.trim().isEmpty() || !department.matches("^[^\\s]*$")) {
			
			model.addAttribute("department", "部署を選択してください。");
			return "enroll";
		}
		
		if(rights == null || rights.trim().isEmpty() || !rights.matches("^[^\\s]*$")) {
			
			model.addAttribute("rights","権限を選択してください。");
		
			return "enroll";
		}
		   System.out.println(name);
		if(name == null || name.trim().isEmpty() || !name.matches("^[^\\s]*$")) {

			model.addAttribute("name", "名前を入力してください。");
			return "enroll";
		}
		
		String nameRegex = "^[\\u3040-\\u309F\\u30A0-\\u30FF\\u4E00-\\u9FFF]+$";
		if(!name.matches(nameRegex)) {
			model.addAttribute("name", "ひらがな、カタカナ、漢字を入力してください" );
			return "enroll";
		}
		
		if(email == null || email.trim().isEmpty() || !email.matches("^[^\\s]*$")) {
			
			model.addAttribute("email","メールを入力してください");
			return "enroll";
		}
		
		String emailRegex = "^[a-z0-9]+([._%-+]?[a-z0-9]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*\\.[a-z]{2,}$";
		if(!email.matches(emailRegex)) {
			
			model.addAttribute("email", "英語の小文字, 数字を含めて入力してください");
			return "enroll";
		}
		
		if(phone == null || phone.trim().isEmpty() || !phone.matches("^[^\\s]*$")) {
			
			model.addAttribute("phone","電話番号を入力してください。");
			return "enroll";
		}
		
		String phoneRegex = "^[0-9]+$";
        if(!phone.matches(phoneRegex)) {
			
        	model.addAttribute("phone", "数字を入力してください");
			return "enroll";
		}
		
		if(id == null || id.trim().isEmpty() || !id.matches("^[^\\s]*$")) {
			
			model.addAttribute("id","ユーザーIDを入力してください。");
			return "enroll";
		}
		
		String idRegex = "^[a-z0-9]+$";
		if(!id.matches(idRegex)) {
			
			model.addAttribute("id", "英語の小文字,数字だけ入力してください");
			return "enroll";
		}
		
		if(password == null || password.trim().isEmpty() || !password.matches("^[^\\s]*$")) {
			System.out.println("passwordError パスワードを入力してください。");
			model.addAttribute("password","パスワードを入力してください。");
			return "enroll";
		}
		
		String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%&*_]).*$";
		if(!password.matches(passwordRegex)) {
			System.out.println("passwordError  @ # $ % &");
			model.addAttribute("password","英語の大文字·小文字·数字·特殊記号! @ # $ % & * _を含めて入力してください");
			return "enroll";
		}
		
		else {
			employeeService.saveEmployee(employeeDTO);
			return "redirect:/enroll/success";
		}
	}
	
	@GetMapping("/enroll/success")
	public String enrollSuccess() {
		return "dashboard";
	}
}
