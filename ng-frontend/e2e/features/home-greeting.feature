Feature: Greet the user
  As a user of the page
  I would like to get a greeting

  Scenario: Greet the user
    Given I am on the Home page
    Then I should get the welcome message "Welcome to My Product Catalogue!"
