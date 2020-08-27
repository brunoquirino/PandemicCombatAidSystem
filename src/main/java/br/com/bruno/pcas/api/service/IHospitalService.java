package br.com.bruno.pcas.api.service;

import java.util.List;

import br.com.bruno.pcas.api.dominio.Hospital;
import br.com.bruno.pcas.api.dominio.TransacoesHistorico;

public interface IHospitalService {

 	Hospital incluir(Hospital hospital) throws ValidacaoException;
 	
 	Hospital alterar(Hospital hospital) throws ValidacaoException;
 	
 	List<Hospital> listar() throws ValidacaoException;
 	
	Hospital obter(Long id) throws ValidacaoException;

	TransacoesHistorico trocarRecursos(TransacoesHistorico transacao) throws ValidacaoException;
}
