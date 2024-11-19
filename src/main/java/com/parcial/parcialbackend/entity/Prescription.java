package com.parcial.parcialbackend.entity;

import java.time.LocalDate;
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
public class Prescription {//receta medica
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String medicine; //meicamento
    private String dosis; 
    private String frecuency; //frecuencia
    private String duration;
    private String observation;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name= "consultationId", referencedColumnName = "id")
    private Consultation consulta;

    @PrePersist
    protected void onCreate() {
        this.date = LocalDate.now();
    }
}
