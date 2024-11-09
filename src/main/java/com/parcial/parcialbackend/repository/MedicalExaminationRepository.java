package com.parcial.parcialbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parcial.parcialbackend.entity.MedicalExamination;

@Repository
public interface MedicalExaminationRepository extends JpaRepository<MedicalExamination,Integer> {
    List<MedicalExamination> findByPacient_Ci(Integer ci);

    // Buscar exámenes médicos por ID de la consulta
    List<MedicalExamination> findByConsultation_Id(Integer consultationId);
}
