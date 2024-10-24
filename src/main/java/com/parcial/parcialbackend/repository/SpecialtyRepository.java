package com.parcial.parcialbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parcial.parcialbackend.entity.Specialty;

public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {
    Optional<Specialty> findByName(String name);
}
