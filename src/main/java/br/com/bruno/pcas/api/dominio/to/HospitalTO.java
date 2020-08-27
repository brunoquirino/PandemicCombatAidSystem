package br.com.bruno.pcas.api.dominio.to;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.bruno.pcas.api.dominio.Hospital;
import br.com.bruno.pcas.api.dominio.Recurso;

public class HospitalTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String nome;
	
	private String cnpj;
	
	private String endereco;
	
	private String cidade;
	
	private String uf;
	
	private String latitude;
	
	private String longitude;
	
	private Integer limiteOcupacao; 
	
	private BigDecimal percentualOcupacao;
	
	private Date dataInclusao;
	
	private List<RecursoTO> recursos;
	
	public HospitalTO() {
		super();
		this.recursos = new ArrayList<RecursoTO>();
	}

	public HospitalTO(Hospital hospital) {
		this();
		this.setId(hospital.getId());
		this.setNome(hospital.getNome());
		this.setCnpj(hospital.getCnpj());
		this.setEndereco(hospital.getEndereco());
		this.setCidade(hospital.getCidade());
		this.setUf(hospital.getUf());
		this.setLatitude(hospital.getLatitude());
		this.setLongitude(hospital.getLongitude());
		this.setPercentualOcupacao(hospital.getPercentualOcupacao());
		this.setLimiteOcupacao(hospital.getLimiteOcupacao());
		
		this.addRecursos(hospital.getRecursos());
	}
	
	public void addRecursos(List<Recurso> recursos) {
		this.recursos = new ArrayList<RecursoTO>();
		for (Recurso recurso : recursos) {
			RecursoTO recursoTo = new RecursoTO(recurso);
			recursoTo.setId(recurso.getId());
			recursoTo.setNome(recurso.getNome());
			recursoTo.setTipo(recurso.getTipo());
			recursoTo.setHospitalID(this.getId());
			
			this.recursos.add(recursoTo);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Integer getLimiteOcupacao() {
		return limiteOcupacao;
	}

	public void setLimiteOcupacao(Integer limiteOcupacao) {
		this.limiteOcupacao = limiteOcupacao;
	}

	public List<RecursoTO> getRecursos() {
		return recursos;
	}

	public void setRecursos(List<RecursoTO> recursos) {
		this.recursos = recursos;
	}

	public BigDecimal getPercentualOcupacao() {
		return percentualOcupacao;
	}

	public void setPercentualOcupacao(BigDecimal percentualOcupacao) {
		this.percentualOcupacao = percentualOcupacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HospitalTO other = (HospitalTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
