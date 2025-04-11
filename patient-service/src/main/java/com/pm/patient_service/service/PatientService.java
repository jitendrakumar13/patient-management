package com.pm.patient_service.service;

import billing.BillingServiceGrpc;
import com.pm.patient_service.dto.PatientRequestDTO;
import com.pm.patient_service.dto.PatientResponseDTO;
import com.pm.patient_service.exception.EmailAlreadyExistsException;
import com.pm.patient_service.exception.PatientNotFoundException;
import com.pm.patient_service.grpc.BIllingServiceGrpcClient;
import com.pm.patient_service.mapper.PatientMapper;
import com.pm.patient_service.model.Patient;
import com.pm.patient_service.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.nio.file.ProviderNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final BIllingServiceGrpcClient billingServiceGrpcClient;

    // Constructor injection for the repository
    //why this constructor injection is used because it is a good practice to use constructor injection for mandatory dependencies
    //how it works
    public PatientService(PatientRepository patientRepository, BIllingServiceGrpcClient billingServiceGrpcClient) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
    }

    // Add methods to interact with the repository
    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDTO> patientResponseDTOs=patients.stream()
                .map(PatientMapper::toDto).toList();
        return patientResponseDTOs;
    }

    // Add more methods as needed for CRUD operations
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Patient with email " + patientRequestDTO.getEmail() + " already exists");
        }
        Patient savedPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));

        // Call the billing service to create a billing account
        billingServiceGrpcClient.createBillingAccount(savedPatient.getId().toString(), savedPatient.getName(), savedPatient.getEmail());

        return PatientMapper.toDto(savedPatient);
    }



    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO){

        // Check if the patient exists
        if(!patientRepository.existsById(id)) {
            throw new PatientNotFoundException("Patient not found with id: " + id);
        }
        //Check if the email already exists
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Patient with email " + patientRequestDTO.getEmail() + " already exists");
        }

        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));

        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDto(updatedPatient);
    }

    public String deletePatient(UUID id){
        if(!patientRepository.existsById(id)){
            throw new PatientNotFoundException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);

         return "Deleted sucessfully";
    }

}
