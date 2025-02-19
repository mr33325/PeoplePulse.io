package io.peoplepulse.api.model;

import io.peoplepulse.api.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Response model for user search results")
public class UserResponse {

    @Schema(description = "List of users in the response")
    private List<User> users;

    @Schema(description = "Total number of users found", example = "100")
    private int total;

    @Schema(description = "Number of users skipped for pagination", example = "10")
    private int skip;

    @Schema(description = "Maximum number of users returned per request", example = "20")
    private int limit;

    // Getters and Setters
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
