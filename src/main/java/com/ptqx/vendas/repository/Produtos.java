package com.ptqx.vendas.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ptqx.vendas.model.Produto;


public interface Produtos extends JpaRepository<Produto, Long>{


	List<Produtos> findByUsuario_idNot(long id);
	
	//método que vai retornar somente os produtos cadastrados por outros usuários
	@Query("select p from Produto p where p.usuario.id != ?1")
	List<Produto> findVendas(long id);
	
	//método que vai retornar somente os produtos cadastrados pelo usuário que está logado
	@Query("select p from Produto p where p.usuario.id = ?1")
	List<Produto> findProdutos(long id);
}
