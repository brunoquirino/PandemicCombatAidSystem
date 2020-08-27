package br.com.bruno.pcas.api.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import br.com.bruno.pcas.api.dominio.to.HospitalTO;
import br.com.bruno.pcas.api.dominio.to.RecursoTO;
import br.com.bruno.pcas.api.dominio.to.TransacaoHistoricoTO;

@SpringBootTest
@AutoConfigureMockMvc
class HospitalResourceTest {

	@Autowired
	private HospitalResource resource;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testConsultar() throws Exception {
		assertThat(resource).isNotNull();

		this.mockMvc.perform(get("/hospitais").contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	void testIncluir() throws Exception {
		assertThat(resource).isNotNull();
		HospitalTO hospital = new HospitalTO();
		hospital.setNome("H1");
		hospital.setPercentualOcupacao(BigDecimal.valueOf(90));

		this.mockMvc
				.perform(
						post("/hospitais").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(hospital)))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	void testAlterar() throws Exception {
		assertThat(resource).isNotNull();
		HospitalTO hospital = new HospitalTO();
		hospital.setId(1l);
		hospital.setPercentualOcupacao(BigDecimal.valueOf(92));

		this.mockMvc
				.perform(put("/hospitais").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(hospital)))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	void testObter() throws Exception {
		assertThat(resource).isNotNull();

		this.mockMvc.perform(get("/hospitais/{id}", 1).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());

		HospitalTO hospitalTO = resource.obter(1l);
		assertThat(hospitalTO.getId()).isEqualTo(1l);
	}

	@Test
	void testObterPercentualOcupacaoPorHospitalID() throws Exception {
		assertThat(resource).isNotNull();

		this.mockMvc.perform(get("/hospitais/ocupacao/{id}", 1).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());

		BigDecimal percentual = resource.obterPercentualOcupacaoPorHospitalID(1l);
		assertThat(percentual).isEqualTo(BigDecimal.valueOf(92));
	}

	@Test
	void testTrocarRecursos() throws Exception {
		assertThat(resource).isNotNull();
		TransacaoHistoricoTO transacao = new TransacaoHistoricoTO();
		transacao.setHospitalOrigemID(1l);
		transacao.setHospitalDestinoID(2l);
		RecursoTO recursoOfertado = new RecursoTO();
		recursoOfertado.setTipo(1);
		transacao.addRecursosOfertados(recursoOfertado);
		RecursoTO recursoSolicitado = new RecursoTO();
		recursoSolicitado.setTipo(2);
		transacao.addRecursosSolicitados(recursoSolicitado);

		this.mockMvc.perform(
				put("/hospitais/trocar").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(transacao)))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	void testTrocarRecursosInvalidos() throws Exception {
		assertThat(resource).isNotNull();
		TransacaoHistoricoTO transacao = new TransacaoHistoricoTO();
		transacao.setHospitalOrigemID(1l);
		transacao.setHospitalDestinoID(2l);
		RecursoTO recursoOfertado = new RecursoTO();
		recursoOfertado.setTipo(1);
		transacao.addRecursosOfertados(recursoOfertado);
		RecursoTO recursoSolicitado = new RecursoTO();
		recursoSolicitado.setTipo(5);
		transacao.addRecursosSolicitados(recursoSolicitado);

		this.mockMvc
				.perform(put("/hospitais/trocar").content(new Gson().toJson(transacao))
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print()).andExpect(status().is(400))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(header().string("content-type", MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(new Gson().toJson(new String[]{"Sua oferta de troca está inválida!"})));
	}

	@Test
	void testObterRelatorioHospitaisOcupacaoMaior90() throws Exception {
		assertThat(resource).isNotNull();

		this.mockMvc
				.perform(get("/hospitais/relatorios/hospitaisocupacaomaior90").contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	void testObterRelatorioHospitaisOcupacaoMenor90() throws Exception {
		assertThat(resource).isNotNull();

		this.mockMvc
				.perform(get("/hospitais/relatorios/hospitaisocupacaomenor90").contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	void testObterRelatorioQuantidadeRecursosPorHospitais() throws Exception {
		assertThat(resource).isNotNull();

		this.mockMvc.perform(
				get("/hospitais/relatorios/quantidaderecursoshospitais").contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	void testObterRelatorioHospitaisOcupacaoMaior90Tempo() throws Exception {
		assertThat(resource).isNotNull();

		this.mockMvc.perform(
				get("/hospitais/relatorios/hospitaisocupacaomaior90tempo").contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	void testObterRelatorioHospitaisOcupacaoMenor90Tempo() throws Exception {
		assertThat(resource).isNotNull();

		this.mockMvc.perform(
				get("/hospitais/relatorios/hospitaisocupacaomenor90tempo").contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	void testObterRelatorioHistoricoNegociacoes() throws Exception {
		assertThat(resource).isNotNull();

		this.mockMvc.perform(get("/hospitais/relatorios/historicotransacoes").contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
	}

}
