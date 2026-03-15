@edit_profile_picture @smoke @regression
Feature: Edit Profile Picture

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

  Scenario: User changes their profile picture
    And user sees their current picture and username
      | username    |
      | AC_USERNAME |
    And clicks on the change picture link
    And selects picture
    And clicks on the submit button
    Then user should see the successful changed message