Feature: Admin serach a plant and search reset

  As an admin
  I want form validation for search plants and to reset plant searches
  So that invalid data is not empty and I can return to the full plant list

  Background:
    Given the "admin" is logged in

@ui @admin @PlantSearch_Reset_01
  Scenario: Reset clears search and shows full list
    Given the user is on the plants page
    And the admin searches plant name "Red Rose"
    When the admin clicks the Reset button
    Then the plant search field should be empty
    And the full list of plants should be displayed

@ui @admin @PlantSearch_Result_02
  Scenario: Search shows Beetroot in the table
    Given the user is on the plants page
    When the admin searches plant name "Beetroot"
    Then the plant table should show "Beetroot" with category "Root" and price "2.00"