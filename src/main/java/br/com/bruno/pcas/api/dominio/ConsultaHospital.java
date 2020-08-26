package br.com.bruno.pcas.api.dominio;

import java.io.Serializable;

public class ConsultaHospital implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String texto;

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
