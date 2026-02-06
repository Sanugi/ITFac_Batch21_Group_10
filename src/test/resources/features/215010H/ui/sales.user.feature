Feature: Sales page (User)

  Background:
    Given the "testuser" is logged in

  @ui @admin @TC_UI_USR_01 @215010H
  Scenario: Verify sales page loads successfully after navigation (User)
    When the user clicks the Sales tab in the navigation menu
    Then the Sales page should load without errors
    And the sales table should be displayed
    And the Sales tab should be highlighted

  @ui @admin @TC_UI_USR_02 @215010H
  Scenario: Verify sales records columns are displayed correctly (User)
    When the user clicks the Sales tab in the navigation menu
    Then the Sales table columns should be visible
      | Plant       |
      | Quantity    |
      | Total Price |
      | Sold At     |

  @ui @admin @TC_UI_USR_03 @215010H
  Scenario: Verify Delete action absence (User)
    When the user clicks the Sales tab in the navigation menu
    Then the Delete action should not be visible for any record