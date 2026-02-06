package starter.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages authentication tokens for API testing.
 * Stores tokens in memory for reuse across test scenarios.
 */
public class TokenManager {

    private static final Map<String, String> tokens = new HashMap<>();
    private static String currentToken;
    private static String currentTokenType;

    /**
     * Store a token with a specific key (e.g., "user", "admin")
     */
    public static void storeToken(String key, String token, String tokenType) {
        tokens.put(key, token);
        currentToken = token;
        currentTokenType = tokenType;
    }

    /**
     * Store the current token (default)
     */
    public static void storeToken(String token, String tokenType) {
        currentToken = token;
        currentTokenType = tokenType;
    }

    /**
     * Get a stored token by key
     */
    public static String getToken(String key) {
        return tokens.get(key);
    }

    /**
     * Get the current token
     */
    public static String getCurrentToken() {
        return currentToken;
    }

    /**
     * Get the current token type (e.g., "Bearer")
     */
    public static String getCurrentTokenType() {
        return currentTokenType;
    }

    /**
     * Get the full authorization header value (e.g., "Bearer eyJhbGc...")
     */
    public static String getAuthorizationHeader() {
        if (currentToken == null || currentTokenType == null) {
            throw new IllegalStateException("No token available. Please authenticate first.");
        }
        return currentTokenType + " " + currentToken;
    }

    /**
     * Get authorization header for a specific stored token
     */
    public static String getAuthorizationHeader(String key) {
        String token = tokens.get(key);
        if (token == null) {
            throw new IllegalStateException("No token found for key: " + key);
        }
        return currentTokenType + " " + token;
    }

    /**
     * Clear all stored tokens
     */
    public static void clearTokens() {
        tokens.clear();
        currentToken = null;
        currentTokenType = null;
    }

    /**
     * Check if a token is available
     */
    public static boolean hasToken() {
        return currentToken != null;
    }
}
