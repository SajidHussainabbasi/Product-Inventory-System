package com.example.productinventorysystem.service;

import com.example.productinventorysystem.dto.LoginRequestDTO;
import com.example.productinventorysystem.dto.LoginResponseDTO;
import com.example.productinventorysystem.dto.RegisterRequestDTO;
import com.example.productinventorysystem.exception.UserAlreadyExistsException;
import com.example.productinventorysystem.model.User;
import com.example.productinventorysystem.repository.UserRepository;
import com.example.productinventorysystem.security.JwtUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       JwtUtils jwtUtils,
                       BCryptPasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    /** REGISTER USER **/
    public void register(RegisterRequestDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException(
                    "User with email '" + dto.getEmail() + "' already exists"
            );
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setHashedPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);
    }

    /** LOGIN USER **/
    public LoginResponseDTO login(LoginRequestDTO dto) {

        User user = userRepository.findByEmail(dto.getEmail());
        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getHashedPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token = jwtUtils.generateToken(
                user.getEmail(),
                Map.of("userId", user.getId())
        );

        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(token);
        response.setUsername(user.getUsername());

        return response;
    }
}

