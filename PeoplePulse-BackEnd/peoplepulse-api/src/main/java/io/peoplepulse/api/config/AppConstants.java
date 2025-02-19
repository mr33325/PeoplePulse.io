package io.peoplepulse.api.config;

public class AppConstants {
	public static final String APPLICATION_NAME = "peoplepulse-api";
	
	public static final String APPLICATION_BASE_PATH = "/api/users";
	
	public static final String POST_MAPPING_LOAD = "/load";
	
	public static final String GET_MAPPING_ID = "/{id}";
	
	public static final String GET_MAPPING_EMAIL = "/email";
	
	public static final String GET_MAPPING_SEARCH = "/search";
	
	public static final String EMPTY_STRING = "";
	
	public static final String URL_STRING_FORMAT = "%s%s";
	
	public static final int CONNECT_TIMEOUT = 10; // in seconds
	
    public static final int READ_TIMEOUT = 15; // in seconds
}
