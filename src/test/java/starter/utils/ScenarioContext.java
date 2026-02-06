package starter.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Manages scenario context for storing and retrieving values across test steps.
 * Also provides utility methods for processing dynamic values like {random}
 * placeholders.
 */
public class ScenarioContext {

    private static final Map<String, Object> scenarioContext = new HashMap<>();
    private static final Random random = new Random();

    /**
     * Store a value in the scenario context
     */
    public static void put(String key, Object value) {
        scenarioContext.put(key, value);
    }

    /**
     * Retrieve a value from the scenario context
     */
    public static Object get(String key) {
        return scenarioContext.get(key);
    }

    /**
     * Check if a key exists in the scenario context
     */
    public static boolean contains(String key) {
        return scenarioContext.containsKey(key);
    }

    /**
     * Clear all stored values
     */
    public static void clear() {
        scenarioContext.clear();
    }

    /**
     * Process body string and replace {random} with random numbers
     */
    public static String processBodyWithRandomValues(String body) {
        String processed = body;

        while (processed.contains("{random}")) {
            // Generate a random 2-digit number (10-99)
            int randomValue = 1 + random.nextInt(90);
            processed = processed.replaceFirst("\\{random\\}", String.valueOf(randomValue));
        }

        return processed;
    }

    /**
     * Replace saved variables in the format {variableName} or bare variableName
     * with their stored values
     */
    public static String replaceSavedVariables(String text) {
        String processed = text;

        for (Map.Entry<String, Object> entry : scenarioContext.entrySet()) {
            String variableName = entry.getKey();
            String value = String.valueOf(entry.getValue());

            // Replace {variableName} format
            String placeholder = "{" + variableName + "}";
            if (processed.contains(placeholder)) {
                processed = processed.replace(placeholder, value);
            }

            // Replace bare variableName (not inside quotes, for JSON values)
            // Pattern: "key": variableName or "key": variableName}
            String barePattern1 = ": " + variableName + "}";
            String barePattern2 = ": " + variableName + ",";
            String barePattern3 = ": " + variableName + "\n";
            String barePattern4 = ": " + variableName + " ";

            if (processed.contains(barePattern1)) {
                processed = processed.replace(barePattern1, ": " + value + "}");
            }
            if (processed.contains(barePattern2)) {
                processed = processed.replace(barePattern2, ": " + value + ",");
            }
            if (processed.contains(barePattern3)) {
                processed = processed.replace(barePattern3, ": " + value + "\n");
            }
            if (processed.contains(barePattern4)) {
                processed = processed.replace(barePattern4, ": " + value + " ");
            }
        }

        return processed;
    }

    /**
     * Get all stored values (for debugging)
     */
    public static Map<String, Object> getAll() {
        return new HashMap<>(scenarioContext);
    }
}
