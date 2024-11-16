package com.parcial.parcialbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.parcial.parcialbackend.entity.Shedule;

@Repository
public interface SheduleRepository extends JpaRepository<Shedule, Integer> {
    // Puedes agregar métodos personalizados aquí si necesitas
}
