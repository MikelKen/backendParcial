package com.parcial.parcialbackend.entity;

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
@Table(name = "schedule")
public class Shedule { // Entidad Shedule para horario de atención
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String dayWeek; // Día de la semana
    private String turn; // Turno (Mañana, Tarde, Noche, etc.)
    private LocalTime startTime; // Hora de inicio
    private LocalTime endTime; // Hora de fin

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id") // Clave foránea para Doctor
    private Doctor doctor;
}
