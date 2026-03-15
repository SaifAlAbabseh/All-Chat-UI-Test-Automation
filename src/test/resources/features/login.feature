@login @smoke @regression
Feature: Login

  Background:
    Given user opens the site login page
      | url    | title    |
      | AC_URL | AC_TITLE |
    When user closes popup box

  Scenario: User logs into the site
    And user enters valid credentials
      | username    | password    |
      | AC_USERNAME | AC_PASSWORD |
    And clicks login
    Then user should be navigated to the main page
      | username    |
      | AC_USERNAME |

  Scenario: User switches from signup to login
    And switches to signup
    And switches back to login
    And user enters valid credentials
      | username    | password    |
      | AC_USERNAME | AC_PASSWORD |
    And clicks login
    Then user should be navigated to the main page
      | username    |
      | AC_USERNAME |