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

    /**
     * Create a new user and return a jwt token linked to that new user
     * 
     * @param request
     * @return AuthDTO
     */
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

    /**
     * Find a user by his email and check credentials
     * 	if matching, return a jwt token
     *  else return Exceptions
     * 
     * @param request
     * @return AuthDTO
     */
    public AuthDTO login(LoginDTO request) {
        User user = findUserByEmail(request.getEmail());
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Mot de passe invalide");
        }

        String jwt = jwtService.generateToken(user.getEmail());
        return new AuthDTO(jwt);
    }

    public UserDTO getCurrentUser(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }
    
    /**
     * Find a user by his email and return it
     * 
     * @param email
     * @return user
     */
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email);
        }
        return user;
    }
}
