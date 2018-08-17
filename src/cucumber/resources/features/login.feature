Feature: Login
  login.

  Background:
    Given There are 3 courses
    And "mary@example.com" which in "China" and "Chengdu" is a contact already
    And "JohnSmith@mail.com" which in "China" and "Chengdu" is a contact already
    And "JaneDoe@mail.com" which in "China" and "Chengdu" is a contact already
    And "john@example.com" which in "China" and "Chengdu" is a contact already
    And "Bobb@example.com" which in "China" and "Chengdu" is a contact already
    When I visit "CSD-1" detail page from course list page
    When I enroll participants to "CSD-1" from course detail page
      | JohnSmith@mail.com	Tom	Smith	CS	Singapore |
      | JaneDoe@mail.com	John	Fisher	CS	Singapore |
    When I visit "CSD-2" detail page from course list page
    When I enroll participants to "CSD-2" from course detail page
      | john@example.com	john	jon	CS	Singapore    |
      | JaneDoe@mail.com	John	Fisher	CS	Singapore |

  Scenario Outline: login
    Given Visit Login Page
    Given There is a user with "<userEmail>" and "<userPassword>" and password initialize is <initPassword>
    Given Fill form with "<inputEmail>" and "<inputPassword>"
    When I click login button
    Then I should move to page with url "<url>"
    And Login failed message is <message>

    Examples:
      | userEmail        | initPassword | userPassword | inputEmail          | inputPassword | url                   | message |
      | mary@example.com | done         | abcd1234     | mary@example.com    | abcd1234      | course_list.jsp       | hidden  |
      | mary@example.com | done         | abcd1234     | mary@example.com    | hogehoge      | login.jsp?status=fail | shown   |
      | mary@example.com | done         | abcd1234     | unknown@example.com | abcd1234      | login.jsp?status=fail | shown   |
      | mary@example.com | undone       | abcd1234     | mary@example.com    | abcd1234      | login.jsp?status=fail | shown   |

  @developing
  Scenario Outline: Courses List after Login
    Given Visit Login Page
    Given There are users as bellow
      | JohnSmith@mail.com | abcd1234 |
      | JaneDoe@mail.com   | abcd1001 |
      | john@example.com   | abcd1002 |
      | Bobb@example.com   | abcd1003 |
    Given Fill form with "<email>" and "<password>"
    When I click login button
    Then Show courses list "<courses>"

    Examples:
      | email              | password | courses     |
      | JohnSmith@mail.com | abcd1234 | CSD-1       |
      | JaneDoe@mail.com   | abcd1001 | CSD-1,CSD-2 |
      | john@example.com   | abcd1002 | CSD-2       |
      | Bobb@example.com   | abcd1003 |             |

  @developing
  Scenario Outline: Preserve login info after navigation
    Given Visit Login Page
    Given There are users as bellow
      | JaneDoe@mail.com   | abcd1001 |
      | john@example.com   | abcd1002 |
      | Bobb@example.com   | abcd1003 |
    Given Fill form with "<email>" and "<password>"
    And I click login button
    And I move to top page
    When I move to course list page
    Then Show courses list "<courses>"

    Examples:
      | email              | password | courses     |
      | JaneDoe@mail.com   | abcd1001 | CSD-1,CSD-2 |
      | john@example.com   | abcd1002 | CSD-2       |
      | Bobb@example.com   | abcd1003 |             |

  @developing
  Scenario: Show Cources List Test
    Given I am on create new course page
    When Add a course with below details
      | coursename    | CSD-1        |
      | duration      | 30           |
      | country       | China        |
      | city          | Chengdu      |
      | startdate     | 2017-10-23   |
      | address       | odd-e        |
      | coursedetails | CSD training |
      | instructor    | Terry        |
    And I click the Create button
    Then Matsuo Show cources list test "1 - Tokyo CSD,2 - CSD cource,3 - Osaka CSD"

#    TODO: ScenarioからScenarioを呼ぶ方法を聞く
  @developing
  Scenario: go to contact list link after login
    Given Visit Login Page
    Given There is a user with "mary@example.com" and "abcd1234" and password initialize is done
    Given Fill form with "mary@example.com" and "abcd1234"
    When I click login button
    Then Show course list of current user
    When I go to contact list link
    Then Show contact list

  @developing
  Scenario: go to contact list link after not login
    When I go to contact list link
    Then Show login page
