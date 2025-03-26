package com.list.company.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.list.company.model.Users;
import com.list.company.service.UsersService;


import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class LoginController {
	 
	private final UsersService usersService;
	
	public LoginController(UsersService usersService) { 
		this.usersService = usersService;
	}
	
	 @GetMapping("/login")
	    public String loginForm(Model loginForm, Users users) {
	        loginForm.addAttribute("users", users);
	        return "login";
	    }

	@PostMapping("/login/check")    //@ModelAttribute　：　userId, passwordがUsersにオブジェクト自動マッピング 
									//@ValidがチェックしたらBindingResultに検証結果を保存
	public String loginCheck(@Valid @ModelAttribute Users loginForm, BindingResult result, Model loginErrorMessage, HttpSession session) {
		
		if (loginForm.getUserId().isEmpty()) {//isEmpty(): 配列の長さを返還
			loginErrorMessage.addAttribute("idError", "IDを入力してください。");
	        return "login";
	    }
		
	    if (loginForm.getPassword().isEmpty()) {
	    	loginErrorMessage.addAttribute("passwordError", "パスワードを入力してください。");
	        return "login";
	    }
	    
		//if(result.hasErrors()) {
		//	return "login";
		//}
		
		if (usersService.authenticate(loginForm.getUserId(),loginForm.getPassword())) {
			
			session.setAttribute("userId", loginForm.getUserId());
			return "redirect:/success";
	    } else {
	    	loginErrorMessage.addAttribute("diffError", "IDまたはパスワードが違います。");
	        return "login";
	    }
	}
	
	@GetMapping("/success")
    public String loginSuccess(HttpSession session, Users loginForm) {
		session.getAttribute("userId");
        return "dashboard";
    }
}
