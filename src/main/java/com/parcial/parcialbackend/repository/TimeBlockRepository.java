package com.parcial.parcialbackend.repository;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parcial.parcialbackend.entity.TimeBlock;

@Repository
public interface TimeBlockRepository extends JpaRepository<TimeBlock,Integer> {
 //List<TimeBlock> findByOpeningHourDoctorIdAndDateAndState(Integer doctorId, LocalDate date, String state);     
 List<TimeBlock> findByOpeningHourDoctorId(Integer doctorId);

 boolean existsByDateAndOpeningHourDoctorCi(LocalDate date, Integer doctorCi);

 @Query("SELECT t FROM TimeBlock t WHERE t.openingHour.doctor.ci = :doctorCi AND t.date = :date AND t.state = 'Disponible'")
List<TimeBlock> findAvailableFichasByDoctorAndDate(Integer doctorCi, LocalDate date);

@Query("SELECT t FROM TimeBlock t WHERE t.openingHour.doctor.ci = :doctorCi")
List<TimeBlock> findAllFichasByDoctorCi(Integer doctorCi);

@Query("SELECT t FROM TimeBlock t WHERE t.openingHour.doctor.ci = :doctorCi AND t.state = 'DISPONIBLE'")
    List<TimeBlock> findAvailableFichasByDoctorCi(Integer doctorCi);

@Query("SELECT t FROM TimeBlock t WHERE t.pacient.id = :pacientId AND t.date = :date AND t.state = 'RESERVADO'")
List<TimeBlock> findReservedFichasByPacientAndDate(@Param("pacientId") Integer pacientId, @Param("date") LocalDate date);

@Query("SELECT t FROM TimeBlock t WHERE t.pacient.ci = :ci")
    List<TimeBlock> findAllByPacientCi(@Param("ci") Integer ci);

    @Query("SELECT t FROM TimeBlock t WHERE t.pacient.ci = :ci AND t.date = :date")
    List<TimeBlock> findByPacientCiAndDate(@Param("ci") Integer ci, @Param("date") LocalDate date);

  

}
