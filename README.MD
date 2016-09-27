Pre-prerequisite
* The environment variables `MM_EMAIL_USERID` and `MM_EMAIL_PASSWORD` are required to send emails out. on Mac OS X, add the following to your environment variables (~/.bash_profile).
```
export MM_EMAIL_USERID=<your gmail address>
export MM_EMAIL_PASSWORD=<your gmail password>
launchctl setenv MM_EMAIL_USERID $MM_EMAIL_USERID
launchctl setenv MM_EMAIL_PASSWORD $MM_EMAIL_PASSWORD
```

* Make sure firefox binary is in the path export PATH=$PATH:/Applications/Firefox.app/Contents/MacOS