package starter.stepdefinitions.UI;

import io.cucumber.java.en.*;
import starter.pages.LoginPage;

public class LoginStepDefinitions {

    LoginPage loginPage;

    @Given("the user is on the login page")
    public void userIsOnLoginPage() {
        loginPage.openLoginPage();
    }

    @When("the user enters username {string}")
    public void userEntersUsername(String username) {
        loginPage.enterUsername(username);
    }

    @When("the user enters password {string}")
    public void userEntersPassword(String password) {
        loginPage.enterPassword(password);
    }

    @When("the user clicks the login button")
    public void userClicksLoginButton() {
        loginPage.clickLogin();
    }

    @Then("the user should be logged in successfully")
    public void userShouldBeLoggedInSuccessfully() {
        loginPage.verifySuccessfulLogin();
    }

    @Then("an error message should be displayed here that normal user login and admin login provide that")
    public void errorMessageShouldBeDisplayed() {
        loginPage.verifyLoginError();
    }

    @Given("the {string} is logged in")
    public void userIsLoggedIn(String userType) {
        loginPage.openLoginPage();
        if (userType.equalsIgnoreCase("admin")) {
            loginPage.enterUsername("admin");
            loginPage.enterPassword("admin123");
        } else {
            loginPage.enterUsername("testuser");
            loginPage.enterPassword("test123");
        }
        loginPage.clickLogin();
        loginPage.verifySuccessfulLogin();
    }
}
