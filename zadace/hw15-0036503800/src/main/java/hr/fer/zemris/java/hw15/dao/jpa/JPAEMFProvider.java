package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Razred pružatelj tvornice objekta {@code EntityManagerFactory}.
 * 
 * @author Antonio Kuzminski
 *
 */
public class JPAEMFProvider {

	public static EntityManagerFactory emf;
	
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}
