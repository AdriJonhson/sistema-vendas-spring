package com.ptqx.vendas.servico;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.ptqx.vendas.model.Produto;
import com.ptqx.vendas.repository.Produtos;

public class CarrinhoServico {
	
	@Autowired
	private Produtos produtosRepository;

	private List<Produto> produtos = new ArrayList<>();

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
}
