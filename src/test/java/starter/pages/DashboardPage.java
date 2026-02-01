package starter.pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;

public class DashboardPage extends PageObject {

    private By manageCategoriesLink = By.xpath("//a[contains(text(),'Manage Categories')]");

    public void verifyOnDashboard() {
        String currentUrl = getDriver().getCurrentUrl();
        if (!currentUrl.contains("/ui/dashboard")) {
            throw new AssertionError("Not on dashboard page. Current URL: " + currentUrl);
        }
    }

    public void clickManageCategories() {
        $(manageCategoriesLink).waitUntilClickable();
        $(manageCategoriesLink).click();
    }


    public void verifyOnCategoriesPage() {
        String currentUrl = getDriver().getCurrentUrl();
        if (!currentUrl.contains("/ui/categories")) {
            throw new AssertionError("Not on categories page. Current URL: " + currentUrl);
        }
    }

    private By managePlantsLink = By.xpath("//a[contains(text(),'Manage Plants')]");

    public void clickManagePlants() {
        $(managePlantsLink).waitUntilClickable();
        $(managePlantsLink).click();
    }


    public void verifyOnPlantsPage() {
        String currentUrl = getDriver().getCurrentUrl();
        if (!currentUrl.contains("/ui/plants")) {
            throw new AssertionError("Not on plants page. Current URL: " + currentUrl);
        }
    }

    private By viewSalesLink = By.xpath("//a[contains(text(),'View Sales')]");

    public void clickViewSales() {
        $(viewSalesLink).waitUntilClickable();
        $(viewSalesLink).click();
    }

    public void verifyOnSalesPage() {
        String currentUrl = getDriver().getCurrentUrl();
        if (!currentUrl.contains("/ui/sales")) {
            throw new AssertionError("Not on sales page. Current URL: " + currentUrl);
        }
    }
}
