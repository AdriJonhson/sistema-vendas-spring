package com.ptqx.vendas.model;

public class Mensagem {

	private String remetente;
	private String destinatario;
	private String asunto;
	private String corpo;
	
	public Mensagem(String remetente, String destinatario, String asunto, String corpo) {
		this.remetente = remetente;
		this.destinatario = destinatario;
		this.asunto = asunto;
		this.corpo = corpo;
	}
	
	public String getRemetente() {
		return remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getCorpo() {
		return corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}
}
