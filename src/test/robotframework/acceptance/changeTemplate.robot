*** Settings ***
Resource          resource.robot
Suite Setup       Cleanup Template
Test Setup        Open Browser To Send Email
Test Teardown     Close Browser
Default Tags      template

*** Test Cases ***
Subject And Content Changed When Template Is Selected
	Given There is a template
	When Select a template    1
	Then Textbox is replaced with the template    subject    Greeting {FirstName}

*** Keywords ***
There is a template
    insert_default_template

Cleanup Template
    clear_template

Select a template    [Arguments]    ${textboxID}
    Select From List  id=templateList    ${textboxID}


Textbox is replaced with the template   [Arguments]    ${textboxID}    ${expectedValue}
   ${value}=  Get Value  ${textboxID}
   Should Be Equal    ${value}    ${expectedValue}