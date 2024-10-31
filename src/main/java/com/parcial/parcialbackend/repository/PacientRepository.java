package com.parcial.parcialbackend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.parcial.parcialbackend.entity.Pacient;
import com.parcial.parcialbackend.entity.TimeBlock;

@Repository
public interface PacientRepository extends JpaRepository<Pacient, Integer>{
    Optional<Pacient> findByCi(Integer ci);

    boolean existsByCi(Integer ci);

    @Query("SELECT t FROM TimeBlock t WHERE t.pacient.id = :pacientId AND t.date = :date AND t.state = 'RESERVADO'")
    List<TimeBlock> findReservedFichasByPacientAndDate(Integer pacientId, LocalDate date);
}
