package com.pm.patient_service.service;

import com.pm.patient_service.dto.PatientRequestDTO;
import com.pm.patient_service.dto.PatientResponseDTO;
import com.pm.patient_service.mapper.PatientMapper;
import com.pm.patient_service.model.Patient;
import com.pm.patient_service.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    // Constructor injection for the repository
    //why this constructor injection is used because it is a good practice to use constructor injection for mandatory dependencies
    //how it works
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
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
        Patient savedPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
        return PatientMapper.toDto(savedPatient);
    }
}
