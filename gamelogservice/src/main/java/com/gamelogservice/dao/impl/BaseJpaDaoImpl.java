package com.gamelogservice.dao.impl;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.gamelogservice.dao.IBaseJpaRepository;

public class BaseJpaDaoImpl<T,ID extends Serializable> extends SimpleJpaRepository<T,ID> implements IBaseJpaRepository<T,ID>{
	private final EntityManager entityManager;
	public BaseJpaDaoImpl(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
		this.entityManager=em;
	}

	@SuppressWarnings("unchecked")
	@Override
	//@Query("select u from User u where u.userName= :userName and u.email= :email")
	public T getOneBySql(Class<T> clazz,String sql, Map<String, Object> params)
			throws Exception {
		 return (T) entityManager.createNativeQuery(sql).getSingleResult();
	}


}
