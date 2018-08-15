Feature: Login
  login.

  Background:
    Given There are 3 courses
    Given I am on create new course page
    And Add a course with below details
      | coursename    | 1 - Tokyo CSD |
      | duration      | 30            |
      | country       | China         |
      | city          | Chengdu       |
      | startdate     | 2017-10-23    |
      | address       | odd-e         |
      | coursedetails | CSD training  |
      | instructor    | Terry         |
    And I click the Create button
    And I am on create new course page
    And Add a course with below details
      | coursename    | 1 - Osaka CSD |
      | duration      | 30            |
      | country       | China         |
      | city          | Chengdu       |
      | startdate     | 2017-10-23    |
      | address       | odd-e         |
      | coursedetails | CSD training  |
      | instructor    | Terry         |
    And I click the Create button
    And "terry@odd-e.com" which in "China" and "Chengdu" is a contact already


  @developing
  Scenario: Login success
    Given Visit Login Page
    Given There is a user with "mary@example.com" and "abcd1234"
    Given Fill form with "mary@example.com" and "abcd1234"
    When I click login button
    Then Show course list of current user

  @developing
  Scenario Outline: Login fail
    Given Visit Login Page
    Given Fill form with "<email>" and "<password>"
    When I click login button
    Then I should move to page with url "<url>"
    And Login failed message is shown

    Examples:
      | email               | password       | url                   |
      | mary@example.com    | hogehoge       | login.jsp?status=fail |
      | unknown@example.com | hogehoge       | login.jsp?status=fail |
      | uninit@example.com  | uninitpassword | login.jsp?status=fail |

  @developing
  Scenario Outline: Cources List after Login
    Given Visit Login Page
    Given Fill form with "<email>" and "<password>"
    When I click login button
    Then Show cources list "<cources>"

    Examples:
      | email              | password | cources                     |
      | JohnSmith@mail.com | abcd1234 | 1 - Tokyo CSD               |
      | JaneDoe@mail.com   | abcd1001 | 1 - Tokyo CSD,3 - Osaka CSD |
#      | john@example.com   | abcd1002 | 3 - Osaka CSD               |
#      | Bobb@example.com   | abcd1003 |                             |

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
