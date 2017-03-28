Feature: Send all events to contacts with the same location as the event - Singapore
  As the admin I want to send all the registered events in a single email to the contacts in the same location
  so that I can gather as many people as possible in the event

  Background:
    Given visit event list page

  @email
  Scenario Outline: Send all events to all contacts
    When <number of contacts in location> out of <number of contacts> contacts are in Singapore
     And <number of events in location> out of <number of events> events are in Singapore
     And I click send button
     Then <number of email recipients> contact(s) receive an email that contains <number of events in an email>


  Examples: All combinations
    |number of events| number of events in location| number of contacts | number of contacts in location | number of events in an email | number of email recipients |
    |               1|                           1 |                  1 |                               1|                            1 |                           1|
    |               1|                           0 |                  1 |                               1|                          N/A |                         N/A|
    |               1|                           1 |                  1 |                               0|                          N/A |                         N/A|
    |               1|                           1 |                  1 |                               1|                            1 |                           1|
    |               2|                           1 |                  1 |                               1|                            1 |                           1|
    |               2|                           2 |                  1 |                               1|                            2 |                           1|
    |               2|                           1 |                  2 |                               1|                            1 |                           1|
    |               2|                           1 |                  2 |                               2|                            1 |                           2|
