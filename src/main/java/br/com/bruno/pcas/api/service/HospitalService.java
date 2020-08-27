package br.com.bruno.pcas.api.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.bruno.pcas.api.dominio.Hospital;
import br.com.bruno.pcas.api.dominio.Recurso;
import br.com.bruno.pcas.api.dominio.TransacoesHistorico;
import br.com.bruno.pcas.api.dominio.to.HospitalTO;
import br.com.bruno.pcas.api.dominio.to.TransacaoHistoricoTO;
import br.com.bruno.pcas.api.integracao.dao.IDaoGenerico;

@Named
public class HospitalService implements IHospitalService {
	
	@Inject
	private IDaoGenerico dao;

	@Override
	public Hospital incluir(Hospital hospital) throws ValidacaoException {
		hospital.setDataInclusao(new Date());
		
		return dao.incluir(hospital);
	}

	@Override
	public Hospital alterar(Hospital hospital) throws ValidacaoException {
		hospital.setDataAtualzacao(new Date());
		
		return dao.alterar(hospital);
	}

	@Override
	public List<Hospital> listar() throws ValidacaoException {
		return dao.listarTodos(Hospital.class);
	}

	@Override
	public Hospital obter(Long id) throws ValidacaoException {
		if (id == null) {
			throw new ValidacaoException("ID obrigatório para consulta!");
		}
		
		return dao.obter(Hospital.class, id);
	}

	/**
	 * Método para fazer troca de recursos entre hospitais.
	 * <p>Este método recebe uma solicitação com o hospital de origem onde os recursos solicitados serão retirados, 
	 * e um hospital de destino, onde os recursos ofertados serão alocados.</p>
	 * 
	 * @param transacao {@link TransacoesHistorico}
	 * @return TransacoesHistorico
	 * @exception ValidacaoException
	 */
	@Override
	public TransacoesHistorico trocarRecursos(TransacoesHistorico transacao) throws ValidacaoException {
		Hospital hospitalOrigem = dao.obter(Hospital.class, transacao.getHospitalOrigem().getId());
		Hospital hospitalDestino = dao.obter(Hospital.class, transacao.getHospitalDestino().getId());

		List<Integer> tiposSolicitados = new ArrayList<Integer>();
		Integer pontosSolicitados = 0;
		for (Recurso recurso : transacao.getRecursosSolicitados()) {
			pontosSolicitados += recurso.getCsTipoRecurso().getPontos();
			tiposSolicitados.add(recurso.getTipo());
		}

		List<Integer> tiposOfertados = new ArrayList<Integer>();
		Integer pontosOfertados = 0; 
		for (Recurso recurso : transacao.getRecursosOfertados()) {
			pontosOfertados += recurso.getCsTipoRecurso().getPontos();
			tiposOfertados.add(recurso.getTipo());
		}
		
//		Verificação de pontos de recursos ofertados vs recursos solicitados
//		Caso a soma dos pontos forem difentes, a oferta se torna inválida.
		if (pontosSolicitados != pontosOfertados) {
//			Caso o hospital solicitante estiver com o percentual de ocupação maior que 90%, o mesmo pode continuar com a solicitação.
			if (hospitalDestino.getPercentualOcupacao().doubleValue() <= 90) {
				throw new ValidacaoException("Sua oferta de troca está inválida!");
			}
		}
		
		List<Recurso> recursosSolicitados = dao.consultarPorNamedQuery("consultarRecursosPorTipoHospital", tiposSolicitados, hospitalOrigem.getId());
		if (recursosSolicitados.size() < tiposSolicitados.size()) {
			throw new ValidacaoException(String.format("O Hospital %s não possui os recursos solicitados!", hospitalOrigem.getNome()));
		}
		
		List<Recurso> recursosOfertados = dao.consultarPorNamedQuery("consultarRecursosPorTipoHospital", tiposOfertados, hospitalDestino.getId());
		if (recursosOfertados.size() < tiposOfertados.size()) {
			throw new ValidacaoException("Você não possui os recursos ofertados!");
		}

//		aloca os recursos solicitados ao hospital de destino
		for (Recurso recurso : recursosSolicitados) {
			recurso.setHospital(hospitalDestino);
		}
//		aloca os recursos ofertados ao hospital de origem		
		for (Recurso recurso : recursosOfertados) {
			recurso.setHospital(hospitalOrigem);
		}
//		gera o histórico da transação		
		transacao.setRecursosSolicitados(recursosSolicitados);
		transacao.setRecursosOfertados(recursosOfertados);
				
		return dao.incluir(transacao);
	}

