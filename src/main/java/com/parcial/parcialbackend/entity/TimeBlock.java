package com.parcial.parcialbackend.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class TimeBlock {//CITAS O FICHAS
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date ; // fecha
    private LocalTime startTime;
    private LocalTime endTime;
    private String state;

    @ManyToOne
    @JoinColumn(name = "openingHour", referencedColumnName = "id")
    private OpeningHour openingHour;

    @ManyToOne
    @JoinColumn(name = "paient_id", referencedColumnName = "id")
    private Pacient pacient;


}
