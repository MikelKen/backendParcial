package com.parcial.parcialbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parcial.parcialbackend.entity.Speciality;

public interface SpecialtyRepository extends JpaRepository<Speciality, Integer> {
    Optional<Speciality> findByName(String name);
}
