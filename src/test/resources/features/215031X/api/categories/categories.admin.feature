Feature: Categories API - Admin Operations

  Background:
    Given I am authenticated as an admin

  @api @admin @TC_API_ADM_01 @215031X
  Scenario: Verify categories are sorting by ID
    When I send an authenticated GET request to "/categories/page?page=1&size=10&sortField=id&sortDir=asc"
    Then I should receive a 200 response

  @api @admin @TC_API_ADM_02 @215031X
  Scenario: Verify admin gets list of categories for each page
    When I send an authenticated GET request to "/categories/page?page=2&size=10"
    Then I should receive a 200 response

  @api @admin @TC_API_ADM_03 @215031X
  Scenario: Verify filtering by categories by name
    When I send an authenticated GET request to "/categories?name=Fungi"
    Then I should receive a 200 response
    And only categories with name containing "Fungi" should be returned

  @api @admin @TC_API_ADM_04 @215031X
  Scenario: Verify filtering by parent category
    When I send an authenticated GET request to "/categories?parentId=1"
    Then I should receive a 200 response

  @api @admin @TC_API_ADM_05 @215031X
  Scenario: Ensure API supports combined filters
    When I send an authenticated GET request to "/categories?name=Melon&parentId=23"
    Then I should receive a 200 response

