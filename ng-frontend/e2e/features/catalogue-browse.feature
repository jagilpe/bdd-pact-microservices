Feature: Browse the product catalogue
  As a user I want to browse the product catalogue

  Scenario: Get a list of products of a category
    Given there are 5 products in the "Smartphones" category
    When I go to the "Smartphones" category page
    Then I should get 5 items in the products list
