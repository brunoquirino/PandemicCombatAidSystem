package br.com.bruno.pcas.api.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.bruno.pcas.api.dominio.ConsultaHospital;
import br.com.bruno.pcas.api.dominio.Hospital;
import br.com.bruno.pcas.api.integracao.dao.IDaoGenerico;

@Named
public class HospitalService implements IHospitalService {
	
	@Inject
	private IDaoGenerico dao;

	@Override
	public Hospital incluir(Hospital hospital) throws ValidacaoException {
		return dao.incluir(hospital);
	}

	@Override
	public Hospital alterar(Hospital hospital) throws ValidacaoException {
		return dao.alterar(hospital);
	}

	@Override
	public List<Hospital> listar(ConsultaHospital consulta) throws ValidacaoException {
		return dao.listarTodos(Hospital.class);
	}

	@Override
	public void excluir(Hospital hospital) throws ValidacaoException {
		dao.excluir(hospital);
	}

	@Override
	public Hospital obter(Long id) throws ValidacaoException {
		if (id == null) {
			throw new ValidacaoException("ID obrigatório para consulta!");
		}
		
		return dao.obter(Hospital.class, id);
	}

}
