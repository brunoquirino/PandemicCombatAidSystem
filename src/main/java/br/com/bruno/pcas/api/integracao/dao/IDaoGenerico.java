package br.com.bruno.pcas.api.integracao.dao;

import java.util.List;

public interface IDaoGenerico {

	<T> T incluir(T entidade);

	<T> T alterar(T entidade);

	<T> void excluir(T entidade);

	<T> List<T> listarTodos(Class<T> classe);
	
	<T> List<T> listarTodos(Class<?> classe, int firstResult, int maxResults);
	
	Long contarTodos(Class<?> classe);

	<T> T obter(Class<T> classe, Long id);

	<T> List<T> consultarPorNamedQuery(String namedQuery, Object... parametros);
	
	<T> List<T> consultarPorNamedQueryPaginado(String namedQuery, int firstResult, int maxResults, Object... parametros);

	<T> T buscarPorNamedQuery(String namedQuery, Object... parametros);

	<T> List<T> consultarPorQuery(String sql, Object... parametros);
	
	<T> T buscarPorNativeQuery(String sql, Object... parametros);
	
	<T> T buscarPorNativeQuery(String sql, RowMapperNativeQuery<T> mapper, Object... parametros);
	
	<T> T buscarPorQuery(String sql, Object... parametros);
	
	<T> List<T> consultarPorNativeQuery(String sql, RowMapperNativeQuery<T> mapper, Object... parametros);
	
	<T> List<T> consultarPorNativeQuery(String string, Object... parametros);
	
	<T> List<T> consultarPorNativeQueryPaginado(String string, int firstResult, int maxResults, Object... parametros);
	
	<T> List<T> consultarPorNativeQueryPaginado(String string, RowMapperNativeQuery<T> mapper, int firstResult, int maxResults, Object... parametros);

	void excluirPorQuery(String query, Object... parametros);
	
	<T> T obterReferencia(Class<T> classe, Number id);

	void alterarPorQuery(String query, Object... parametros);

	void alterarPorNativeQuery(String sql, Object... parametros);
	
}
