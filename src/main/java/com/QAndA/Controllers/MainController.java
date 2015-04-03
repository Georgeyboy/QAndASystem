package com.QAndA.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by George on 03/04/2015.
 */

@Controller
@RequestMapping("/")
public class MainController {

	public MainController(){
		System.out.println("MainController initialized");
	}




	@RequestMapping("")
	public String mainRedirect(final Model model){
		model.asMap().clear();
		return "redirect:/basic/";
	}
}
