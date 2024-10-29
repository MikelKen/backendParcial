package com.parcial.parcialbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parcial.parcialbackend.entity.Users;


public interface UserRepository extends JpaRepository<Users,Integer>{
    Optional<Users> findByEmail(String email);
    Optional<Users> findByCi(String ci);

    boolean existsByCi(String ci);
}
