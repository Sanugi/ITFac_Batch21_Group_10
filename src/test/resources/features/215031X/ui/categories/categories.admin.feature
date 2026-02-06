Feature: Manage Categories UI

  Background:
    Given the "admin" is logged in

  @ui @admin @TC_UI_ADM_01 @215031X
  Scenario: Verify category page is accessible
    Given the user is on the dashboard page
    When the user clicks on the "Categories" option in the side navigation bar
    Then the user is on the categories page

  @ui @admin @TC_UI_ADM_02 @215031X
  Scenario: Verify whether searching by subcategory filters results correctly
    Given the user is on the categories page
    And subcategory search field is visible
    When the user enters category name "seed"
    And the user clicks the search button
    Then the url should be "name=seed&parentId="

  @ui @admin @TC_UI_ADM_03 @215031X
  Scenario: Verify that filtering by the parent category dropdown filters results correctly
    Given the user is on the categories page
    And the All Parents dropdown field is visible
    When the user clicks the dropdown field
    And the user clicks the "Outdoor" in the dropdown list
    And the user clicks the search button
    Then the url should be "name=&parentId=2"

  @ui @admin @TC_UI_ADM_04 @215031X
  Scenario: Verify using the subcategory and parent dropdown filter together
    Given the user is on the categories page
    And subcategory search field is visible
    And the All Parents dropdown field is visible
    When the user enters category name "Rural"
    And the user clicks the dropdown field
    And the user clicks the "Outdoor" in the dropdown list
    And the user clicks the search button
    Then the url should be "name=Rural&parentId=2"

  @ui @admin @TC_UI_ADM_05 @215031X
  Scenario: Ensure Reset clears all filters
    Given the user is on the categories page
    When the user enters category name "seed"
    And the user clicks the search button
    Then the url should be "name=seed&parentId="
    When the user clicks the Reset button
    Then the user is on the categories page





