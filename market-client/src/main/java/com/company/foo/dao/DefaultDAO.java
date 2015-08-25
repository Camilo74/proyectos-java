package com.company.foo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.company.foo.model.Entity;

public class DefaultDAO implements DAO{

	private static EntityManager em;
	private static EntityTransaction tx;
	
	static{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.hibernate.jpa");
        em = emf.createEntityManager();
        tx = em.getTransaction();
	}
	
	@Override
	public List<?> list(Class<?> clazz) {
		return em.createQuery("SELECT o FROM "+clazz.getSimpleName()+" o").getResultList();
	}

	@Override
	public Object get(Class<?> clazz, Long id) {
		return em.find(clazz,id);
	}

	@Override
	public Boolean remove(Class<?> clazz, Long id) {
        tx.begin();
		em.remove(get(clazz,id));
		tx.commit();
		return true;
	}
	
	@Deprecated
	@Override
	public void add(Entity entity){
        tx.begin();
        em.persist(entity);
        tx.commit();
	}

	@Override
	public boolean save(Entity entity) {
        tx.begin();
        em.persist(entity);
		tx.commit();
		return true;
	}

	@Override
	public boolean update(Entity entity) {
        tx.begin();
        em.merge(entity);
        tx.commit();
		return false;
	}

}