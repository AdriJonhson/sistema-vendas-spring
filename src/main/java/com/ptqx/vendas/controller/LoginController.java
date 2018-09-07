package com.ptqx.vendas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ptqx.vendas.model.Usuario;

@Controller
public class LoginController {

	@GetMapping(value = {"/", "/entrar"})
	public ModelAndView entrar()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
		return mv;
	}
	
	@GetMapping("/cadastro")
	public ModelAndView cadastro()
	{
		ModelAndView mv = new ModelAndView();
		mv.addObject(new Usuario());
		mv.setViewName("cadastro");
		return mv;
	}
}
