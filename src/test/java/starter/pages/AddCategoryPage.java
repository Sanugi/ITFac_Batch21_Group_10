package starter.pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;

public class AddCategoryPage extends PageObject {

    private By addCategoryButton = By.xpath("//a[contains(@href,'/ui/categories/add') or contains(text(), 'Add')]");
    private By toastMessage = By.xpath("//div[contains(@class, 'alert') or contains(@class, 'toast')]");
    private By parentIdSelect = By.name("parentId");

    private By errorMessage = By.xpath("//div[contains(@class, 'alert') or contains(@class, 'toast')]");

    private By successMessage = By.xpath("//div[contains(@class, 'alert') or contains(@class, 'toast')]");

    public void openAddCategoriesPage() {
        openUrl("http://localhost:8080/ui/categories/add");
    }


    public void verifyAddCategoryButtonVisible() {
        $(addCategoryButton).waitUntilVisible();
        $(addCategoryButton).shouldBeVisible();
    }

    public void clickAddCategoryButton() {
        $(addCategoryButton).waitUntilClickable();
        $(addCategoryButton).click();
    }

    public void verifyOnAddCategoryPage() {
        waitFor(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("/ui/categories/add"));
        String currentUrl = getDriver().getCurrentUrl();
        if (!currentUrl.contains("/ui/categories/add")) {
            throw new AssertionError("Not on add category page. Current URL: " + currentUrl);
        }
    }

    public void selectParentCategoryByText(String parentName) {
        $(parentIdSelect).selectByVisibleText(parentName);
    }

    public void verifyRedirectToCategoriesList() {
        waitFor(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("/ui/categories"));
        String currentUrl = getDriver().getCurrentUrl();
        if (!currentUrl.contains("/ui/categories")) {
            throw new AssertionError("Not on category page. Current URL: " + currentUrl);
        }

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
