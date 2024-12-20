package com.parcial.parcialbackend.entity;


import java.time.LocalDate;

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
@Table
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    private String diagnosis;

    private String state;

    private String observation;

    @ManyToOne
    @JoinColumn(name = "doctorId", referencedColumnName = "id")
    private Doctor doctor;

    @OneToOne
    @JoinColumn(name = "preConcultationId", referencedColumnName = "id" )
    private PreConsultation preConsultation;

    @ManyToOne
    @JoinColumn(name = "medicalRecordId", referencedColumnName = "id")
    private MedicalRecord medicalRecord; // historia clinica
    
}
