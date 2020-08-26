package br.com.bruno.pcas.api.service;

import java.util.ArrayList;
import java.util.List;

public class ValidacaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private List<String> erros;
	
	public ValidacaoException(List<String> erros) {
		super(erros.toString());
		this.erros = erros;
	}

	public ValidacaoException(String erro) {
		super(erro);
		this.erros = new ArrayList<>();
		this.erros.add(erro);
	}

	public List<String> getErros() {
		return erros;
	}

}
