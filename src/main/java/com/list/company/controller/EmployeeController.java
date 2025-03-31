package com.list.company.controller;

import java.util.HashMap;
import java.util.Map;

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
		
		 Map<String, String> errors = (Map<String, String>) model.asMap().get("errors");
		    model.addAttribute("errors", errors);
		    model.addAttribute("employeeDTO", employeeDTO);
		return "enroll";
		
	}
	
	@PostMapping("/enrollEmployeeCheck")
	public String enrollCheck(@ModelAttribute EmployeeDTO employeeDTO, Model model, RedirectAttributes redirectAttributes) {
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
		
		Map<String, String> errors = new HashMap<>();
		
		if (isBlank(department)) {
	        errors.put("department", "部署を選択してください。");
	    }
	    if (isBlank(rights)) {
	        errors.put("rights", "権限を選択してください。");
	    }
	    if (isBlank(name)) {
	        errors.put("name", "名前を入力してください。");
	    }
	    if (isBlank(email)) {
	        errors.put("email", "メールを入力してください");
	    }
	    if (isBlank(phone)) {
	        errors.put("phone", "電話番号を入力してください。");
	    }
	    if (isBlank(id)) {
	        errors.put("id", "ユーザーIDを入力してください。");
	    }
	    if (isBlank(password)) {
	        errors.put("password", "パスワードを入力してください。");
	    }
	    
	    
	    if (!errors.containsKey("name") && !isValidJapaneseName(name)) {
	        errors.put("name", "ひらがな、カタカナ、漢字を入力してください");
	    }
	    if (!errors.containsKey("email") && !isValidEmail(email)) {
	    	
	    	System.out.println("isBlank: " + isBlank(email) + ", email: " + email + ", email errs: " + errors);
	        errors.put("email", "英語の小文字, 数字を含めて入力してください");
	    }
	    if (!errors.containsKey("phone") && !isValidPhone(phone)) {
	        errors.put("phone", "数字を入力してください");
	    }
	    if (!errors.containsKey("id") && !isValidId(id)) {
	        errors.put("id", "英語の小文字,数字だけ入力してください");
	    }
	    if (!errors.containsKey("password") && !isValidPassword(password)) {
	        errors.put("password", "英語の大文字·小文字·数字·特殊記号! @ # $ % & * _を含めて入力してください");
	    }
	    
	    if (!errors.isEmpty()) {
	    	redirectAttributes.addFlashAttribute("errors", errors);
	   
	        return "redirect:/enroll";
	    }
		else {
			employeeService.saveEmployee(employeeDTO);
			return "redirect:/enroll/success";
		}
	}
	
	//不足な部分：全角のスペースは種類が多い、Tabも制御しないといけない
	public boolean isBlank(String value) {                 //左右半角のスペースを除去
		                        //半角のスペースを除去        //空白を含まない文字列
	    return value == null || value.trim().isEmpty() || !value.matches("^[^\\p{Z}]+$");
	}

	private boolean isValidJapaneseName(String name) {
	    return name.matches("^[\\u3040-\\u309F\\u30A0-\\u30FF\\u4E00-\\u9FFF]+$");
	}

	private boolean isValidEmail(String email) {
	    return email.matches("^[a-z0-9]+([._%-+]?[a-z0-9]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*\\.[a-z]{2,}$");
	}

	private boolean isValidPhone(String phone) {
	    return phone.matches("^[0-9]+$");
	}

	private boolean isValidId(String id) {
	    return id.matches("^[a-z0-9]+$");
	}

	private boolean isValidPassword(String password) {
	    return password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%&*_]).*$");
	}
	
	@GetMapping("/enroll/success")
	public String enrollSuccess() {
		return "dashboard";
	}
}
