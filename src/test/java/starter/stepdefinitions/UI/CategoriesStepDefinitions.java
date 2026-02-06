package starter.stepdefinitions.UI;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import starter.pages.CategoriesPage;
import starter.pages.AddCategoryPage;
import net.serenitybdd.annotations.Steps;
import starter.pages.DashboardPage;
import starter.pages.NavbarPage;

public class CategoriesStepDefinitions {

    @Steps
    CategoriesPage categoriesPage;
    DashboardPage dashboardPage;
    NavbarPage navBar;


    @When("the user clicks on the {string} option in the side navigation bar")
    public void userClicksOnTheCategoriesOptionInTheSideNavigationBar(String categories){
        if (categories.equals("Categories")) {
            navBar.clickAddCategoryButton();
        }
    }

    @Then("the Categories page should be displayed")
    public void categoriesPageShouldBeDisplayed(){
        categoriesPage.openCategoriesPage();
    }

    @Given("subcategory search field is visible")
    public void subcategorySearchFieldIsVisible(){
        categoriesPage.verifySubCategoryFieldVisibleAndEnabled();
    }

    @Then("the url should be {string}")
    public void urlShouldBe(String content){
        categoriesPage.verifyUrlContains(content);
    }

    @Given("the All Parents dropdown field is visible")
    public void allParentsDropDownFieldIsVisible(){
        categoriesPage.verifyAllParentsDropDownFieldVisibleAndEnabled();
    }

    @Given("the user is on the page {string}")
    public void userIsOnPage(String pageNumber) {
        categoriesPage.openCategoriesPageOnPage(Integer.parseInt(pageNumber));
    }

    @When("the user clicks the dropdown field")
    public void userClicksTheDropDownField(){
        categoriesPage.clickDropDown();
    }

    @When("the user clicks the \"Outdoor\" in the dropdown list")
    public void userClicksTheCategoryInTheDropDownList(){
        categoriesPage.clickOutdoorOption();
    }

    @When("the user clicks on the page 2")
    public void userClicksOnThePage(){
        categoriesPage.clickPagination2();
    }

    @When("the user click on the next button")
    public void userClickOnTheNextButton(){
        categoriesPage.clickPaginationNext();
    }

    @When("the user click on the previous button")
    public void userClickOnThePreviousButton(){
        categoriesPage.clickPaginationPrevious();
    }

    @When("the user clicks the Reset button")
    public void userClicksTheResetButton(){
        categoriesPage.verifyResetButtonVisibleAndEnabled();

    }

    @When("the user navigates to the bottom of the categories list")
    public void userNavigatesToBottomOfCategoriesList() {
        categoriesPage.scrollToBottomOfCategoriesList();
    }

    @When("the user edits the category name to {string}")
    public void userEditsCategoryName(String newName) {
        categoriesPage.editCategoryName(newName);
    }

    @Then("the Delete button should be disabled for category {string}")
    public void deleteButtonShouldDisable (String id) {

       categoriesPage.verifyDeleteButtonDisabledForCategory(id);
    }

    @Then("the Edit button should be disabled for category {string}")
    public void editButtonDisabled (String id){
        categoriesPage.verifyEditButtonDisabledForCategory(id);
    }

    @When("the user enters category name randomly {string}")
    public void userEntersCategoryNameRandomly(String name) {
        String processedName = starter.utils.ScenarioContext.processBodyWithRandomValues(name);

        // If no {random} placeholder was used, add a random component as implied by the
        // step name
        if (processedName.equals(name)) {
            processedName += new java.util.Random().nextInt(100);
        }

        // Ensure length is between 3 and 9 characters
        if (processedName.length() > 9) {
            processedName = processedName.substring(0, 9);
        } else if (processedName.length() < 3) {
            while (processedName.length() < 3) {
                processedName += new java.util.Random().nextInt(10);
            }
        }
        categoriesPage.enterCategoryName(processedName);
    }

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