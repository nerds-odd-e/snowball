*** Variables ***
${SUBJECT_CURRENT_DATE}    	Actual value set dynamically at Test setup by date value
${SENDER_EMAIL}    			myodde@gmail.com
${SENDER_PASSWORD}    		1234qwer@
${RECIPIEN_EMAIL}    		steve.zuckerburk@gmail.com


*** Keywords ***
Test Setup
	${SUBJECT_CURRENT_DATE} =   Get Current Date    result_format=%d%m%Y
	Set Test Variable  ${SUBJECT_CURRENT_DATE}

Send An Email
	Open Browser To Send Email
	Key all email field    ${RECIPIEN_EMAIL}    ${SUBJECT_CURRENT_DATE}    content
	Click Button    send_button
	Close Browser

Sender Name Should be    [Arguments]    ${expected sender}
    Wait Until Keyword Succeeds    1 min    3 sec     Check Sender Name in OutBox    ${expected sender}

Check Sender Name in OutBox    [Arguments]    ${expected sender}
    ${sender} =    Get Sender Name    ${SENDER_EMAIL}    ${SENDER_PASSWORD}    ${SUBJECT_CURRENT_DATE}
    Should Be Equal As Strings    ${sender}    ${expected sender}