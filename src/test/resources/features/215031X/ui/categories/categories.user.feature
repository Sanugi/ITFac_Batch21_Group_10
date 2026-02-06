Feature: Manage categories UI

  Background:
    Given the "testuser" is logged in

  @ui @user @TC_UI_USR_01 @215031X
  Scenario: Verify the delete button is disabled for non-admin users
    Given the user is on the categories page
    Then the Delete button should be disabled for category "89"

  @ui @user @TC_UI_USR_02 @215031X
  Scenario: Verify correct records load when the user clicks page number 2
    Given the user is on the categories page
    When the user navigates to the bottom of the categories list
    And the user clicks on the page 2
    Then the url should contain "page=1&sortField=id&sortDir=asc&name=&parentId="

  @ui @user @TC_UI_USR_03 @215031X
  Scenario: Verify the Next button navigates to the next page
    Given the user is on the categories page
    When the user navigates to the bottom of the categories list
    And the user click on the next button
    Then the url should contain "page=1&sortField=id&sortDir=asc&name=&parentId="

  @ui @user @TC_UI_USR_04 @215031X
  Scenario: Verify the Previous button navigates to the previous page
    Given the user is on the page "2"
    When the user navigates to the bottom of the categories list
    And the user click on the previous button
    Then the url should contain "page=1&sortField=id&sortDir=asc&name=&parentId="

  @ui @user @TC_UI_USR_05 @215031X
  Scenario: Verify the save button is not working for non-admin users
    Given the user is on the categories page
    When the user clicks the edit button for category "90"
    Then the user should be redirected to the edit category page for "90"
    When the user edits the category name to "Berr"
    And the user clicks the save button
    Then an error message should be displayed


  @ui @user @bug @TC_UI_USR_06 @215031X
  Scenario: Verify the edit button is disabled for non-admin users
    Given the user is on the categories page
    Then the Edit button should be disabled for category "89"


