package com.ptqx.vendas.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ptqx.vendas.model.Compra;
import com.ptqx.vendas.model.Mensagem;
import com.ptqx.vendas.model.Usuario;
import com.ptqx.vendas.repository.Compras;
import com.ptqx.vendas.repository.Usuarios;

@Controller
public class VendasController {

	@Autowired
	private Compras compras;
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private JavaMailSender javaMailSender;

	@GetMapping("/vendas")
	public ModelAndView listarVendasUsuario()
	{
		// Necessário para recuperar o usuário que está logado atualmente no sistema
		Object usuarioLogado = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = ((UserDetails) usuarioLogado).getUsername();
		Usuario usuario = usuarios.findByEmail(userName);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("vendas", compras.findVendas(usuario.getId()));
		mv.setViewName("vendasUsuario");
		return mv;
	}
	
	@RequestMapping(value = "/confirmar-entrega/{id_compra}", method=RequestMethod.GET)
	public String confirmarEntrega(@PathVariable long id_compra, HttpServletRequest request)
	{
		Compra compra = compras.findOne(id_compra);
		
		String email_vendedor  = compra.getVendedor().getEmail();
		String email_comprador = compra.getComprador().getEmail();
				
		Mensagem mensagem = new Mensagem(email_vendedor, email_comprador,
				"Loja Online", "Sua Mercadoria chegou na sua residência! Obrigado pela preferência.");
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(mensagem.getRemetente());
		simpleMailMessage.setTo(mensagem.getDestinatario());
		simpleMailMessage.setSubject(mensagem.getAsunto());
		simpleMailMessage.setText(mensagem.getCorpo());
		
		javaMailSender.send(simpleMailMessage);
		
		compra.setStatus("entregue");
		compras.save(compra);
		
		HttpSession session = request.getSession();
		session.setAttribute("message", "Um e-mail foi enviado para o comprador avisando que o produto chegou na residência.");
		
		return "redirect:/vendas";
	}

}
