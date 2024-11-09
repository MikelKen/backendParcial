package com.parcial.parcialbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parcial.parcialbackend.entity.Consultation;



@Repository
public interface ConsultationRepository extends JpaRepository<Consultation,Integer> {
   @Query("SELECT c FROM Consultation c WHERE c.doctor.ci = :doctorCi")
    List<Consultation> findByDoctorCi(@Param("doctorCi") Integer doctorCi);

  
    @Query("SELECT c FROM Consultation c WHERE c.medicalRecord.id = :medicalRecordId")
    List<Consultation> findByMedicalRecordId(@Param("medicalRecordId") Integer medicalRecordId);
}
