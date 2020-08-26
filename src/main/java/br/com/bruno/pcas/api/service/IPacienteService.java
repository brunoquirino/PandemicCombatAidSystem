package br.com.bruno.pcas.api.service;

import java.util.List;

import br.com.bruno.pcas.api.dominio.Paciente;

public interface IPacienteService {

 	Paciente incluir(Paciente paciente) throws ValidacaoException;
 	
 	Paciente alterar(Paciente paciente) throws ValidacaoException;
 	
 	List<Paciente> listar() throws ValidacaoException;
 	
 	void excluir(Paciente paciente) throws ValidacaoException;

	Paciente obter(Long id) throws ValidacaoException;
}
