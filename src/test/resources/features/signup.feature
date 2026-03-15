@signup @smoke @regression
Feature: Signup

  Scenario: User creates an account
    Given user opens the site login page
      | url    | title    |
      | AC_URL | AC_TITLE |
    When user closes popup box
    And switches to signup
    Then user should see signup box