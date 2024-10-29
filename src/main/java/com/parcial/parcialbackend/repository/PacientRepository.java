package com.parcial.parcialbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parcial.parcialbackend.entity.Pacient;

@Repository
public interface PacientRepository extends JpaRepository<Pacient, Integer>{
    Optional<Pacient> findByCi(Integer ci);

    boolean existsByCi(Integer ci);
}
