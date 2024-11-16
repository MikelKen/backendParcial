package com.parcial.parcialbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parcial.parcialbackend.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Integer>{
    List<Notification> findByPacient_Ci(Integer ci);
}
