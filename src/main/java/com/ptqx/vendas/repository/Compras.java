package com.ptqx.vendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ptqx.vendas.model.Compra;

public interface Compras extends JpaRepository<Compra, Long>{

	//Método que vai retornar os produtos que foram comprados pelo usuário logado
	@Query("select c from Compra c where c.comprador.id = ?1")
	List<Compra> findCompras(long id);
	
	//Método que vai retornar os produtos que foram vendidos pelo usuário logado
	@Query("select c from Compra c where c.vendedor.id = ?1")
	List<Compra> findVendas(long id);
}
