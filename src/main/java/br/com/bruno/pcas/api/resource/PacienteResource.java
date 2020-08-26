package br.com.bruno.pcas.api.resource;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bruno.pcas.api.dominio.Paciente;
import br.com.bruno.pcas.api.service.IPacienteService;

@RestController
@RequestMapping(value = "pacientes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PacienteResource {

	@Inject
	IPacienteService pacienteService;

	@GetMapping
	public List<Paciente> consultar() {
		return pacienteService.listar();
	}

	@PostMapping
	public Paciente incluir(Paciente paciente) {
		return pacienteService.incluir(paciente);
	}

	@PutMapping
	public Paciente alterar(Paciente paciente) {
		return pacienteService.alterar(paciente);
	}

	@DeleteMapping
	public void excluir(Paciente paciente) {
		pacienteService.excluir(paciente);
	}
	
	@GetMapping
	@RequestMapping(value = "/{id}")
	public Paciente obter(@PathVariable("id") Long id) {
		return pacienteService.obter(id);
	}
}
