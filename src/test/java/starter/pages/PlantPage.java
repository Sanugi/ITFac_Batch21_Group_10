package starter.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class PlantPage extends PageObject {

    // search bar
    private static final By SEARCH_INPUT = By.name("name");

    // search button
    private static final By SEARCH_BUTTON = By.xpath("//button[contains(text(),'Search')]");

    // "No plants found" message
    private static final By NO_RESULTS_MESSAGE = By.xpath("//*[contains(text(),'No plants found')]");

    // price header link
    private static final By PRICE_COLUMN_HEADER = By.xpath("//th/a[contains(text(),'Price')]");

    // cells in the price column
    private static final By PRICE_CELLS = By.xpath("//tbody/tr/td[3]");

    // The "Add Plant" button
    private static final By ADD_PLANT_BUTTON = By
            .xpath("//a[contains(text(),'Add a Plant')] | //button[contains(text(),'Add a Plant')]");

    // The edit icon
    private static final By EDIT_ICON = By.cssSelector(".bi-pencil-square"); 

    // The delete icon
    private static final By DELETE_ICON = By.cssSelector(".bi-trash");

    // The "Next" button in the pagination bar
    private static final By NEXT_PAGE_BUTTON = By.xpath("//ul[@class='pagination']//a[contains(text(),'Next')]");

    // Current Active Page Number
    private static final By ACTIVE_PAGE_NUMBER = By.cssSelector("li.page-item.active");

    // The Save Button inside the Form
    private static final By SAVE_BUTTON = By.xpath("//button[text()='Save']");

    // The Validation Error Message
    private static final By CATEGORY_ERROR_MSG = By.xpath("//*[contains(text(),'Category is required')]");

    // The name input field
    private static final By NAME_INPUT = By.name("name");

    // The category select field
    private static final By CATEGORY_SELECT = By.name("categoryId");

    // The price input field
    private static final By PRICE_INPUT = By.name("price");

    // The quantity input field
    private static final By QUANTITY_INPUT = By.name("quantity");

    // method to verify low badge is visible for a specific quantity
    public void verifyLowBadgeVisibleForQuantity(String quantity) {
        // build the XPath dynamically based on the quantity value passed from the
        // feature file
        String dynamicXpath = "//tr[.//span[text()='" + quantity
                + "']]//span[contains(@class, 'badge')][contains(text(), 'Low')]";
        // find the element and wait until it is visible
        $(By.xpath(dynamicXpath)).waitUntilVisible();
    }

    // method to search for a plant by typing text and clicking search
    public void searchForPlant(String plantName) {
        $(SEARCH_INPUT).type(plantName); // Types into the box
        $(SEARCH_BUTTON).click(); // Clicks the button
    }

    // method to verify error messasge is visible
    public void verifyNoPlantsMessageIsDisplayed() {
        $(NO_RESULTS_MESSAGE).shouldBeVisible();
    }

    // method to click the price header to sort the table by price
    public void clickPriceHeader() {
        $(PRICE_COLUMN_HEADER).click();
    }

    // method to verify that the prices in the table are sorted in ascending order
    public void verifyPricesAreSortedAscending() {
        // Find all price elements in the table
        List<WebElementFacade> priceElements = findAll(PRICE_CELLS);

        // Extract text, convert to Double, and save to a list
        List<Double> prices = new ArrayList<>();

        for (WebElementFacade element : priceElements) {
            // element.getText() returns string "15.00". Double.parseDouble turns it into
            // number 15.00
            String priceText = element.getText().trim();
            prices.add(Double.parseDouble(priceText));
        }

        // 3. Debugging: Print prices to console so you can see them when running
        System.out.println("Prices found in table: " + prices);

        // Verify the list is sorted
        for (int i = 0; i < prices.size() - 1; i++) {
            if (prices.get(i) > prices.get(i + 1)) {
                throw new AssertionError(
                        "List is not sorted! " + prices.get(i) + " is bigger than " + prices.get(i + 1));
            }
        }
    }

    // method to verify add button is not visible
    public void verifyAddButtonNotVisible() {
        // Serenity method that passes ONLY if the element is hidden or not in the HTML
        // at all
        $(ADD_PLANT_BUTTON).shouldNotBeVisible();
    }

    // method to verify edit and delete icons are not visible
    public void verifyEditDeleteIconsNotVisible() {
        // We check that NO edit or delete icons exist on the page
        $(EDIT_ICON).shouldNotBeVisible();
        $(DELETE_ICON).shouldNotBeVisible();
    }

    // method to click the "Next" button in pagination
    public void clickNextPage() {
        $(NEXT_PAGE_BUTTON).waitUntilClickable().click();
    }

    // method to verify the active page number in pagination matches expected value
    public void verifyActivePageNumber(String expectedPageNumber) {
        $(ACTIVE_PAGE_NUMBER).waitUntilVisible();

        String actualPageNumber = $(ACTIVE_PAGE_NUMBER).getText().trim();

        // Standard Java/JUnit check
        Assertions.assertEquals(expectedPageNumber, actualPageNumber, "Page number did not match!");

        // Verify URL
        String currentUrl = getDriver().getCurrentUrl();
        int expectedPageIndex = Integer.parseInt(expectedPageNumber) - 1; // URLs usually start at 0

        Assertions.assertTrue(currentUrl.contains("page=" + expectedPageIndex),
                "URL did not contain correct page index. Url was: " + currentUrl);
    }

    // method to click the "Add Plant" button
    public void clickAddPlantButton() {
        // Wait for it to be clickable and click
        $(ADD_PLANT_BUTTON).waitUntilClickable().click();
    }

    // method to click the "Save" button in the form
    public void clickSaveButton() {
        // Usually, a modal pops up. We wait for the save button to be visible first.
        $(SAVE_BUTTON).waitUntilVisible().click();
    }

    // method to verify the category required error message is displayed
    public void verifyCategoryRequiredError() {
        // Check if the error text appears
        $(CATEGORY_ERROR_MSG).shouldBeVisible();
    }

    // method to verify error messages are displayed
    public void verifyErrorMessage(String messageText) {
        // Construct an XPath that looks for an element containing the specific text
        String dynamicXpath = "//*[contains(text(),'" + messageText + "')]";
        
        // Check if it is visible
        $(By.xpath(dynamicXpath)).shouldBeVisible();
    }

    // method to fill the plant form with given details
    public void fillPlantForm(String name, String price, String quantity) {
        // Type the Name
        $(NAME_INPUT).type(name);

        // Select a valid Category 
        $(CATEGORY_SELECT).selectByValue("44"); 

        // Type the Price
        $(PRICE_INPUT).type(price);

        // Type the (Negative) Quantity
        $(QUANTITY_INPUT).type(quantity);
    }

    // Finds the row containing the specific plant name, then finds the Edit button inside that row.

    public void clickEditButtonForPlant(String plantName) {
        String dynamicXpath = "//tr[td[contains(.,'" + plantName + "')]]//i[contains(@class,'bi-pencil-square')]";
        $(By.xpath(dynamicXpath)).waitUntilClickable().click();
    }

    // Updates the price field. 
    public void updatePrice(String newPrice) {
        $(PRICE_INPUT).waitUntilVisible();
        $(PRICE_INPUT).clear(); // Remove old price 
        $(PRICE_INPUT).type(newPrice); // Type new price
    }

    // Verifies the new price appears in the table for the specific plant.
    public void verifyPriceForPlant(String plantName, String expectedPrice) {
        String priceCellXpath = "//tr[td[normalize-space()='" + plantName + "']]/td[3]";
        String actualPrice = $(By.xpath(priceCellXpath)).getText().trim();
        if (!actualPrice.equals(expectedPrice)) {
            throw new AssertionError("Expected price " + expectedPrice + " but found " + actualPrice);
        }
    }

    // method to verify add button is visible
    public void verifyAddButtonIsVisible() {
        $(ADD_PLANT_BUTTON).shouldBeVisible();
    }

    // method to verify edit button and delete button are visible
    public void verifyEditDeleteIconsAreVisible() {
        $(EDIT_ICON).shouldBeVisible();
        $(DELETE_ICON).shouldBeVisible();
    }
}