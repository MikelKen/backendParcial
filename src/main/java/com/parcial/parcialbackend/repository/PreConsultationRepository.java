package com.parcial.parcialbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parcial.parcialbackend.entity.PreConsultation;

@Repository
public interface PreConsultationRepository extends JpaRepository<PreConsultation,Integer>{

    Optional<PreConsultation> findByCitaId(Integer citaId);
    List<PreConsultation> findAllByCitaId(@Param("citaId") Integer citaId);

}
