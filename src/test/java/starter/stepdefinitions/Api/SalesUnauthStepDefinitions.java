package starter.stepdefinitions.Api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.util.EnvironmentVariables;
import starter.utils.ScenarioContext;
import starter.utils.TokenManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SalesUnauthStepDefinitions {

    private EnvironmentVariables environmentVariables;

    @Given("no user is logged in")
    public void no_user_is_logged_in() {
        // Clear stored tokens and scenario context to ensure unauthenticated state
        TokenManager.clearTokens();
        ScenarioContext.clear();
        System.out.println("✓ Cleared tokens and scenario context (unauthenticated)");
    }

    @Then("the response should indicate access is denied")
    public void the_response_should_indicate_access_is_denied() {
        Response response = SerenityRest.lastResponse();
        String body = response.getBody().asString();

        // Basic checks: 401 expected (this step complements the explicit 401 assertion in feature)
        assertThat("Response status should be 401 or Unauthorized", response.getStatusCode(), anyOf(equalTo(401), equalTo(403)));

        // Check common access denied messages
        assertThat("Response should mention access denied/unauthorized",
                body, anyOf(
                        containsStringIgnoreCase("access denied"),
                        containsStringIgnoreCase("unauthorized"),
                        containsStringIgnoreCase("unauthorized"),
                        containsStringIgnoreCase("not authorized"),
                        containsStringIgnoreCase("access is denied")
                ));

        System.out.println("✓ Access denied validated. Status: " + response.getStatusCode() + " Body: " + body);
    }

    // helper matcher to support case-insensitive containsString
    private static org.hamcrest.Matcher<String> containsStringIgnoreCase(String str) {
        return org.hamcrest.CoreMatchers.containsString(str.toLowerCase()) == null
                ? org.hamcrest.CoreMatchers.containsString(str)
                : new org.hamcrest.TypeSafeDiagnosingMatcher<String>() {
            @Override
            public void describeTo(org.hamcrest.Description description) {
                description.appendText("a string containing (ignore case) ").appendValue(str);
            }
            @Override
            protected boolean matchesSafely(String item, org.hamcrest.Description mismatchDescription) {
                if (item == null) {
                    mismatchDescription.appendText("was null");
                    return false;
                }
                return item.toLowerCase().contains(str.toLowerCase());
            }
        };
    }
}