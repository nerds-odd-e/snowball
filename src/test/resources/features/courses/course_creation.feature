Feature: Create Course
  As an admin I want to create courses

  Scenario: Creating course successfully
    Given There is a course with below details
      |courseName    | CSD-1         |
      |duration      | 30            |
      |country       | China         |
      |city          | Chengdu       |
      |startDate     | 2017-10-23    |
      |address       | odd-e         |
      |courseDetails | CSD training  |
      |instructor    | Terry         |
    Then Course should save and successfully saved message should appear

  Scenario Outline: Creating course with unknown location
    When Add a course with location "<city>", "<country>"
    Then Course should <expected result>

    Examples:
      | city        | country      | expected result                                |
      #---------------------------------------------------------------------------
      | NotExist    | Japan     | not save and show error messagea                  |
      | Chengdu     | China     | save and successfully saved message should appear |



