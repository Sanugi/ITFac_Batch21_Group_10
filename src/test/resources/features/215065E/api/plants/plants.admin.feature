Feature: Plant Management API - Admin Operations

  Background:
    Given I am authenticated as an admin

  @TC_API_ADM_01
  Scenario: Verify Admin can create a plant under a specific category
    # Sending the POST request
    When I send an authenticated POST request to "/plants/category/10" with body:
      """
      {
        "name": "Palm Date{random}",
        "price": 100,
        "quantity": 50
      }
      """
    
    # Validating the Response Status Code (201 Created)
    Then the API should respond with status 201

    # Validating the Response Body
    # We check that the response contains the data we sent back
    And the response should contain a "name"
    And the response should contain a "price"
    
    # We check that an ID was generated (key exists)
    And the response should contain a "id"


    @TC_API_ADM_02
    Scenario: Verify API returns 400 Bad Request when price is 0
    # 1. Send POST with invalid price (0)
    When I send an authenticated POST request to "/plants/category/10" with body:
      """
      {
        "name": "Palm Lady",
        "price": 0,
        "quantity": 10
      }
      """

    # 2. Verify Status Code 400 (Bad Request)
    Then the API should respond with status 400

    # 3. Verify the Error Message
    # This step checks if the text exists anywhere in the response JSON
    And the response body should contain "Price must be greater than 0"


      @TC_API_ADM_03
  Scenario: Verify Admin can update a plant using the PUT endpoint
    # 1. Send PUT request to update plant ID 100
    When I send an authenticated PUT request to "/plants/100" with body:
      """
      {
        "id": 100,
        "name": "Delphinium Updated",
        "price": 120,
        "quantity": 50
      }
      """

    # 2. Verify Status Code 200 OK
    Then the API should respond with status 200

    # 3. Verify the response body contains the updated values
    And the response should contain "name" as "Delphinium Updated"
    And the response should contain a "price"


      @TC_API_ADM_04
  Scenario: Verify API validation for plant names shorter than 3 characters
    # 1. Send POST with a short name ("AB" = 2 characters)
    When I send an authenticated POST request to "/plants/category/10" with body:
      """
      {
        "name": "AB",
        "price": 50,
        "quantity": 10
      }
      """

    # 2. Verify Status Code 400 (Bad Request)
    Then the API should respond with status 400

    # 3. Verify the error message about name length
    And the response body should contain "Plant name must be between 3 and 25 characters"


      @TC_API_ADM_05
  Scenario: Verify Admin can delete an existing plant by ID
    # 1. Send DELETE request to remove plant ID 99
    When I send an authenticated DELETE request to "/plants/99"

    # 2. Verify Status Code 204 (No Content = deleted successfully)
    Then the API should respond with status 204

    # 3. Verify the plant no longer exists by sending a GET request
    When I send an authenticated GET request to "/plants/99"

    # 4. Verify Status Code 404 (Not Found)
    Then the API should respond with status 404