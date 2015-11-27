*** Settings ***
Documentation  A test suite for sending valid emails.
Resource       resource.robot
Default Tags   send_mail

*** Test Cases ***
Send Email one recepient success.
    Given Create default email with recipients   terry@odd-e.com
    When Send Email
    Then Should sent email  1
    
Send Email multiple recepient success.
    Given Create default email with recipients   terry@odd-e.com;roof@odd-e.com
    When Send Email
    Then Should sent email  2    

Send Email from company name.
	[tags]	  work_in_progress
	Given Create default email with recipients	company:ComA
	When Send Email
	Then Should sent email 2
