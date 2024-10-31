package com.parcial.parcialbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.parcial.parcialbackend.DTO.OpeningHourDTO;
import com.parcial.parcialbackend.entity.OpeningHour;

@Repository
public interface OpeningHourRepository extends JpaRepository<OpeningHour,Integer> {
    //List<OpeningHour> findByDoctorId (Integer doctorId);
    @Query("SELECT o.id AS id, o.dayWeek AS dayWeek, o.turn AS turn, o.startTime AS startTime, o.endTime AS endTime, o.doctor.id AS doctorId " +
           "FROM OpeningHour o WHERE o.doctor.ci = :doctorId")
    List<OpeningHourDTO> findOpeningHoursByDoctorId(Integer doctorId);

    @Query("SELECT o FROM OpeningHour o WHERE o.doctor.ci = :ciDoctor")
    List<OpeningHour> findByDoctorCi(Integer ciDoctor);

    @Query("SELECT o FROM OpeningHour o WHERE o.doctor.ci = :ciDoctor AND LOWER(o.dayWeek) = LOWER(:dayOfWeek)")
List<OpeningHour> findByDoctorCiAndDayOfWeek(Integer ciDoctor, String dayOfWeek);

}
