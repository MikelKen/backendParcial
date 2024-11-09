package com.parcial.parcialbackend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "pre_consultation")
public class PreConsultation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String arterialPressure; // presión arterial

    private Integer heartRate; // frecuencia cardiaca

    @Column(precision = 5, scale = 2)
    private BigDecimal temperature;

    @Column(precision = 5, scale = 2)
    private BigDecimal weight;

    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "enfermeraId", referencedColumnName = "ci", unique = true)
    private Enfermera enfermera;

    @ManyToOne
    @JoinColumn(name = "cita_id", referencedColumnName = "id")
    private TimeBlock cita;

}
