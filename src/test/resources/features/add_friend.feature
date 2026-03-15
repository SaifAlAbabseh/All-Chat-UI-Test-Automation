@add_friend @smoke @regression
Feature: Add Friend

  Background:
    Given user opens the site login page
      | url    | title    |
      | AC_URL | AC_TITLE |
    When user closes popup box

  Scenario: User search and send friend request to friend
    And user enters valid credentials
      | username    | password    |
      | AC_USERNAME | AC_PASSWORD |
    And clicks login
    Then user should be navigated to the main page
      | username    |
      | AC_USERNAME |
    When user sees friend is already added and remove them
      | username           |
      | AC_FRIEND_USERNAME |
    And clicks on add friend link
    And types friend username
      | username           |
      | AC_FRIEND_USERNAME |
    And picks friend from suggestions box
      | username           |
      | AC_FRIEND_USERNAME |
    And clicks on the add button
    Then user should see sent friend request message
      | subject                                  | body                                  | username    | email           | app_password    |
      | AC_FRIEND_REQUEST_EMAIL_EXPECTED_SUBJECT | AC_FRIEND_REQUEST_EMAIL_EXPECTED_BODY | AC_USERNAME | AC_FRIEND_EMAIL | AC_APP_PASSWORD |

  Scenario: User accepts friend request
    And user enters valid credentials
      | username           | password    |
      | AC_FRIEND_USERNAME | AC_PASSWORD |
    And clicks login
    Then user should be navigated to the main page
      | username           |
      | AC_FRIEND_USERNAME |
    And clicks on the notifications button
    And clicks accept for the friend request from user
      | username    |
      | AC_USERNAME |
    Then user sees the new added friend
      | username    |
      | AC_USERNAME |