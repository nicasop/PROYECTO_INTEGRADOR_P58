package com.daos.proyecto;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
			consulta = "SELECT u FROM Usuario u WHERE u.usuario = ?1 AND u.contra = ?2";
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
	
	public List<Usuario>getUsuarios(){
		TypedQuery<Usuario> consulta = em.createNamedQuery("Usuario.todos", Usuario.class);
		return consulta.getResultList();
	}

}
