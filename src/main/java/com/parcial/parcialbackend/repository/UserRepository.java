package com.parcial.parcialbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parcial.parcialbackend.auth.Role;
import com.parcial.parcialbackend.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer>{
    Optional<Users> findByEmail(String email);
    Optional<Users> findByCi(String ci);
    List<Users> findByRole(Role role);

    boolean existsByCi(String ci);
}
