Feature: Manage User Categories

  Background:
    Given the "admin" is logged in

  @ui @admin @TC_UI_ADM_01
  Scenario: Verify Add Category Button is Clickable
    Given the user is on the categories page
    Then the "Add a Category" button should be visible
    When the user clicks the "Add a Category" button
    Then the user should be on the add category page

  @ui @admin @TC_UI_ADM_02
  Scenario: Verify Adding a New Category
    Given the user is on the AddCategories page
    When the user enters category name "seed"
    And the user selects parent category "Seeds"
    And the user clicks the save button
    Then the user should be redirected to the categories page from add category
    And a success message "Category created successfully" should be displayed

  @ui @admin @TC_UI_ADM_03
  Scenario: Verify Edit Button is Clickable
    Given the user is on the categories page
    When the user clicks the edit button for category "96"
    Then the user should be redirected to the edit category page for "96"

  @ui @admin @TC_UI_ADM_04
  Scenario: Verify Cancel Button Action in Edit Specific Category Page
    Given the user is on the edit categories page for "96"
    When the user clicks the cancel button
    Then the user should be redirected to the categories page from edit category

  @ui @admin @TC_UI_ADM_05
  Scenario: Verify Delete Action with Confirmation
    Given the user is on the categories page
    When the user clicks the delete button for category for "95"
    Then a confirmation popup should be displayed
    When the user clicks "OK" on the confirmation popup
    Then the category should be deleted
    And a success delete message "Category deleted successfully" should be displayed




