package starter.stepdefinitions.Api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.util.EnvironmentVariables;
import starter.utils.TokenManager;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AuthStepDefinitions {

    private EnvironmentVariables environmentVariables;

    @When("I authenticate with username {string} and password {string}")
    public void i_authenticate_with_username_and_password(String username, String password) {
        String baseApiUrl = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getOptionalProperty("api.base.url")
                .orElse("http://localhost:8080/api");

        String loginUrl = baseApiUrl + "/auth/login";
        // Simple JSON construction for this specific case
        String body = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);

        SerenityRest.given()
                .contentType("application/json")
                .accept("*/*")
                .body(body)
                .post(loginUrl);
    }

    @Given("I am authenticated as {string} with password {string}")
    public void i_am_authenticated_as_user(String username, String password) {
        i_authenticate_with_username_and_password(username, password);

        // Store the token if login was successful
        Response response = SerenityRest.lastResponse();
        if (response.getStatusCode() == 200) {
            String token = response.jsonPath().getString("token");
            String tokenType = response.jsonPath().getString("tokenType");

            // Store with username as key (e.g., "testuser", "admin")
            TokenManager.storeToken(username, token, tokenType);
            System.out.println("✓ Token stored for user: " + username);
        }
    }

    @Given("I am authenticated as a user")
    public void i_am_authenticated_as_a_user() {
        i_am_authenticated_as_user("testuser", "test123");
    }

    @Given("I am authenticated as an admin")
    public void i_am_authenticated_as_an_admin() {
        i_am_authenticated_as_user("admin", "admin123");
    }

    @When("I send an authenticated GET request to {string}")
    public void i_send_authenticated_get_request(String endpoint) {
        String baseApiUrl = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getOptionalProperty("api.base.url")
                .orElse("http://localhost:8080/api");

        // Replace any saved variables in the endpoint
        String processedEndpoint = starter.utils.ScenarioContext.replaceSavedVariables(endpoint);

        System.out.println("=== GET Request Debug ===");
        System.out.println("Endpoint: " + baseApiUrl + processedEndpoint);

        Response response = SerenityRest.given()
                .header("Authorization", TokenManager.getAuthorizationHeader())
                .contentType("application/json")
                .get(baseApiUrl + processedEndpoint);
    }

    @When("I send an authenticated POST request to {string} with body:")
    public void i_send_authenticated_post_request(String endpoint, String body) {
        String baseApiUrl = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getOptionalProperty("api.base.url")
                .orElse("http://localhost:8080/api");

        // Replace {random} placeholders with random numbers
        String processedBody = starter.utils.ScenarioContext.processBodyWithRandomValues(body);

        // Replace any saved variables in the body
        processedBody = starter.utils.ScenarioContext.replaceSavedVariables(processedBody);

        System.out.println("=== POST Request Debug ===");
        System.out.println("Endpoint: " + baseApiUrl + endpoint);
        System.out.println("Body: " + processedBody);

        Response response = SerenityRest.given()
                .header("Authorization", TokenManager.getAuthorizationHeader())
                .contentType("application/json")
                .body(processedBody)
                .post(baseApiUrl + endpoint);

        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("========================");
    }

    @When("I send an authenticated PUT request to {string} with body:")
    public void i_send_authenticated_put_request(String endpoint, String body) {
        String baseApiUrl = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getOptionalProperty("api.base.url")
                .orElse("http://localhost:8080/api");

        // Replace any saved variables in the endpoint
        String processedEndpoint = starter.utils.ScenarioContext.replaceSavedVariables(endpoint);

        // Replace {random} placeholders with random numbers
        String processedBody = starter.utils.ScenarioContext.processBodyWithRandomValues(body);

        // Replace any saved variables in the body
        processedBody = starter.utils.ScenarioContext.replaceSavedVariables(processedBody);

        System.out.println("=== PUT Request Debug ===");
        System.out.println("Endpoint: " + baseApiUrl + processedEndpoint);
        System.out.println("Body: " + processedBody);

        Response response = SerenityRest.given()
                .header("Authorization", TokenManager.getAuthorizationHeader())
                .contentType("application/json")
                .body(processedBody)
                .put(baseApiUrl + processedEndpoint);

        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("========================");
    }

    @When("I send an authenticated DELETE request to {string}")
    public void i_send_authenticated_delete_request(String endpoint) {
        String baseApiUrl = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getOptionalProperty("api.base.url")
                .orElse("http://localhost:8080/api");

        // Replace any saved variables in the endpoint
        String processedEndpoint = starter.utils.ScenarioContext.replaceSavedVariables(endpoint);

        System.out.println("=== DELETE Request Debug ===");
        System.out.println("Endpoint: " + baseApiUrl + processedEndpoint);

        Response response = SerenityRest.given()
                .header("Authorization", TokenManager.getAuthorizationHeader())
                .contentType("application/json")
                .delete(baseApiUrl + processedEndpoint);

        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Body: " + SerenityRest.lastResponse().getBody().asString());
        System.out.println("============================");
    }

    @Then("the API should respond with status {int}")
    public void the_api_should_respond_with_status_ok(int status) {
        SerenityRest.then().statusCode(status);
    }

    @Then("the response should contain a {string}")
    public void the_response_should_contain_a(String key) {
        SerenityRest.then().body(key, notNullValue());
    }

    @Then("the response should contain {string} as {string}")
    public void the_response_should_contain_key_as_value(String key, String value) {
        SerenityRest.then().body(key, equalTo(value));
    }

    @Then("the response should contain an error message")
    public void the_response_should_contain_an_error_message() {
        // Check if response contains either "error" or "message" field
        SerenityRest.then()
                .body("$", org.hamcrest.Matchers.anyOf(
                        org.hamcrest.Matchers.hasKey("error"),
                        org.hamcrest.Matchers.hasKey("message")));
    }

    @Then("I save the authentication token")
    public void i_save_the_authentication_token() {
        Response response = SerenityRest.lastResponse();
        String token = response.jsonPath().getString("token");
        String tokenType = response.jsonPath().getString("tokenType");
        TokenManager.storeToken(token, tokenType);
        System.out.println(
                "✓ Token saved: " + tokenType + " " + token.substring(0, Math.min(20, token.length())) + "...");
    }

    @Then("the response should contain error {string}")
    public void the_response_should_contain_error(String expectedError) {
        SerenityRest.then().body("error", equalTo(expectedError));
        System.out.println("✓ Response contains expected error: " + expectedError);
    }
}
