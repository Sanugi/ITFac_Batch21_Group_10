Feature: Plant Management API - User Operations

Background:
    # User is authenticated (Source: AuthStepDefinitions.java)
    Given I am authenticated as a user


@TC_API_USR_01
Scenario: Verify User can retrieve a list of plants using the paged endpoint
    # Send GET request to the paged plants endpoint
    When I send an authenticated GET request to "/plants/paged"

    # Verify Status Code 200 OK
    Then the API should respond with status 200

    # Verify the response contains the paged list structure
    And the response body should contain "content"
    And the response body should contain "totalElements"


@TC_API_USR_02
Scenario: Verify User can search using the name query parameter
    # Send GET request with query parameter (name=Cactus Ball)
    # Note: We use '%20' to represent the space in the URL
    When I send an authenticated GET request to "/plants/paged?name=Cactus%20Ball"
    
    # Status Code 200 OK is returned
    Then the API should respond with status 200
    
    # Response content verification
    # We use the existing generic step to verify the text exists in the response
    And the response body should contain "Cactus Ball"

@TC_API_USR_03
Scenario: Verify User can retrieve all plants belonging to a specific category
    # Send GET request to retrieve plants for category ID 5
    When I send an authenticated GET request to "/plants/category/5"

    # Verify Status Code 200 OK
    Then the API should respond with status 200

    # Verify the response contains plant data with matching category
    And the response body should contain "category"


@TC_API_USR_04
Scenario: Verify User cannot create a plant (RBAC Check)
    # Send POST request as a regular user (not admin)
    When I send an authenticated POST request to "/plants/category/10" with body:
    """
    {
        "name": "UserPlant",
        "price": 10,
        "quantity": 5
    }
    """

    # Verify Status Code 403 (Forbidden - access denied)
    Then the API should respond with status 403


@TC_API_USR_05
Scenario: Verify User cannot delete a plant
    # Send DELETE request as a regular user (not admin)
    When I send an authenticated DELETE request to "/plants/98"

    # Verify Status Code 403 (Forbidden - access denied)
    Then the API should respond with status 403