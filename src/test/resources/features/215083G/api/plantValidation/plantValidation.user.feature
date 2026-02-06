Feature: Plant Validation - User Operations

  Background:
    Given User is authenticated

  @api @user_PlantValidation_01
  Scenario: Verify user is blocked from Admin-only UI routes
    When User sends an authenticated GET request to "/ui/plants/add"
    Then the API should respond with status 403
    And the response should contain error "Forbidden"

  @api @user_PlantValidation_02
  Scenario: Verify user can search plants by name and category ID
    When User sends an authenticated GET request to "/api/plants/paged?name=Rose&categoryId=2"
    Then the API should respond with status 200
    And the response should contain plants filtered by name "Rose" and categoryId "2"

  @api @user_PlantValidation_03
  Scenario: Verify user cannot modify plant list
    When User sends an authenticated PUT request to "/api/plants/edit" with body:
      """
      {
        "id": 1,
        "name": "Hacked Plant"
      }
      """
    Then the API should respond with status 403
    And the response should contain error "Forbidden"

  @api @user_PlantValidation_04
  Scenario: Verify user can filter plants using partial name strings
    When User sends an authenticated GET request to "/api/plants/paged?name=Lav"
    Then the API should respond with status 200
    And the response should contain plants with name containing "Lav"

  @api @user_PlantValidation_05
  Scenario: Ensure the API respects the size parameter in a paged request
    When User sends an authenticated GET request to "/api/plants/paged?size=5"
    Then the API should respond with status 200
    And the response should contain exactly 5 plants in content