Feature: Login
  User should be able to login
  if email address and password are valid

  Background:
    Given There are 3 courses
    And I enroll participants to "CSD-1" from course detail page
      | JohnSmith@mail.com	Tom	Smith	CS	Singapore |
      | JaneDoe@mail.com	John	Fisher	CS	Singapore |
    And I enroll participants to "CSD-2" from course detail page
      | john@example.com	john	jon	CS	Singapore    |
      | JaneDoe@mail.com	John	Fisher	CS	Singapore |

  Scenario Outline: login
    Given There are users as bellow
      | mary@example.com | abcd1234 |
    When I login with "<email>" and "<password>"
    Then I should move to page with url "<url>"
    And Login failed message is <message>

    Examples:
      | email               | password | url                   | message |
      | mary@example.com    | abcd1234 | course_list.jsp       | hidden  |
      | mary@example.com    | hogehoge | login.jsp?status=fail | shown   |
      | unknown@example.com | abcd1234 | login.jsp?status=fail | shown   |

  Scenario: login fail when user's password is not initialized
    Given There is a user with "mary@example.com" but password initialize is undone
    When I login with "mary@example.com" and "abcd1234"
    Then I should move to page with url "login.jsp?status=fail"
    And Login failed message is shown

  Scenario Outline: Courses List after Login
    Given There are users as bellow
      | JohnSmith@mail.com | abcd1234 |
      | JaneDoe@mail.com   | abcd1001 |
      | john@example.com   | abcd1002 |
      | Bobb@example.com   | abcd1003 |
    When I login with "<email>" and "<password>"
    Then Show courses list "<courses>"

    Examples:
      | email              | password | courses     |
      | JohnSmith@mail.com | abcd1234 | CSD-1       |
      | JaneDoe@mail.com   | abcd1001 | CSD-1,CSD-2 |
      | john@example.com   | abcd1002 | CSD-2       |
      | Bobb@example.com   | abcd1003 |             |

  Scenario: Preserve login info after navigation
    Given There are users as bellow
      | john@example.com | abcd1002 |
    And I login with "john@example.com" and "abcd1002"
    And I move to top page
    When I move to course list page
    Then Show courses list "CSD-2"

    Scenario: Courses List no login
      When I move to course list page
      Then Show all courses list "CSD-1,CSD-2,CSD-3"