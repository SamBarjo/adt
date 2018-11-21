package ca.ulaval.glo4002.adt.domain;

public class PatientFactory {

    public Patient create(String name) {
        return new Patient(name);
    }

    public Patient create(String name, String dept) {
        Patient patient = new Patient(name);
        patient.moveToDepartment(dept);
        return patient;
    }
}
