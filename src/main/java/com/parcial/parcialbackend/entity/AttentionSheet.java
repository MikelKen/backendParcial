package com.parcial.parcialbackend.entity;

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
public class AttentionSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String state;  //estado

    @OneToOne
    @JoinColumn(name = "timeBlock", referencedColumnName = "id")
    private TimeBlock timeBlock;

    @ManyToOne
    @JoinColumn(name = "paient_id", referencedColumnName = "id")
    private Pacient pacient;
    
}
