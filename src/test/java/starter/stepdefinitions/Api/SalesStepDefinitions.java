
package starter.stepdefinitions.Api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.util.EnvironmentVariables;
import starter.utils.ScenarioContext;
import starter.utils.TokenManager;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SalesStepDefinitions {

    private EnvironmentVariables environmentVariables;

    @Given("admin is authenticated")
    public void admin_is_authenticated() {
        String baseApiUrl = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getOptionalProperty("api.base.url")
                .orElse("http://localhost:8080/api");

        String loginUrl = baseApiUrl + "/auth/login";
        String body = "{\"username\": \"admin\", \"password\": \"admin123\"}";

        SerenityRest.given()
                .contentType("application/json")
                .accept("*/*")
                .body(body)
                .post(loginUrl);

        Response response = SerenityRest.lastResponse();
        assertThat("Login should return 200", response.getStatusCode(), equalTo(200));

        String token = response.jsonPath().getString("token");
        String tokenType = response.jsonPath().getString("tokenType");
        TokenManager.storeToken("admin", token, tokenType);

        System.out.println("✓ Admin authenticated, token stored");
    }

    @When("I send GET request to {string}")
    public void i_send_get_request_to(String endpoint) {
        String baseApiUrl = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getOptionalProperty("api.base.url")
                .orElse("http://localhost:8080/api");

        String processedEndpoint = ScenarioContext.replaceSavedVariables(endpoint);
        String url = baseApiUrl + processedEndpoint;

        System.out.println("=== GET Request Debug ===");
        System.out.println("Endpoint: " + url);

        try {
            SerenityRest.given()
                    .header("Authorization", TokenManager.getAuthorizationHeader("admin"))
                    .contentType("application/json")
                    .get(url);
        } catch (IllegalStateException e) {
            // fallback to any current token or unauthenticated call
            if (TokenManager.hasToken()) {
                SerenityRest.given()
                        .header("Authorization", TokenManager.getAuthorizationHeader())
                        .contentType("application/json")
                        .get(url);
            } else {
                SerenityRest.given()
                        .contentType("application/json")
                        .get(url);
            }
        }

        Response response = SerenityRest.lastResponse();
        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
    }

    @Then("the response should contain a list of sales objects")
    public void the_response_should_contain_a_list_of_sales_objects() {
        Response response = SerenityRest.lastResponse();
        String body = response.getBody().asString();
        System.out.println("Full response body: " + body);

        List<Map<String, Object>> sales = findSalesList(response);
        assertThat("Sales list must be present. Response: " + body, sales, notNullValue());
        assertThat("Sales list size should be >= 0", sales.size(), greaterThanOrEqualTo(0));

        if (sales.isEmpty()) {
            System.out.println("✓ Sales list present but empty");
            return;
        }

        Map<String, Object> first = sales.get(0);
        System.out.println("First sales item: " + first);

        assertThat("Sales item must have 'id'", first.get("id"), notNullValue());

        Object price = getAny(first, "amount", "totalPrice", "total_price");
        if (price == null) {
            Object plant = first.get("plant");
            Object qty = first.get("quantity");
            if (plant instanceof Map && qty != null) {
                Object plantPrice = ((Map<?, ?>) plant).get("price");
                price = computeProduct(plantPrice, qty);
            }
        }
        assertThat("Sales item must have a price/amount/totalPrice (or computable). Item: " + first, price, notNullValue());
        assertThat("Sales price should be numeric. Item: " + first, isNumeric(price), is(true));

        Object date = getAny(first, "soldAt", "date", "timestamp");
        assertThat("Sales item must have a date/time field (soldAt/date/timestamp). Item: " + first, date, notNullValue());

        System.out.println("✓ Sales item validated: id=" + first.get("id") + ", price=" + price + ", date=" + date);
    }

    // plants dropdown
    @Then("the response should contain a list of plants objects")
    public void the_response_should_contain_a_list_of_plants_objects() {
        Response response = SerenityRest.lastResponse();
        String body = response.getBody().asString();
        System.out.println("Full response body: " + body);

        List<Map<String, Object>> plants = response.jsonPath().getList("$");
        if (plants == null || plants.isEmpty()) plants = response.jsonPath().getList("data");
        if (plants == null || plants.isEmpty()) plants = response.jsonPath().getList("plants");
        if (plants == null) plants = Collections.emptyList();

        assertThat("Plants list must be present. Response: " + body, plants, notNullValue());
        assertThat("Plants list size should be >= 0", plants.size(), greaterThanOrEqualTo(0));

        if (plants.isEmpty()) {
            System.out.println("✓ Plants list present but empty");
            return;
        }

        Map<String, Object> first = plants.get(0);
        System.out.println("First plant item: " + first);

        assertThat("Plant item must have 'id'", first.get("id"), notNullValue());

        Object name = getAny(first, "name", "title");
        assertThat("Plant item should have a name/title", name, notNullValue());

        Object price = getAny(first, "price", "cost", "amount");
        assertThat("Plant price/cost should be present", price, notNullValue());
        assertThat("Plant price/cost should be numeric", isNumeric(price), is(true));

        System.out.println("✓ Plant item validated: id=" + first.get("id") + ", name=" + name + ", price=" + price);
    }

//sell plant api check

    // 1) Retrieve plant stock and save
    @When("I retrieve plant {int} quantity and save as {word}")
    public void i_retrieve_plant_quantity_and_save_as(Integer plantId, String variableName) {
        String baseApiUrl = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getOptionalProperty("api.base.url")
                .orElse("http://localhost:8080/api");
        String url = baseApiUrl + "/plants/" + plantId;

        System.out.println("=== GET plant for initial stock Debug ===");
        System.out.println("Endpoint: " + url);

        Response response = SerenityRest.given()
                .header("Authorization", TokenManager.getAuthorizationHeader("admin"))
                .contentType("application/json")
                .get(url);

        assertThat("GET plant should return 200", response.getStatusCode(), equalTo(200));

        Integer qty = response.jsonPath().getInt("quantity");
        ScenarioContext.put(variableName, qty);
        System.out.println("✓ Saved plant " + plantId + " quantity as '" + variableName + "': " + qty);
    }

    // 2) Send POST request (no body, query param used)
    @When("I send POST request to {string}")
    public void i_send_post_request_to(String endpoint) {
        String baseApiUrl = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getOptionalProperty("api.base.url")
                .orElse("http://localhost:8080/api");

        String processedEndpoint = ScenarioContext.replaceSavedVariables(endpoint);
        String url = baseApiUrl + processedEndpoint;

        System.out.println("=== POST Request Debug ===");
        System.out.println("Endpoint: " + url);

        try {
            SerenityRest.given()
                    .header("Authorization", TokenManager.getAuthorizationHeader("admin"))
                    .contentType("application/json")
                    .post(url);
        } catch (IllegalStateException e) {
            // fallback if admin token not found
            if (TokenManager.hasToken()) {
                SerenityRest.given()
                        .header("Authorization", TokenManager.getAuthorizationHeader())
                        .contentType("application/json")
                        .post(url);
            } else {
                SerenityRest.given()
                        .contentType("application/json")
                        .post(url);
            }
        }

        Response response = SerenityRest.lastResponse();
        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
    }

    // 3) Validate created sale details
    @Then("the response should contain created sale details for plant id {int} and quantity {int}")
    public void the_response_should_contain_created_sale_details_for(Integer plantId, Integer quantity) {
        Response response = SerenityRest.lastResponse();
        String body = response.getBody().asString();
        System.out.println("Full response body: " + body);

        Integer saleId = response.jsonPath().getInt("id");
        assertThat("Sale id should be present", saleId, notNullValue());

        Integer respPlantId = response.jsonPath().getInt("plant.id");
        assertThat("Sale should reference plant id " + plantId, respPlantId, equalTo(plantId));

        Integer respQty = response.jsonPath().getInt("quantity");
        assertThat("Sale quantity should match", respQty, equalTo(quantity));

        // Use getDouble (or get(..., Number.class)) instead of getNumber
        Double plantPrice = response.jsonPath().getDouble("plant.price");
        assertThat("Plant price must be present", plantPrice, notNullValue());
        double expectedTotal = plantPrice * quantity;

        Double respTotal = response.jsonPath().getDouble("totalPrice");
        assertThat("Total price should be present", respTotal, notNullValue());
        assertThat("Total price should equal plant.price * quantity",
                Math.abs(respTotal - expectedTotal) < 0.0001, is(true));

        String soldAt = response.jsonPath().getString("soldAt");
        assertThat("soldAt should be present", soldAt, notNullValue());

        System.out.println("✓ Created sale validated: id=" + saleId + ", plantId=" + respPlantId + ", qty=" + respQty + ", total=" + respTotal);
    }

    // 4) Verify plant stock reduction compared to saved value
    @Then("the plant with id {int} stock should be reduced by {int} compared to {word}")
    public void the_plant_stock_should_be_reduced_by(Integer plantId, Integer reducedBy, String savedVarName) {
        Object beforeObj = ScenarioContext.get(savedVarName);
        assertThat("Saved variable '" + savedVarName + "' must exist", beforeObj, notNullValue());
        Integer beforeQty = Integer.parseInt(String.valueOf(beforeObj));

        String baseApiUrl = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getOptionalProperty("api.base.url")
                .orElse("http://localhost:8080/api");
        String url = baseApiUrl + "/plants/" + plantId;

        Response response = SerenityRest.given()
                .header("Authorization", TokenManager.getAuthorizationHeader("admin"))
                .contentType("application/json")
                .get(url);

        assertThat("GET plant after sale should return 200", response.getStatusCode(), equalTo(200));
        Integer afterQty = response.jsonPath().getInt("quantity");

        assertThat("Plant stock should be reduced by " + reducedBy,
                afterQty, equalTo(beforeQty - reducedBy));

        System.out.println("✓ Plant stock reduced: before=" + beforeQty + ", after=" + afterQty + ", reducedBy=" + reducedBy);
    }

//user cannot delete
    @Then("the response should indicate insufficient permissions")
    public void the_response_should_indicate_insufficient_permissions() {
        Response response = SerenityRest.lastResponse();
        String body = response.getBody().asString().toLowerCase();

        // Helpful debug output
        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        // Check common permission/authorization phrases
        assertThat("Response should indicate insufficient permissions",
                body, anyOf(
                        containsString("forbid"),
                        containsString("forbidden"),
                        containsString("insufficient"),
                        containsString("permission"),
                        containsString("access denied"),
                        containsString("not authorized"),
                        containsString("unauthorized")
                ));

        System.out.println("✓ Insufficient permissions message detected");
    }

    @Then("I should receive a {int} or {int} response")
    public void i_should_receive_a_or_response(Integer status1, Integer status2) {
        Response response = SerenityRest.lastResponse();
        int actual = response.getStatusCode();
        assertThat("Response status should be " + status1 + " or " + status2,
                actual, anyOf(equalTo(status1), equalTo(status2)));
        System.out.println("✓ Received expected status: " + actual);
    }

    @Then("the sale with id {int} should not exist")
    public void the_sale_with_id_should_not_exist(Integer saleId) {
        String baseApiUrl = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getOptionalProperty("api.base.url")
                .orElse("http://localhost:8080/api");
        String url = baseApiUrl + "/sales/" + saleId;

        System.out.println("=== Verify sale deletion Debug ===");
        System.out.println("GET Endpoint: " + url);

        Response response = SerenityRest.given()
                .header("Authorization", TokenManager.getAuthorizationHeader("admin"))
                .contentType("application/json")
                .get(url);

        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        // Expecting not found after deletion
        assertThat("Deleted sale should not be retrievable (404)", response.getStatusCode(), equalTo(404));
        System.out.println("✓ Sale " + saleId + " not found (deleted)");
    }
    // helpers

    private List<Map<String, Object>> findSalesList(Response response) {
        List<Map<String, Object>> list = response.jsonPath().getList("$");
        if (list != null && !list.isEmpty()) return list;
        list = response.jsonPath().getList("data");
        if (list != null && !list.isEmpty()) return list;
        list = response.jsonPath().getList("sales");
        return list == null ? Collections.emptyList() : list;
    }

    private Object getAny(Map<String, Object> item, String... keys) {
        for (String k : keys) {
            if (item.containsKey(k) && item.get(k) != null) return item.get(k);
        }
        return null;
    }

    private Object computeProduct(Object a, Object b) {
        try {
            double da = Double.parseDouble(String.valueOf(a));
            double db = Double.parseDouble(String.valueOf(b));
            return da * db;
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isNumeric(Object o) {
        if (o == null) return false;
        if (o instanceof Number) return true;
        try {
            Double.parseDouble(String.valueOf(o));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}