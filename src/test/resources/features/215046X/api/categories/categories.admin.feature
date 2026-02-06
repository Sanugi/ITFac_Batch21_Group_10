Feature: Categories API - Admin Operations

  Background:
    Given I am authenticated as an admin

  @api @admin @TC_API_ADM_01 @215046X
  Scenario: Verify Admin can Add a Category using the correct endpoint
    # Step 1: POST /api/categories with  main Category
    When I send an authenticated POST request to "/categories" with body:
      """
      {
        "name": "Flora{random}",
        "description": "Main flowering plants"
      }
      """
    Then the API should respond with status 201
    And the response should contain a "id"
    And the response "id" save as "categoriesMainId"
    # Step 2: GET /categories/{id} to verify creation
    When I send an authenticated GET request to "/categories/{categoriesMainId}"
    Then I should receive a 200 response
    And the response body should contain "Flora"
    #Step 3: POST /api/categories with sub Category
    When I send an authenticated POST request to "/categories" with body:
      """
      {
        "name": "rose{random}",
        "description": "sub flowering plants",
        "parent": {"id": categoriesMainId}
      }
      """
    Then the API should respond with status 201
    And the response should contain a "id"
    And the response "id" save as "categoriesSubId"
    # Step 4: GET /categories/{id} to verify creation
    When I send an authenticated GET request to "/categories/{categoriesSubId}"
    Then I should receive a 200 response
    And the response body should contain "rose"



  @api @admin @TC_API_ADM_02 @215046X
  Scenario: Verify that existing category can't add again
    # Try to create the same main category again
    When I send an authenticated POST request to "/categories" with body:
      """
      {
        "name": "Indoor",
        "description": "main category adding"
      }
      """
    Then the API should respond with status 400
    And the response should contain error message about duplicate category
    # Try to create the same sub category again
    When I send an authenticated POST request to "/categories" with body:
      """
       {
        "name": "Flowering",
        "description": "sub flowering plants",
        "parent": {"id": 2 }
      }
      """
    Then the API should respond with status 400
    And the response should contain error message about duplicate category



  @api @admin @TC_API_ADM_03 @215046X
  Scenario: Verify Admin can update a Category using the PUT endpoint
    # Step 1: POST /api/categories with  main Category
    When I send an authenticated POST request to "/categories" with body:
      """
      {
        "name": "Flora{random}",
        "description": "Main flowering plants"
      }
      """
    Then the API should respond with status 201
    And the response should contain a "id"
    And the response "id" save as "categoriesMainId"
    When I send an authenticated PUT request to "/categories/{categoriesMainId}" with body:
      """
      {
        "name": "Flora",
        "description": "main category updated to sub categories And name changed",
        "parentId": 25
      }
      """
    Then the API should respond with status 200
    And the response should contain "name" as "Flora"
    When I send an authenticated PUT request to "/categories/{categoriesMainId}" with body:
      """
      {
        "name": "Flora",
        "description": "sub categories updated to main category "

      }
      """
    Then the API should respond with status 200
    And the response should contain "name" as "Flora"

  @api @admin @TC_API_ADM_04 @validation @215046X
  Scenario: Verify API validation for Categoryname shorter than 3 characters
    When I send an authenticated POST request to "/categories" with body:
      """
      {
        "name": "Cy",
        "description": "Main flowering plants"
      }
      """
    Then the API should respond with status 400
    And the response should contain validation error for category name length
    When I send an authenticated POST request to "/categories" with body:
    """
      {
        "name": "cy",
        "description": "sub flowering plants",
        "parent": {"id": 2 }
      }
      """
    Then the API should respond with status 400
    And the response should contain validation error for category name length
    When I send an authenticated POST request to "/categories" with body:
    """
      {
        "name": "",
        "description": "sub flowering plants",
        "parent": {"id": 2 }
      }
      """
    Then the API should respond with status 400
    And the response should contain validation error for category name length

  @api @admin @TC_API_ADM_05 @215046X
  Scenario: Verify Admin can delete an existing Category by ID
    #delete main categoires
    When I send an authenticated POST request to "/categories" with body:
      """
      {
        "name": "Flora{random}",
        "description": "Main flowering plants"
      }
      """
    Then the API should respond with status 201
    And the response should contain a "id"
    And the response "id" save as "categoriesMainId"
    When I send an authenticated DELETE request to "/categories/{categoriesMainId}"
    Then the API should respond with status 204
      # Step 4: GET /categories/{id} to verify delete
    When I send an authenticated GET request to "/categories/{categoriesMainId}"
    Then I should receive a 404 response

    #delete sub categoires
    When I send an authenticated POST request to "/categories" with body:
      """
      {
        "name": "Flora{random}",
        "description": "Sub flowering plants",
         "parent": {"id": 2 }
      }
      """
    Then the API should respond with status 201
    And the response should contain a "id"
    And the response "id" save as "categoriesSubId"
    When I send an authenticated DELETE request to "/categories/{categoriesSubId}"
    Then the API should respond with status 204
    # Step 4: GET /categories/{id} to verify delete
    When I send an authenticated GET request to "/categories/{categoriesSubId}"
    Then I should receive a 404 response





  @api @admin @smoke
  Scenario: Verify Admin can't delete an existing MainCategory with have subCategory by ID
        # Step 1: POST /api/categories with  main Category
    When I send an authenticated POST request to "/categories" with body:
      """
      {
        "name": "Flora{random}",
        "description": "Main flowering plants"
      }
      """
    Then the API should respond with status 201
    And the response should contain a "id"
    And the response "id" save as "categoriesMainId"
    # Step 2: GET /categories/{id} to verify creation
    When I send an authenticated GET request to "/categories/{categoriesMainId}"
    Then I should receive a 200 response
    And the response body should contain "Flora"
    #Step 3: POST /api/categories with sub Category
    When I send an authenticated POST request to "/categories" with body:
      """
      {
        "name": "rose{random}",
        "description": "sub flowering plants",
        "parent": {"id": categoriesMainId}
      }
      """
    Then the API should respond with status 201
    And the response should contain a "id"
    And the response "id" save as "categoriesSubId"
        # Step 4: Try to DELETE main category (should fail because it has subcategories)
    When I send an authenticated DELETE request to "/categories/{categoriesMainId}"
    Then I should receive a 500 response
    And the response should contain error message about deleting category with subcategories


  @api @admin @validation @smoke
  Scenario: Verify API validation for Categoryname Longer than 10 characters
    When I send an authenticated POST request to "/categories" with body:
      """
      {
        "name": "CyTooMuchCharctors",
        "description": "Main flowering plants"
      }
      """
    Then the API should respond with status 400
    And the response should contain validation error for category name length
    When I send an authenticated POST request to "/categories" with body:
    """
      {
        "name": "CyTooMuchCharctors",
        "description": "sub flowering plants",
        "parent": {"id": 2 }
      }
      """
    Then the API should respond with status 400
    And the response should contain validation error for category name length
