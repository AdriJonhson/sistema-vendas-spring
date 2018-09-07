package com.ptqx.vendas.servico;

import com.ptqx.vendas.model.Usuario;

public interface UsuarioService {

	public Usuario buscarUsuarioPorEmail(String email);
	public void salvarUsuario(Usuario usuario);
	
}
