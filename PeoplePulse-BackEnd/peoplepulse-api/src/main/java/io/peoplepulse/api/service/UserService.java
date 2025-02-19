package io.peoplepulse.api.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.retry.annotation.Retry;
import io.peoplepulse.api.config.AppConstants;
import io.peoplepulse.api.config.DataSetProvider;
import io.peoplepulse.api.entity.User;
import io.peoplepulse.api.model.UserResponse;
import io.peoplepulse.api.repository.UserRepository;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DataSetProvider dataSetProvider;

	
	/**
	 * Load users from external API into H2 DB
	 * 
	 * @return Message String
	 */
	@Retry(name = "userDataRetry", fallbackMethod = "fallbackLoadUsers")
	public String loadUsersFromExternalAPI() {
		logger.info("Fetching user data from external API...");
		String url = String.format(AppConstants.URL_STRING_FORMAT, dataSetProvider.getBaseUrl(),
				dataSetProvider.getGetUsers());
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

	
	/**
	 * Fallback for loading users
	 * 
	 * @param ex The exception
	 * @return  Message String
	 */
	public String fallbackLoadUsers(Throwable ex) {
		logger.error("Failed to load users from external API: {}", ex.getMessage());
		return "Failed to load users. Please try again later.";
	}


	/**
	 * Find user by ID
	 * 
	 * @param id The User Id
	 * @return Optional User
	 */
	public Optional<User> findUserById(Long id) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("Invalid user ID. ID must be a positive number.");
		}
		return userRepository.findById(id);
	}


	/**
	 * Find user by email
	 * 
	 * @param email
	 * @return Optional User
	 */
	public Optional<User> findUserByEmail(String email) {
		if (email == null || email.trim().isEmpty() || !EMAIL_PATTERN.matcher(email).matches()) {
			throw new IllegalArgumentException("Invalid email format.");
		}
		return userRepository.findByEmail(email);
	}

	/**
	 * Free-text search on firstName, lastName, and SSN
	 * 
	 * @param query
	 * @return List of Users
	 */
	public List<User> searchUsers(String query) {
		if (query == null || query.trim().length() < 3) {
			throw new IllegalArgumentException("Search query must be at least 3 characters long.");
		}
		return userRepository.searchByKeyword(query);
	}
	
}
