package br.com.bruno.pcas.api.integracao.dao;

public final class Row {

	private final Object[] values;

	public Row(Object[] values) {
		this.values = values;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(int columnIndex) {
		if (this.values[columnIndex] == null) {
			return null;
		}
		return (T) this.values[columnIndex];
	}

	public Long getAsLong(int columnIndex) {
		Number value = get(columnIndex);
		if (value == null) {
			return null;
		}
		
		return value.longValue();
	}
	
	public Integer getAsInteger(int columnIndex) {
		Number value = get(columnIndex);
		if (value == null) {
			return null;
		}
		
		return value.intValue();
	}

}
