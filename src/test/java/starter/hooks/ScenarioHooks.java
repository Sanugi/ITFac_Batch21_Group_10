package starter.hooks;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import starter.utils.ScenarioContext;

/**
 * Cucumber hooks that run before/after scenarios
 */
public class ScenarioHooks {

    @Before
    public void beforeScenario(Scenario scenario) {
        // Clear scenario context before each scenario to avoid data leakage
        ScenarioContext.clear();

        // NOTE: We don't clear tokens here because it prevents the Background step
        // from authenticating properly. The Background step runs AFTER this hook,
        // so clearing tokens here causes "No token available" errors.
        // Token management is handled by the Background step in each feature file.

        System.out.println("\n========================================");
        System.out.println("Starting Scenario: " + scenario.getName());
        System.out.println("========================================\n");
    }
}
