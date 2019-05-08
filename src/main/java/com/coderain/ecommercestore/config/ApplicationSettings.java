package com.coderain.ecommercestore.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application")
public class ApplicationSettings {
	private boolean loadMockData;

	public boolean isLoadMockData() {
		return loadMockData;
	}

	public void setLoadMockData(boolean loadMockData) {
		this.loadMockData = loadMockData;

	}
}
