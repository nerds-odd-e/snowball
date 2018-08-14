Feature: Login
  login.

  Background:
    Given Visit Login Page

  @developing
  Scenario: Login success
    Given Fill form with "mary@example.com" and "abcd1234"
    When I click login button
    Then Show course list of current user

  @developing
  Scenario Outline: Login fail
    Given Fill form with "<email>" and "<password>"
    When I click login button
    Then I should move to page with url "<url>" and message "<message>"

    Examples:
      | email               | password       | url       | message      |
      | mary@example.com    | hogehoge       | login.jsp | login failed |
      | unknown@example.com | hogehoge       | login.jsp | login failed |
      | uninit@example.com  | uninitpassword | login.jsp | login failed |

  @developing
  Scenario Outline: Cources List after Login
    Given Fill form with "<email>" and "<password>"
    When I click login button
    Then Show cources list "<cources>"

    Examples:
      | email            | password | cources                     |
      | mary@example.com | abcd1234 | 1 - Tokyo CSD               |
      | tom@example.com  | abcd1001 | 1 - Tokyo CSD,3 - Osaka CSD |
      | john@example.com | abcd1002 | 3 - Osaka CSD               |
      | Bobb@example.com | abcd1003 |                             |

  @developing
  Scenario: Show Cources List Test
    Given I am on create new course page
    When Add a course with below details
      |coursename    | CSD-1         |
      |duration      | 30            |
      |country       | China         |
      |city          | Chengdu       |
      |startdate     | 2017-10-23    |
      |address       | odd-e         |
      |coursedetails | CSD training  |
      |instructor    | Terry         |
    And I click the Create button
    Then Matsuo Show cources list test "1 - Tokyo CSD,2 - CSD cource,3 - Osaka CSD"
