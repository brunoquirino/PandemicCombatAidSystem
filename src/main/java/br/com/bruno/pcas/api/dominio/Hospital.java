package br.com.bruno.pcas.api.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.bruno.pcas.api.dominio.to.HospitalTO;
import br.com.bruno.pcas.api.dominio.to.RecursoTO;

@Entity
@Table(name = "hospitais")
public class Hospital implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "cnpj")
	private String cnpj;
	
	@Column(name = "endereco")
	private String endereco;
	
	@Column(name = "cidade")
	private String cidade;
	
	@Column(name = "uf")
	private String uf;
	
	@Column(name = "latitude")
	private String latitude;
	
	@Column(name = "longitude")
	private String longitude;
	
	@Column(name = "limite_ocupacao")
	private Integer limiteOcupacao; 
	
	@Column(name = "percentual_ocupacao")
	private BigDecimal percentualOcupacao;
	
	@Column(name = "data_atualizacao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAtualzacao;
	
	@Column(name = "data_inclusao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInclusao;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "hospital", cascade = CascadeType.ALL)
	private List<Recurso> recursos;
	
	public Hospital() {
		super();
	}

	public Hospital(HospitalTO hospitalTo) {
		this.cnpj = hospitalTo.getCnpj();
		this.nome = hospitalTo.getNome();
		this.endereco = hospitalTo.getEndereco();
		this.cidade = hospitalTo.getCidade();
		this.uf = hospitalTo.getUf();
		this.latitude = hospitalTo.getLatitude();
		this.longitude = hospitalTo.getLongitude();
		this.percentualOcupacao = hospitalTo.getPercentualOcupacao();
		this.limiteOcupacao = hospitalTo.getLimiteOcupacao();
		this.dataInclusao = new Date();
		
		this.addRecursos(hospitalTo.getRecursos());
	}

	public Hospital(Long id) {
		this();
		this.id = id;
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

	public List<Recurso> getRecursos() {
		return recursos;
	}

	public void setRecursos(List<Recurso> recursos) {
		this.recursos = recursos;
	}

	public BigDecimal getPercentualOcupacao() {
		return percentualOcupacao;
	}

	public void setPercentualOcupacao(BigDecimal percentualOcupacao) {
		this.percentualOcupacao = percentualOcupacao;
	}

	public Date getDataAtualzacao() {
		return dataAtualzacao;
	}

	public void setDataAtualzacao(Date dataAtualzacao) {
		this.dataAtualzacao = dataAtualzacao;
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
		Hospital other = (Hospital) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void addRecursos(List<RecursoTO> recursos) {
		this.recursos = new ArrayList<Recurso>();
		for (RecursoTO recursoTO : recursos) {
			Recurso recurso = new Recurso();
			recurso.setNome(recursoTO.getNome());
			recurso.setTipo(recursoTO.getTipo());
			recurso.setDataInclusao(new Date());
			recurso.setHospital(this);
			
			this.recursos.add(recurso);
		}	
	}
}
