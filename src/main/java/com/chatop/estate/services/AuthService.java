package com.chatop.estate.services;

import java.util.Date;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatop.estate.dtos.AuthDTO;
import com.chatop.estate.dtos.LoginDTO;
import com.chatop.estate.dtos.RegisterDTO;
import com.chatop.estate.dtos.UserDTO;
import com.chatop.estate.models.User;
import com.chatop.estate.repositories.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthDTO register(RegisterDTO request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        //user.setCreatedAt(LocalDateTime.now());
        //user.setUpdatedAt(LocalDateTime.now());
        Date date = new Date();
        user.setCreatedAt(date);
        user.setUpdatedAt(date);
        
        userRepository.save(user);
        
        String jwt = jwtService.generateToken(user.getEmail());
        return new AuthDTO(jwt);
    }

    public AuthDTO login(LoginDTO request) {
        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Mot de passe invalide");
        }

        String jwt = jwtService.generateToken(user.getEmail());
        return new AuthDTO(jwt);
    }

    public UserDTO getCurrentUser(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }
    
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email);
        }
        return user;
    }
}
