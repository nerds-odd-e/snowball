*** Settings ***
Library        Selenium2Library
Resource       resource.robot
Suite Teardown  Close All Browsers
Default Tags   contact

*** Test Cases ***
Display Contacts In The Contact List
	Given There is a contact    terry1@gmail.com
    When Open Browser To See Contacts
    Then Contact Should Be There	terry1@gmail.com
    And Company Should Be There	terry1@gmail.com	todo company

# Update Company In The Contact List
# 	Given There is a contact    terry1@gmail.com  
#     And There is a contact    terry2@gmail.com
#     When Open Browser To See Contacts
#    	And Update Contact Company	terry2@gmail.com	Com A
#     Then Company Should Be There	terry2@gmail.com	Com A

*** Keywords ***
Open Browser To See Contacts
    Open Browser  ${LIST_CONTACT_URL}  ${BROWSER}
    Maximize Browser Window

Contact Should Be There	[Arguments]	${email}
	Element Should Contain	xpath=//*[@id="contactTable"]/li[contains(., '${email}')]		${email}
	
Company Should Be There	[Arguments]	${email}	${company}
	Element Should Contain	xpath=//*[@id="contactTable"]/li[contains(., '${email}')]//following-sibling::li[3]		${company}

There is a contact    [Arguments]    ${email}
    Wait Until Keyword Succeeds    10 sec    1    Open Browser To Add Contact     
    Key email field    ${email}
    Click Add Contact
    sleep    1000milliseconds
    Close Browser

Update Contact Company	[Arguments]	${email}	${company}
	Click Button	xpath=//*[@id="contactTable"]/li[contains(., '${email}')]//following-sibling::li[4]/input
	sleep    1000milliseconds
	Input textfield	company    ${company}
	Click Button	save_button