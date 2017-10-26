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
    |Singapore-Singapore|                  2|                               6|

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

  @ys
  Scenario Outline: send email only one event
    Given There is a contact "ivan@odd-e.com" at Japan/Tokyo
    And there are two courses at Japan/Tokyo
    When I trigger the sending twice
    Then Page Should Contain "0 emails contain 2 events sent."

  @developing
  Scenario Outline: add new contact
    Given There is a contact "<email1>" at Japan/Tokyo
    And there are two courses at Japan/Tokyo
    And I trigger the sending once
    And add contact "<email2>" at Japan/Tokyo
    When I trigger the sending once
    Then "<email2>" should receive email
    But "<email1>" shouldn't receive email

    Examples:
      | email1         | email2          |
      | ivan@odd-e.com | terry@odd-e.com |

  @developing
  Scenario Outline: Even though there are multiple courses, one user receive only 1 email
    Given there is a contact "<email>" at Japan/Tokyo
    And there are two courses at Japan/Tokyo, Souel
    When I trigger the sending twice
    Then contact "<email>" should receive only 1 email


  @developing
  Scenario: Report Page Includes
    Given There is a contact "abc@odd-e.com" at Japan/Tokyo
    And there is a course at Japan/Tokyo
    And I trigger the sending once
    When Report page Includes
      | email         | course | SendDate |
      | abc@odd-e.com | 1      | *        |

  Scenario Outline: send email only one event
    Given There is a contact "ivan@odd-e.com" at Japan/Tokyo
    And there are one courses at "Japan/Tokyo"
    And I trigger the sending once
    And there are one courses at "Japan/Osaka"
    When I trigger the sending once
    Then Page Should Contain "1 emails contain 2 events sent."

