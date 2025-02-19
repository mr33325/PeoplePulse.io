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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(AppConstants.APPLICATION_BASE_PATH)
@Tag(name = "User Management", description = "Endpoints for managing users")
public class UserController {

	@Autowired
	private UserService userService;

	// Endpoint to load users from external API into the database
	@Operation(summary = "Load users from external API", description = "Fetches users from an external API and saves them to the database.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Users loaded successfully"),
			@ApiResponse(responseCode = "500", description = "Failed to load users") })
	@PostMapping(AppConstants.POST_MAPPING_LOAD)
	public ResponseEntity<String> loadUsers() {
		String message = userService.loadUsersFromExternalAPI();
		return ResponseEntity.ok(message);
	}

	// Endpoint to find a user by ID
	@Operation(summary = "Find user by ID", description = "Fetches a user from the database by their unique ID.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "User found"),
			@ApiResponse(responseCode = "404", description = "User not found") })
	@GetMapping(AppConstants.GET_MAPPING_ID)
	public ResponseEntity<User> getUserById(
			@Parameter(description = "User ID", required = true) @PathVariable Long id) {
		Optional<User> user = userService.findUserById(id);
		return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	// Endpoint to find a user by email
	@Operation(summary = "Find user by email", description = "Fetches a user from the database by their email address.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "User found"),
			@ApiResponse(responseCode = "404", description = "User not found") })
	@GetMapping(AppConstants.GET_MAPPING_EMAIL)
	public ResponseEntity<User> getUserByEmail(
			@Parameter(description = "User email", required = true) @RequestParam String email) {
		Optional<User> user = userService.findUserByEmail(email);
		return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	// Endpoint for free-text search
	@Operation(summary = "Search users", description = "Performs a free-text search on users based on the provided query.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid search query") })
	@GetMapping(AppConstants.GET_MAPPING_SEARCH)
	public ResponseEntity<List<User>> searchUsers(
			@Parameter(description = "Search query", required = true) @RequestParam String query) {
		List<User> users = userService.searchUsers(query);
		return ResponseEntity.ok(users);
	}
}
