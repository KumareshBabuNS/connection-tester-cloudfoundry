package com.github.peterwippermann.cloudfoundry.connection.tester;

public class CloudEnvironmentVariables {
	private static final String URL = "URL";

	public static String getVariable(String key) {
		String property = System.getProperty(key);
		return property;
		// String getenv2 = System.getenv(key);
		// return getenv2;
	}

	public static String getUrl() {
		return getVariable(URL);
	}
}
