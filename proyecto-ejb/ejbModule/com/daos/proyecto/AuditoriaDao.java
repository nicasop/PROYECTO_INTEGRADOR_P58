package com.daos.proyecto;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.entities.proyecto.Auditoria;
import com.entities.proyecto.Usuario;

@Stateless
public class AuditoriaDao {

	@PersistenceContext
	private EntityManager em;
	
	public Auditoria actulizar(Auditoria auditoria) {
		return em.merge(auditoria);
	}
	
	public List<Auditoria> auditoria(){
		TypedQuery<Auditoria> consulta = em.createNamedQuery("Auditoria.todos", Auditoria.class);
		return consulta.getResultList();
	}
}
