package br.com.bruno.pcas.api.service;

import java.util.List;

import br.com.bruno.pcas.api.dominio.Hospital;
import br.com.bruno.pcas.api.dominio.TransacaoHistorico;
import br.com.bruno.pcas.api.dominio.to.HospitalTO;
import br.com.bruno.pcas.api.dominio.to.TransacaoHistoricoTO;

public interface IHospitalService {

 	Hospital incluir(Hospital hospital) throws ValidacaoException;
 	
 	Hospital alterar(Hospital hospital) throws ValidacaoException;
 	
 	List<Hospital> listar() throws ValidacaoException;
 	
	Hospital obter(Long id) throws ValidacaoException;

	TransacaoHistorico trocarRecursos(TransacaoHistorico transacao) throws ValidacaoException;

	List<HospitalTO> obterRelatorioHospitaisOcupacaoMenor90();

	List<HospitalTO> obterRelatorioHospitaisOcupacaoMaior90();

	List<HospitalTO> obterRelatorioQuantidadeRecursosPorHospitais();

	List<HospitalTO> obterRelatorioHospitaisOcupacaoMaior90Tempo();

	List<HospitalTO> obterRelatorioHospitaisOcupacaoMenor90Tempo();

	List<TransacaoHistoricoTO> obterRelatorioHistoricoNegociacoes();
}
