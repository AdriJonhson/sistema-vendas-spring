package com.ptqx.vendas.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ptqx.vendas.model.Avaliacao;
import com.ptqx.vendas.model.Compra;
import com.ptqx.vendas.model.Produto;
import com.ptqx.vendas.model.ProdutoCarrinho;
import com.ptqx.vendas.model.Usuario;
import com.ptqx.vendas.repository.Compras;
import com.ptqx.vendas.repository.Produtos;
import com.ptqx.vendas.repository.Usuarios;

@Controller
@RequestMapping("/compra")
public class CompraController {

	@Autowired
	private Compras compras;

	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private Produtos produtos;	

	//Método que vai pegar os itens do carrinho e salvar um de cada vez na tabela de compras
	@GetMapping("/finalizar")
	public String finalizarCompra(HttpServletRequest request) {
		// Necessário para recuperar o usuário que está logado atualmente no sistema
		Object usuarioLogado = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = ((UserDetails) usuarioLogado).getUsername();
		Usuario usuario = usuarios.findByEmail(userName);

		HttpSession session = request.getSession();
		List<ProdutoCarrinho> carrinho = (List<ProdutoCarrinho>) session.getAttribute("carrinho");

		for (int i = 0; i < carrinho.size(); i++) {

			Compra compra = new Compra();

			compra.setComprador(usuario);
			compra.setVendedor(carrinho.get(i).getProduto().getUsuario());
			compra.setProduto(carrinho.get(i).getProduto());
			compra.setQtd(carrinho.get(i).getQtd());
			compra.setPreco_total(carrinho.get(i).getPreco_total());
			compra.setStatus("pendente");
			
			//set -1 pois vai significar que o produto ainda n foi avaliado
			compra.setAvaliacao(-1);
			
			compras.save(compra);

			//faz a alteração da quantidade de produtos no estoque
			Produto produto = produtos.findOne(carrinho.get(i).getProduto().getId());
			int qtd_antes = produto.getQtd();
			int qtd_nova = produto.getQtd() - carrinho.get(i).getQtd();
			produto.setQtd(qtd_nova);
			produtos.save(produto);
		}

		session.setAttribute("carrinho", null);

		session.setAttribute("message",
				"Compra Realizada com sucesso! Verifique em 'Minhas Compras' para mais informações!");

		return "redirect:/produtos";
	}

	// Método que vai retornar as compras feitas pelo usuário logado
	@GetMapping
	public ModelAndView verComprasUsuario() {
		// Necessário para recuperar o usuário que está logado atualmente no sistema
		Object usuarioLogado = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = ((UserDetails) usuarioLogado).getUsername();
		Usuario usuario = usuarios.findByEmail(userName);

		ModelAndView mv = new ModelAndView();
		mv.addObject("compras", compras.findCompras(usuario.getId()));
		mv.addObject(new Avaliacao());
		mv.setViewName("comprasUsuario");
		return mv;
	}

	@PostMapping("/avaliar")
	public String avaliar(Avaliacao avaliacao, HttpServletRequest request)
	{
		int avaliacaoPonto = avaliacao.getAvaliacao();
		long id_compra = avaliacao.getId_compra();
		
		Compra compra = compras.findOne(id_compra);
		compra.setAvaliacao(avaliacaoPonto);
		compras.save(compra);
		
		HttpSession session = request.getSession();
		session.setAttribute("message",
				"Obrigado por sua avaliação!");

		return "redirect:/compra";
	}
}
