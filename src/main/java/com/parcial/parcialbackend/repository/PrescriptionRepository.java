package com.parcial.parcialbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parcial.parcialbackend.entity.Prescription;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {
    @Query("SELECT p FROM Prescription p WHERE p.consulta.id = :consultationId")
    List<Prescription> findByConsultationId(@Param("consultationId") Integer consultationId);
}
