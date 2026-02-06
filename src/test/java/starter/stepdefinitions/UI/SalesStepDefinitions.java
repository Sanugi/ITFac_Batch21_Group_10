package starter.stepdefinitions.UI;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import starter.pages.SalesPage;
import starter.pages.SellPlantPage;

import java.util.List;

public class SalesStepDefinitions {

    SalesPage salesPage;
    SellPlantPage sellPlantPage;

    @When("the user clicks the Sales tab in the navigation menu")
    public void theUserClicksTheSalesTabInTheNavigationMenu() {
        salesPage.clickSalesTab();
    }

    @Then("the Sales page should load without errors")
    public void theSalesPageShouldLoadWithoutErrors() {
        salesPage.shouldBeOnSalesPage();
    }

    @Then("the sales table should be displayed")
    public void theSalesTableShouldBeDisplayed() {
        salesPage.salesTableShouldBeVisible();
        salesPage.shouldHaveSomeRows();
    }

    @Then("the Sales tab should be highlighted")
    public void theSalesTabShouldBeHighlighted() {
        salesPage.salesTabShouldBeHighlighted();
    }

    @Then("the Sales table columns should be visible")
    public void theSalesTableColumnsShouldBeVisible(DataTable dataTable) {
        List<String> expectedColumns = dataTable.asList();
        salesPage.shouldSeeColumns(expectedColumns);
    }

    @When("the user goes to page {int} in sales pagination")
    public void theUserGoesToPageInSalesPagination(Integer pageNumber) {
        salesPage.goToPageNumber(pageNumber);
    }

    @When("the user clicks next page in sales pagination")
    public void theUserClicksNextPageInSalesPagination() {
        salesPage.clickNextPage();
    }

    @When("the user clicks previous page in sales pagination")
    public void theUserClicksPreviousPageInSalesPagination() {
        salesPage.clickPreviousPage();
    }

    @Then("the sales table should show records for the selected page")
    public void theSalesTableShouldShowRecordsForTheSelectedPage() {
        salesPage.shouldHaveSomeRows();
    }

    @Then("the current sales page number should be highlighted")
    public void theCurrentSalesPageNumberShouldBeHighlighted() {
        salesPage.currentPageShouldBeHighlighted();
    }

    @Then("the Sell Plant button should be visible")
    public void theSellPlantButtonShouldBeVisible() {
        salesPage.sellPlantButtonShouldBeVisible();
    }

    @When("the user opens the Sell Plant page")
    public void theUserOpensTheSellPlantPage() {
        salesPage.openSellPlantPage();
    }

    @When("the user submits the sell plant form with empty fields")
    public void theUserSubmitsTheSellPlantFormWithEmptyFields() {
        sellPlantPage.submitEmptyForm();
    }

    @Then("the plant validation error should show {string}")
    public void thePlantValidationErrorShouldShow(String expectedMessage) {
        sellPlantPage.verifyPlantValidationMessage(expectedMessage);
    }

    @Then("the quantity validation error should show {string}")
    public void theQuantityValidationErrorShouldShow(String expectedMessage) {
        sellPlantPage.verifyQuantityValidationMessage(expectedMessage);
    }

    @When("the user deletes the first sales record and confirms")
    public void theUserDeletesTheFirstSalesRecordAndConfirms() {
        salesPage.deleteFirstRowAndConfirm();
    }

    @Then("the sales record should be removed from the table")
    public void theSalesRecordShouldBeRemovedFromTheTable() {
        salesPage.salesTableShouldBeVisible();
    }

    @Then("a sale deleted success message should be shown")
    public void aSaleDeletedSuccessMessageShouldBeShown() {
        salesPage.shouldSeeSaleDeletedSuccessMessage();
    }

    @Then("the sales should be sorted by Sold At in descending order")
    public void theSalesShouldBeSortedBySoldAtInDescendingOrder() {
        salesPage.salesShouldBeSortedBySoldAtDescending();
    }

    @Then("the Delete action should not be visible for any record")
    public void theDeleteActionShouldNotBeVisibleForAnyRecord() {
        salesPage.deleteActionShouldNotBeVisibleForAnyRecord();
    }
}