package br.com.bruno.pcas.api.integracao.dao;

public interface RowMapperNativeQuery<T> {
	
	T toMapper(Row row);

}
