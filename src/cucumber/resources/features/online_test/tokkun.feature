Feature:
  User can take an online tokkun test :)

  @developing
  Scenario: Displaying the question for tokkun
    Given there 1 question in the system
    When "Ken San" Ha tokkunを受講しmasu in different days
    Then He should see the follow numbers of question:
      | day 1 |   0  |
      | day 2 |   0  |
      | day 3 |   0  |
      | day 4 |   0  |
      | day 5 |   1  |
