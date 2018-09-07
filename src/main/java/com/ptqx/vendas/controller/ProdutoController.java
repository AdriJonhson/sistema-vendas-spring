package com.ptqx.vendas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProdutoController {

	@GetMapping("/produtos")
	public ModelAndView login()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("produtos");
		return mv;
	}
	
}
