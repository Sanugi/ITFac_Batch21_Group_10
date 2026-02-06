Feature: User Login

  As a registered user
  I want to log into the system
  So that I can access my account

  @valid
  Scenario: User Login with valid credentials
    Given the user is on the login page
    When the user enters username "testuser"
    And the user enters password "test123"
    And the user clicks the login button
    Then the user should be logged in successfully

  @valid
  Scenario: Admin Login with valid credentials
    Given the user is on the login page
    When the user enters username "Admin"
    And the user enters password "admin123"
    And the user clicks the login button
    Then the user should be logged in successfully

  @invalid
  Scenario: Login with invalid credentials
    Given the user is on the login page
    When the user enters username "wronguser"
    And the user enters password "wrongpass"
    And the user clicks the login button
    Then an error message should be displayed here that normal user login and admin login provide that
