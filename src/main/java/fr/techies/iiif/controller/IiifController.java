package fr.techies.iiif.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IiifController {

	@GetMapping("/")
	public String home() {
		
		return "index";
	}
}
