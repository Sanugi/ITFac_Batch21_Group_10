package starter.stepdefinitions.Api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.model.util.EnvironmentVariables;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PlantValidationStepDefinition {

    private EnvironmentVariables env() {
        return net.serenitybdd.core.Serenity.environmentVariables();
    }

    private String baseUrl() {
        String baseUrl = EnvironmentSpecificConfiguration.from(env()).getProperty("api.base.url");
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("api.base.url is not set");
        }
        return baseUrl;
    }

    private RequestSpecification authRequestForRole(String role) {
        String token = EnvironmentSpecificConfiguration.from(env()).getProperty("api." + role + ".token");
        String username = EnvironmentSpecificConfiguration.from(env()).getProperty("api." + role + ".username");
        if (username == null || username.isBlank()) {
            username = role;
        }
        String password = EnvironmentSpecificConfiguration.from(env()).getProperty("api." + role + ".password");
        if (password == null || password.isBlank()) {
            password = role;
        }

        RequestSpecification req = SerenityRest.given()
                .baseUri(baseUrl())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);

        if (token != null && !token.isBlank()) {
            req.auth().oauth2(token);
        } else {
            req.auth().preemptive().basic(username, password);
        }
        return req;
    }

    @Given("Admin is authenticated")
    public void adminIsAuthenticated() {
    }

    @Given("User is authenticated")
    public void userIsAuthenticated() {
    }

    @Given("User is not authenticated")
    public void userIsNotAuthenticated() {
    }

    @When("Admin sends an authenticated GET request to {string}")
    public void adminSendsAuthenticatedGET(String path) {
        SerenityRest.given().spec(authRequestForRole("admin")).when().get(path);
    }

    @When("User sends an authenticated GET request to {string}")
    public void userSendsAuthenticatedGET(String path) {
        SerenityRest.given().spec(authRequestForRole("user")).when().get(path);
    }

    @When("Admin sends an authenticated POST request to {string} with body:")
    public void adminSendsAuthenticatedPOST(String path, String body) {
        SerenityRest.given().spec(authRequestForRole("admin")).body(body).when().post(path);
    }

    @When("User sends an authenticated POST request to {string} with body:")
    public void userSendsAuthenticatedPOST(String path, String body) {
        SerenityRest.given().spec(authRequestForRole("user")).body(body).when().post(path);
    }

    @When("Admin sends an authenticated PUT request to {string} with body:")
    public void adminSendsAuthenticatedPUT(String path, String body) {
        SerenityRest.given().spec(authRequestForRole("admin")).body(body).when().put(path);
    }

    @When("User sends an authenticated PUT request to {string} with body:")
    public void userSendsAuthenticatedPUT(String path, String body) {
        SerenityRest.given().spec(authRequestForRole("user")).body(body).when().put(path);
    }

    @When("User sends an unauthenticated GET request to {string}")
    public void userSendsUnauthenticatedGET(String path) {
        SerenityRest.given()
                .baseUri(baseUrl())
                .accept(ContentType.JSON)
                .redirects().follow(false)
                .when().get(path);
    }

    @Then("the API should respond with status {int}")
    public void theAPIShouldRespondWithStatus(Integer status) {
        SerenityRest.then().statusCode(status);
    }

    @Then("the response should contain {string} header with value {string}")
    public void theResponseShouldContainHeaderWithValue(String header, String value) {
        String actual = SerenityRest.lastResponse().getHeader(header);
        if (actual == null || !actual.equals(value)) {
            throw new AssertionError("Header mismatch: " + header +
                    " | Expected: " + value + " | Actual: " + actual);
        }
    }

    @Then("the response should contain error {string}")
    public void theResponseShouldContainError(String message) {
        String body = SerenityRest.lastResponse().asString();
        if (!body.contains(message)) {
            throw new AssertionError("Expected error not found: " + message + " | Body: " + body);
        }
    }

    @Then("the response list should be ordered as {string}")
    public void theResponseListShouldBeOrderedAs(String expectedCsv) {
        List<String> expected = Arrays.stream(expectedCsv.split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        List<String> actual = SerenityRest.lastResponse()
                .jsonPath()
                .getList("content.name");

        if (actual == null || actual.size() < expected.size()) {
            throw new AssertionError("Response list is smaller than expected. Actual: " + actual);
        }

        for (int i = 0; i < expected.size(); i++) {
            if (!actual.get(i).equalsIgnoreCase(expected.get(i))) {
                throw new AssertionError("Order mismatch at index " + i +
                        " | Expected: " + expected.get(i) +
                        " | Actual: " + actual.get(i));
            }
        }
    }

    @Then("the response should contain error message {string}")
    public void theResponseShouldContainErrorMessage(String message) {
        String body = SerenityRest.lastResponse().asString();
        if (!body.contains(message)) {
            throw new AssertionError("Expected error message not found: " + message + " | Body: " + body);
        }
    }

    @Then("the response should contain {string} as {string}")
    public void theResponseShouldContainAs(String field, String expected) {
        String actual = SerenityRest.lastResponse().jsonPath().getString(field);

        if (actual == null) {
            String body = SerenityRest.lastResponse().asString();
            if (!body.contains("\"" + field + "\"")) {
                throw new AssertionError("Field not found: " + field + " | Body: " + body);
            }
        } else if (!actual.equals(expected)) {
            throw new AssertionError("Field mismatch for " + field +
                    " | Expected: " + expected + " | Actual: " + actual);
        }
    }

    @Then("the response should contain plants filtered by name {string} and categoryId {string}")
    public void theResponseShouldContainPlantsFilteredByNameAndCategory(String name, String categoryId) {
        List<String> names = SerenityRest.lastResponse().jsonPath().getList("content.name");
        List<Integer> categoryIds = SerenityRest.lastResponse().jsonPath().getList("content.categoryId");

        if (names == null || categoryIds == null || names.size() != categoryIds.size()) {
            throw new AssertionError("Response content is missing name/categoryId fields.");
        }

        int expectedCategoryId = Integer.parseInt(categoryId);
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i) == null || !names.get(i).toLowerCase().contains(name.toLowerCase())) {
                throw new AssertionError("Name filter mismatch at index " + i + " | Actual: " + names.get(i));
            }
            if (!categoryIds.get(i).equals(expectedCategoryId)) {
                throw new AssertionError("CategoryId mismatch at index " + i +
                        " | Expected: " + expectedCategoryId + " | Actual: " + categoryIds.get(i));
            }
        }
    }

    @Then("the response should contain plants with name containing {string}")
    public void theResponseShouldContainPlantsWithNameContaining(String part) {
        List<String> names = SerenityRest.lastResponse().jsonPath().getList("content.name");
        if (names == null || names.isEmpty()) {
            throw new AssertionError("No plant names found in response.");
        }
        for (String n : names) {
            if (n == null || !n.toLowerCase().contains(part.toLowerCase())) {
                throw new AssertionError("Name does not contain '" + part + "': " + n);
            }
        }
    }

    @Then("the response should contain exactly {int} plants in content")
    public void theResponseShouldContainExactlyPlantsInContent(Integer count) {
        List<Object> content = SerenityRest.lastResponse().jsonPath().getList("content");
        if (content == null || content.size() != count) {
            throw new AssertionError("Expected content size " + count + " but was " +
                    (content == null ? 0 : content.size()));
        }
    }
}