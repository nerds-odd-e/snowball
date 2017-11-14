Feature: Send all events to contacts with the same location as the event - Singapore
  As the admin I want to send all the registered events in a single email to the contacts in the same location
  so that I can gather as many people as possible in the event

  Scenario Outline: upcoming courses notification
    Given there are <sg courses/contacts> courses and contacts in Singapore, Singapore
    Given there are <tokyo> courses and contacts in Tokyo, Japan
    Given there are <bangkok> courses and contacts in Bangkok, Thailand
    Given there are <osaka> courses and contacts in Osaka, Japan
    When I send the upcoming courses emails
    Then It should send <expected emails> emails
    And there should be in total <expected courses> courses in all the emails

    Examples:
    | sg courses/contacts | bangkok | osaka | tokyo | expected emails | expected courses |
    | 0/1                 | 0/0     | 0/0   | 0/0   | 0               | 0                |
    | 1/0                 | 0/0     | 0/0   | 0/0   | 0               | 0                |
    | 2/3                 | 0/0     | 0/0   | 0/0   | 3               | 6                |
    | 3/2                 | 0/0     | 0/0   | 5/0   | 2               | 6                |
    | 3/2                 | 1/0     | 0/0   | 7/0   | 2               | 8                |
    | 3/2                 | 1/0     | 0/0   | 7/5   | 7               | 43               |
    | 3/2                 | 0/3     | 0/0   | 0/5   | 5               | 15               |
    | 0/2                 | 0/3     | 0/0   | 3/0   | 0               | 0                |

    @now
  Scenario: Contacts with country and city will be notified about events in near location
    When We create 2 contacts at Osaka, Japan
    And We create 3 courses at Tokyo, Japan
    And I send the upcoming courses emails
    Then It should send 2 emails
    And there should be in total 6 courses in all the emails

  @location
  Scenario: Contacts with country and city will be notified about events in near location
    When We create 2 contacts at Tokyo, Japan
    And We create 3 courses at Osaka, Japan
    And I send the upcoming courses emails
    Then It should send 2 emails
    And there should be in total 6 courses in all the emails