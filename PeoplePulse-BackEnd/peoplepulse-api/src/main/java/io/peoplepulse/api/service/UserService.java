package io.peoplepulse.api.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.retry.annotation.Retry;
import io.peoplepulse.api.config.DataSetProvider;
import io.peoplepulse.api.entity.User;
import io.peoplepulse.api.repository.UserRepository;
import io.peoplepulse.api.response.UserResponse;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private DataSetProvider dataSetProvider;

    private static final String USER_API_URL = "https://dummyjson.com/users";

    // Load users from external API into H2 DB
    @Retry(name = "userDataRetry", fallbackMethod = "fallbackLoadUsers")
    public String loadUsersFromExternalAPI() {
        logger.info("Fetching user data from external API...");
        String url = dataSetProvider.getBaseUrl()+dataSetProvider.getGetUsers();
        ResponseEntity<UserResponse> response = restTemplate.getForEntity(url, UserResponse.class);

        if (response.getBody() != null && response.getBody().getUsers() != null) {
            List<User> users = response.getBody().getUsers();
            userRepository.saveAll(users);
            logger.info("Successfully loaded {} users into the database.", users.size());
            return "User data successfully loaded into the in-memory database.";
        } else {
            throw new RuntimeException("Failed to retrieve users from external API.");
        }
    }

    // Fallback for loading users
    public String fallbackLoadUsers(Throwable ex) {
        logger.error("Failed to load users from external API: {}", ex.getMessage());
        return "Failed to load users. Please try again later.";
    }

    // Find user by ID
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    // Find user by email
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Free-text search on firstName, lastName, and SSN
    public List<User> searchUsers(String query) {
        return userRepository.searchByKeyword(query);
    }
}
