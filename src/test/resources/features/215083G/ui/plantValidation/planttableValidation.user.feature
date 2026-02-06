Feature: User search a plant and search reset

  As a user
  I want to search plants and reset the search
  So that I can find plants and return to the full list

  Background:
    Given the "testuser" is logged in

  @ui @user_PlantSearch_Reset_01
  Scenario: Reset clears search and shows full list
    Given the user is on the plants page
    And the user searches plant name "Red Rose"
    When the user clicks the Reset button
    Then the plant search field should be empty
    And the full list of plants should be displayed

  @ui @user_PlantSearch_Result_02
  Scenario: Search shows Beetroot in the table
    Given the user is on the plants page
    When the user searches plant name "Beetroot"
    Then the plant table should show "Beetroot" with category "Root" and price "2.00"

  @ui @user_PlantSort_Price_Desc_01
  Scenario: Sort Price from Ascending to Descending
    Given the user is on the plants page
    When the user clicks the Price column header
    Then the URL should contain "sortDir=desc"
    And the table should be sorted by price in descending order

  @ui @user_PlantSort_Price_Asc_01
  Scenario: Sort Price from Descending to Ascending
    Given the user is on the plants page
    When the user clicks the Price column header
    Then the URL should contain "sortDir=asc"
    And the table should be sorted by price in ascending order

  @ui @user_PlantEdit_Hidden_01
  Scenario: Edit button is hidden for user
    Given the user is on the plants page
    Then the Edit button should not be visible in the plant table
