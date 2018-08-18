Feature: Create Course
  As an admin I want to create courses

  Scenario: Creating course successfully
    Given There is a course with below details
      |coursename    | CSD-1         |
      |duration      | 30            |
      |country       | China         |
      |city          | Chengdu       |
      |startdate     | 2017-10-23    |
      |address       | odd-e         |
      |coursedetails | CSD training  |
      |instructor    | Terry         |
    Then Course should save and successfully saved message should appear

  @now
  Scenario Outline: Creating course with unknown location
    When Add a course with location "<city>", "<country>"
    Then Course should <expected result>

    Examples:
      | city     | country   | expected result                                   |
      #---------------------------------------------------------------------------
      | Foobar$$$   | China     | not save and show error messagea                  |
      | Chengdu  | China     | save and successfully saved message should appear |



