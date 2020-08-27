package br.com.bruno.pcas.api.resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bruno.pcas.api.dominio.Hospital;
import br.com.bruno.pcas.api.dominio.TransacaoHistorico;
import br.com.bruno.pcas.api.dominio.to.HospitalTO;
import br.com.bruno.pcas.api.dominio.to.TransacaoHistoricoTO;
import br.com.bruno.pcas.api.service.IHospitalService;
import br.com.bruno.pcas.api.service.ValidacaoException;

@RestController
@RequestMapping(value = "hospitais", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class HospitalResource {

	@Inject
	private IHospitalService hospitalService;

	/**
	 * Método para listar todos os hospitais cadastrados e seus recursos
	 * 
	 * @return List<HospitalTO>
	 */
	@GetMapping
	public List<HospitalTO> consultar() {
		List<Hospital> lista = hospitalService.listar();
		List<HospitalTO> hospitais = new ArrayList<HospitalTO>();
		for (Hospital hospital : lista) {
			HospitalTO hospitalTo = new HospitalTO(hospital);
			
			hospitais.add(hospitalTo);
		}
		
		
		return hospitais;
	}

	/**
	 * Método para incluir hospitais e seus recursos
	 * 
	 * @param hospitalTo
	 * @return HospitalTO
	 */
	@Transactional
	@PostMapping
	public HospitalTO incluir(@RequestBody HospitalTO hospitalTo) {
		Hospital hospital = new Hospital(hospitalTo);
		
		return new HospitalTO(hospitalService.incluir(hospital));
	}

	/**
	 * Método para atualização de informações de um hospital
	 *  
	 * @param hospitalTo
	 * @return HospitalTO
	 * @see {@link HospitalTO}
	 */
	@Transactional
	@PutMapping
	public HospitalTO alterar(@RequestBody HospitalTO hospitalTo) {
		Hospital hospital = new Hospital(hospitalTo);
		hospital.setId(hospitalTo.getId());
		
		return new HospitalTO(hospitalService.alterar(hospital));
	}

	/**
	 * Método para obter um hospital por ID 
	 * 
	 * @param id
	 * @return HospitalTO
	 */
	@GetMapping
	@RequestMapping(value = "/{id}")
	public HospitalTO obter(@PathVariable("id") Long id) {
		Hospital hospital = hospitalService.obter(id);
		HospitalTO hospitalTO = new HospitalTO(hospital);
		
		return hospitalTO;
	}
	
	/**
	 * Método para obter percentual de ocupação por ID do hospital
	 * 
	 * @param id
	 * @return {@link BigDecimal}
	 */
	@GetMapping
	@RequestMapping(value = "/ocupacao/{id}")
	public BigDecimal obterPercentualOcupacaoPorHospitalID(@PathVariable("id") Long id) {
		Hospital hospital = hospitalService.obter(id);
		HospitalTO hospitalTO = new HospitalTO(hospital);
		
		return hospitalTO.getPercentualOcupacao();
	}
	
	/**
	 * Método para fazer trocas de recursos entre hospitais
	 * 
	 * @param transacao
	 * @return TransacoesHistorico
	 */
	@Transactional
	@PutMapping(value = "/trocar")
	public ResponseEntity<?> trocarRecursos(@RequestBody TransacaoHistoricoTO transacaoTo) {
		try {
			TransacaoHistorico transacao = new TransacaoHistorico(transacaoTo);
			
			hospitalService.trocarRecursos(transacao);
			
			return ResponseEntity.ok().build();
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getErros());
		}
	}
	
	/**
	 * @return List<HospitalTO>
	 */
	@GetMapping(value = "/relatorios/hospitaisocupacaomaior90")
	public List<HospitalTO> obterRelatorioHospitaisOcupacaoMaior90() {
		return hospitalService.obterRelatorioHospitaisOcupacaoMaior90();
	}
	
	/**
	 * @return List<HospitalTO>
	 */
	@GetMapping(value = "/relatorios/hospitaisocupacaomenor90")
	public List<HospitalTO> obterRelatorioHospitaisOcupacaoMenor90() {
		return hospitalService.obterRelatorioHospitaisOcupacaoMenor90();
	}

	/**
	 * @return List<HospitalTO>
	 */
	@GetMapping(value = "/relatorios/quantidaderecursoshospitais")
	public List<HospitalTO> obterRelatorioQuantidadeRecursosPorHospitais() {
		return hospitalService.obterRelatorioQuantidadeRecursosPorHospitais();
	}

	/**
	 * @return List<HospitalTO>
	 */
	@GetMapping(value = "/relatorios/hospitaisocupacaomaior90tempo")
	public List<HospitalTO> obterRelatorioHospitaisOcupacaoMaior90Tempo() {
		return hospitalService.obterRelatorioHospitaisOcupacaoMaior90Tempo();
	}

	/**
	 * @return List<HospitalTO>
	 */
	@GetMapping(value = "/relatorios/hospitaisocupacaomenor90tempo")
	public List<HospitalTO> obterRelatorioHospitaisOcupacaoMenor90Tempo() {
		return hospitalService.obterRelatorioHospitaisOcupacaoMenor90Tempo();
	}

	/**
	 * Serviço para relatório de historicos de transações
	 * @return List<HospitalTO>
	 */
	@GetMapping(value = "/relatorios/historicotransacoes")
	public List<TransacaoHistoricoTO> obterRelatorioHistoricoNegociacoes() {
		return hospitalService.obterRelatorioHistoricoNegociacoes();
	}
}
