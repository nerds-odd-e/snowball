Feature: Send all events to contacts with the same location as the event - Singapore
  As the admin I want to send all the registered events in a single email to the contacts in the same location
  so that I can gather as many people as possible in the event

  Background:
    Given visit event list page

  @email
  Scenario: Have events but have no events at same location
    When We have below number of contacts at each location:
      | location            | number of contacts |
      | Singapore/Singapore | 0                  |
    And We have below number of events at each location:
      |country   |city     | number of events  |
      |Singapore |Singapore|                  3|
    And I click send button
    Then It should not send out emails

  @email
  Scenario: Have contacts but no event at same location
    When We have below number of contacts at each location:
      | location            | number of contacts |
      | Singapore/Singapore | 2                  |
    And We have below number of events at each location:
      |country   |city     | number of events  |
      |Singapore |Singapore|                  0|
    And I click send button
    Then It should not send out emails

  @email @now
  Scenario: Have both event and contacts at single location
    When We have below number of contacts at each location:
      | location            | number of contacts |
      | Singapore/Singapore | 2                  |
    And We have below number of events at each location:
    |country  |city     | number of events  |
    |Singapore|Singapore|                  3|
    And I click send button
    Then It should send out emails:
    |location           | number of emails  | number of events in the email  |
    |Singapore/Singapore|                  2|                               6|

  @email
  Scenario: Contacts from multiple location not within radius, Events at single location
    When We have below number of contacts at each location:
      | location            | number of contacts |
      | Singapore/Singapore | 2                  |
      | Japan/Tokyo         | 5                  |
    And We have below number of events at each location:
      |country  |city     | number of events  |
      |Singapore|Singapore|                  3|
    And I click send button
    Then It should send out emails:
      | location | number of emails | number of events in the email |
      | Combined | 2                | 6                             |

  @email
  Scenario: Contacts from single location, events at multiple location with some within radius
    When We have below number of contacts at each location:
      | location            | number of contacts |
      | Singapore/Singapore | 2                  |
    And We have below number of events at each location:
      |country  |city     | number of events  |
      |Singapore|Singapore|                  3|
      |Japan    |Tokyo    |                  7|
      |Thailand |Bangkok  |                  1|
    And I click send button
    Then It should send out emails:
      | location | number of emails | number of events in the email |
      | Combined | 2                | 8                             |

  @email
  Scenario: Have both event and contacts at multiple location
    When We have below number of contacts at each location:
      | location            | number of contacts |
      | Singapore/Singapore | 2                  |
      | Japan/Tokyo         | 5                  |
    And We have below number of events at each location:
      |country  |city     | number of events  |
      |Singapore|Singapore|                  3|
      |Japan    |Tokyo    |                  7|
      |Thailand |Bangkok  |                  1|
    And I click send button
    Then It should send out emails:
      | location | number of emails | number of events in the email |
      | Combined | 7                | 43                            |

  @email
  Scenario: Contacts from multiple locations, events at single location with some within radius
    When We have below number of contacts at each location:
      | location            | number of contacts |
      | Singapore/Singapore | 2                  |
      | Japan/Tokyo         | 5                  |
      | Thailand/Bangkok    | 3                  |
    And We have below number of events at each location:
      |country  |city     | number of events  |
      |Singapore|Singapore|                  3|
    And I click send button
    Then It should send out emails:
      | location | number of emails | number of events in the email |
      | Combined | 5                | 15                            |

  @email
  Scenario: Contacts from multiple locations, events at single location not within radius
    When We have below number of contacts at each location:
      | location            | number of contacts |
      | Singapore/Singapore | 2                  |
      | Thailand/Bangkok    | 3                  |
    And We have below number of events at each location:
      |country|city     | number of events  |
      |Japan  |Tokyo    |                  3|
    And I click send button
    Then It should not send out emails

  @email
  Scenario: Contacts with country and city will be notified about events in near location
    When We have below number of contacts at each location:
      | location    | number of contacts |
      | Japan/Osaka | 2                  |
    And We have below number of events at each location:
      | country | city  | number of events |
      | Japan   | Tokyo | 3                |
    And I click send button
    Then It should send out emails:
      | location | number of emails | number of events in the email |
      | Combined | 2                | 6                             |

  @developing
  Scenario Outline: send email event
    Given There is a contact "ivan@odd-e.com" at Japan/Tokyo
    And there are "<number>" courses at "<location>"
    And I trigger the sending once
    And Page Should Contain "1 emails contain 2 events sent."
    When I trigger the sending once
    Then Page Should Contain "0 emails contain 2 events sent."

    Examples:
      | location                | number |
      | Japan/Tokyo             | 2      |
      | Japan/Tokyo,Japan/Osaka | 1      |

  Scenario: add new contact
    Given There is a contact "ivan@odd-e.com" at Japan/Tokyo
    And there are "2" courses at "Japan/Tokyo"
    And I trigger the sending once
    And Page Should Contain "1 emails contain 2 events sent."
    And add contact "terry@odd-e.com" at Japan/Tokyo
    When I trigger the sending once
    Then Page Should Contain "1 emails contain 4 events sent."

  Scenario: Report Page Includes
    Given There is a contact "abc@odd-e.com" at Japan/Tokyo
    And there are "1" courses at "Japan/Tokyo"
    And I trigger the sending once
    When Report page Includes
      | email         | Location       | SendDate | courseCount |
      | abc@odd-e.com | Japan/Tokyo    | *        | 1           |

  @developing
  Scenario: send email only one event
    Given There is a contact "ivan@odd-e.com" at Japan/Tokyo
    And there are "1" courses at "Japan/Tokyo"
    And I trigger the sending once
    And there are "1" courses at "Japan/Osaka"
    When I trigger the sending once
    Then Page Should Contain "1 emails contain 2 events sent."

  @developing
  Scenario: send mail to same contact again when there is new course
    Given We have below number of contacts at each location:
      | location            | number of contacts |
      | Singapore/Singapore | 1                  |
    And We have below number of events at each location:
      | location  | number of events |
      | Singapore | 1                |
    And I click send button
    And We have below number of events at each location:
      | location  | number of events |
      | Singapore | 1                |
    And I click send button
    Then It should send out emails:
      | location | number of emails | number of events in the email |
      | Combined | 1                | 2                             |
