*** Settings ***
Library        Selenium2Library
Resource       resource.robot
Test Teardown  Close Browser
Default Tags   contact

*** Test Cases ***
Select A Contact To Recipient
	Given There is a contact    terry@gmail.com
	When Select a contact    terry@gmail.com
	Then Selected contact appear in the recipient form    terry@gmail.com;

Select Two Contact To Recipient
    Given There is a contact    terry1@gmail.com  
    And There is a contact    terry2@gmail.com
    When Select two contact    terry1@gmail.com    terry2@gmail.com  
    Then Selected contact appear in the recipient form    terry1@gmail.com;terry2@gmail.com;
    #And Selected contact appear in the recipient form    terry2@gmail.com      

*** Keywords ***
Open Browser To Select Contact
    Open Browser    http://localhost:8080/sendemail.jsp    ${BROWSER}

There is a contact    [Arguments]    ${email}
    Open Browser To Add Contact
    Key email field    ${email}
    Click Add Contact
    Close Browser

Select a contact    [Arguments]    ${email}
    Open Browser To Select Contact
    Click Element    select_contact
    sleep    1000milliseconds
    Select Checkbox    xpath=//*[@id="selectContactTable"]/li[contains(., '${email}')]//preceding-sibling::li[1]/input
    Click Button	add_contact_button

Select two contact    [Arguments]    ${email1}    ${email2}
    Open Browser To Select Contact
    Click Element    select_contact
    sleep    1000milliseconds 
    Select Checkbox    xpath=//*[@id="selectContactTable"]/li[contains(., '${email1}')]//preceding-sibling::li[1]/input
    sleep    1000milliseconds
    Select Checkbox    xpath=//*[@id="selectContactTable"]/li[contains(., '${email2}')]//preceding-sibling::li[1]/input
    Click Button	add_contact_button

Selected contact appear in the recipient form    [Arguments]    ${email}
    Textfield Value Should Be    recipient    ${email}
