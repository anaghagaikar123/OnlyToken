package com.example.demo;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GenericDAOImpl<E, K extends Serializable> implements GenericDAO<E, K> {

	@PersistenceContext	
	protected EntityManager entityManager;
	
	protected Class<? extends E> dao;
	
	public GenericDAOImpl()
	{
		Type t=getClass().getGenericSuperclass();
		ParameterizedType pt=(ParameterizedType)t;
		dao=(Class)pt.getActualTypeArguments()[0];
	}
	

	@Override
	public void save(E entity) {
		
		System.out.println("in daoImpl..."+dao.getName()+entity.getClass());
		entityManager.persist(entity);
		
	}

	@Override
	public void saveOrUpdate(E entity) {

		entityManager.merge(entity);
	}

	@Override
	public E findOne(K key) {
		return entityManager.find(dao,key);
	}

	@Override
	public List<E> findAll() {
		String quesry="from"+dao.getName();
		return entityManager.createQuery(quesry).getResultList();
	}

	
}
