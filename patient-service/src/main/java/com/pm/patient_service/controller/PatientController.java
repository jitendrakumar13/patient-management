package com.pm.patient_service.controller;

import com.pm.patient_service.dto.CreatePatientValidationGroup;
import com.pm.patient_service.dto.PatientRequestDTO;
import com.pm.patient_service.dto.PatientResponseDTO;
import com.pm.patient_service.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient API", description = "Patient Management API")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    //health check endpoint
    @GetMapping("/health")
    @Operation(summary = "Health Check", description = "Check if the Patient Service is running")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Patient Service is up and running");
    }
    // Add endpoints to handle requests
    // For example, to get all patients
    @GetMapping
    @Operation(summary = "Get all patients", description = "Retrieve a list of all patients")
    public ResponseEntity<?> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @PostMapping
    @Operation(summary = "Create a new patient", description = "Create a new patient")
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        return ResponseEntity.ok(patientService.createPatient(patientRequestDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing patient", description = "Update an existing patient by ID")
    public ResponseEntity<PatientResponseDTO> updatePatient( @PathVariable UUID id,@Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
       PatientResponseDTO patientResponseDTO= patientService.updatePatient(id, patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a patient", description = "Delete a patient by ID")
    public String deletePatient(@PathVariable UUID id){

        patientService.deletePatient(id);
        return "Deleted successfully";
    }



}
