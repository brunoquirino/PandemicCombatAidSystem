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

import br.com.bruno.pcas.api.dominio.ConsultaHospital;
import br.com.bruno.pcas.api.dominio.Hospital;
import br.com.bruno.pcas.api.service.IHospitalService;

@RestController
@RequestMapping(value = "hospitais", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class HospitalResource {

	@Inject
	IHospitalService hospitalService;

	@GetMapping
	public List<Hospital> consultar(ConsultaHospital consulta) {
		return hospitalService.listar(consulta);
	}

	@PostMapping
	public Hospital incluir(Hospital hospital) {
		return hospitalService.incluir(hospital);
	}

	@PutMapping
	public Hospital alterar(Hospital hospital) {
		return hospitalService.alterar(hospital);
	}

	@DeleteMapping
	public void excluir(Hospital hospital) {
		hospitalService.excluir(hospital);
	}
	
	@GetMapping
	@RequestMapping(value = "/{id}")
	public Hospital obter(@PathVariable("id") Long id) {
		return hospitalService.obter(id);
	}
}
