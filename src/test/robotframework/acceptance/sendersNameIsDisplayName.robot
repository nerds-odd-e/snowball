*** Settings ***
Library		   Selenium2Library
Library        DateTime
Library        get_senders.py
Resource       resource.robot
resource 	   senderNameCheckResource.robot
Test Setup     Test Setup
Default Tags   send_mail

*** Testcases ***
Sender Name In Send Box Should Contain Display Name
	[tags]	  work_in_progress
	Given Send An Email
    Then Sender Name Should be    Inspector Gadget <myodde@gmail.com>
