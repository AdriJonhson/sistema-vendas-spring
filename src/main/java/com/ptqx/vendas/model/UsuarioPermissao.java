package com.ptqx.vendas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuario_permissao")
public class UsuarioPermissao {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "permissao_id")
	private Permissao permisao;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	public void setPermisao(Permissao permisao) {
		this.permisao = permisao;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Permissao getPermisao() {
		return permisao;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public Long getId() {
		return id;
	}
}
