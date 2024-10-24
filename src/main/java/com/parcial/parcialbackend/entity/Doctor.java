package com.parcial.parcialbackend.entity;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

public class Doctor {
    
    @Id
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "ci", unique = true)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "id_especialidad", referencedColumnName = "id")
    private Specialty specialty;
}
