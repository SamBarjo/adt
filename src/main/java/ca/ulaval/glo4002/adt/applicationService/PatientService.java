package ca.ulaval.glo4002.adt.applicationService;

import ca.ulaval.glo4002.adt.context.ServiceLocator;
import ca.ulaval.glo4002.adt.domain.Patient;
import ca.ulaval.glo4002.adt.domain.PatientFactory;
import ca.ulaval.glo4002.adt.interfaces.PatientRepository;

import java.util.Collection;

public class PatientService {
    private final PatientFactory patientFactory;

    public PatientService(PatientFactory patientFactory) {
        this.patientFactory = patientFactory;
    }

    public void fillDatabase() {
        PatientRepository patientRepository = ServiceLocator.INSTANCE.resolve(PatientRepository.class);

        Patient pierre = patientFactory.create("Pierre");
        patientRepository.persist(pierre);

        Patient marie = patientFactory.create("Marie", "ICU");
        patientRepository.persist(marie);
    }

    public Collection<Patient> listPatients() {
        PatientRepository patientRepository = ServiceLocator.INSTANCE.resolve(PatientRepository.class);
        return patientRepository.findAll();
    }

    public void movePatient(int patientId, String newDepartment) {
        PatientRepository patientRepository = ServiceLocator.INSTANCE.resolve(PatientRepository.class);
        Patient patient = patientRepository.findById(patientId);
        patient.moveToDepartment(newDepartment);
        patientRepository.persist(patient);
    }

    public void dischargePatient(int patientId) {
        PatientRepository patientRepository = ServiceLocator.INSTANCE.resolve(PatientRepository.class);
        Patient patient = patientRepository.findById(patientId);
        patient.discharge();
        patientRepository.persist(patient);
    }
}
