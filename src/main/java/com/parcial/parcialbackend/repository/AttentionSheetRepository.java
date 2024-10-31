package com.parcial.parcialbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parcial.parcialbackend.entity.AttentionSheet;

@Repository
public interface AttentionSheetRepository extends JpaRepository<AttentionSheet, Integer> {
    
}
