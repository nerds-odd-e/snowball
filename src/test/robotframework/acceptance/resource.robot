*** Settings ***

Documentation  A resource file containing the application specific keywords
...            that create our own domain specific language. This resource
...            implements keywords for testing HTML version of the test
...            application.
Library        Selenium2Library 
Library        com.odde.massivemailer.testlibrary.SqliteLibrary
Library        CustomKeyword.py

*** Variables ***
${SERVER}        localhost:8080
${BROWSER}       chrome
${DELAY}         0.5
${BASE_URL}          http://${SERVER}
${SENDMAIL URL}     http://${SERVER}
${CONTACT LIST URL}    http://${SERVER}/add_contact.jsp

${LIST_CONTACT_URL}     ${BASE_URL}/contactlist.jsp

${subject}       subject for test send email
${content}       content for test send email

${DBAPI}          sqlite3
${DBNAME}         jdbc:sqlite:oddemail.db


*** Keywords ***
Key email field  [Arguments]  ${email}
    Input textfield    email    ${email}
    
Key all email field  [Arguments]  ${recipient}  ${subject}  ${content}
    Input textfield    recipient    ${recipient}
    Input textfield    subject      ${subject}
    Input textfield    content      ${content}

Open Browser To Send Email
    Open Browser  ${SENDMAIL URL}  ${BROWSER}
    Maximize Browser Window
    Set Selenium Speed  ${DELAY}

# To be deprecated
Open Browser To Add Contact
    Open Browser  ${CONTACT LIST URL}  ${BROWSER}
    Maximize Browser Window

Input textfield  [Arguments]  ${input_id}    ${input_value}
    Input Text  ${input_id}  ${input_value}

Send Email
    Click Button    send_button

Click Add Contact
    Click Button    add_button   

Can click  [Arguments]  ${send_button}
     Element Should Be Enabled  ${send_button}
     
Can not click  [Arguments]  ${send_button}
     Element Should Be Disabled  ${send_button}     
     
Create default email with recipients  [Arguments]  ${recipient}  
    Open Browser To Send Email
    Input textfield    recipient    ${recipient}
    Input textfield    subject      ${subject}
    Input textfield    content      ${content}

Should sent email  [Arguments]    ${msg_cnt}
    ${value}=  Get Value  msg_sent_cnt
    Should Be Equal    ${value}   ${msg_cnt}
    Close Browser

I should get an alert dialog with message  [Arguments]    ${alertMessage}
     Wait Until Keyword Succeeds    10 sec    1    Alert Should Be Present    ${alertMessage}          
    
Add A Contact          [Arguments]    ${email}
    Go To              ${CONTACT LIST URL}
    Key email field    ${email}
    Click Add Contact

Delete All Contacts
	Clear All Contacts
