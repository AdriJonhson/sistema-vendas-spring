package com.ptqx.vendas.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ptqx.vendas.model.Produto;
import com.ptqx.vendas.model.ProdutoCarrinho;
import com.ptqx.vendas.repository.Produtos;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {
	
	@Autowired
	private Produtos produtos;
	
	private List<ProdutoCarrinho> carrinho;
	
	@GetMapping
	public ModelAndView verCarrinho(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		ModelAndView mv = new ModelAndView();
		double preco_total_compra = 0;
			
		if(session.getAttribute("carrinho") != null) {
			preco_total_compra = calcularPrecoTotalCarrinho(request);
		}
		
		mv.addObject("precoTotalCompra", preco_total_compra);
		mv.setViewName("carrinho");
		return mv;
	}
	
	@PostMapping("/adicionar")
	public String adicionar(HttpServletRequest request, ProdutoCarrinho produtoCarrinho)
	{
		HttpSession session = request.getSession();
		
		if(session.getAttribute("carrinho") == null) {
			carrinho = new ArrayList<>();
			
			Produto produto = produtos.findOne(produtoCarrinho.getId_produto());
			produtoCarrinho.setProduto(produto);
			
			carrinho.add(produtoCarrinho);
			session.setAttribute("carrinho", carrinho);
		}else {
			
			//Caso o produto não esteja no carrinho ele vai ser adicionado
			if(!verificarProduto(request, produtoCarrinho)) {
				Produto produto = produtos.findOne(produtoCarrinho.getId_produto());
				produtoCarrinho.setProduto(produto);
				
				List<ProdutoCarrinho> carrinho_dois = (List<ProdutoCarrinho>) session.getAttribute("carrinho");
				carrinho_dois.add(produtoCarrinho);
				session.setAttribute("carrinho", carrinho_dois);	
			}
					
		}

		session.setAttribute("message", "Produto adicionado ao carrinho.");

		return "redirect:/produtos";
	}
	
	//Função que verifica se o produto selecionado já está no carrinho
	public boolean verificarProduto(HttpServletRequest request, ProdutoCarrinho produtoCarrinho)
	{
		HttpSession session = request.getSession();
		
		List<ProdutoCarrinho> carrinho = (List<ProdutoCarrinho>) session.getAttribute("carrinho");
		
		for(int i = 0; i < carrinho.size(); i++) {
			
			if(carrinho.get(i).getId_produto() == produtoCarrinho.getId_produto()) {
				editarProdutoExistente(carrinho.get(i), produtoCarrinho);
				return true;
			}
			
		}
		
		return false;
	}
	
	//Função que edita a quantidade e preço total caso o produto selecionado já esteja no carrinho
	public void editarProdutoExistente(ProdutoCarrinho produtoCarrinho, ProdutoCarrinho produtoCarrinhoSelecionado)
	{
		int qtd_nova  = produtoCarrinho.getQtd() + produtoCarrinhoSelecionado.getQtd();
		double total_novo = produtoCarrinho.getPreco_total() + produtoCarrinhoSelecionado.getPreco_total();
		produtoCarrinho.setQtd(qtd_nova);
		produtoCarrinho.setPreco_total(total_novo);
	}
	
	//Função que faz o somatorio dos preços para gerar o total que vai ser pago juntado todos os itens do carrinho
	public double calcularPrecoTotalCarrinho(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		List<ProdutoCarrinho> carrinho = (List<ProdutoCarrinho>) session.getAttribute("carrinho");
		double total = 0;
		
		for(int i = 0; i < carrinho.size(); i++) {
			total += carrinho.get(i).getPreco_total();					
		}
		
		return total;
	}
	
	//Função para remover um produto do carrinho
	@RequestMapping(value = "/remover/{id_produto}", method=RequestMethod.GET)
	public String remover(@PathVariable long id_produto, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		
		List<ProdutoCarrinho> carrinho = (List<ProdutoCarrinho>) session.getAttribute("carrinho");
		
		for(int i = 0; i < carrinho.size(); i++) {
			if(carrinho.get(i).getId_produto() == id_produto) {
				carrinho.remove(i);		
			}	
		}
		
		if(carrinho.size() == 0) {
			session.setAttribute("carrinho", null);
		}else {
			session.setAttribute("carrinho", carrinho);			
		}
		
		session.setAttribute("message", "Produto retirado do carrinho.");

		return "redirect:/carrinho";
	}
}
