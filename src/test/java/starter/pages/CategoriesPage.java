package starter.pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.ClickStrategy;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import net.serenitybdd.core.Serenity;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class CategoriesPage extends PageObject {

    private By searchButton = By.xpath("//button[contains(text(),'Search')]");
    private By nameInput = By.name("name");
    private By parentIdSelect = By.name("parentId");
    private By saveButton = By.xpath("//button[text()='Save']");
    private By searchSubCategoryField = By.xpath("(//input[@placeholder='Search sub category'])[1]");
    private By AllParentDropDownField = By.xpath("(//select[@name='parentId'])[1]");
    private By outDoor = By.xpath("/html/body/div[1]/div/div[2]/div[2]/form/div[2]/select/option[3]");
    private By pagination2 = By.xpath("/html/body/div[1]/div/div[2]/div[2]/nav/ul/li[3]/a");
    private By categoryPagination = By.xpath("/html/body/div[1]/div/div[2]/div[2]/nav/ul");
    private By paginationNextButton = By.xpath("(//a[normalize-space()='Next'])[1]");
    private By paginationPreviousButton = By.xpath("/html/body/div[1]/div/div[2]/div[2]/nav/ul/li[1]/a");
    private By categoryNameField = By.xpath("//*[@id=\"name\"]");

    // Using a more generic selector for the close button inside an alert/toast
    private By errorCloseButton = By.xpath("//div[contains(@class, 'alert') or contains(@class, 'toast')]//button");
    private By errorMessage = By.xpath("//div[contains(@class, 'alert') or contains(@class, 'toast')]");

    private By cancelButton = By.xpath("//a[contains(@href, '/ui/categories')]");
    private By resetButton = By.xpath("/html/body/div[1]/div/div[2]/div[2]/form/div[3]/a[1]");

    private By successMessage = By.xpath("//div[contains(@class, 'alert') or contains(@class, 'toast')]");

    public void openCategoriesPage() {
        net.thucydides.model.util.EnvironmentVariables environmentVariables = net.serenitybdd.core.Serenity
                .environmentVariables();
        net.serenitybdd.model.environment.EnvironmentSpecificConfiguration config = net.serenitybdd.model.environment.EnvironmentSpecificConfiguration
                .from(environmentVariables);
        String baseUrl = config.getProperty("webdriver.base.url");
        openUrl(baseUrl + "/categories");
    }

    public void openCategoriesPageOnPage(int pageNumber) {
        net.thucydides.model.util.EnvironmentVariables environmentVariables =
                net.serenitybdd.core.Serenity.environmentVariables();

        net.serenitybdd.model.environment.EnvironmentSpecificConfiguration config =
                net.serenitybdd.model.environment.EnvironmentSpecificConfiguration.from(environmentVariables);

        String baseUrl = config.getProperty("webdriver.base.url");

        openUrl(baseUrl + "/categories?page=" + pageNumber
                + "&sortField=id&sortDir=asc&name=&parentId=");
    }

    public void verifySearchButtonVisibleAndEnabled() {
        $(searchButton).shouldBeVisible();
        $(searchButton).shouldBeEnabled();
    }

    public void scrollToBottomOfCategoriesList() {
        evaluateJavascript("arguments[0].scrollIntoView(true);",
                $(categoryPagination).waitUntilVisible());
    }

    public void clickPagination2() {
        $(pagination2).click();
    }

    public void clickPaginationNext() {
        $(paginationNextButton).click();
    }

    public void clickPaginationPrevious() {
        $(paginationPreviousButton).click();
    }

    public void editCategoryName(String newName) {
        $(categoryNameField).waitUntilVisible();
        $(categoryNameField).click();
        $(categoryNameField).clear();          // removes existing value (e.g. "fungi")
        $(categoryNameField).type(newName);    // enter updated name (e.g. "fung")
    }

    public void verifyResetButtonVisibleAndEnabled(){
        $(resetButton).shouldBeVisible();
        $(resetButton).shouldBeEnabled();
    }

    public void verifyAllParentsDropDownFieldVisibleAndEnabled(){
        $(AllParentDropDownField).shouldBeVisible();
        $(AllParentDropDownField).shouldBeEnabled();
    }

    public void verifySubCategoryFieldVisibleAndEnabled() {
        $(searchSubCategoryField).shouldBeVisible();
        $(searchSubCategoryField).shouldBeEnabled();
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

    public void clickOutdoorOption() {
        $(outDoor).click();
    }

    public void verifyDeleteButtonDisabledForCategory(String id) {
        String deleteXpath = "//form[contains(@action, '/ui/categories/delete/" + id + "')]//button";
        $(deleteXpath).shouldBeVisible();
        $(deleteXpath).shouldNotBeEnabled();

    }

    public void verifyEditButtonDisabledForCategory(String id) {
        String editXpath = "(//a[@title='Edit'])[2]";
        $(editXpath).shouldBeVisible();
        $(editXpath).shouldNotBeEnabled();

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

    public void clickDropDown() {
        $(AllParentDropDownField).waitUntilClickable();
        $(AllParentDropDownField).click();

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
