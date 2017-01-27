package com.github.peterwippermann.cloudfoundry.connection.tester;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ConnectionTester {

    private static final int CONNECTION_TIMEOUT_MILLISECONDS = 10000;
    private static final String URL_SEPARATOR = ":";
    private static final int DEFAULT_PORT = 80;

    public static void main(String args[]) {
        String url = CloudEnvironmentVariables.getUrl();
        System.out.println("Read URL from environment variables: \"" + url + "\"");
        if (url == null) {
            System.err.println("No URL could be read! Connection test will be aborted.");
            return;
        }

        String[] split = url.split(URL_SEPARATOR);
        String host = split[0];
        int port;
        if (split.length < 2) {
            System.out.println("No port could be determined in the URL (Use \":\" as a separator!). Port 80 is used as a default.");
            port = DEFAULT_PORT;
        } else {
            port = Integer.valueOf(split[1]);
        }

        System.out.println("Trying to connect (timeout: " + CONNECTION_TIMEOUT_MILLISECONDS + " milliseconds)...");
        if (isReachable(host, port)) {
            System.out.println("Connecting to \"" + host + URL_SEPARATOR + port + "\" was successful!");
        } else {
            System.err.println("Connecting to \"" + host + URL_SEPARATOR + port + "\" failed!");
        }
    }

    private static boolean isReachable(String host, int port) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port), CONNECTION_TIMEOUT_MILLISECONDS);
            return true;
        } catch (IOException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                new RuntimeException("Closing the socket for cleanup failed", e).printStackTrace();
            }
        }
    }
}
