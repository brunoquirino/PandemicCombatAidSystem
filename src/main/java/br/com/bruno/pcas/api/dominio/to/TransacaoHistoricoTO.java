package br.com.bruno.pcas.api.dominio.to;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.bruno.pcas.api.dominio.TransacoesHistorico;

public class TransacaoHistoricoTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long hospitalOrigemID;
	
	private Long hospitalDestinoID;
	
	private List<RecursoTO> recursosOfertados;
	
	private List<RecursoTO> recursosSolicitados;
	
	private Date dataInclusao;
	
	private String hospitalOrigem;
	
	private String hospitalDestino;
	
	private String recursoOfertado;
	
	private String recursoSolicitado;
	
	
	public TransacaoHistoricoTO() {
		super();
	}
	
	public TransacaoHistoricoTO(TransacoesHistorico transacao) {
		this();
		this.id = transacao.getId();
		this.hospitalOrigemID = transacao.getHospitalOrigem().getId();
		this.hospitalDestinoID = transacao.getHospitalOrigem().getId();
		this.dataInclusao = transacao.getDataInclusao();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getHospitalOrigemID() {
		return hospitalOrigemID;
	}

	public void setHospitalOrigemID(Long hospitalOrigemID) {
		this.hospitalOrigemID = hospitalOrigemID;
	}

	public Long getHospitalDestinoID() {
		return hospitalDestinoID;
	}

	public void setHospitalDestinoID(Long hospitalDestinoID) {
		this.hospitalDestinoID = hospitalDestinoID;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public List<RecursoTO> getRecursosOfertados() {
		return recursosOfertados;
	}

	public void setRecursosOfertados(List<RecursoTO> recursosOfertados) {
		this.recursosOfertados = recursosOfertados;
	}

	public List<RecursoTO> getRecursosSolicitados() {
		return recursosSolicitados;
	}

	public void setRecursosSolicitados(List<RecursoTO> recursosSolicitados) {
		this.recursosSolicitados = recursosSolicitados;
	}

	public String getHospitalOrigem() {
		return hospitalOrigem;
	}

	public void setHospitalOrigem(String hospitalOrigem) {
		this.hospitalOrigem = hospitalOrigem;
	}

	public String getHospitalDestino() {
		return hospitalDestino;
	}

	public void setHospitalDestino(String hospitalDestino) {
		this.hospitalDestino = hospitalDestino;
	}

	public String getRecursoOfertado() {
		return recursoOfertado;
	}

	public void setRecursoOfertado(String recursoOfertado) {
		this.recursoOfertado = recursoOfertado;
	}

	public String getRecursoSolicitado() {
		return recursoSolicitado;
	}

	public void setRecursoSolicitado(String recursoSolicitado) {
		this.recursoSolicitado = recursoSolicitado;
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
		TransacaoHistoricoTO other = (TransacaoHistoricoTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
