Feature: Sales API - (Unauthenticated)

  Background:
    Given no user is logged in

  @api @user @TC_API_USR_02 @215010H
  Scenario: Unauthenticated user cannot access sales
    When I send GET request to "/sales"
    Then I should receive a 401 response
    And the response should indicate access is denied