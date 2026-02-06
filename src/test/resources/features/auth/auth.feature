
Feature: API Authentication

  @api
  Scenario:User Login with valid credentials
   
    When I authenticate with username "testuser" and password "test123"
    Then the API should respond with status 200
    And the response should contain a "token"
    And the response should contain "tokenType" as "Bearer"

  @api
  Scenario:Admin Login with valid credentials
    
    When I authenticate with username "admin" and password "admin123"
    Then the API should respond with status 200
    And the response should contain a "token"
    And the response should contain "tokenType" as "Bearer"
    And I save the authentication token


  @api
  Scenario: Failed Login with invalid username
    When I authenticate with username "invaliduser" and password "test123"
    Then the API should respond with status 401
    And the response should contain an error message

  @api
  Scenario: Failed Login with invalid password
 
    When I authenticate with username "testuser" and password "wrongpassword"
    Then the API should respond with status 401
    And the response should contain an error message

  @api
  Scenario: Failed Login with both invalid username and password
  
    When I authenticate with username "invaliduser" and password "wrongpassword"
    Then the API should respond with status 401
    And the response should contain an error message

  @api
  Scenario: Failed Login with empty username
   
    When I authenticate with username "" and password "test123"
    Then the API should respond with status 401

  @api
  Scenario: Failed Login with empty password
   
    When I authenticate with username "testuser" and password ""
    Then the API should respond with status 401

  @api
  Scenario: Failed Login with empty credentials

    When I authenticate with username "" and password ""
    Then the API should respond with status 401
