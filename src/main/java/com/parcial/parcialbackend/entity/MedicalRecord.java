package com.parcial.parcialbackend.entity;


import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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
public class MedicalRecord { //historia clinica
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;

    private LocalDate creationDate;
    private LocalTime creationTime;

    @ManyToOne
    @JoinColumn(name = "paient_id", referencedColumnName = "id")
    private Pacient pacient;

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDate.now();
        this.creationTime = LocalTime.now().withSecond(0).withNano(0);
    }
}
