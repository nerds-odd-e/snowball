Feature: Student Score
  User should be able to view their score

  @now @developing
  Scenario Outline: Students can view their scores
    Given There are <questions> questions and <points> points
    When Test is finished
    Then Display the message "<message>"
    Then The percent is "<percent>"
    Then The fraction is "(<points>/<questions>)"

    Examples:
      | questions | points | message                     | percent |
      | 10        | 5      | What were you thinking?     | 50%     |
      | 10        | 6      | Study harder please!        | 60%     |
      | 10        | 9      | Good job. Now read more!    | 90%     |
      | 10        | 10     | Great job. Now go practice! | 100%    |
      | 7         | 4      | What were you thinking?     | 57%     |

