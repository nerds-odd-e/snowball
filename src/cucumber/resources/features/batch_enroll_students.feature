Feature: batch enroll students
  As a user I want to enroll multiple students to course

  Background:
    Given There is a course "CSD Tokyo" already

  @developing
  Scenario: Add 1 new student
    Given Tom does not exists in system
      | email,firstname,lastname,company,country,city    |
      | tom@example.com,Tom,Smith,CS,Singapore,Singapore |
    When I enroll a student
    Then Enrollment Result page is displayed
    And "Tom" is displayed as "OK"
      | tom@example.com |
    When I visit Contact List page
    Then "Tom" is displayed in Contact List
    When I visit "CSD Tokyo" summary
    Then "Tom" is enrolled

  @developing
  Scenario: Add 1 exists student
    Given Carry does exists in system
      | email,firstname,lastname,company,country,city         |
      | carry@example.com,Carry,Fisher,CS,Singapore,Singapore |
    When I enroll a student
    Then Enrollment Result page is displayed
    And "Carry" is displayed as "OK"
      | carry@example.com |
    When I visit Contact List page
    Then "Carry" is displayed in Contact List
    When I visit "CSD Tokyo" summary
    Then "Carry" is enrolled

  @developing
  Scenario: Error on enroll a students
    Given Tom does exists in system
      | email,firstname,lastname,company,country,city |
      | tom@.com,Tom,Smith,CS,Singapore,Singapore     |
    When I enroll students
    Then Enrollment Result page is displayed
    And "Tom" is displayed as "NG"
      | tom@example.com |
    When I visit Contact List page
    Then "Tom" is not displayed in Contact List
    When I visit "CSD Tokyo" summary
    Then "Tom" is not enrolled

  @developing
  Scenario: Mixed (success/error) on enroll multiple students
    Given Tom does exists in system
    And   Carry does not exists in system
    And   Takamiya does exists in system
    And   Stefan does not exists in system
      | email,firstname,lastname,company,country,city         |
      | tom@example.com,Tom,Smith,CS,Singapore,Singapore      |
      | carry@example.com,Carry,Fisher,CS,Singapore,Singapore |
      | takamiya@,Keisuke,Takamiya,CS,Singapore,Singapore     |
      | stefan@,Stefan,Meier,CS,Singapore,Singapore           |
    When I enroll students
    Then Enrollment Result page is displayed
    And "Tom" is displayed as "OK"
      | tom@example.com |
    And "Carry" is displayed as "NG"
      | carry@ |
    And "Takamiya" is displayed as "OK"
      | takamiya@example.com |
    And "Stefan" is displayed as "NG"
      | stefan@ |
    When I visit Contact List page
    Then "Tom" is displayed in Contact List
    And "Carry" is not displayed in Contact List
    And "Takamiya" is not displayed in Contact List
    And "Stefan" is not displayed in Contact List
    When I visit "CSD Tokyo" summary
    Then "Tom" is enrolled
    And "Carry" is not enrolled
    And "Takamiya" is not enrolled
    And "Stefan" is not enrolled
