package it.uniroma3.siw.silphspa.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.silphspa.model.FunzionarioForm;

@Controller
public class LoginController {
	

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute(name= "funzionarioForm") FunzionarioForm funzionarioForm, Model model) {
		
		String username = funzionarioForm.getUsername();
		String password = funzionarioForm.getPassword();
		
		if("admin".equals(username) && "admin".equals(password)) {
			
			return "funzionarioHome.html";
			
		}
			
		model.addAttribute("invalidCredentials",true);
		return "funzionarioLogin.html";
		
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginForm() {
		return "funzionarioLogin.html";
	}
	

}
