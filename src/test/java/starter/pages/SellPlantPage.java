package starter.pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SellPlantPage extends PageObject {

    private final By plantDropdown = By.id("plantId");
    private final By quantityField = By.name("quantity");
    private final By submitButton = By.xpath("//form//div[3]/button");

    private final By plantValidationMsg = By.xpath("//select[@id='plantId']/following-sibling::div[contains(@class,'text-danger')]");
    private final By quantityValidationMsg = By.xpath("//input[@name='quantity']/following-sibling::div[contains(@class,'text-danger')]");

    public void submitEmptyForm() {
        $(plantDropdown).selectByValue("");

        if ($(quantityField).isPresent()) {
            $(quantityField).clear();
        }

        $(submitButton).waitUntilClickable().click();
    }

    public void submitWithValidQuantityButEmptyPlant(int quantity) {
        $(plantDropdown).selectByValue("");
        $(quantityField).waitUntilVisible().clear();
        $(quantityField).type(String.valueOf(quantity));
        $(submitButton).waitUntilClickable().click();
    }

    public void verifyPlantValidationMessage(String expectedMessage) {
        String actualMessage = $(plantValidationMsg).waitUntilVisible().getText().trim();
        assertEquals(
                expectedMessage,
                actualMessage,
                () -> "Plant validation message mismatch. Expected: [" + expectedMessage + "] but was: [" + actualMessage + "]"
        );
    }

    public void verifyQuantityValidationMessage(String expectedMessage) {
        String actualMessage = $(quantityValidationMsg).waitUntilVisible().getText().trim();
        String url = getDriver().getCurrentUrl();

        assertEquals(
                expectedMessage,
                actualMessage,
                () -> "SRS validation mismatch for field [Quantity]. Expected: [" + expectedMessage + "], but was: [" + actualMessage + "]. URL: [" + url + "]"
        );
    }

    public void quantityNativeValidationMessageShouldContain(String expectedPart) {
        WebElement qty = $(quantityField).waitUntilVisible();
        String validationMessage = (String) evaluateJavascript("return arguments[0].validationMessage;", qty);

        assertTrue(
                validationMessage != null && validationMessage.contains(expectedPart),
                () -> "Quantity native validation message mismatch. Expected to contain: [" + expectedPart + "] but was: [" + validationMessage + "]"
        );
    }
}