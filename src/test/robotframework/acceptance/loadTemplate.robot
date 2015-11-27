*** Settings ***
Resource          resource.robot
Test Teardown    Close Browser
Default Tags      template

*** Test Cases ***
Verify Blank Template Is Selected By Default On First Load
	Given Open Browser To Send Email
	Then Template Should Be Selected    0
	And Textbox is Empty    subject
	And Textbox is Empty    content

Verify Default Template Exists In Dropdown
    Given Open Browser To Send Email
    Then Default Template Exists In Dropdown

*** Keywords ***
Template Should Be Selected    [Arguments]    ${selectedTemplate}
    ${value}=  Get Value  templateList
    Should Be Equal    ${value}   ${selectedTemplate}

Textbox is Empty    [Arguments]    ${textboxID}
    ${value}=  Get Value  ${textboxID}
    Should Be Equal    ${value}    ${EMPTY}

Default Template Exists In Dropdown
    ${value}=  Get Value  template_1
    Should Be Equal    ${value}    1