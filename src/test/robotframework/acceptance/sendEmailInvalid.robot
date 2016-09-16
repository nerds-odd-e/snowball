*** Settings ***
Resource       resource.robot
Test Setup     Open Browser To Send Email
Test Teardown  Close Browser
Default Tags   send_mail

*** Variables ***
${recipient}     name1@gmail.com; name2@gmail.com   
${subject}       subject for test send email
${content}       content for test send email
${invalid_format_recipient}     name1gmail.com
${Blankformat1}	company:
${Blankformat2}	company:"
${Blankformat3}	company:""
${Companyformat1}	company:"AAA"
${Companyformat2}	company:"AAA
${Companyformat3}	company:AAA
${Companyformat4}	company:A"AA
${Companyformat5}	company:AAA"
${Companyformat6}	company:AAA"A"
${Companyformat7}	company:"AA"BB
${Companyformat8}	company:"AA"BB"


***Keywords***
Check Send Button
	[Arguments]  ${recipient}   ${subject}   ${content}    ${expected_status}
    Input textfield    	recipient 	${recipient}
    Input textfield    	subject   	${subject}
    Input textfield    	content  	${content}
    Run Keyword If		'${expected_status}'=='Enabled' 	Can click  		send_button
    Run Keyword If    	'${expected_status}'=='Disabled' 	Can not click 	send_button
	
*** Test Cases ***
Verify value and check button
	[Template]   Check Send Button
	#*recipient*   *subject*    *content*   *expectation*
	#----------------------------------------------------------
	${EMPTY}      ${EMPTY}     ${EMPTY}      Disabled
	${recipient}  ${EMPTY}     ${EMPTY}      Disabled
	${EMPTY}      ${subject}   ${EMPTY}      Disabled
	${EMPTY}      ${EMPTY}     ${content}    Disabled
	${recipient}  ${subject}   ${content}    Enabled
	${invalid_format_recipient}    ${subject}    ${content}	 Disabled
	${Blankformat1}    ${subject}    ${content}	 Disabled
	${Blankformat2}    ${subject}    ${content}	 Disabled
	${Blankformat3}    ${subject}    ${content}	 Disabled
	${Companyformat1}    ${subject}    ${content}	 Enabled
	${Companyformat2}    ${subject}    ${content}	 Enabled
	${Companyformat3}    ${subject}    ${content}	 Enabled
	${Companyformat4}    ${subject}    ${content}	 Enabled
	${Companyformat5}    ${subject}    ${content}	 Disabled
	${Companyformat6}    ${subject}    ${content}	 Disabled
	${Companyformat7}    ${subject}    ${content}	 Enabled
	${Companyformat8}    ${subject}    ${content}	 Disabled


