package com.ptqx.vendas.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ptqx.vendas.model.Usuario;
import com.ptqx.vendas.repository.Usuarios;

@Service("UsuarioService")
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private Usuarios usuarios;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Usuario buscarUsuarioPorEmail(String email) {
		return usuarios.findByEmail(email);
	}

	@Override
	public void salvarUsuario(Usuario usuario) {
		usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
		usuarios.save(usuario);
	}

}
