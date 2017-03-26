Feature: Send all events to all contacts
  As the admin I want to send all the registered events in a single email to all contacts
  so that I can gather as many people as possible in the event

  Background:
    Given visit event list page

  @email
  @debug
  Scenario Outline: Send all events to all contacts
    When number of contact is "<number of contacts>"
     And number of event is "<number of events>"
     And I click send button
    Then <number of email recipients> contact(s) receive an email that contains <number of events in an email>

  Examples: When no events
    | number of events | number of contacts | number of events in an email | number of email recipients |
    |                0 |                  0 |                          N/A |                        N/A |
    |                0 |                  1 |                          N/A |                        N/A |
    |                0 |                  2 |                          N/A |                        N/A |

  Examples: When no contacts
    | number of events | number of contacts | number of events in an email | number of email recipients |
    |                1 |                  0 |                          N/A |                        N/A |
    |                2 |                  0 |                          N/A |                        N/A |

  Examples: Normal case
    | number of events | number of contacts | number of events in an email | number of email recipients |
    |                1 |                  1 |                            1 |                          1 |
    |                1 |                  2 |                            1 |                          2 |
    |                2 |                  1 |                            2 |                          1 |
    |                2 |                  2 |                            2 |                          2 |
