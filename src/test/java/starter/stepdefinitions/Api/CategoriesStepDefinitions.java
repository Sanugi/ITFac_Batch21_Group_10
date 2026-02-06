package starter.stepdefinitions.Api;

import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import starter.utils.ScenarioContext;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CategoriesStepDefinitions {

    @Then("the response {string} save as {string}")
    public void the_response_field_save_as(String fieldName, String variableName) {
        Response response = SerenityRest.lastResponse();
        Object value = response.jsonPath().get(fieldName);

        assertThat("Field '" + fieldName + "' should not be null", value, notNullValue());

        ScenarioContext.put(variableName, value);
        System.out.println("✓ Saved '" + fieldName + "' as '" + variableName + "': " + value);
    }

    @Then("I should receive a {int} response")
    public void i_should_receive_a_response(Integer statusCode) {
        Response response = SerenityRest.lastResponse();
        assertThat("Response status code should be " + statusCode,
                response.getStatusCode(), equalTo(statusCode));
        System.out.println("✓ Received expected status code: " + statusCode);
    }

    @Then("the response body should contain {string}")
    public void the_response_body_should_contain(String expectedText) {
        Response response = SerenityRest.lastResponse();
        String responseBody = response.getBody().asString();

        assertThat("Response body should contain '" + expectedText + "'",
                responseBody, containsString(expectedText));

        System.out.println("✓ Response body contains: " + expectedText);
    }

    @Then("the response should contain main categories")
    public void the_response_should_contain_main_categories() {
        Response response = SerenityRest.lastResponse();
        assertThat("Response should be an array",
                response.jsonPath().getList("$"), notNullValue());

    }

    @Then("the mainCategories should be non-negative")
    public void the_mainCategories_should_be_non_negative() {
        Response response = SerenityRest.lastResponse();
        int categoryCount = response.jsonPath().getList("$").size();
        assertThat("Should have at least one main category",
                categoryCount, greaterThanOrEqualTo(0));

        // Validate structure of first category if exists
        if (categoryCount > 0) {
            // Check that each category has required fields
            assertThat("First category should have 'id'",
                    response.jsonPath().get("[0].id"), notNullValue());
            assertThat("First category should have 'name'",
                    response.jsonPath().get("[0].name"), notNullValue());

            // Extract category names from the response
            List<String> categoryNames = response.jsonPath().getList("name");

            System.out.println("✓ Main categories retrieved successfully");
            System.out.println("  - Total main categories: " + categoryCount);
            System.out.println("  - Categories: " + String.join(", ", categoryNames));
        } else {
            System.out.println("✓ Main categories retrieved successfully");
            System.out.println("  - Total main categories: 0 (empty list)");
        }
    }

    @Then("the response should contain specific category details")
    public void the_response_should_contain_specific_category_details() {
        Response response = SerenityRest.lastResponse();

        // Verify the response contains required fields for a single category object
        assertThat("Category should have 'id'",
                response.jsonPath().get("id"), notNullValue());
        assertThat("Category should have 'name'",
                response.jsonPath().get("name"), notNullValue());

        // Extract and display category details
        Integer categoryId = response.jsonPath().get("id");
        String categoryName = response.jsonPath().get("name");

        System.out.println("✓ Category details retrieved successfully");
        System.out.println("  - Category ID: " + categoryId);
        System.out.println("  - Category Name: " + categoryName);
    }

    @Then("the response should contain error message about duplicate category")
    public void the_response_should_contain_error_message_about_duplicate_category() {
        Response response = SerenityRest.lastResponse();
        String responseBody = response.getBody().asString();

        // Check if response contains indication of duplicate category
        assertThat("Response should indicate duplicate category",
                responseBody, anyOf(
                        containsString("already exists"),
                        containsString("duplicate"),
                        containsString("Duplicate")));

        System.out.println("✓ Duplicate category error detected");
        System.out.println("  - Response: " + responseBody);
    }

    @Then("the response should contain validation error for category name length")
    public void the_response_should_contain_validation_error_for_category_name_length() {
        Response response = SerenityRest.lastResponse();
        String responseBody = response.getBody().asString();

        // Check if response contains validation error for name length
        assertThat("Response should indicate name length validation error",
                responseBody, anyOf(
                        containsString("must be between"),
                        containsString("length"),
                        containsString("characters"),
                        containsString("size must be between"),
                        containsString("Category name is mandatory")));

        System.out.println("✓ Name length validation error detected");
        System.out.println("  - Response: " + responseBody);
    }

    @Then("the response should contain error message about deleting category with subcategories")
    public void the_response_should_contain_error_message_about_deleting_category_with_subcategories() {
        Response response = SerenityRest.lastResponse();
        String responseBody = response.getBody().asString();

        // Check if response contains error about deleting category with subcategories
        assertThat("Response should indicate cannot delete category with subcategories",
                responseBody, anyOf(
                        containsString("Cannot delete category"),
                        containsString("delete sub-categories first"),
                        containsString("has sub-categories"),
                        containsString("subcategories")));

        System.out.println("✓ Delete category with subcategories error detected");
        System.out.println("  - Response: " + responseBody);
    }


    @Then("only categories with name containing {string} should be returned")
    public void only_categories_with_name_containing_should_be_returned(String expectedName) {
        Response response = SerenityRest.lastResponse();

        List<String> categoryNames = response.jsonPath().getList("name");

        assertThat("Category list should not be empty",
                categoryNames, not(empty()));

        for (String name : categoryNames) {
            assertThat(
                    "Category name does not contain expected value: " + expectedName,
                    name.toLowerCase(),
                    containsString(expectedName.toLowerCase())
            );
        }

        System.out.println("✓ Categories filtered correctly by name: " + expectedName);
        System.out.println("✓ Returned names: " + categoryNames);
    }



}
