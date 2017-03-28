##### Pre-prerequisite
* The environment variables `MM_EMAIL_USERID` and `MM_EMAIL_PASSWORD` are required to send emails out.
* On Mac OS X, add the following to your environment variables (~/.bash_profile).
```
export MM_EMAIL_USERID=<your gmail address>
export MM_EMAIL_PASSWORD=<your gmail password>
launchctl setenv MM_EMAIL_USERID $MM_EMAIL_USERID
launchctl setenv MM_EMAIL_PASSWORD $MM_EMAIL_PASSWORD
```
* Reboot your IDE.

##### Phantomjs

We use phantomjs for cucumber tests. Make sure it is installed and if it is in a path other than /usr/local/bin then please edit build.gradle file and update the path to phantomjs in the tasks.cucumber section.

* Make sure firefox binary is in the path export PATH=$PATH:/Applications/Firefox.app/Contents/MacOS˜

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
