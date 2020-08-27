package br.com.bruno.pcas.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.com.bruno.pcas.api.dominio.Hospital;
import br.com.bruno.pcas.api.dominio.Recurso;
import br.com.bruno.pcas.api.dominio.TransacaoHistorico;
import br.com.bruno.pcas.api.dominio.to.HospitalTO;
import br.com.bruno.pcas.api.dominio.to.RecursoTO;
import br.com.bruno.pcas.api.dominio.to.TransacaoHistoricoTO;

@SpringBootTest
@AutoConfigureMockMvc
class HospitalServiceTest {
	
	@Autowired
	HospitalService hospitalSerivceMock;

	@Test
	@Transactional
	void testIncluir() throws Exception {
		assertThat(hospitalSerivceMock).isNotNull();
		Hospital hospital = new Hospital();
		hospital.setNome("H1");
		hospital.setPercentualOcupacao(BigDecimal.valueOf(90));
		
		Hospital registro = hospitalSerivceMock.incluir(hospital);
		assertThat(registro).isEqualTo(hospital);
		assertEquals(registro.getNome(), hospital.getNome());
		assertThat(registro.getDataInclusao()).isNotNull();
	}

	@Test
	@Transactional
	void testAlterar() throws Exception {
		assertThat(hospitalSerivceMock).isNotNull();
		Hospital hospital = new Hospital();
		hospital.setId(1l);
		hospital.setPercentualOcupacao(BigDecimal.valueOf(90));
		
		Hospital registro = hospitalSerivceMock.alterar(hospital);
		assertThat(registro).isEqualTo(hospital);
		assertEquals(registro.getPercentualOcupacao(), hospital.getPercentualOcupacao());
	}

	@Test
	void testListar() throws Exception {
		assertThat(hospitalSerivceMock).isNotNull();
		
		List<Hospital> registros = hospitalSerivceMock.listar();
		assertNotEquals(registros, new ArrayList<Hospital>());
		
		assertThat(registros.isEmpty()).isEqualTo(false);
	}

	@Test
	void testObter() throws Exception {
		assertThat(hospitalSerivceMock).isNotNull();
		
		Hospital hospital = hospitalSerivceMock.obter(1l);
		assertThat(hospital.getId()).isEqualTo(1l);
	}

	@Test
	@Transactional
	void testTrocarRecursos() throws Exception {
		assertThat(hospitalSerivceMock).isNotNull();		

		TransacaoHistorico transacao = new TransacaoHistorico();
		transacao.setHospitalOrigem(new Hospital(1l));
		transacao.setHospitalDestino(new Hospital(2l));
		List<RecursoTO> recursosOfertados = new ArrayList<RecursoTO>();
		RecursoTO recursoOfertado = new RecursoTO();
		recursoOfertado.setTipo(1);
		recursosOfertados.add(recursoOfertado);
		transacao.addRecursosOfertados(recursosOfertados);
		List<RecursoTO> recursosSolicitados = new ArrayList<RecursoTO>();
		RecursoTO recursoSolicitado = new RecursoTO();
		recursoSolicitado.setTipo(2);
		recursosSolicitados.add(recursoSolicitado);
		transacao.addRecursosSolicitados(recursosSolicitados);

		hospitalSerivceMock.trocarRecursos(transacao);
	}
	
	@Test
	@Transactional
	void testTrocarRecursosInvalidos() throws Exception {
		assertThat(hospitalSerivceMock).isNotNull();
		
		TransacaoHistorico transacao = new TransacaoHistorico();
		transacao.setHospitalOrigem(new Hospital(1l));
		transacao.setHospitalDestino(new Hospital(2l));
		List<RecursoTO> recursosOfertados = new ArrayList<RecursoTO>();
		RecursoTO recursoOfertado = new RecursoTO();
		recursoOfertado.setTipo(1);
		recursosOfertados.add(recursoOfertado);
		transacao.addRecursosOfertados(recursosOfertados);
		List<RecursoTO> recursosSolicitados = new ArrayList<RecursoTO>();
		RecursoTO recursoSolicitado = new RecursoTO();
		recursoSolicitado.setTipo(5);
		recursosSolicitados.add(recursoSolicitado);
		transacao.addRecursosSolicitados(recursosSolicitados);
		
		try {
			hospitalSerivceMock.trocarRecursos(transacao);
		} catch (ValidacaoException e) {
			assertThat(e.getMessage()).isEqualTo("Sua oferta de troca está inválida!");
		}
	}
	
