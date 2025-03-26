package com.list.company.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {
	 
	@GetMapping("/error")
	    public String handleError() {
	        // errorページを返還
	        return "error"; // error.htmlページ
	    }

}
