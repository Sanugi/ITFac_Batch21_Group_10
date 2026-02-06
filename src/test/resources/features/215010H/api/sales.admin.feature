# File: `src/test/resources/features/215010H/api/sales.admin.feature`
Feature: Sales API -  (Admin)

  Background:
    Given admin is authenticated

  @api @admin @TC_API_ADM_01 @215010H
  Scenario: Get All Sales (Admin)
    When I send GET request to "/sales"
    Then I should receive a 200 response
    And the response should contain a list of sales objects

  @api @admin @TC_API_ADM_02 @215010H
  Scenario: Load sell plant dropdown (Admin)
    When I send GET request to "/plants"
    Then I should receive a 200 response
    And the response should contain a list of plants objects

  @api @admin @TC_API_ADM_03 @215010H
  Scenario: Verify Admin can create a new sale with valid data
    # Precondition: record current stock
    When I retrieve plant 10 quantity and save as initialQty
    # Create sale using the API endpoint form /sales/plant/{id}?quantity={qty}
    When I send POST request to "/sales/plant/10?quantity=5"
    Then I should receive a 201 response
    And the response should contain created sale details for plant id 10 and quantity 5
    And the plant with id 10 stock should be reduced by 5 compared to initialQty



  @api @admin @TC_API_ADM_04 @215010H
  Scenario: Verify system prevents sale creation if Plant ID is missing
    When I send POST request to "/sales?quantity=5"
    Then I should receive a 404 response
    And the response body should contain "Plant is required"



  @api @user @TC_API_ADM_05 @215010H
  Scenario: Verify system prevents sale creation with Zero quantity
    When I send POST request to "/sales/plant/10?quantity=0"
    Then I should receive a 400 response
    And the response body should contain "Quantity must be greater than 0"



@api @admin @TC_API_ADM_06 @215010H
Scenario: Verify Admin can delete an existing sale
  When I send an authenticated DELETE request to "/sales/50"
  Then I should receive a 200 or 404 response
  And the sale with id 50 should not exist