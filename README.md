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

##### Setup MySQL database

This project use MySQL database. The test and development environment need a MySQL service on the localhost with root
use with no password.

Database names:
    
* test: massive_mailer_test (for cucumber tests) massive_mailer_unittest (for junit tests)
* development: massive_mailer_development
* production: massive_mailer_production

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

http://javalite.io/intellij_idea_integration

In IntelliJ's Project Structure, change the output path of the massive_mailer_test module to:
<project_dir>/build/classes/java/main

#### Mac Setup

On MacOS, after the Sierra update a function call to "InetAddress.getLocalHost" becomes very slow (5000ms).
Adding the following to the /etc/hosts will solve the problem:

```
127.0.0.1   localhost mbpro.local
::1         localhost mbpro.local
```

#### MySQL Setup

In order to run the `LOAD DATA INFILE` command you first need to add a configuration file as follows:

1. Stop the MySQL server

`mysql.server stop`

2. Create a file `/etc/my.cnf` with the following contents:

```
[mysqld]
secure-file-priv=""
```

3. Start the MySQL server

`mysql.server start`
