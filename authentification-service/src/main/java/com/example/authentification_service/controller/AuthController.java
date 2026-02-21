package com.example.authentification_service.controller;

import com.example.authentification_service.model.User;
import com.example.authentification_service.payload.LoginRequest;
import com.example.authentification_service.payload.LoginResponse;
import com.example.authentification_service.payload.SignupRequest;
import com.example.authentification_service.service.UserService;
import com.example.authentification_service.security.JwtUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    public AuthController(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        User user = userService.findByEmail(req.getEmail());

        if (user == null || !user.getPassword().equals(req.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants invalides");
        }

        String token = jwtUtils.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(token, user));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Ici on peut parse le token si besoin (simplifié)
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest req) {

        if (userService.findByEmail(req.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email déjà utilisé");
        }

        User newUser = new User();
        newUser.setId(userService.nextId()); // méthode à créer
        newUser.setNom(req.getNom());
        newUser.setEmail(req.getEmail());
        newUser.setPassword(req.getPassword());

        userService.save(newUser);

        return ResponseEntity.ok("Compte créé");
    }
}