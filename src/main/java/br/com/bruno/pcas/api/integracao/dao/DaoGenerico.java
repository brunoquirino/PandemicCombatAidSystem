package br.com.bruno.pcas.api.integracao.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

@Named
public class DaoGenerico implements IDaoGenerico {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public <T> T incluir(T entidade) {
		entityManager.persist(entidade);
		return entidade;
	}
	
	@Override
	public Long contarTodos(Class<?> classe) {
		Query query = entityManager.createQuery("SELECT COUNT(e) FROM " + classe.getSimpleName() + " e");
		return ((Long) query.getSingleResult());
	}

	@Override
	public <T> T alterar(T entidade) {
		return entityManager.merge(entidade);
	}
	
	@Override
	public <T> T obterReferencia(Class<T> classe, Number id) {
		return entityManager.getReference(classe, id);
	}
	
	@Override
	public <T> void excluir(T entidade) {
		Object entidadeAtualizada = entityManager.merge(entidade);
		entityManager.remove(entidadeAtualizada);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> listarTodos(Class<T> classe) {
		Query query = entityManager.createQuery("SELECT e FROM " + classe.getSimpleName() + " e");
		return (List<T>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> listarTodos(Class<?> classe, int firstResult, int maxResults) {
		Query query = entityManager.createQuery("SELECT e FROM " + classe.getSimpleName() + " e");
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return (List<T>) query.getResultList();
	}

	@Override
	public <T> T obter(Class<T> classe, Long id) {
		return entityManager.find(classe, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> consultarPorNamedQuery(String namedQuery, Object... parametros) {
		Query query = this.entityManager.createNamedQuery(namedQuery);
		configurarParametros(query, parametros);
		return (List<T>) query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> consultarPorNamedQueryPaginado(String namedQuery, int firstResult, int maxResults, Object... parametros) {
		Query query = this.entityManager.createNamedQuery(namedQuery);
		configurarParametros(query, parametros);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return (List<T>) query.getResultList();
	}
	
	@Override
	public <T> List<T> consultarPorNativeQuery(String sql, RowMapperNativeQuery<T> mapper, Object... parametros) {
		Query query = this.entityManager.createNativeQuery(sql);
		configurarParametros(query, parametros);
		return mapearResultado(mapper, query);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> consultarPorNativeQuery(String sql, Object... parametros) {
		Query query = this.entityManager.createNativeQuery(sql);
		configurarParametros(query, parametros);
		return (List<T>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> consultarPorNativeQueryPaginado(String sql, int firstResult, int maxResults, Object... parametros) {
		Query query = this.entityManager.createNativeQuery(sql);
		configurarParametros(query, parametros);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);		
		return (List<T>) query.getResultList();
	}
	
	@Override
	public <T> List<T> consultarPorNativeQueryPaginado(String sql, RowMapperNativeQuery<T> mapper, int firstResult, int maxResults, Object... parametros) {
		Query query = this.entityManager.createNativeQuery(sql);
		configurarParametros(query, parametros);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);		
		return mapearResultado(mapper, query);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T buscarPorNamedQuery(String namedQuery, Object... parametros) {
		Query query = this.entityManager.createNamedQuery(namedQuery);
		configurarParametros(query, parametros);
		try {
			return (T) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> consultarPorQuery(String sql, Object... parametros) {
		Query query = this.entityManager.createQuery(sql);
		configurarParametros(query, parametros);
		return (List<T>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public <T> T buscarPorQuery(String sql, Object... parametros) {
		Query query = this.entityManager.createQuery(sql);
		configurarParametros(query, parametros);
		try {
			return (T) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T buscarPorNativeQuery(String sql, Object... parametros) {
		Query query = this.entityManager.createNativeQuery(sql);
		configurarParametros(query, parametros);
		try {
			return (T) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T buscarPorNativeQuery(String sql, RowMapperNativeQuery<T> mapper, Object... parametros) {
		Query query = this.entityManager.createNativeQuery(sql);
		configurarParametros(query, parametros);
		try {
			T singleResult = (T) query.getSingleResult();
			if (singleResult == null) {
				return null;
			}
			
			if (!singleResult.getClass().isArray()) {
				return singleResult;
			}
			
			return mapper.toMapper(new Row((Object[])singleResult));
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public void excluirPorQuery(String sql, Object... parametros) {
		Query query = this.entityManager.createQuery(sql);
		configurarParametros(query, parametros);
		query.executeUpdate();
	}

	@Override
	public void alterarPorQuery(String sql, Object... parametros) {
		Query query = this.entityManager.createQuery(sql);
		for (int i = 0; i < parametros.length; i++) {
			Object param = parametros[i];
			query.setParameter(i + 1, param);
		}
		query.executeUpdate();
	}
	
	@Override
	public void alterarPorNativeQuery(String sql, Object... parametros) {
		Query query = this.entityManager.createNativeQuery(sql);
		for (int i = 0; i < parametros.length; i++) {
			Object param = parametros[i];
			query.setParameter(i + 1, param);
		}
		query.executeUpdate();
	}
	
	private void configurarParametros(Query query, Object... parametros) {
		for (int i = 0; i < parametros.length; i++) {
			Object param = parametros[i];
			if (param instanceof java.util.Date) {
				query.setParameter(i + 1, (java.util.Date)param, TemporalType.DATE);
			} else {
				query.setParameter(i + 1, param);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private <T> List<T> mapearResultado(RowMapperNativeQuery<T> mapper, Query query) {
		List<T> result = new ArrayList<T>();
		
		for (Object[] values : (List<Object[]>)query.getResultList()) {
			T value = mapper.toMapper(new Row(values));
			
			if (value != null) {
				result.add(value);
			}
		}
		
		return result;
	}

}