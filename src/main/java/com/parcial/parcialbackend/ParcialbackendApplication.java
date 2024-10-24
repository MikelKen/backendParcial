package com.parcial.parcialbackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.parcial.parcialbackend.auth.Role;
import com.parcial.parcialbackend.entity.Users;
import com.parcial.parcialbackend.repository.UserRepository;

@SpringBootApplication
public class ParcialbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParcialbackendApplication.class, args);
	}

		@Bean
	CommandLineRunner init(UserRepository usersRepository, BCryptPasswordEncoder passwordEncoder){
		return args -> {
			if(usersRepository.count()==0){
				Users admin = Users.builder()
					.name("Admin")
					.email("admin@gmail.com")
					.password(passwordEncoder.encode("12345"))
					.phone(71234568)
					.role(Role.ADMINISTRADOR)
					.build();
				usersRepository.save(admin);
				System.out.println("Default admin user created "+admin.getName());
			}
		};
	}



}
