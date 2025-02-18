package io.peoplepulse.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "urlmappings.data-set-provider")
public class DataSetProvider {
	private String baseUrl = "";
	private String getUsers = "";
	
	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	public String getGetUsers() {
		return getUsers;
	}
	public void setGetUsers(String getUsers) {
		this.getUsers = getUsers;
	}
	
	
}
