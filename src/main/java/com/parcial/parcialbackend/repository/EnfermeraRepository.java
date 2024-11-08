package com.parcial.parcialbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.parcial.parcialbackend.entity.Enfermera;

@Repository
public interface EnfermeraRepository  extends JpaRepository<Enfermera, Integer>{
    Optional<Enfermera> findByCi(Integer ci);

    boolean existsByCi(Integer ci);
}
