Feature: Get the offers of a product
  As a user I want to view the offers of a product

  Scenario: Get a list of offers of a product
    Given there is a product "iPhone 8"
    And the "iPhone 8" product has 8 offers
    When I go to the "iPhone 8" product offers page
    Then I get the "iPhone 8" product details
    And I get 8 items in the list of offers
