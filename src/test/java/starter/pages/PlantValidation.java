package starter.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class PlantValidation extends PageObject {

    private final By nameField = By.cssSelector("input#name, input[name='name']");
    private final By categorySelect = By.cssSelector("select#categoryId, select#category, select[name='categoryId'], select[name='category']");
    private final By priceField = By.cssSelector("input#price, input[name='price']");
    private final By quantityField = By.cssSelector("input#quantity, input[name='quantity']");
    private final By saveButton = By.xpath("//button[normalize-space()='Save' or contains(.,'Save')]");
    private final By cancelButton = By.xpath("//button[normalize-space()='Cancel' or contains(.,'Cancel')]");

    private final String nameFieldXpath = "//input[@id='name' or @name='name']";
    private final String categoryFieldXpath = "//select[@id='categoryId' or @id='category' or @name='categoryId' or @name='category']";
    private final String priceFieldXpath = "//input[@id='price' or @name='price']";
    private final String quantityFieldXpath = "//input[@id='quantity' or @name='quantity']";

    private final By searchField = By.cssSelector("input[name='name']");
    private final By resetButton = By.cssSelector("a.btn.btn-outline-secondary");
    private final By tableRows = By.cssSelector("table tbody tr");
    private final By editButton = By.cssSelector("table a[title='Edit']");

    public void openAddPlantPage() {
            net.thucydides.model.util.EnvironmentVariables environmentVariables = net.serenitybdd.core.Serenity
                    .environmentVariables();
            net.serenitybdd.model.environment.EnvironmentSpecificConfiguration config = net.serenitybdd.model.environment.EnvironmentSpecificConfiguration
                    .from(environmentVariables);
            String baseUrl = config.getProperty("webdriver.base.url");
            openUrl(baseUrl + "/plants/add");
        }

    public void leaveNameEmpty() {
        $(nameField).waitUntilVisible().clear();
    }

    public void enterName(String name) {
        WebElementFacade field = $(nameField).waitUntilVisible();
        field.clear();
        field.type(name);
    }

    public void selectCategory(String category) {
        WebElementFacade dropdown = $(categorySelect).waitUntilVisible();
        new Select(dropdown).selectByVisibleText(category);
    }

    public void leaveCategoryUnselected() {
        WebElementFacade dropdown = $(categorySelect).waitUntilVisible();
        new Select(dropdown).selectByIndex(0);
    }

    public void leavePriceEmpty() {
        $(priceField).waitUntilVisible().clear();
    }

    public void enterPrice(String price) {
        WebElementFacade field = $(priceField).waitUntilVisible();
        field.clear();
        field.type(price);
    }

    public void leaveQuantityEmpty() {
        $(quantityField).waitUntilVisible().clear();
    }

    public void enterQuantity(String qty) {
        WebElementFacade field = $(quantityField).waitUntilVisible();
        field.clear();
        field.type(qty);
    }

    public void clickSave() {
        $(saveButton).waitUntilClickable().click();
    }

    public void clickCancel() {
        $(cancelButton).waitUntilClickable().click();
    }

    public void shouldShowErrorForName(String message) {
        shouldShowErrorForField(nameFieldXpath, nameField, message);
    }

    public void shouldShowErrorForCategory(String message) {
        shouldShowErrorForField(categoryFieldXpath, categorySelect, message);
    }

    public void shouldShowErrorForPrice(String message) {
        shouldShowErrorForField(priceFieldXpath, priceField, message);
    }

    public void shouldShowErrorForQuantity(String message) {
        shouldShowErrorForField(quantityFieldXpath, quantityField, message);
    }

    private void shouldShowErrorForField(String fieldXpath, By fieldBy, String message) {
        By inline = By.xpath("(" + fieldXpath + ")/ancestor::div[contains(@class,'form-group') or contains(@class,'mb-')]"
                + "//*[contains(normalize-space(.), \"" + message + "\")]");

        if ($(inline).isVisible()) {
            return;
        }

        WebElementFacade field = $(fieldBy).waitUntilVisible();
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String validationMessage = (String) js.executeScript("return arguments[0].validationMessage;", field);

        if (validationMessage == null || validationMessage.isBlank()) {
            throw new AssertionError("No validation message displayed for field. Expected: " + message);
        }
        if (!validationMessage.equals(message)) {
            throw new AssertionError("Expected: " + message + " | Actual: " + validationMessage);
        }
    }

    public void shouldShowSubCategories(List<String> expected) {
        WebElementFacade dropdown = $(categorySelect).waitUntilVisible();
        List<String> actual = new Select(dropdown).getOptions().stream()
                .map(e -> e.getText().trim().toLowerCase())
                .collect(Collectors.toList());

        for (String exp : expected) {
            if (!actual.contains(exp.toLowerCase())) {
                throw new AssertionError("Missing sub-category: " + exp + " | Actual: " + actual);
            }
        }
    }

    public void shouldBeOnPlantListPage() {
        String url = getDriver().getCurrentUrl();
        if (!url.contains("/plants") || url.contains("/add")) {
            throw new AssertionError("Not on plant list page. Current URL: " + url);
        }
    }

    public void openPlantsPage() {
        net.thucydides.model.util.EnvironmentVariables environmentVariables = net.serenitybdd.core.Serenity
                .environmentVariables();
        net.serenitybdd.model.environment.EnvironmentSpecificConfiguration config = net.serenitybdd.model.environment.EnvironmentSpecificConfiguration
                .from(environmentVariables);
        String baseUrl = config.getProperty("webdriver.base.url");
        openUrl(baseUrl + "/plants");
    }

    public void searchByName(String name) {
        WebElementFacade field = $(searchField).waitUntilVisible();
        field.clear();
        field.type(name);
        field.sendKeys(org.openqa.selenium.Keys.ENTER);
    }

    public void clickReset() {
        $(resetButton).waitUntilClickable().click();
    }

    public void shouldSearchFieldBeEmpty() {
        String value = $(searchField).waitUntilVisible().getValue();
        if (value != null && !value.isBlank()) {
            throw new AssertionError("Search field not empty. Value: " + value);
        }
    }

    public void shouldShowFullList() {
        List<WebElementFacade> rows = findAll(tableRows);
        if (rows.size() < 2) {
            throw new AssertionError("Expected full list (>=2 rows). Actual rows: " + rows.size());
        }
    }

    public void shouldShowRow(String name, String category, String price) {
        List<WebElementFacade> rows = findAll(tableRows);
        boolean match = rows.stream().anyMatch(r -> {
            List<String> cells = r.findElements(By.cssSelector("td")).stream()
                    .map(e -> e.getText().trim())
                    .collect(Collectors.toList());
            return cells.size() >= 3
                    && cells.get(0).equalsIgnoreCase(name)
                    && cells.get(1).equalsIgnoreCase(category)
                    && cells.get(2).equalsIgnoreCase(price);
        });

        if (!match) {
            throw new AssertionError("Row not found: " + name + " | " + category + " | " + price);
        }
    }

        public void shouldHideEditButtons() {
        List<WebElementFacade> edits = findAll(editButton);
        if (!edits.isEmpty()) {
            throw new AssertionError("Edit button is visible for user.");
        }
    }
}