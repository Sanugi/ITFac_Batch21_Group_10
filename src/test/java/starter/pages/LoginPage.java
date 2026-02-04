package starter.pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.Serenity;

public class LoginPage extends PageObject {

    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By loginButton = By.cssSelector("button[type='submit']");

    public void openLoginPage() {
        net.thucydides.model.util.EnvironmentVariables environmentVariables = net.serenitybdd.core.Serenity.environmentVariables();
        net.serenitybdd.model.environment.EnvironmentSpecificConfiguration config = net.serenitybdd.model.environment.EnvironmentSpecificConfiguration.from(environmentVariables);
        String baseUrl = config.getProperty("webdriver.base.url");
        openUrl(baseUrl + "/login");
        $(usernameField).waitUntilVisible();
    }

    public void enterUsername(String username) {
        $(usernameField).waitUntilVisible();
        $(usernameField).clear();
        $(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        $(passwordField).waitUntilVisible();
        $(passwordField).clear();
        $(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        $(loginButton).waitUntilClickable();
        $(loginButton).click();
    }

    public void verifySuccessfulLogin() {
        waitFor(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("/ui/dashboard"));
        String currentUrl = getDriver().getCurrentUrl();
        // After successful login, we should be on the dashboard page
        if (!currentUrl.contains("/ui/dashboard")) {
            throw new AssertionError("Login failed or not redirected to dashboard. Current URL: " + currentUrl);
        }
    }

    public void verifyLoginError() {

        String currentUrl = getDriver().getCurrentUrl();
        if (!currentUrl.contains("/ui/login")) {
            throw new AssertionError("Not on login page - expected to stay on login page after failed login");
        }
    }
}