	/**
	 * Método retorna um relatório de hospitais com percentual de ocupação maior que 90%
	 */
	@Override
	public List<HospitalTO> obterRelatorioHospitaisOcupacaoMaior90() {
		List<Object[]> rows = dao.consultarPorNativeQuery("SELECT nome, percentual_ocupacao FROM hospitais " + 
														  "WHERE percentual_ocupacao > 90 " + 
														  "ORDER BY percentual_ocupacao DESC ");
		List<HospitalTO> hospitais = new ArrayList<HospitalTO>();
		for (Object[] row : rows) {
			HospitalTO hospital = new HospitalTO();
			hospital.setNome((String) row[0]);
			hospital.setPercentualOcupacao((BigDecimal) row[1]);
			
			hospitais.add(hospital);
		}
		
		return hospitais;
	}

	/**
	 * Método retorna um relatório de hospitais com percentual de ocupação menor que 90%
	 */
	@Override
	public List<HospitalTO> obterRelatorioHospitaisOcupacaoMenor90() {
		List<Object[]> rows = dao.consultarPorNativeQuery("SELECT nome, percentual_ocupacao FROM hospitais " + 
														  "WHERE percentual_ocupacao <= 90 " + 
														  "ORDER BY percentual_ocupacao DESC ");
		List<HospitalTO> hospitais = new ArrayList<HospitalTO>();
		for (Object[] row : rows) {
			HospitalTO hospital = new HospitalTO();
			hospital.setNome((String) row[0]);
			hospital.setPercentualOcupacao((BigDecimal) row[1]);
			
			hospitais.add(hospital);
		}
		
		return hospitais;
	}

	/**
	 * Método retorna um relatório com a quantidade de recurusos por hospitais 
	 */
	@Override
	public List<HospitalTO> obterRelatorioQuantidadeRecursosPorHospitais() {
		List<Object[]> rows = dao.consultarPorNativeQuery("SELECT h.nome, " + 
														  "       COALESCE(medicos.total, 0) as medicos, " + 
														  "       COALESCE(enfermeiros.total, 0) as enfermeiros, " + 
														  "       COALESCE(respiradores.total, 0) as respiradores, " + 
														  "       COALESCE(tomografos.total, 0) as tomografos, " + 
														  "       COALESCE(ambulancias.total, 0) as ambulancias " + 
														  "FROM hospitais h " + 
														  "  LEFT JOIN (SELECT hospital_id, COUNT(*) AS total " + 
													 	  "	     FROM recursos WHERE tipo_id = 1 GROUP BY hospital_id) AS medicos " + 
														  "	ON medicos.hospital_id = h.id " + 
														  "  LEFT JOIN (SELECT hospital_id, COUNT(*) AS total " + 
														  "             FROM recursos WHERE tipo_id = 2 GROUP BY hospital_id) AS enfermeiros " + 
														  "	ON enfermeiros.hospital_id = h.id " + 
														  "  LEFT JOIN (SELECT hospital_id, COUNT(*) AS total " + 
														  "             FROM recursos WHERE tipo_id = 3 GROUP BY hospital_id) AS respiradores " + 
														  "	ON respiradores.hospital_id = h.id " + 
														  "  LEFT JOIN (SELECT hospital_id, COUNT(*) AS total " + 
														  "	     FROM recursos WHERE tipo_id = 4 GROUP BY hospital_id) AS tomografos " + 
														  "	ON tomografos.hospital_id = h.id " + 
														  "  LEFT JOIN (SELECT hospital_id, COUNT(*) AS total " + 
														  "	     FROM recursos WHERE tipo_id = 5 GROUP BY hospital_id) AS ambulancias " + 
														  "	ON ambulancias.hospital_id = h.id " + 
														 "ORDER BY h.nome ");
		List<HospitalTO> hospitais = new ArrayList<HospitalTO>();
		for (Object[] row : rows) {
			HospitalTO hospital = new HospitalTO();
			hospital.setNome((String) row[0]);
			hospital.setTotalMedicos(((Number) row[1]).longValue());
			hospital.setTotalEnfermeiros(((Number) row[2]).longValue());
			hospital.setTotalRespiradores(((Number) row[3]).longValue());
			hospital.setTotalTomografos(((Number) row[4]).longValue());
			hospital.setTotalAmbulancias(((Number) row[5]).longValue());
			
			hospitais.add(hospital);
		}
		
		return hospitais;
	}
	
