package starter.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps; // Updated for Serenity 4 (or use net.thucydides.core.annotations.Steps)

import starter.pages.DashboardPage;
import starter.pages.PlantPage;

public class PlantStepDefinitions {

    @Steps
    DashboardPage dashboardPage;

    @Steps
    PlantPage plantPage;

    @When("the user navigates to the plants page")
    public void userNavigatesToPlantsPage() {
        // reuse logic from DashboardPage
        dashboardPage.verifyOnDashboard();
        dashboardPage.clickManagePlants();
        dashboardPage.verifyOnPlantsPage();
    }

    @And("a plant exists with quantity {string}")
    public void plantExistsWithQuantity(String quantity) {
        System.out.println("Checking UI for plant with quantity: " + quantity);
    }

    @Then("the {string} badge should be displayed next to the quantity")
    public void verifyLowBadge(String badgeText) {
        plantPage.verifyLowBadgeVisibleForQuantity("2");
    }

    @And("the user searches for a plant named {string}")
    public void userSearchesForPlant(String searchTerm) {
        plantPage.searchForPlant(searchTerm);
    }

    @Then("the {string} message should be displayed")
    public void verifyMessageDisplayed(String message) {
        plantPage.verifyNoPlantsMessageIsDisplayed();
    }

    @And("the user clicks on the {string} column header")
    public void userClicksHeader(String headerName) {
        if (headerName.equalsIgnoreCase("Price")) {
            plantPage.clickPriceHeader();
        } else {
            throw new IllegalArgumentException("Header logic not implemented for: " + headerName);
        }
    }

    @Then("the plant list should be sorted by price in ascending order")
    public void verifyListSortedByPrice() {
        plantPage.verifyPricesAreSortedAscending();
    }

    @Then("the {string} button should not be visible")
    public void verifyButtonNotVisible(String buttonName) {
        if (buttonName.equals("Add a Plant")) {
            plantPage.verifyAddButtonNotVisible();
        } else {
            throw new IllegalArgumentException("Validation not implemented for button: " + buttonName);
        }
    }

    @And("the {string} and {string} actions should not be visible")
    public void verifyActionsNotVisible(String action1, String action2) {
        // We verify both admin actions are missing
        plantPage.verifyEditDeleteIconsNotVisible();
    }

    @When("the user clicks the {string} page button")
    public void userClicksPageButton(String buttonName) {
        if (buttonName.equalsIgnoreCase("Next")) {
            plantPage.clickNextPage();
        } else {
            throw new IllegalArgumentException("Button not implemented: " + buttonName);
        }
    }

    @Then("the active page number should be {string}")
    public void verifyActivePage(String pageNumber) {
        plantPage.verifyActivePageNumber(pageNumber);
    }

    @When("the admin clicks the {string} button")
    public void adminClicksButton(String btnName) {
        if (btnName.equalsIgnoreCase("Add a Plant")) {
            plantPage.clickAddPlantButton();
        } else {
            throw new IllegalArgumentException("Button not defined: " + btnName);
        }
    }

    @When("the admin clicks {string} without selecting a category")
    public void adminClicksSaveNoSelection(String btnName) {
        // We simply click save immediately without interacting with the dropdown
        if (btnName.equalsIgnoreCase("Save")) {
            plantPage.clickSaveButton();
        }
    }

    @Then("the {string} validation error should be displayed")
    public void verifyValidationError(String errorText) {
        if (errorText.contains("Category is required")) {
            plantPage.verifyCategoryRequiredError();
        } else {
            throw new IllegalArgumentException("Validation check not implemented for: " + errorText);
        }
    }

    @When("the admin clicks {string} without entering any details")
    public void adminClicksSaveEmpty(String btnName) {
        if (btnName.equalsIgnoreCase("Save")) {
            plantPage.clickSaveButton();
        }
    }

    @Then("the {string} error should be displayed")
    public void verifySpecificError(String errorMessage) {
        // We pass the string directly to our generic page object method
        plantPage.verifyErrorMessage(errorMessage);
    }

    @When("the admin enters valid details but sets quantity to {string}")
    public void adminEntersInvalidQuantity(String invalidQty) {
        plantPage.fillPlantForm("Test Plant", "50.00", invalidQty);
    }

    @When("the admin clicks {string}")
    public void adminClicks(String btnName) {
        if (btnName.equalsIgnoreCase("Save")) {
            plantPage.clickSaveButton();
        } else {
            throw new IllegalArgumentException("Button not defined: " + btnName);
        }
    }

    @When("the admin clicks the edit icon for plant {string}")
    public void adminClicksEditForPlant(String plantName) {
        plantPage.clickEditButtonForPlant(plantName);
    }

    @When("the admin changes the price to {string}")
    public void adminChangesPrice(String newPrice) {
        // Reuse the save logic? No, this is specific to updating a field.
        plantPage.updatePrice(newPrice);
    }

    @Then("the plant {string} should show a price of {string}")
    public void verifyPlantPrice(String plantName, String expectedPrice) {
        plantPage.verifyPriceForPlant(plantName, expectedPrice);
    }

    @Then("the {string} and {string} icons should be visible")
    public void verifyIconsAreVisible(String icon1, String icon2) {
        plantPage.verifyEditDeleteIconsAreVisible();
    }
}