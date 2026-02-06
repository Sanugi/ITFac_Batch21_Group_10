package starter.stepdefinitions.Api;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DashboardStepDefinitions {

    @Then("the {string} should be non-negative")
    public void the_card_values_should_be_non_negative(String value) {
        Response response = SerenityRest.lastResponse();
        Integer total = response.jsonPath().getInt(value);

        assertThat(value + " should be non-negative", total, greaterThanOrEqualTo(0));
        System.out.println("✓ " + value + " is valid: " + total);
    }

}
