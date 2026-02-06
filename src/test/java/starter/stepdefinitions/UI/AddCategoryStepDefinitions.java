package starter.stepdefinitions.UI;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import starter.pages.CategoriesPage;
import starter.pages.AddCategoryPage;
import net.serenitybdd.annotations.Steps;

public class AddCategoryStepDefinitions {

    @Steps
    AddCategoryPage addCategoryPage;

    @Steps
    CategoriesPage categoriesPage;

    @Given("the user is on the AddCategories page")
    public void userIsOnCategoriesPage() {
        addCategoryPage.openAddCategoriesPage();
    }

    @Then("the {string} button should be visible")
    public void buttonShouldBeVisible(String buttonName) {
        if (buttonName.equals("Add a Category")) {
            addCategoryPage.verifyAddCategoryButtonVisible();
        }
    }

    @When("the user clicks the {string} button")
    public void userClicksButton(String buttonName) {
        if (buttonName.equals("Add a Category")) {
            addCategoryPage.clickAddCategoryButton();
        }
    }

    @Then("the user should be on the add category page")
    public void userShouldBeOnAddCategoryPage() {
        addCategoryPage.verifyOnAddCategoryPage();
    }

    @Then("the user should be redirected to the categories page from add category")
    public void userShouldBeRedirectedToCategoriesPage() {
        addCategoryPage.verifyRedirectToCategoriesList();
    }

    @Then("a success message {string} should be displayed")
    public void successMessageShouldBeDisplayed(String message) {
        addCategoryPage.verifySuccessMessageDisplayed(message);
    }

    @Then("a success message {string} should be displayed here that {string} {string} like random genarate")
    public void successMessageShouldBeDisplayedWithExamples(String message, String ex1, String ex2) {
        addCategoryPage.verifySuccessMessageDisplayed(message);
    }
}
