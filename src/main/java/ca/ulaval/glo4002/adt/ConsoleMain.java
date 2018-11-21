package ca.ulaval.glo4002.adt;

import ca.ulaval.glo4002.adt.applicationService.PatientService;
import ca.ulaval.glo4002.adt.context.ServiceLocator;
import ca.ulaval.glo4002.adt.domain.Patient;
import ca.ulaval.glo4002.adt.domain.PatientFactory;
import ca.ulaval.glo4002.adt.interfaces.PatientRepository;
import ca.ulaval.glo4002.adt.persistence.EntityManagerProvider;
import ca.ulaval.glo4002.adt.persistence.HibernatePatientRepository;

import java.util.Collection;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConsoleMain {
	private static Scanner scanner;

	public static void main(String[] args) {
		initializeServiceLocator();
		initializeEntityManagerProvider();
		PatientService patientService = ServiceLocator.INSTANCE.resolve(PatientService.class);
		patientService.fillDatabase();

		try (Scanner scanner = new Scanner(System.in)) {
			ConsoleMain.scanner = scanner;
			startCommandPromptLoop();
		}

		System.exit(0);
	}

	private static void initializeServiceLocator() {
		ServiceLocator.INSTANCE.register(PatientRepository.class, new HibernatePatientRepository());
		ServiceLocator.INSTANCE.register(EntityManagerFactory.class, Persistence.createEntityManagerFactory("adt"));
		ServiceLocator.INSTANCE.register(PatientService.class, new PatientService(new PatientFactory()));
	}

	private static void initializeEntityManagerProvider() {
		EntityManagerFactory entityManagerFactory = ServiceLocator.INSTANCE.resolve(EntityManagerFactory.class);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityManagerProvider.setEntityManager(entityManager);
	}

	private static void startCommandPromptLoop() {
		System.out.println("Welcome to GLO-4002's ADT!");

		boolean quit = false;

		while (!quit) {
			String option = pickOptionFromMenu();
			System.out.println("\n");
			switch (option) {
			case "1": {
				displayPatientList();
				break;
			}
			case "2": {
				movePatient();
				break;
			}
			case "3": {
				dischargePatient();
				break;
			}
			case "q": {
				quit = true;
				break;
			}
			default: {
				System.out.println("Invalid option");
			}
			}
		}

		System.out.println("Bye bye");

	}

	private static String pickOptionFromMenu() {
		System.out.println("\n");
		System.out.println("-----------------");
		System.out.println("What do you want to do?");
		System.out.println("1) List patients");
		System.out.println("2) Move a patient");
		System.out.println("3) Discharge a patient");

		System.out.print("Choice (or q tp quit) : ");
		return scanner.nextLine();
	}

	private static void displayPatientList() {
		PatientService patientService = ServiceLocator.INSTANCE.resolve(PatientService.class);
		Collection<Patient> patients = patientService.listPatients();

		for (Patient patient : patients) {
			System.out.println(String.format("%d : %s (status = %s, department = %s)", patient.getId(),
					patient.getName(), patient.getStatus(), patient.getDepartment()));
		}
	}

	private static void movePatient() {
		System.out.println("First, you must select a patient : ");
		displayPatientList();

		System.out.print("Patient ID to move : ");
		int patientId = Integer.parseInt(scanner.nextLine());

		System.out.print("New department : ");
		String newDepartment = scanner.nextLine();

		PatientService patientService = ServiceLocator.INSTANCE.resolve(PatientService.class);
		patientService.movePatient(patientId, newDepartment);
	}
	
	private static void dischargePatient() {
		System.out.println("First, you must select a patient : ");
		displayPatientList();

		System.out.print("Patient ID to discharge : ");
		int patientId = Integer.parseInt(scanner.nextLine());

		PatientService patientService = ServiceLocator.INSTANCE.resolve(PatientService.class);
		patientService.dischargePatient(patientId);
	}

}
