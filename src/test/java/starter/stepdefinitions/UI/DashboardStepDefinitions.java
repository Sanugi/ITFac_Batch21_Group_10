package starter.stepdefinitions.UI;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import starter.pages.DashboardPage;
import net.serenitybdd.annotations.Steps;

public class DashboardStepDefinitions {

    @Steps
    DashboardPage dashboardPage;

    @Given("the user is on the dashboard page")
    public void userIsOnDashboardPage() {
        dashboardPage.verifyOnDashboard();
    }

    @When("the user clicks on the \"Manage Categories\" link")
    public void userClicksManageCategories() {
        dashboardPage.clickManageCategories();
    }

    @Then("the user should be redirected to the categories page")
    public void userShouldBeOnCategoriesPage() {
        dashboardPage.verifyOnCategoriesPage();
    }

    @When("the user clicks on the \"Manage Plants\" link")
    public void userClicksManagePlants() {
        dashboardPage.clickManagePlants();
    }

    @Then("the user should be redirected to the plants page")
    public void userShouldBeOnPlantsPage() {
        dashboardPage.verifyOnPlantsPage();
    }

    @When("the user clicks on the \"View Sales\" link")
    public void userClicksViewSales() {
        dashboardPage.clickViewSales();
    }

    @Then("the user should be redirected to the sales page")
    public void userShouldBeOnSalesPage() {
        dashboardPage.verifyOnSalesPage();
    }
}