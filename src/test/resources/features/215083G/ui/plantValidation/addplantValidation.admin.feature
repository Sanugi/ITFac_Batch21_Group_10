Feature: Admin add plant - field validation

  As an admin
  I want form validation for adding plants
  So that invalid data is rejected and errors are shown below each field

  Background:
    Given the "admin" is logged in

  @ui @admin_PlantValidation_01
  Scenario: Plant name is required
    When the user is on the plants page
    And the admin leaves the plant name empty
    And the admin selects plant category "Roses"
    And the admin enters plant price "250"
    And the admin enters plant quantity "10"
    And the admin clicks the save button
    Then validation error "Plant name is required" should be displayed below the name field

  @ui @admin_PlantValidation_02
  Scenario: Plant name too short
    When the user is on the plants page
    And the admin enters plant name "Ar"
    And the admin selects plant category "Roses"
    And the admin enters plant price "250"
    And the admin enters plant quantity "10"
    And the admin clicks the save button
    Then validation error "Plant name must be between 3 and 25 characters" should be displayed below the name field

  @ui @admin_PlantValidation_03
  Scenario: Plant name too long
    When the user is on the plants page
    And the admin enters plant name "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    And the admin selects plant category "Roses"
    And the admin enters plant price "250"
    And the admin enters plant quantity "10"
    And the admin clicks the save button
    Then validation error "Plant name must be between 3 and 25 characters" should be displayed below the name field

  @ui @admin_PlantValidation_04
  Scenario: Price is required
    When the user is on the plants page
    And the admin enters plant name "Rose Red"
    And the admin selects plant category "Roses"
    And the admin leaves the plant price empty
    And the admin enters plant quantity "10"
    And the admin clicks the save button
    Then validation error "Price is required" should be displayed below the price field

  @ui @admin_PlantValidation_05
  Scenario: Price must be greater than 0
    When the user is on the plants page
    And the admin enters plant name "Tulip Pink"
    And the admin selects plant category "Tulips"
    And the admin enters plant price "0"
    And the admin enters plant quantity "10"
    And the admin clicks the save button
    Then validation error "Price must be greater than 0" should be displayed below the price field

  @ui @admin_PlantValidation_06
  Scenario: Quantity is required
    When the user is on the plants page
    And the admin enters plant name "Orchid Moth"
    And the admin selects plant category "Orchids"
    And the admin enters plant price "250"
    And the admin leaves the plant quantity empty
    And the admin clicks the save button
    Then validation error "Quantity is required" should be displayed below the quantity field

  @ui @admin_PlantValidation_07
  Scenario: Quantity cannot be negative
    When the user is on the plants page
    And the admin enters plant name "Fern Boston"
    And the admin selects plant category "Ferns"
    And the admin enters plant price "250"
    And the admin enters plant quantity "-1"
    And the admin clicks the save button
    Then validation error "Quantity cannot be negative" should be displayed below the quantity field

  @ui @admin_PlantValidation_08
  Scenario: Quantity must be numeric
    When the user is on the plants page
    And the admin enters plant name "Cactus Ball"
    And the admin selects plant category "Cacti"
    And the admin enters plant price "250"
    And the admin enters plant quantity "a"
    And the admin clicks the save button
    Then validation error "Please enter a number" should be displayed below the quantity field

  @ui @admin_PlantValidation_09
  Scenario: Category is required
    When the user is on the plants page
    And the admin enters plant name "Peace Lily"
    And the admin leaves the category unselected
    And the admin enters plant price "250"
    And the admin enters plant quantity "10"
    And the admin clicks the save button
    Then validation error "Category is required" should be displayed below the category field

  @ui @admin_addPlant @TC_UI_ADM_10
  Scenario: Sub-categories are visible in category dropdown
    When the user is on the plants page
    Then the category dropdown should show sub-categories "Orchids, Roses, Lilies, Tulips"

  @ui @admin_addPlant @TC_UI_ADM_11
  Scenario: Multiple validation errors appear under respective fields
    When the user is on the plants page
    And the admin leaves the plant name empty
    And the admin leaves the category unselected
    And the admin enters plant price "0"
    And the admin enters plant quantity "-5"
    And the admin clicks the save button
    Then validation error "Plant name is required" should be displayed below the name field
    And validation error "Category is required" should be displayed below the category field
    And validation error "Price must be greater than 0" should be displayed below the price field
    And validation error "Quantity cannot be negative" should be displayed below the quantity field

  @ui @admin_addPlant @TC_UI_ADM_12
  Scenario: Cancel returns to plant list
    When the user is on the plants page
    And the admin clicks the cancel button
    Then the admin should be returned to the plant list page