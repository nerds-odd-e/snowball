Feature: Send all events to contacts with the same location as the event - Singapore
  As the admin I want to send all the registered events in a single email to the contacts in the same location
  so that I can gather as many people as possible in the event

  Background:
    Given visit event list page

  @email @developing
  Scenario: Have events but have no events at same location
    When We have below number of contacts at each location:
      |location | number of contacts|
      |Singapore|                  0|
    And We have below number of events at each location:
      |location | number of events  |
      |Singapore|                  3|
    And I click send button
    Then It should send out emails:
      |location | number of emails  | number of events in the email  |

  @email @developing
  Scenario: Have contacts but no event at same location
    When We have below number of contacts at each location:
      |location | number of contacts|
      |Singapore|                  2|
    And We have below number of events at each location:
      |location | number of events  |
      |Singapore|                  0|
    And I click send button
    Then It should send out emails:
      |location | number of emails  | number of events in the email  |

  @email @developing
  Scenario: Have both event and contacts at single location
    When We have below number of contacts at each location:
    |location | number of contacts|
    |Singapore|                  2|
    And We have below number of events at each location:
    |location | number of events  |
    |Singapore|                  3|
    And I click send button
    Then It should send out emails:
    |location | number of emails  | number of events in the email  |
    |Singapore|                  2|                               6|

  @email @developing
  Scenario: Contacts from multiple location, Events at single location
    When We have below number of contacts at each location:
      |location | number of contacts|
      |Singapore|                  2|
      |Japan    |                  5|
    And We have below number of events at each location:
      |location | number of events  |
      |Singapore|                  3|
    And I click send button
    Then It should send out emails:
      |location | number of emails  | number of events in the email  |
      |Combined |                  2|                              6|

  @email @developing
  Scenario: Contacts from single location, events at multiple location
    When We have below number of contacts at each location:
      |location | number of contacts|
      |Singapore|                  2|
    And We have below number of events at each location:
      |location | number of events  |
      |Singapore|                  3|
      |Japan    |                  7|
      |Thailand |                  1|
    And I click send button
    Then It should send out emails:
      |location | number of emails  | number of events in the email  |
      |Combined |                  2|                              6|

  @email @developing
  Scenario: Have both event and contacts at multiple location
    When We have below number of contacts at each location:
      |location | number of contacts|
      |Singapore|                  2|
      |Japan    |                  5|
    And We have below number of events at each location:
      |location | number of events  |
      |Singapore|                  3|
      |Japan    |                  7|
      |Thailand |                  1|
    And I click send button
    Then It should send out emails:
      |location | number of emails  | number of events in the email  |
      |Combined |                  7|                              41|
