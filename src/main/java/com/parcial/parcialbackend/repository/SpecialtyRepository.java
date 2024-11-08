package com.parcial.parcialbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parcial.parcialbackend.entity.Speciality;

@Repository
public interface SpecialtyRepository extends JpaRepository<Speciality, Integer> {
    Optional<Speciality> findByName(String name);
}
