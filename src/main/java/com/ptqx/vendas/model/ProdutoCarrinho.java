package com.ptqx.vendas.model;

public class ProdutoCarrinho {
	
	private long id_produto;
	private Produto produto;
	private int qtd;
	private double preco_total;
	
	public int getQtd() {
		return qtd;
	}
	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
	public double getPreco_total() {
		return preco_total;
	}
	public void setPreco_total(double preco_total) {
		this.preco_total = preco_total;
	}
	public long getId_produto() {
		return id_produto;
	}
	public void setId_produto(long id_produto) {
		this.id_produto = id_produto;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
}
