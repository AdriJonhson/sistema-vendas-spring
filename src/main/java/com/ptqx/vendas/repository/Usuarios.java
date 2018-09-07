package com.ptqx.vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ptqx.vendas.model.Usuario;

public interface Usuarios extends JpaRepository<Usuario, Long>{
}
