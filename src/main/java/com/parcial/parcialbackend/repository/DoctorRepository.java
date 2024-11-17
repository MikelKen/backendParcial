package com.parcial.parcialbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parcial.parcialbackend.entity.Doctor;


@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {
    Optional<Doctor> findByCi(Integer ci);

    boolean existsByCi(Integer ci);
    
    Optional<Doctor> findById(Integer id);


    List<Doctor> findBySpecialityName(String specialityName);
}
