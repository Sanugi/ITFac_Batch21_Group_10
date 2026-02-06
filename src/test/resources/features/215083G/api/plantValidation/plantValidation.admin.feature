Feature: Plant API - Admin Validation & Sorting

  Background:
    Given Admin is authenticated

  @ui @admin @PlantValidation_01
  Scenario: Verify unauthenticated user is redirected to login when accessing Add Plant
    Given User is not authenticated
    When User sends an unauthenticated GET request to "/ui/plants/add"
    Then the API should respond with status 302
    And the response should contain "Location" header with value "/ui/login"

  @api @admin @PlantValidation_02
  Scenario: Verify alphanumeric sorting for Plants table
    When Admin sends an authenticated GET request to "/plants/paged?sort=name,asc"
    Then the API should respond with status 200
    And the response list should be ordered as "Aloe Vera, Bamboo Lucky, Beetroot, ZZ Plant"

  @api @admin @PlantValidation_03
  Scenario: Verify prevent updating a plant with a null category
    When Admin sends an authenticated PUT request to "/plants/1" with body:
      """
      {
        "id": 1,
        "name": "Snake Plant",
        "price": 10,
        "quantity": 5,
        "categoryId": null
      }
      """
    Then the API should respond with status 400
    And the response should contain error message "Category is required"

  @api @admin @PlantValidation_04
  Scenario: Verify data persistence for high-precision decimal prices
    When Admin sends an authenticated PUT request to "/plants/edit/1" with body:
      """
      {
        "price": 1250.50
      }
      """
    Then the API should respond with status 200
    And the response should contain "price" as "1250.50"
