package br.com.bruno.pcas.api.dominio;

public enum CsTipoRecurso {

	MEDICO(1, "Médico", 3),
	ENFERMEIRO(2, "Enfermeiro", 3),
	RESPIRADOR(3, "Respirador", 5),
	TOMOGRAFO(4, "Tomógrafo", 12),
	AMBULANCIA(5, "Ambulância", 10);
	
	private Integer codigo;
	
	private String nome;
	
	private Integer pontos;

	private CsTipoRecurso(Integer codigo, String nome, Integer pontos) {
		this.codigo = codigo;
		this.nome = nome;
		this.pontos = pontos;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getPontos() {
		return pontos;
	}

	public void setPontos(Integer pontos) {
		this.pontos = pontos;
	}
	
	public static CsTipoRecurso valueOf(Integer codigo) {
    	for (CsTipoRecurso cs : values()) {
			if(cs.getCodigo().equals(codigo)) {
				return cs;
			}
		}
    	
    	return null;
    }
}
