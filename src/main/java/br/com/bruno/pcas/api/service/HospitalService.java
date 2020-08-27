package br.com.bruno.pcas.api.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.bruno.pcas.api.dominio.Hospital;
import br.com.bruno.pcas.api.dominio.Recurso;
import br.com.bruno.pcas.api.dominio.TransacoesHistorico;
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
	 * <p>Método para fazer troca de recursos entre hospitais.</p>
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

		for (Recurso recurso : recursosSolicitados) {
			recurso.setHospital(hospitalDestino);
		}
		
		List<Recurso> recursosOfertados = dao.consultarPorNamedQuery("consultarRecursosPorTipoHospital", tiposOfertados, hospitalDestino.getId());
		if (recursosOfertados.size() < tiposOfertados.size()) {
			throw new ValidacaoException("Você não possui os recursos ofertados!");
		}
		
		for (Recurso recurso : recursosOfertados) {
			recurso.setHospital(hospitalOrigem);
		}
				
		transacao.setRecursosSolicitados(recursosSolicitados);
		transacao.setRecursosOfertados(recursosOfertados);
				
		return dao.incluir(transacao);
	}

//	usar TO
//	Porcentagem de hospitais com ocupação maior que 90%.
//	Porcentagem de hospitais com ocupação menor que 90%.
//	Quantidade média de cada tipo de recurso por hospital (Ex: 2 tomógrafos por hospital).
//	Pontos perdidos devido a traidores.
//	Hospital em super-lotação (ocupação maior que 90%) a mais tempo.
//	Hospital em abaixo de super-lotação (ocupação maior que 90%) a mais tempo.
//	Histórico de negociação.
}
