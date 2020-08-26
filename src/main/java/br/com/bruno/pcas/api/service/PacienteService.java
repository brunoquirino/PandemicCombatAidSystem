package br.com.bruno.pcas.api.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.bruno.pcas.api.dominio.Paciente;
import br.com.bruno.pcas.api.integracao.dao.IDaoGenerico;

@Named
public class PacienteService implements IPacienteService {
	
	@Inject
	private IDaoGenerico dao;

	@Override
	public Paciente incluir(Paciente paciente) throws ValidacaoException {
		return dao.incluir(paciente);
	}

	@Override
	public Paciente alterar(Paciente paciente) throws ValidacaoException {
		return dao.alterar(paciente);
	}

	@Override
	public List<Paciente> listar() throws ValidacaoException {
		return dao.listarTodos(Paciente.class);
	}

	@Override
	public void excluir(Paciente paciente) throws ValidacaoException {
		dao.excluir(paciente);
	}

	@Override
	public Paciente obter(Long id) throws ValidacaoException {
		if (id == null) {
			throw new ValidacaoException("ID obrigatório para consulta!");
		}
		
		return dao.obter(Paciente.class, id);
	}

}
