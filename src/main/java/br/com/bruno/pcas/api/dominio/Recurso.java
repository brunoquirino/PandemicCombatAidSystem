package br.com.bruno.pcas.api.dominio;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "recursos")
@NamedQueries({
	@NamedQuery(name = "consultarRecursosPorTipoHospital",
				query = "SELECT recurso FROM Recurso recurso" +
						" WHERE recurso.hospital.id = ?2" +
						"   AND tipo IN(?1)")
})
public class Recurso implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "tipo_id")
	private Integer tipo;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name="hospital_id")
	private Hospital hospital;
	
	@Column(name = "data_inclusao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInclusao;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(name = "recursos_solicitados_transacoes",
            joinColumns = {
        @JoinColumn(name = "recurso_id")},
            inverseJoinColumns = {
        @JoinColumn(name = "transacao_id")})
	private List<TransacoesHistorico> recursosSolicitados;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(name = "recursos_ofertados_transacoes",
            joinColumns = {
        @JoinColumn(name = "recurso_id")},
            inverseJoinColumns = {
        @JoinColumn(name = "transacao_id")})
	private List<TransacoesHistorico> recursosOfertados;

	public Recurso() {
		super();
	}
	
	public CsTipoRecurso getCsTipoRecurso() {
		return CsTipoRecurso.valueOf(this.tipo);
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

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public List<TransacoesHistorico> getRecursosSolicitados() {
		return recursosSolicitados;
	}

	public void setRecursosSolicitados(List<TransacoesHistorico> recursosSolicitados) {
		this.recursosSolicitados = recursosSolicitados;
	}

	public List<TransacoesHistorico> getRecursosOfertados() {
		return recursosOfertados;
	}

	public void setRecursosOfertados(List<TransacoesHistorico> recursosOfertados) {
		this.recursosOfertados = recursosOfertados;
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
		Recurso other = (Recurso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
