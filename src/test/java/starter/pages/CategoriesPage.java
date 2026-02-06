package starter.pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import net.serenitybdd.core.Serenity;

public class CategoriesPage extends PageObject {

    private By searchButton = By.xpath("//button[contains(text(),'Search')]");
    private By nameInput = By.name("name");
    private By parentIdSelect = By.name("parentId");
    private By saveButton = By.xpath("//button[text()='Save']");

    // Using a more generic selector for the close button inside an alert/toast
    private By errorCloseButton = By.xpath("//div[contains(@class, 'alert') or contains(@class, 'toast')]//button");
    private By errorMessage = By.xpath("//div[contains(@class, 'alert') or contains(@class, 'toast')]");

    private By cancelButton = By.xpath("//a[contains(@href, '/ui/categories')]");

    private By successMessage = By.xpath("//div[contains(@class, 'alert') or contains(@class, 'toast')]");

    public void openCategoriesPage() {
        net.thucydides.model.util.EnvironmentVariables environmentVariables = net.serenitybdd.core.Serenity
                .environmentVariables();
        net.serenitybdd.model.environment.EnvironmentSpecificConfiguration config = net.serenitybdd.model.environment.EnvironmentSpecificConfiguration
                .from(environmentVariables);
        String baseUrl = config.getProperty("webdriver.base.url");
        openUrl(baseUrl + "/categories");
    }

    public void verifySearchButtonVisibleAndEnabled() {
        $(searchButton).shouldBeVisible();
        $(searchButton).shouldBeEnabled();
    }

    public void hoverOverSearchButton() {
        Actions actions = new Actions(getDriver());
        WebElement button = $(searchButton);
        actions.moveToElement(button).perform();
    }

    public void verifySearchButtonClickable() {
        $(searchButton).waitUntilClickable();
    }

    public void enterCategoryName(String name) {
        $(nameInput).type(name);
    }

    public void selectParentCategory(String parentId) {
        $(parentIdSelect).selectByValue(parentId);
    }

    public void clickSearchButton() {
        $(searchButton).click();
    }

    public void verifyUrlContains(String content) {
        waitFor(org.openqa.selenium.support.ui.ExpectedConditions.urlContains(content));
        String currentUrl = getDriver().getCurrentUrl();
        if (!currentUrl.contains(content)) {
            throw new AssertionError("URL does not contain '" + content + "'. Actual URL: " + currentUrl);
        }
    }

    public void clickEditButtonForCategory(String id) {
        By editButton = By.xpath("//a[contains(@href, '/ui/categories/edit/" + id + "')]");
        $(editButton).waitUntilClickable();
        $(editButton).click();
    }


    public void verifyRedirectToEditCategoryPage(String id) {
        waitFor(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("/ui/categories/edit/" + id));
        String currentUrl = getDriver().getCurrentUrl();
        if (!currentUrl.contains("/ui/categories/edit/" + id)) {
            throw new AssertionError("Not on edit category page for id " + id + ". Current URL: " + currentUrl);
        }
    }

    public void clickSaveButton() {
        $(saveButton).waitUntilClickable();
        $(saveButton).click();

    }

    public void verifyErrorMessageDisplayed() {
        $(errorMessage).waitUntilVisible();
    }

    public void clickErrorMessageCloseButton() {
        $(errorCloseButton).waitUntilClickable();
        $(errorCloseButton).click();
    }

    public void verifyErrorMessageDismissed() {
        $(errorMessage).waitUntilNotVisible();
    }

    public void clickCancelButton() {
        $(cancelButton).click();
    }

    public void verifyRedirectToCategoriesListFromEdit() {
        waitFor(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("/ui/categories"));
        String currentUrl = getDriver().getCurrentUrl();
        if (!currentUrl.contains("/ui/categories")) {
            throw new AssertionError("Not on category page ");
        }
    }

    public void clickDeleteButtonByXpath(String xpath) {
        $(By.xpath(xpath)).click();
    }

    public void verifyConfirmationPopupDisplayed() {
        waitFor(org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent());
    }

    public void clickOkOnConfirmationPopup() {
        getDriver().switchTo().alert().accept();
    }

    public void verifyCategoryDeleted() {
        waitFor(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("/ui/categories"));
    }

    public void verifySuccessMessageDisplayed(String message) {
        $(successMessage).waitUntilVisible();
        String actualMessage = $(successMessage).getText();
        if (!actualMessage.contains(message)) {
            throw new AssertionError(
                    "Expected success message checking for: '" + message + "' but found: '" + actualMessage + "'");
        }
    }
}
