package com.parcial.parcialbackend.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.parcial.parcialbackend.entity.Users;
import com.parcial.parcialbackend.repository.UserRepository;
import com.parcial.parcialbackend.security.jwt.JwtService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request){
        try {
            authenticationManager.authenticate((new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())));

            Users user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            String token = jwtService.getToken(user);

            return AuthResponse.builder()
            .data(token)
            .error(false)
            .message("Login successfully")
            .build();
        } catch (Exception e) {
            return AuthResponse.builder()
            .data(null)
            .error(true)
            .message(e.getMessage())
            .build();
        }
    }

    public AuthResponse register(RegisterRequest request){
        try {
            if(request.getEmail() == null || request.getEmail().isEmpty()){
                throw new Exception("Porfavor proporcione correo");
            }
            if(request.getPassword() == null || request.getPassword().isEmpty()){
                throw new Exception("Porfavro proporcione contraseña"); 
            }
            if(request.getNombre()==null || request.getNombre().isEmpty()){
                throw new Exception("Porfavor porporcione nombre");
            }
        Users user = Users.builder()
            .name(request.getNombre())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .phone(request.getTelefono())
            .address(request.getDireccion())
            .role(Role.ADMINISTRADOR)
            .build();
        
        userRepository.save(user);    
        
        return AuthResponse.builder()
            .data(user)
            .error(false)
            .message("User created Successfully!!")
            .build();
        } catch (Exception e) {
            return AuthResponse.builder()
            .data(null)
            .error(true)
            .message(e.getMessage())
            .build();
        }
    }
}
