Feature: Home page
  As a user of the page
  I would like to see the home page

  Scenario: Get the content of the home page
    Given there are 6 product categories
    When I am on the Home page
    Then I should get the welcome message "Welcome to My Product Catalogue!"
    And I should see 6 items in the category list
