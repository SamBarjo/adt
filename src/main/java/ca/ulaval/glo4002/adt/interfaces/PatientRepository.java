package ca.ulaval.glo4002.adt.interfaces;

import ca.ulaval.glo4002.adt.domain.Patient;

import java.util.Collection;

public interface PatientRepository {
    public void persist(Patient patient);
    public Collection<Patient> findAll();
    public Patient findById(int patientId);
}
