Feature: Manage User Categories

  Background:
    Given the "testuser" is logged in

  Scenario: Verify Search Button Hover
    Given the user is on the categories page
    Then the search button should be visible and enabled
    When the user hovers over the search button

  Scenario: Verify Search Button clickable
    Given the user is on the categories page
    When the user enters category name "Toxic"
    And the user selects parent category "1"
    And the user clicks the search button
    Then the url should contain "name=Toxic&parentId=1"

  Scenario: Dismiss Error Message on Edit Category
    Given the user is on the categories page
    When the user clicks the edit button for category "109"
    And the user clicks the save button
    Then an error message should be displayed
    When the user clicks the close icon on the error message
    Then the error message should be dismissed

