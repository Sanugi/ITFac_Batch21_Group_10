Feature: Sales API - user

  Background:
    Given I am authenticated as a user

  @api @user @TC_API_USR_01 @215010H
  Scenario: Verify User can retrieve all sales records
    When I send an authenticated GET request to "/sales"
    Then the API should respond with status 200
    And the response should contain a list of sales objects


  @api @user @TC_API_USR_03 @215010H
  Scenario: Verify User (Non-Admin) CANNOT create a sale
    When I send POST request to "/sales/plant/10?quantity=5"
    Then the API should respond with status 403
    And the response should indicate insufficient permissions


  @api @user @TC_API_USR_04 @215010H
  Scenario: Verify User (Non-Admin) CANNOT delete a sale
    When I send an authenticated DELETE request to "/sales/50"
    Then I should receive a 403 response
    And the response should indicate insufficient permissions
