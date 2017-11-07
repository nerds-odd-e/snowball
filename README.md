##### Pre-prerequisite
* The environment variables `MM_EMAIL_USERID` and `MM_EMAIL_PASSWORD` are required to send emails out.
* On Mac OS X, add the following to your environment variables (~/.bash_profile).
```
export MM_EMAIL_USERID=<email>
export MM_EMAIL_PASSWORD=<pwd>
launchctl setenv MM_EMAIL_USERID $MM_EMAIL_USERID
launchctl setenv MM_EMAIL_PASSWORD $MM_EMAIL_PASSWORD
```
* Reboot your IDE.

##### Run Production

`gradle appStart -DACTIVE_ENV=production`

##### Run Junit tests

`gradle test`

##### Run accpetance tests

`gradle cucumber`

##### Run Javascript tests

```
npm install
npm test
```
* Make sure firefox binary is in the path export PATH=$PATH:/Applications/Firefox.app/Contents/MacOS˜

#### IntelliJ Integration

http://javalite.io/ibntellij_idea_integratio