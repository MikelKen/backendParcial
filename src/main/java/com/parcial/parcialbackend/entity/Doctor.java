package com.parcial.parcialbackend.entity;

import jakarta.persistence.Entity;
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
public class Doctor {
    
    @Id
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id", unique = true)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "id_especialidad", referencedColumnName = "id")
    private Specialty specialty;
}
