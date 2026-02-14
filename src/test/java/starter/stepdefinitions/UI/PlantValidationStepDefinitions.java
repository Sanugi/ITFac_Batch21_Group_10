package starter.stepdefinitions.UI;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import starter.pages.PlantValidation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PlantValidationStepDefinitions {

    private PlantValidation page() {
        return (PlantValidation) Serenity.getCurrentSession().get(PlantValidation.class);
    }

    @When("the user is on the plants page")
    public void theUserIsOnThePlantsPage() {
        page().openAddPlantPage();
    }

    @And("the admin leaves the plant name empty")
    public void theAdminLeavesThePlantNameEmpty() {
        page().leaveNameEmpty();
    }

    @And("the admin enters plant name {string}")
    public void theAdminEntersPlantName(String name) {
        page().enterName(name);
    }

    @And("the admin selects plant category {string}")
    public void theAdminSelectsPlantCategory(String category) {
        page().selectCategory(category);
    }

    @And("the admin leaves the category unselected")
    public void theAdminLeavesTheCategoryUnselected() {
        page().leaveCategoryUnselected();
    }

    @And("the admin leaves the plant price empty")
    public void theAdminLeavesThePlantPriceEmpty() {
        page().leavePriceEmpty();
    }

    @And("the admin enters plant price {string}")
    public void theAdminEntersPlantPrice(String price) {
        page().enterPrice(price);
    }

    @And("the admin leaves the plant quantity empty")
    public void theAdminLeavesThePlantQuantityEmpty() {
        page().leaveQuantityEmpty();
    }

    @And("the admin enters plant quantity {string}")
    public void theAdminEntersPlantQuantity(String qty) {
        page().enterQuantity(qty);
    }

    @And("the admin clicks the save button")
    public void theAdminClicksTheSaveButton() {
        page().clickSave();
    }

    @And("the admin clicks the cancel button")
    public void theAdminClicksTheCancelButton() {
        page().clickCancel();
    }

    @Then("validation error {string} should be displayed below the name field")
    public void validationErrorShouldBeDisplayedBelowTheNameField(String message) {
        page().shouldShowErrorForName(message);
    }

    @Then("validation error {string} should be displayed below the category field")
    public void validationErrorShouldBeDisplayedBelowTheCategoryField(String message) {
        page().shouldShowErrorForCategory(message);
    }

    @Then("validation error {string} should be displayed below the price field")
    public void validationErrorShouldBeDisplayedBelowThePriceField(String message) {
        page().shouldShowErrorForPrice(message);
    }

    @Then("validation error {string} should be displayed below the quantity field")
    public void validationErrorShouldBeDisplayedBelowTheQuantityField(String message) {
        page().shouldShowErrorForQuantity(message);
    }

    @Then("the category dropdown should show sub-categories {string}")
    public void theCategoryDropdownShouldShowSubCategories(String csv) {
        List<String> expected = Arrays.stream(csv.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        page().shouldShowSubCategories(expected);
    }

    @Then("the admin should be returned to the plant list page")
    public void theAdminShouldBeReturnedToThePlantListPage() {
        page().shouldBeOnPlantListPage();
    }

     @And("the admin searches plant name {string}")
    public void theAdminSearchesPlantName(String name) {
        page().searchByName(name);
    }

    @When("the admin clicks the Reset button")
    public void theAdminClicksTheResetButton() {
        page().clickReset();
    }

    @Then("the plant search field should be empty")
    public void thePlantSearchFieldShouldBeEmpty() {
        page().shouldSearchFieldBeEmpty();
    }

    @Then("the full list of plants should be displayed")
    public void theFullListOfPlantsShouldBeDisplayed() {
        page().shouldShowFullList();
    }

    @Then("the plant table should show {string} with category {string} and price {string}")
    public void thePlantTableShouldShow(String name, String category, String price) {
        page().shouldShowRow(name, category, price);
    }

    @And("the user searches plant name {string}")
    public void theUserSearchesPlantName(String name) {
        page().searchByName(name);
    }

    @When("the user clicks the Reset button")
    public void theUserClicksTheResetButton() {
        page().clickReset();
    }

    @Then("the Edit button should not be visible in the plant table")
    public void theEditButtonShouldNotBeVisibleInThePlantTable() {
        page().shouldHideEditButtons();
    }
}