@group_chat @smoke @regression
Feature: Group Chat

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

  Scenario: User admin chats in group
    When user clicks on chat button for group
      | name     |
      | testAuto |
    And types message
      | message                                |
      | Hello, this is test automation message |
    And clicks on the send message button
    Then user should see the new sent message in the chat box
      | message                                |
      | Hello, this is test automation message |

  Scenario: User admin changes group picture
    When user clicks on chat button for group
      | name     |
      | testAuto |
    When user admin clicks on group settings button
    And clicks on edit group picture button
    And selects group picture
    And clicks on change group picture button
    Then user admin should see the successfully changed group picture message

  Scenario: User admin invites friend to group
    When user clicks on chat button for group
      | name     |
      | testAuto |
    When user admin clicks on invite friends button
    And clicks invite button for friend
      | username           |
      | AC_FRIEND_USERNAME |

  Scenario: User admin removes friend from group
    When user clicks on chat button for group
      | name     |
      | testAuto |
    When user admin clicks on group settings button
    Then user admin clicks on kick button and sees friend removed
      | username           |
      | AC_FRIEND_USERNAME |

  Scenario: User admin deletes group
    When user clicks on chat button for group
      | name     |
      | testAuto |
    When user admin clicks on group settings button
    And clicks on delete group button
    Then user admin do not see the group anymore
      | name     |
      | testAuto |