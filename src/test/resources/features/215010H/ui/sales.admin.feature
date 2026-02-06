Feature: Sales page - (Admin)

  Background:
    Given the "admin" is logged in

  @ui @admin @TC_UI_ADM_01 @215010H
  Scenario: Verify sales page loads successfully after navigation
    When the user clicks the Sales tab in the navigation menu
    Then the Sales page should load without errors
    And the sales table should be displayed
    And the Sales tab should be highlighted

  @ui @admin @TC_UI_ADM_02 @215010H
  Scenario: Verify sales records are displayed correctly in the tables
    When the user clicks the Sales tab in the navigation menu
    Then the Sales table columns should be visible
      | Plant       |
      | Quantity    |
      | Total Price |
      | Sold At     |
      | Actions     |


  @ui @admin @TC_UI_ADM_03 @215010H
  Scenario: Verify that pagination works correctly
    When the user clicks the Sales tab in the navigation menu
    And the user goes to page 2 in sales pagination
    Then the sales table should show records for the selected page
    And the current sales page number should be highlighted
    When the user clicks next page in sales pagination
    Then the sales table should show records for the selected page
    When the user clicks previous page in sales pagination
    Then the sales table should show records for the selected page


  @ui @admin @TC_UI_ADM_04 @215010H
  Scenario: Verify that the sell plant button is visible to the admin
    When the user clicks the Sales tab in the navigation menu
    Then the Sell Plant button should be visible

  @ui @admin @TC_UI_ADM_05 @215010H
  Scenario: Verify the delete action with confirmation
    When the user clicks the Sales tab in the navigation menu
    And the user deletes the first sales record and confirms
    Then the sales record should be removed from the table
    And a sale deleted success message should be shown

  @ui @admin @TC_UI_ADM_06 @215010H
  Scenario: Verify Form Validation: Empty Fields
    When the user clicks the Sales tab in the navigation menu
    And the user opens the Sell Plant page
    And the user submits the sell plant form with empty fields
    Then the plant validation error should show "Plant is required"
    And the quantity validation error should show "Quantity must be greater than 0"

  @ui @admin @TC_UI_ADM_07 @215010H
  Scenario: Verify default sorting by sold date (Admin)
    When the user clicks the Sales tab in the navigation menu
    Then the sales should be sorted by Sold At in descending order