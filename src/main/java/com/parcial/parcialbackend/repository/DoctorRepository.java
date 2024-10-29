package com.parcial.parcialbackend.repository;

//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parcial.parcialbackend.entity.Doctor;


public interface DoctorRepository extends JpaRepository<Doctor,Integer> {
    //Optional<Doctor> findByUsario_ci(Integer id);
}
