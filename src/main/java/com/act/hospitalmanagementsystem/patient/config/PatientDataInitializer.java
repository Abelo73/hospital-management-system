package com.act.hospitalmanagementsystem.patient.config;

import com.act.hospitalmanagementsystem.patient.entity.Patient;
import com.act.hospitalmanagementsystem.patient.enums.BloodType;
import com.act.hospitalmanagementsystem.patient.enums.Gender;
import com.act.hospitalmanagementsystem.patient.enums.PatientStatus;
import com.act.hospitalmanagementsystem.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class PatientDataInitializer implements CommandLineRunner {

    private final PatientRepository patientRepository;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("Initializing default patient data...");

        if (patientRepository.count() == 0) {
            createMockPatients();
            log.info("Mock patient data created successfully.");
        } else {
            log.info("Patient data already exists, skipping initialization.");
        }
    }

    private void createMockPatients() {
        String[] firstNames = {"John", "Jane", "Michael", "Sarah", "David", "Emily", "Robert", "Lisa", "James", "Maria"};
        String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez"};
        String[] cities = {"New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Philadelphia", "San Antonio", "San Diego", "Dallas", "San Jose"};
        String[] states = {"NY", "CA", "IL", "TX", "AZ", "PA", "TX", "CA", "TX", "CA"};

        for (int i = 0; i < 25; i++) {
            Patient patient = new Patient();
            patient.setMedicalRecordNumber(generateMedicalRecordNumber(i + 1));
            patient.setFirstName(firstNames[i % firstNames.length]);
            patient.setLastName(lastNames[i % lastNames.length]);
            patient.setDateOfBirth(LocalDate.of(1980 + (i % 30), (i % 12) + 1, (i % 28) + 1));
            patient.setGender(i % 2 == 0 ? Gender.MALE : Gender.FEMALE);
            // Use only blood types that fit within 10 characters: A_POSITIVE, B_POSITIVE, O_POSITIVE, UNKNOWN
            BloodType[] safeBloodTypes = {BloodType.A_POSITIVE, BloodType.B_POSITIVE, BloodType.O_POSITIVE, BloodType.UNKNOWN};
            patient.setBloodType(safeBloodTypes[i % safeBloodTypes.length]);
            patient.setPhoneNumber("+1" + String.format("%010d", 2000000000L + i));
            patient.setEmail(patient.getFirstName().toLowerCase() + "." + patient.getLastName().toLowerCase() + (i + 1) + "@example.com");
            patient.setAddress((i + 1) + " Main Street");
            patient.setCity(cities[i % cities.length]);
            patient.setState(states[i % states.length]);
            patient.setPostalCode(String.format("%05d", 10000 + i));
            patient.setCountry("United States");
            patient.setEmergencyContactName(firstNames[(i + 1) % firstNames.length] + " " + lastNames[(i + 1) % lastNames.length]);
            patient.setEmergencyContactPhone("+1" + String.format("%010d", 3000000000L + i));
            patient.setEmergencyContactRelationship("Spouse");
            patient.setAllergies(i % 3 == 0 ? "Penicillin" : "");
            patient.setChronicConditions(i % 4 == 0 ? "Diabetes Type 2" : "");
            patient.setCurrentMedications(i % 5 == 0 ? "Metformin" : "");
            patient.setInsuranceProvider(i % 2 == 0 ? "Blue Cross" : "Aetna");
            patient.setInsurancePolicyNumber("POL" + String.format("%08d", 10000000 + i));
            patient.setRegistrationDate(LocalDateTime.now().minusDays(i));
            patient.setStatus(i % 10 == 0 ? PatientStatus.INACTIVE : PatientStatus.ACTIVE);
            patient.setNotes("Mock patient data for testing purposes.");
            patient.setDeleted(false);

            patientRepository.save(patient);
        }
    }

    private String generateMedicalRecordNumber(int index) {
        return String.format("MRN-2026-%05d", index);
    }
}
