package com.parcial.parcialbackend.security.jwt;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.parcial.parcialbackend.entity.Users;
import com.parcial.parcialbackend.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
                final String token = getTokenFromRequest(request);
   
        final String ci;
        if (token==null) {
            filterChain.doFilter(request, response);
            return;
        }
        
        ci = jwtService.getUsernameFromToken(token);
        
 
        if(ci !=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=userDetailsService.loadUserByUsername(ci);
            Optional<Users> userDet = userRepository.findByCi(ci);

            if(jwtService.isTokenValid(token,userDet)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
                request.setAttribute("userId", userDet.get().getCi());
                request.setAttribute("username",userDet.get().getName());
                request.setAttribute("email",userDet.get().getEmail());
            }
        }
        
        filterChain.doFilter(request, response);        
    }

    private String getTokenFromRequest(HttpServletRequest request) {

        final String authHeader = request.getHeader("authorization");
     
        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){
            return authHeader.substring(7);
        }
        return null;
    }
    
}
