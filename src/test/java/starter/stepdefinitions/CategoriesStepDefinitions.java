package starter.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import starter.pages.CategoriesPage;
import starter.pages.AddCategoryPage;
import net.serenitybdd.annotations.Steps;

public class CategoriesStepDefinitions {

    @Steps
    CategoriesPage categoriesPage;

    @Given("the user is on the categories page")
    public void userIsOnCategoriesPage() {
        categoriesPage.openCategoriesPage();
    }

    @Given("the user is on the edit categories page for {string}")
    public void userIsOnEditCategoriesPage(String id) {
        categoriesPage.openCategoriesPage();
        categoriesPage.clickEditButtonForCategory(id);
        categoriesPage.verifyRedirectToEditCategoryPage(id);

    }

    @Then("the search button should be visible and enabled")
    public void searchButtonShouldBeVisibleAndEnabled() {
        categoriesPage.verifySearchButtonVisibleAndEnabled();
    }

    @When("the user hovers over the search button")
    public void userHoversOverSearchButton() {
        categoriesPage.hoverOverSearchButton();
    }

    @Then("the search button should be clickable")
    public void searchButtonShouldBeClickable() {
        categoriesPage.verifySearchButtonClickable();
    }

    @When("the user enters category name {string}")
    public void userEntersCategoryName(String name) {
        categoriesPage.enterCategoryName(name);
    }

    @When("the user clicks the search button")
    public void userClicksSearchButton() {
        categoriesPage.clickSearchButton();
    }

    @Then("the url should contain {string}")
    public void urlShouldContain(String content) {
        categoriesPage.verifyUrlContains(content);
    }

    @When("the user clicks the edit button for category {string}")
    public void userClicksEditButtonForCategory(String id) {
        categoriesPage.clickEditButtonForCategory(id);
    }

    @Then("the user should be redirected to the edit category page for {string}")
    public void userShouldBeRedirectedToEditCategoriesPage(String id) {
        categoriesPage.verifyRedirectToEditCategoryPage(id);
    }

    @When("the user clicks the save button")
    public void userClicksSaveButton() {
        categoriesPage.clickSaveButton();
    }

    @When("the user clicks the cancel button")
    public void userClicksCancelButton() {
        categoriesPage.clickCancelButton();
    }

    @Then("the user should be redirected to the categories page from edit category")
    public void userShouldBeRedirectedToCategoriesPage() {
        categoriesPage.verifyRedirectToCategoriesListFromEdit();
    }

    @Then("an error message should be displayed")
    public void errorMessageShouldBeDisplayed() {
        categoriesPage.verifyErrorMessageDisplayed();
    }

    @When("the user clicks the close icon on the error message")
    public void userClicksCloseIconOnErrorMessage() {
        categoriesPage.clickErrorMessageCloseButton();
    }

    @Then("the error message should be dismissed")
    public void errorMessageShouldBeDismissed() {
        categoriesPage.verifyErrorMessageDismissed();
    }

    @Steps
    AddCategoryPage addCategoryPage;

    @When("the user selects parent category {string}")
    public void userSelectsParentCategory(String parentIdentifier) {
        if (parentIdentifier.matches("\\d+")) {
            categoriesPage.selectParentCategory(parentIdentifier);
        } else {
            addCategoryPage.selectParentCategoryByText(parentIdentifier);
        }
    }

    @When("the user clicks the delete button for category for {string}")
    public void userClicksDeleteButton(String id) {
        String deleteXpath = "//form[contains(@action, '/ui/categories/delete/" + id + "')]//button";
        categoriesPage.clickDeleteButtonByXpath(deleteXpath);
    }

    @When("the user clicks the {string} button for category with Xpath {string}")
    public void userClicksDeleteButtonXpath(String buttonName, String xpath) {
        if (buttonName.equalsIgnoreCase("Delete")) {
            categoriesPage.clickDeleteButtonByXpath(xpath);
        }
    }

    @Then("a confirmation popup should be displayed")
    public void confirmationPopupDisplayed() {
        categoriesPage.verifyConfirmationPopupDisplayed();
    }

    @When("the user clicks {string} on the confirmation popup")
    public void userClicksOnConfirmationPopup(String action) {
        if (action.equalsIgnoreCase("OK")) {
            categoriesPage.clickOkOnConfirmationPopup();
        }
    }

    @Then("the category should be deleted")
    public void categoryShouldBeDeleted() {
        categoriesPage.verifyCategoryDeleted();
    }

    @Then("a success delete message {string} should be displayed")
    public void successDeleteMessageShouldBeDisplayed(String message) {
        categoriesPage.verifySuccessMessageDisplayed(message);
    }
}