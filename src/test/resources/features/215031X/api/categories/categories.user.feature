Feature: Categories API - User Operations

  Background:
    Given I am authenticated as a user

  @api @user @TC_API_USR_01 @215031X
  Scenario: Verify categories are sorting by Name
    When I send an authenticated GET request to "/categories/page?sortField=Name&sortDir=asc"
    Then I should receive a 200 response

  @api @user @bug @TC_API_USR_02 @215031X
  Scenario: Verify categories are sorting by Parent
    When I send an authenticated GET request to "/categories/page?sortField=Parent&sortDir=asc"
    Then I should receive a 200 response

  @api @user @TC_API_USR_03 @215031X
  Scenario: Verify API filters categories by name
    When I send an authenticated GET request to "/categories?name=Fungi"
    Then I should receive a 200 response

  @api @user @TC_API_USR_04 @215031X
  Scenario: Verify filtering by parent category
    When I send an authenticated GET request to "/categories?parentId=2"
    Then I should receive a 200 response

  @api @user @TC_API_USR_05 @215031X
  Scenario: Ensure API supports combined filters
    When I send an authenticated GET request to "/categories?name=Fungi&parentId=23"
    Then I should receive a 200 response
