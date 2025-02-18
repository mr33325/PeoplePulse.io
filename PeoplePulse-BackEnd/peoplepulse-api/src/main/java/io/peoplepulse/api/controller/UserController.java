package io.peoplepulse.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.peoplepulse.api.config.AppConstants;
import io.peoplepulse.api.entity.User;
import io.peoplepulse.api.service.UserService;

@RestController
@RequestMapping(AppConstants.APPLICATION_BASE_PATH)
public class UserController {

	@Autowired
    private UserService userService;
	

    // Endpoint to load users from external API into the database
    @PostMapping(AppConstants.POST_MAPPING_LOAD)
    public ResponseEntity<String> loadUsers() {
        String message = userService.loadUsersFromExternalAPI();
        return ResponseEntity.ok(message);
    }

    // Endpoint to find a user by ID
    @GetMapping(AppConstants.GET_MAPPING_ID)
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint to find a user by email
    @GetMapping(AppConstants.GET_MAPPING_EMAIL)
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        Optional<User> user = userService.findUserByEmail(email);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint for free-text search
    @GetMapping(AppConstants.GET_MAPPING_SEARCH)
    public ResponseEntity<List<User>> searchUsers(@RequestParam String query) {
        List<User> users = userService.searchUsers(query);
        return ResponseEntity.ok(users);
    }
}

