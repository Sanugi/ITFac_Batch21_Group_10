Feature: Dashboard API - Plants Summary

  Background:
    Given I am authenticated as a user


  @api @user @TC_API_USR_03 @215046X
  Scenario: Validate total number of Categories
    When I send an authenticated GET request to "/categories/summary"
    Then the API should respond with status 200
    And the response should contain a "mainCategories"
    And the response should contain a "subCategories"
    And the "mainCategories" should be non-negative
    And the "subCategories" should be non-negative

  @api @user @TC_API_USR_04 @215046X
  Scenario: Validate total number of  plants
    When I send an authenticated GET request to "/plants/summary"
    Then the API should respond with status 200
    And the response should contain a "totalPlants"
    And the response should contain a "lowStockPlants"
    And the "totalPlants" should be non-negative
    And the "lowStockPlants" should be non-negative





#  @api @215046X
#  Scenario: Admin can access plants summary
#    Given I am authenticated as an admin
#    When I send an authenticated GET request to "/plants/summary"
#    Then the API should respond with status 200
#    And the response should contain a "totalPlants"
#    And the response should contain a "lowStockPlants"
#    And the "totalPlants" should be non-negative
#    And the "lowStockPlants" should be non-negative
