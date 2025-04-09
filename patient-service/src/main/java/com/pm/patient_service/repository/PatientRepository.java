package com.pm.patient_service.repository;

import com.pm.patient_service.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository  //this jpa repository which is used to access the database and perform CRUD operations

public interface PatientRepository extends JpaRepository<Patient, UUID> {

    boolean existsByEmail(String email); // This method checks if a patient with the given email already exists in the database


    // This interface will automatically provide CRUD operations for the Patient entity
    // You can add custom query methods here if needed
}
