package com.parcial.parcialbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parcial.parcialbackend.entity.PreConsultation;

@Repository
public interface PreConsultationRepository extends JpaRepository<PreConsultation,Integer>{

}
