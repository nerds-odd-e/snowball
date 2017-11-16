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
    Then It should send <expected emails> emails

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

  Scenario: Contacts with country and city will be notified about events in near location
    When We create 2 contacts at Tokyo, Japan
    And We create 3 courses at Osaka, Japan
    And I send the upcoming courses emails
    Then It should send 2 emails
    And there should be in total 6 courses in all the emails

    @developing
  Scenario Outline: Upcoming course email resending
    Given there is a contact "contact@gmail.com" at Singapore, Singapore created <days from last email> before
    And there is a upcoming course at Singapore, Singapore
    And I have sent the upcoming courses emails
#    And the email counting is reset to 0

    When a new contact "new_contact@gmail.com" is <added?> at Singapore, Singapore
    And I send the upcoming courses emails again now

    Then in total, there should be <new emails received by contact>

    Examples:
    | days from last email | added?    | new emails received by contact |
    | 29 days              | not added | 0 new emails                   |
    | 31 days              | not added | 1 new emails                   |
    |  0 days              | not added | 0 new emails                   |
    | 29 days              | added     | 1 new emails                   |

