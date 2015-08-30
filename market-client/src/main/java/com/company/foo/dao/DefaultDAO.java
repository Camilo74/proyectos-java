package com.company.foo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.proxy.HibernateProxy;

import com.company.foo.model.Entity;

public class DefaultDAO implements DAO{

	private static EntityManager em;
	private static EntityTransaction tx;
	
	static{
		System.out.println("### paso una vez");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.hibernate.jpa");
        em = emf.createEntityManager();
        tx = em.getTransaction();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<?> list(Class<?> clazz) {
		List rt = new ArrayList();
		List rs = em.createQuery("SELECT o FROM "+clazz.getSimpleName()+" o").getResultList();
		for (Object proxy : rs) {
			Object obj = null;
			if (proxy instanceof HibernateProxy){
				System.out.println("########### entro por proxy");
				obj = ((HibernateProxy)proxy).getHibernateLazyInitializer().getImplementation();
			}else{
				obj = proxy;
			}
			rt.add(obj);
		}
		return rt;
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
        em.merge(entity);
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