	@Test
	@Transactional
	void testTrocarSemRecursos() throws Exception {
		assertThat(hospitalSerivceMock).isNotNull();
		
		TransacaoHistorico transacao = new TransacaoHistorico();
		Hospital hospitalDestino = new Hospital(2l);
		hospitalDestino.setNome("H2");
		transacao.setHospitalDestino(hospitalDestino);
		Hospital hospitalOrigem = new Hospital(1l);
		hospitalOrigem.setNome("H1");
		transacao.setHospitalOrigem(hospitalOrigem);
		List<Recurso> recursosOfertados = new ArrayList<Recurso>();
		Recurso recursoOfertado = new Recurso();
		recursoOfertado.setTipo(2);
		recursosOfertados.add(recursoOfertado);
		transacao.setRecursosOfertados(recursosOfertados);
		List<Recurso> recursosSolicitados = new ArrayList<Recurso>();
		Recurso recursoSolicitado = new Recurso();
		recursoSolicitado.setTipo(1);
		recursosSolicitados.add(recursoSolicitado);
		transacao.setRecursosSolicitados(recursosSolicitados);
		
		try {
			hospitalSerivceMock.trocarRecursos(transacao);
		} catch (ValidacaoException e) {
			assertThat(e.getMessage()).isEqualTo("O Hospital null não possui os recursos solicitados!");
		}
	}

	@Test
	void testObterRelatorioHospitaisOcupacaoMaior90() {
		assertThat(hospitalSerivceMock).isNotNull();
		
		List<HospitalTO> registros = hospitalSerivceMock.obterRelatorioHospitaisOcupacaoMaior90();
		
		assertNotEquals(registros, new ArrayList<HospitalTO>());
		assertThat(registros.isEmpty()).isEqualTo(false);
	}

	@Test
	void testObterRelatorioHospitaisOcupacaoMenor90() {
		assertThat(hospitalSerivceMock).isNotNull();
		
		List<HospitalTO> registros = hospitalSerivceMock.obterRelatorioHospitaisOcupacaoMenor90();
		
		assertNotEquals(registros, new ArrayList<HospitalTO>());
		assertThat(registros.isEmpty()).isEqualTo(false);
	}

	@Test
	void testObterRelatorioQuantidadeRecursosPorHospitais() {
		assertThat(hospitalSerivceMock).isNotNull();
		
		List<HospitalTO> registros = hospitalSerivceMock.obterRelatorioQuantidadeRecursosPorHospitais();
		
		assertNotEquals(registros, new ArrayList<HospitalTO>());
		assertThat(registros.isEmpty()).isEqualTo(false);
	}

	@Test
	void testObterRelatorioHospitaisOcupacaoMaior90Tempo() {
		assertThat(hospitalSerivceMock).isNotNull();
		
		List<HospitalTO> registros = hospitalSerivceMock.obterRelatorioHospitaisOcupacaoMaior90Tempo();
		
		assertNotEquals(registros, new ArrayList<HospitalTO>());
		assertThat(registros.isEmpty()).isEqualTo(false);
	}

	@Test
	void testObterRelatorioHospitaisOcupacaoMenor90Tempo() {
		assertThat(hospitalSerivceMock).isNotNull();
		
		List<HospitalTO> registros = hospitalSerivceMock.obterRelatorioHospitaisOcupacaoMenor90Tempo();
		
		assertNotEquals(registros, new ArrayList<HospitalTO>());
		assertThat(registros.isEmpty()).isEqualTo(false);
	}

	@Test
	void testObterRelatorioHistoricoNegociacoes() {
		assertThat(hospitalSerivceMock).isNotNull();
		
		List<TransacaoHistoricoTO> registros = hospitalSerivceMock.obterRelatorioHistoricoNegociacoes();
		
		assertNotEquals(registros, new ArrayList<TransacaoHistoricoTO>());
		assertThat(registros.isEmpty()).isEqualTo(false);
	}

}
