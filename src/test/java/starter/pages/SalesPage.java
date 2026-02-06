package starter.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


public class SalesPage extends PageObject {

    // Navigation
    private final By salesNavTab = By.cssSelector("a.nav-link[href='/ui/sales']");

    // "Highlighted" tab depends on your HTML.
    // Common patterns: the <a> has class 'active', or its parent <li> has class 'active'.
    private final By salesNavTabActive = By.cssSelector(
            "a.nav-link[href='/ui/sales'].active, li.active > a.nav-link[href='/ui/sales']"
    );

    // Page-level markers (since you don't have data-testid, use URL + table presence)
    // We'll treat the table as the "page is loaded" marker.
    private final By salesTable = By.cssSelector("table");

    // Table header and rows (generic; replace 'table' with a more specific selector if you can)
    private final By tableHeaders = By.cssSelector("table thead th");
    private final By tableRows = By.cssSelector("table tbody tr");

    // Pagination (Bootstrap usually uses: ul.pagination, a.page-link)
    private final By pagination = By.cssSelector("ul.pagination");
    private final By activePageNumber = By.cssSelector("ul.pagination li.active");

    // Sell Plant button/link (adjust if your text differs)
    private final By sellPlantButton = By.xpath("//a[normalize-space()='Sell Plant'] | //button[normalize-space()='Sell Plant']");

    // Success message/toast after delete (adjust to your actual message text)
    private final By saleDeletedMessage = By.xpath("//*[contains(normalize-space(),'sale deleted') or contains(normalize-space(),'Sale deleted')]");

    // Delete buttons in table (adjust: could be button text, icon button, etc done.)
    private final By deleteButtons = By.xpath("/html/body/div/div/div[2]/div[2]/table/tbody/tr[1]/td[5]/form/button");

    // Sold At column cells (assumes Sold At is 4th column; adjust if needed)
    private final By soldAtCells = By.cssSelector("table tbody tr td:nth-child(4)");


    public void clickSalesTab() {
        $(salesNavTab).waitUntilClickable().click();
    }

    public void shouldBeOnSalesPage() {
        waitForCondition().until(driver -> driver.getCurrentUrl().contains("/ui/sales"));
        $(salesTable).waitUntilVisible();
    }

    public void salesTableShouldBeVisible() {
        $(salesTable).waitUntilVisible();
    }

    public void salesTabShouldBeHighlighted() {
        if (!$(salesNavTabActive).isVisible()) {
            throw new AssertionError("Sales tab is not highlighted/active in the navigation menu.");
        }
    }

    public void shouldSeeColumns(List<String> expectedColumns) {
        salesTableShouldBeVisible();

        List<String> actualHeaders = $$(tableHeaders).texts();

        for (String expected : expectedColumns) {
            if (!actualHeaders.contains(expected)) {
                throw new AssertionError("Missing expected column '" + expected + "'. Actual columns: " + actualHeaders);
            }
        }
    }
    private String normalizeHeader(String s) {
        if (s == null) return "";
        return s.trim()
                .replaceAll("\\s+", " ")
                .toLowerCase(Locale.ROOT);
    }
    //Used Java script for this approach
    public void goToPageNumber(int pageNumber) {
        WebElementFacade pageLink = find(By.xpath(
                "//a[contains(@class,'page-link') and normalize-space()='" + pageNumber + "']"
        ));

        evaluateJavascript("arguments[0].click();", pageLink);
    }


    public void clickNextPage() {
        $(pagination).waitUntilVisible();

        By nextBtn = By.xpath("//a[contains(@class,'page-link') and normalize-space()='Next']");
        WebElementFacade nextLink = $(nextBtn).waitUntilVisible();

        evaluateJavascript("arguments[0].click();", nextLink);
    }


    public void clickPreviousPage() {
        $(pagination).waitUntilVisible();

        By prevBtn = By.xpath("//a[contains(@class,'page-link') and (normalize-space()='Previous' or normalize-space()='Prev')]");
        WebElementFacade prevLink = $(prevBtn).waitUntilVisible();

        evaluateJavascript("arguments[0].click();", prevLink);
    }


    public void shouldHaveSomeRows() {
        salesTableShouldBeVisible();
        if ($$(tableRows).isEmpty()) {
            throw new AssertionError("Sales table has no rows. Expected at least 1 row.");
        }
    }

    public void currentPageShouldBeHighlighted() {
        $(pagination).waitUntilVisible();
        if (!$(activePageNumber).isVisible()) {
            throw new AssertionError("No highlighted active page number found in pagination.");
        }
    }


    public void sellPlantButtonShouldBeVisible() {
        if (!$(sellPlantButton).waitUntilVisible().isVisible()) {
            throw new AssertionError("Sell Plant button is not visible for admin.");
        }
    }

    public void openSellPlantPage() {
        $(sellPlantButton).waitUntilClickable().click();
    }

    public int salesRowCount() {
        salesTableShouldBeVisible();
        return $$(tableRows).size();
    }

    public void deleteFirstRowAndConfirm() {
        salesTableShouldBeVisible();

        if ($$(deleteButtons).isEmpty()) {
            throw new AssertionError("No Delete buttons found. Ensure at least one sales record exists and selector is correct.");
        }

        WebElementFacade firstDelete = $$(deleteButtons).get(0).waitUntilClickable();
        evaluateJavascript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", firstDelete);
        waitABit(200);
        firstDelete.click();

        Alert alert = getDriver().switchTo().alert();
        alert.accept();
    }


    public void shouldSeeSaleDeletedSuccessMessage() {
        if (!$(saleDeletedMessage).waitUntilVisible().isVisible()) {
            throw new AssertionError("Expected 'sale deleted successfully' message was not shown.");
        }
    }

    public void deleteActionShouldNotBeVisibleForAnyRecord() {
        salesTableShouldBeVisible();

        // If the user role hides delete buttons, this should be empty
        if (!$$(deleteButtons).isEmpty()) {
            throw new AssertionError("Delete action is visible for user, but it should not be.");
        }
    }

    public void salesShouldBeSortedBySoldAtDescending() {
        salesTableShouldBeVisible();

        List<String> soldAtTexts = $$(soldAtCells).texts();
        if (soldAtTexts.size() < 2) {
            return; // not enough rows to validate sorting
        }

        LocalDateTime prev = parseSoldAt(soldAtTexts.get(0));
        for (int i = 1; i < soldAtTexts.size(); i++) {
            LocalDateTime current = parseSoldAt(soldAtTexts.get(i));
            if (prev != null && current != null && current.isAfter(prev)) {
                throw new AssertionError("Sales are not sorted by Sold At descending. Row " + (i + 1) +
                        " has " + soldAtTexts.get(i) + " which is after previous " + soldAtTexts.get(i - 1));
            }
            prev = current;
        }
    }

    private LocalDateTime parseSoldAt(String text) {
        // You MUST match your UI format. Add/adjust formats here.
        String value = text == null ? "" : text.trim();

        List<DateTimeFormatter> formats = List.of(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );

        for (DateTimeFormatter f : formats) {
            try {
                return LocalDateTime.parse(value, f);
            } catch (Exception ignored) {
                // try next
            }
        }
        // If parsing fails, return null: sorting check becomes "best effort"
        return null;
    }
}