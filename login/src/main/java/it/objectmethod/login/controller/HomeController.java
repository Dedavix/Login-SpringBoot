package it.objectmethod.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/login")
	public String getHtml() {
		return "frontend";
	}

}
