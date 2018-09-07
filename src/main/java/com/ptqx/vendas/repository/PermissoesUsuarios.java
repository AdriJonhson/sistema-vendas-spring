package com.ptqx.vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ptqx.vendas.model.UsuarioPermissao;

public interface PermissoesUsuarios extends JpaRepository<UsuarioPermissao, Long>{

}
