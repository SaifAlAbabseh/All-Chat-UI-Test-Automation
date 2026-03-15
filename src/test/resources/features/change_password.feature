@change_password @smoke @regression
Feature: Change Password

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
      | username    |
      | AC_USERNAME |
    When user clicks on the edit profile link

  Scenario: User changes their password
    And user clicks on the change password link
    And types current password
      | password    |
      | AC_PASSWORD |
    And types new password
      | password    |
      | AC_PASSWORD |
    And confirms new password
      | password    |
      | AC_PASSWORD |
    And clicks on the change button
    Then user should see the successful changed password message
