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
public class MedicalExamination {// examen medico
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    private String type;

    private String result;

    private String image;

     @ManyToOne
    @JoinColumn(name = "paient_id", referencedColumnName = "id")
    private Pacient pacient;

    @ManyToOne
    @JoinColumn(name = "consultationId", referencedColumnName = "id")
    private Consultation consultation;

    @PrePersist
    protected void onCreate() {
        this.date = LocalDate.now();
    }

}
