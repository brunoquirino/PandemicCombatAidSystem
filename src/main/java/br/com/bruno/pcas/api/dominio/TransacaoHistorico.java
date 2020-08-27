package br.com.bruno.pcas.api.dominio;

import java.io.Serializable;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.bruno.pcas.api.dominio.to.RecursoTO;
import br.com.bruno.pcas.api.dominio.to.TransacaoHistoricoTO;

@Entity
@Table(name = "transacoes_historico")
public class TransacaoHistorico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name="hospital_origem_id")
	private Hospital hospitalOrigem;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name="hospital_destino_id")
	private Hospital hospitalDestino;
	  
	@Column(name = "data_inclusao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInclusao;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(name = "recursos_solicitados_transacoes",
            joinColumns = {
        @JoinColumn(name = "transacao_id")},
            inverseJoinColumns = {
        @JoinColumn(name = "recurso_id")})
	private List<Recurso> recursosSolicitados;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(name = "recursos_ofertados_transacoes",
            joinColumns = {
        @JoinColumn(name = "transacao_id")},
            inverseJoinColumns = {
        @JoinColumn(name = "recurso_id")})
	private List<Recurso> recursosOfertados;
	
	public TransacaoHistorico() {
		super();
	}

	public TransacaoHistorico(TransacaoHistoricoTO transacaoTo) {
		this.id = transacaoTo.getId();
		this.hospitalOrigem = new Hospital(transacaoTo.getHospitalOrigemID());
		this.hospitalDestino = new Hospital(transacaoTo.getHospitalDestinoID());
		this.dataInclusao = new Date();
		
		this.addRecursosSolicitados(transacaoTo.getRecursosSolicitados());
		this.addRecursosOfertados(transacaoTo.getRecursosOfertados());
	}
	
	public void addRecursosSolicitados(List<RecursoTO> recursos) {
		this.recursosSolicitados = new ArrayList<Recurso>();
		for (RecursoTO recursoTO : recursos) {
			Recurso recurso = new Recurso();
			recurso.setNome(recursoTO.getNome());
			recurso.setTipo(recursoTO.getTipo());
			recurso.setDataInclusao(new Date());
			
			this.recursosSolicitados.add(recurso);
		}
	}
	
	public void addRecursosOfertados(List<RecursoTO> recursos) {
		this.recursosOfertados = new ArrayList<Recurso>();
		for (RecursoTO recursoTO : recursos) {
			Recurso recurso = new Recurso();
			recurso.setNome(recursoTO.getNome());
			recurso.setTipo(recursoTO.getTipo());
			recurso.setDataInclusao(new Date());
			
			this.recursosOfertados.add(recurso);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Hospital getHospitalOrigem() {
		return hospitalOrigem;
	}

	public void setHospitalOrigem(Hospital hospitalOrigem) {
		this.hospitalOrigem = hospitalOrigem;
	}

	public Hospital getHospitalDestino() {
		return hospitalDestino;
	}

	public void setHospitalDestino(Hospital hospitalDestino) {
		this.hospitalDestino = hospitalDestino;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public List<Recurso> getRecursosSolicitados() {
		return recursosSolicitados;
	}

	public void setRecursosSolicitados(List<Recurso> recursosSolicitados) {
		this.recursosSolicitados = recursosSolicitados;
	}

	public List<Recurso> getRecursosOfertados() {
		return recursosOfertados;
	}

	public void setRecursosOfertados(List<Recurso> recursosOfertados) {
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
		TransacaoHistorico other = (TransacaoHistorico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
