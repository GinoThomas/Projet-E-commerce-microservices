package com.example.authentification_service.service;

import org.springframework.stereotype.Service;
import com.example.authentification_service.model.User;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();

    public UserService() {
        User u1 = new User();
        u1.setId(1L);
        u1.setNom("Alice");
        u1.setEmail("alice@exemple.com");
        u1.setPassword("password"); // en clair (juste pour test)
        users.add(u1);

        User u2 = new User();
        u2.setId(2L);
        u2.setNom( "admin");
        u2.setEmail("admin@admin.com");
        u2.setPassword("admin");
        users.add(u2);
    }

    public User findByEmail(String email) {
        return users.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public void save(User user) {
        users.add(user);
    }

    public long nextId() {
        return users.size() + 1;
}
}