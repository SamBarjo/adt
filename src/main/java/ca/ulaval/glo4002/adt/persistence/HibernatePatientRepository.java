package ca.ulaval.glo4002.adt.persistence;

import ca.ulaval.glo4002.adt.domain.Patient;
import ca.ulaval.glo4002.adt.interfaces.PatientRepository;

import java.util.Collection;

import javax.persistence.EntityManager;

public class HibernatePatientRepository implements PatientRepository {

	@Override
	public void persist(Patient patient) {
		EntityManager em = EntityManagerProvider.getEntityManager();
		em.getTransaction().begin();
		em.persist(patient);
		em.getTransaction().commit();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Patient> findAll() {
		EntityManager em = EntityManagerProvider.getEntityManager();
		return (Collection<Patient>) em.createQuery("from Patient").getResultList();
	}

	@Override
	public Patient findById(int patientId) {
		EntityManager em = EntityManagerProvider.getEntityManager();
		return em.find(Patient.class, patientId);
	}

}
