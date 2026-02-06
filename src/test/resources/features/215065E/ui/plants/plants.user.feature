Feature: Plant Inventory Management

  Background:
    # This reuses the existing login code mentioned in the PDF
    Given the "user" is logged in

  @TC_UI_USR_01
  Scenario: TC_UI_USR_01 Verify low stock badge for plants with low quantity
    When the user navigates to the plants page
    And a plant exists with quantity "2"
    Then the "Low" badge should be displayed next to the quantity

  @TC_UI_USR_02
  Scenario: TC_UI_USR_02 Check display message when search returns empty list
    When the user navigates to the plants page
    And the user searches for a plant named "xyz123"
    Then the "No plants found" message should be displayed

  @TC_UI_USR_03
  Scenario: TC_UI_USR_03 Verify the list sorts correctly by Price
    When the user navigates to the plants page
    And the user clicks on the "Price" column header
    Then the plant list should be sorted by price in ascending order
  
  @TC_UI_USR_04
  Scenario: TC_UI_USR_04 Ensure user cannot see Admin-only buttons
    When the user navigates to the plants page
    Then the "Add a Plant" button should not be visible
    And the "Edit" and "Delete" actions should not be visible
  
  @TC_UI_USR_05
  Scenario: TC_UI_USR_05 Verify pagination UI works
    When the user navigates to the plants page
    And the user clicks the "Next" page button
    Then the active page number should be "2"