Feature: Dashboard Navigation

  Background:
    Given the "testuser" is logged in

  Scenario: Navigate to Categories page
    Given the user is on the dashboard page
    When the user clicks on the "Manage Categories" link
    Then the user should be redirected to the categories page

  Scenario: Navigate to Plants page
    Given the user is on the dashboard page
    When the user clicks on the "Manage Plants" link
    Then the user should be redirected to the plants page

  Scenario: Navigate to Sales page
    Given the user is on the dashboard page
    When the user clicks on the "View Sales" link
    Then the user should be redirected to the sales page
