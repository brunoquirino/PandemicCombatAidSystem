package br.com.bruno.pcas.api.service;

import java.util.List;

import br.com.bruno.pcas.api.dominio.ConsultaHospital;
import br.com.bruno.pcas.api.dominio.Hospital;

public interface IHospitalService {

 	Hospital incluir(Hospital hospital) throws ValidacaoException;
 	
 	Hospital alterar(Hospital hospital) throws ValidacaoException;
 	
 	List<Hospital> listar(ConsultaHospital consulta) throws ValidacaoException;
 	
 	void excluir(Hospital hospital) throws ValidacaoException;

	Hospital obter(Long id) throws ValidacaoException;
}
