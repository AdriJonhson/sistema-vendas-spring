package com.ptqx.vendas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ptqx.vendas.model.Permissao;
import com.ptqx.vendas.model.Usuario;
import com.ptqx.vendas.model.UsuarioPermissao;
import com.ptqx.vendas.repository.Permissoes;
import com.ptqx.vendas.repository.PermissoesUsuarios;
import com.ptqx.vendas.servico.UsuarioService;

@Controller
public class LoginController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private Permissoes permissoes;
	
	@Autowired
	private PermissoesUsuarios permissoesUsuario;

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
	
	@PostMapping("/cadastro")
	public ModelAndView cadastrar(Usuario usuario, BindingResult bindingResult)
	{
		ModelAndView modelAndView = new ModelAndView();
		Usuario usuarioExiste = usuarioService.buscarUsuarioPorEmail(usuario.getEmail());
		
		if(usuarioExiste != null) {
			bindingResult
			.rejectValue("email", "error.user", "Esse e-mail já foi cadastrado");
		}
		
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("cadastro");
		} else {
			
			usuario.setAtivo(true);
			usuarioService.salvarUsuario(usuario);
			
			Permissao permisao = permissoes.findOne((long) 1);
			UsuarioPermissao up = new UsuarioPermissao();
			up.setPermisao(permisao);
			up.setUsuario(usuario);
			permissoesUsuario.save(up);
					
			
			modelAndView.addObject("successMessage", "Sua conta foi criada com sucesso");
			modelAndView.setViewName("login");
		}
		
		
		return modelAndView;
	}
}
