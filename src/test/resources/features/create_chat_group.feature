@create_chat_group @smoke @regression
Feature: Create Chat Group

  Background:
    Given user opens the site login page
      | url    | title    |
      | AC_URL | AC_TITLE |
    When user closes popup box
    And user enters valid credentials
      | username    | password    |
      | AC_USERNAME | AC_PASSWORD |
    And clicks login
    Then user should be navigated to the main page
      | username           |
      | AC_USERNAME        |

  Scenario: User creates new chat group
    When user clicks on create group button
    And types group name and selects picture
      | name     |
      | testAuto |
    And clicks on the create group submit button
    Then user should see the successful created group message
      | name     |
      | testAuto |
    And user should see the new created group in the groups box
      | name     |
      | testAuto |