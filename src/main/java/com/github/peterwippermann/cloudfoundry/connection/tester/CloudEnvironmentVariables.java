package com.github.peterwippermann.cloudfoundry.connection.tester;

public class CloudEnvironmentVariables {
	private static final String URL = "URL";

	public static String getVariable(String key) {
		// Standard way to read from CF's environment variables
		String envVar = System.getenv(key);
		return envVar;

		// Debug option to read a JVM's command-line argument
		// String cliArg = System.getProperty(key);
		// return cliArg;
	}

	public static String getUrl() {
		return getVariable(URL);
	}
}