	/**
	 * Método retorna um relatório de hospitais com ocupação maior que 90% a mais tempo 
	 */
	@Override
	public List<HospitalTO> obterRelatorioHospitaisOcupacaoMaior90Tempo() {
		List<Object[]> rows = dao.consultarPorNativeQuery("SELECT nome, DATE_PART('day', NOW() - data_atualizacao) AS dias " + 
														  "FROM hospitais " + 
														  "WHERE percentual_ocupacao > 90 " + 
														  "ORDER BY data_atualizacao");
		List<HospitalTO> hospitais = new ArrayList<HospitalTO>();
		for (Object[] row : rows) {
			HospitalTO hospital = new HospitalTO();
			hospital.setNome((String) row[0]);
			hospital.setDias(((Number) row[1]).intValue());
			
			hospitais.add(hospital);
		}
		
		return hospitais;
	}
	
	/**
	 * Método retorna um relatório de hospitais com ocupação menor que 90% a mais tempo 
	 */
	@Override
	public List<HospitalTO> obterRelatorioHospitaisOcupacaoMenor90Tempo() {
		List<Object[]> rows = dao.consultarPorNativeQuery("SELECT nome, DATE_PART('day', NOW() - data_atualizacao) AS dias " + 
														  "FROM hospitais " + 
														  "WHERE percentual_ocupacao < 90 " + 
														  "ORDER BY data_atualizacao");
		List<HospitalTO> hospitais = new ArrayList<HospitalTO>();
		for (Object[] row : rows) {
			HospitalTO hospital = new HospitalTO();
			hospital.setNome((String) row[0]);
			hospital.setDias(((Number) row[1]).intValue());
			
			hospitais.add(hospital);
		}
		
		return hospitais;
	}

	/**
	 * Método retorna um relatório do histórico de negociações
	 */
	@Override
	public List<TransacaoHistoricoTO> obterRelatorioHistoricoNegociacoes() {
		List<Object[]> rows = dao.consultarPorNativeQuery("SELECT ho.nome as hospital_origem, hd.nome as hospital_destino, rto.nome as recurso_ofertado, " + 
														  "	rts.nome as recurso_solicitado, th.data_inclusao " + 
														  "FROM transacoes_historico th " + 
														  "LEFT JOIN hospitais ho on ho.id = th.hospital_origem_id " + 
														  "LEFT JOIN hospitais hd on hd.id = th.hospital_destino_id " + 
														  "LEFT JOIN recursos_ofertados_transacoes rot on rot.transacao_id = th.id " + 
														  "LEFT JOIN recursos ro on ro.id = rot.recurso_id " + 
														  "LEFT JOIN recursos_tipos rto on rto.id = ro.tipo_id " + 
														  "LEFT JOIN recursos_solicitados_transacoes rst on rst.transacao_id = th.id " + 
														  "LEFT JOIN recursos rs on rs.id = rst.recurso_id " + 
														  "LEFT JOIN recursos_tipos rts on rts.id = rs.tipo_id " +
														  "ORDER BY th.data_inclusao DESC");
		List<TransacaoHistoricoTO> transacoes = new ArrayList<TransacaoHistoricoTO>();
		for (Object[] row : rows) {
			TransacaoHistoricoTO transacao = new TransacaoHistoricoTO();
			transacao.setHospitalOrigem((String) row[0]);
			transacao.setHospitalDestino((String) row[1]);
			transacao.setRecursoOfertado((String) row[2]);
			transacao.setRecursoSolicitado((String) row[3]);
			transacao.setDataInclusao((Date) row[4]);
			
			transacoes.add(transacao);
		}
		
		return transacoes;
	}
}
