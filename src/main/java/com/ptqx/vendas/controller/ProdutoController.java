package com.ptqx.vendas.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ptqx.vendas.model.Produto;
import com.ptqx.vendas.model.ProdutoCarrinho;
import com.ptqx.vendas.model.Usuario;
import com.ptqx.vendas.repository.Produtos;
import com.ptqx.vendas.repository.Usuarios;

@Controller
public class ProdutoController {

	@Autowired
	private Produtos produtos;

	@Autowired
	private Usuarios usuarios;
	
	@GetMapping("/produtos")
	public ModelAndView listarProdutos()
	{
		ModelAndView mv = new ModelAndView();

		// Necessário para recuperar o usuário que está logado atualmente no sistema
		Object usuarioLogado = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = ((UserDetails) usuarioLogado).getUsername();
		Usuario usuario = usuarios.findByEmail(userName);

		mv.addObject("produtos", produtos.findVendas(usuario.getId()));
		mv.addObject(new Produto());
		mv.addObject(new ProdutoCarrinho());
		mv.setViewName("produtos");
		return mv;
	}

	@PostMapping("/produtos")
	public String cadastrar(HttpServletRequest request, Produto produto)
	{
		// Necessário para recuperar o usuário que está logado atualmente no sistema
		Object usuarioLogado = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = ((UserDetails) usuarioLogado).getUsername();
		Usuario usuario = usuarios.findByEmail(userName);

		produto.setUsuario(usuario);
		produtos.save(produto);
		
		HttpSession session = request.getSession();
		session.setAttribute("message", "Produto adicionado para venda! Lembrando: ele só vai aparecer para outros usuários e não para você.");

		return "redirect:/seus-produtos";
	}
	
	@GetMapping("/seus-produtos")
	public ModelAndView produtosUsuario()
	{
		ModelAndView mv = new ModelAndView();

		// Necessário para recuperar o usuário que está logado atualmente no sistema
		Object usuarioLogado = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = ((UserDetails) usuarioLogado).getUsername();
		Usuario usuario = usuarios.findByEmail(userName);

		mv.addObject("produtos", produtos.findProdutos(usuario.getId()));
		mv.addObject(new Produto());
		mv.addObject(new ProdutoCarrinho());
		mv.setViewName("produtosUsuario");
		return mv;
	}

}
