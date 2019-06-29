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

* test: snowball_test (for cucumber tests) snowball_unittest (for junit tests)
* development: snowball_development
* production: snowball_production

###### DB migration

We have a very simple DB migration system. It will run and re-run before application start up or before each test case.

The migration scripts are at: `src/main/resource/db.migration/`

The naming convesion is "4digits_the_rest_of_the_name.sql".

The first four digits are the id of the migration, which will be persisited into the database. The db migration will ignore any migration that is older than the largest id ran last time.

##### Run development app

The current Gradle version is 5.5.

Go to the root directory of this project and:

`./gradlew appStart

##### Run Junit tests

`gradle test`

##### Run accpetance tests

`gradle cucumber`

##### Run Javascript tests

```
npm install
npm test
```

#### IntelliJ Integration

This project use ActiveJDBC, which need an extra process named "Instrument" before compiling. To better integrate
with intelliJ, we need to let IntelliJ use gradle for compiling.

IntelliJ -> Preferences... -> Build, Execution, Deployment -> Build Tools -> Gradle -> Running

Check the "Delegate build/run to gradle"

#### Mac Setup

On MacOS, after the Sierra update a function call to "InetAddress.getLocalHost" becomes very slow (5000ms).
Adding the following to the /etc/hosts will solve the problem:

```
127.0.0.1   localhost mbpro.local
::1         localhost mbpro.local
```

#### MySQLn Server Setup

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

