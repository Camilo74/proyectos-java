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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Entity> list(Class<?> clazz) {
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
	public Entity get(Class<?> clazz, Long id) {
		return (Entity) em.find(clazz,id);
	}

	@Override
	public boolean persist(Entity entity) {
        tx.begin();
        em.persist(entity);
		tx.commit();
		return true;
	}

	@Override
	public boolean merge(Entity entity) {
        tx.begin();
        em.merge(entity);
        tx.commit();
		return true;
	}

	@Override
	public boolean remove(Class<?> clazz, Long id) {
        tx.begin();
		em.remove(get(clazz,id));
		tx.commit();
		return true;
	}

}