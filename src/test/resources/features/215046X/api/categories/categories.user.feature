Feature: Categories API - User Operations

  Background:
    Given I am authenticated as a user

  @api @user @TC_API_USR_01
  Scenario: Verify user can't edit category
    #with correct body
    When I send an authenticated PUT request to "/categories/4" with body:
      """
      {
        "name": "Bad Trees",
        "parentId": 2
      }
      """
    Then the API should respond with status 403
    And the response should contain error "Forbidden"
    #with invalid user input(min>3to10<max)
    When I send an authenticated PUT request to "/categories/4" with body:
      """
      {
        "name": "Bad Trees more characters",
        "parentId": 2
      }
      """
    Then the API should respond with status 403
    And the response should contain error "Forbidden"
     #convert to main catagories from sub categories
     When I send an authenticated PUT request to "/categories/4" with body:
      """
      {
        "name": "Trees"
      }
      """
    Then the API should respond with status 403
    And the response should contain error "Forbidden"

  @api @user @TC_API_USR_02 @215046X
  Scenario: Ensure API returns main categories only
    When I send an authenticated GET request to "/categories/main"
    Then the API should respond with status 200
    And the response should contain main categories
    And the mainCategories should be non-negative

  @api @user @TC_API_USR_05 @215046X
  Scenario: Verify that the API returns the correct category details for the categoryID
    When I send an authenticated GET request to "/categories/4"
    Then the API should respond with status 200
    And the response should contain specific category details
