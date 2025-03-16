package com.list.companycontroller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {
	 
	@GetMapping("/error")
	    public String handleError() {
	        // 오류 페이지를 반환
	        return "error"; // error.html 페이지를 반환하도록 설정
	    }

}
