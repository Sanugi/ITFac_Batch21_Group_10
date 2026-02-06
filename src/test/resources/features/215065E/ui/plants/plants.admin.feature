Feature: Plant Management (Admin)

    Background:
        Given the "admin" is logged in

    @ui @admin @TC_UI_ADM_01
    Scenario: TC_UI_ADM_01 Verify that a category must be selected to add a plant
        When the user navigates to the plants page
        And the admin clicks the "Add Plant" button
        And the admin clicks "Save" without selecting a category
        Then the "Category is required" validation error should be displayed

    @ui @admin @TC_UI_ADM_02
    Scenario: TC_UI_ADM_02 Verify error messages when submitting an empty form
        When the user navigates to the plants page
        And the admin clicks the "Add Plant" button
        And the admin clicks "Save" without entering any details
        Then the "Plant name is required" error should be displayed
        And the "Price is required" error should be displayed
        And the "Quantity is required" error should be displayed

    @ui @admin @TC_UI_ADM_03
    Scenario: TC_UI_ADM_03 Verify UI validation prevents negative quantity inputs
        When the user navigates to the plants page
        And the admin clicks the "Add a Plant" button
        And the admin enters valid details but sets quantity to "-5"
        And the admin clicks "Save"
        Then the "Quantity cannot be negative" error should be displayed

    @ui @admin @TC_UI_ADM_04
    Scenario: TC_UI_ADM_04 Verify Admin can edit a plant via the UI
        When the user navigates to the plants page
        And the admin clicks the edit icon for plant "Aloe Vera"
        And the admin changes the price to "500.00"
        And the admin clicks "Save"
        Then the plant "Aloe Vera" should show a price of "500.00"

    @ui @admin @TC_UI_ADM_05
    Scenario: TC_UI_ADM_05 Ensure Edit/Delete buttons and Add Plant are visible
        When the user navigates to the plants page
        Then the "Add a Plant" button should be visible
        And the "Edit" and "Delete" icons should be visible