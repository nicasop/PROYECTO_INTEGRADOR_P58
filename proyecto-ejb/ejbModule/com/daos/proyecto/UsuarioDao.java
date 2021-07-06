package com.daos.proyecto;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.entities.proyecto.Usuario;

@Stateless
public class UsuarioDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public void crear(Usuario usuario) {
		em.persist(usuario);
		em.refresh(usuario);
	}
	
	public Usuario actulizar(Usuario usuario) {
		return em.merge(usuario);
	}
	
	public Usuario buscar(String nombre) {
		return em.find(Usuario.class,nombre);
	}
	
	public Usuario verificarUsuario(Usuario user) {
		Usuario usuario = null;
		String consulta;
		try {
			consulta = "select u from Usuario u where u.usuario = ?1 and u.contra = ?2";
			Query query = em.createQuery(consulta);
			query.setParameter(1, user.getUsuario());
			query.setParameter(2, user.getContra());
			List<Usuario> lista = query.getResultList();
			if(!lista.isEmpty()) {
				usuario = lista.get(0);
			}
		} catch(Exception e) {
			throw e;
		}
		return usuario;
	}

}
