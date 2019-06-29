Feature: Question Category
  A question belongs to a category. Categories are maintained by admin.

  Scenario Outline: Changing the advice for a category
    Given there is a question category "<category>"
    Given I'm on the Update Advice page
    When I select category "<category>"
    And I set the advice as "<advice>"
    And I set the advice link as "<link>"
    And I click the update button
    Then I should see the advice for "<category>" has become "<advice>"
    And the advice link for "<category>" has become "<link>"
    Examples:
      | category | advice                 | link                |
      | Scrum    | You should study scrum | http://google.co.jp |
      | Tech     | You should study tech  | http://facebook.com |

  Scenario: Add new category
    Given I'm on the admin dashboard
    And click the add category button
    When add a new category
    Then it should be redirected to the add question page
    And I can  select the new category

