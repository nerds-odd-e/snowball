# Snowball

Snowball is the platform supporting (and also developed by) the
Odd-e Certified Scrum Developer training classes.

## Project Structure

* Front-end framework: no front-end framework so far. Plain JavaScript, tested using nodejs.
* Backend framework: Simple Servlet runs on Jetty (via Gradle plugin 'gretty')
* Database: MongoDB

##### Setup MongoDB

If MongoDB is not installed, install it with homebrew (MacOS)
```
brew install mongodb
```
And start it with
```
brew service mongodb start
```

Database names:

* test: snowball_test
* development: snowball
* production: snowball


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

##### Pre-prerequisite for sending email
* The environment variables `MM_EMAIL_USERID` and `MM_EMAIL_PASSWORD` are required to send emails out.
* On Mac OS X, add the following to your environment variables (~/.bash_profile).
```
export MM_EMAIL_USERID=<email>
export MM_EMAIL_PASSWORD=<pwd>
launchctl setenv MM_EMAIL_USERID $MM_EMAIL_USERID
launchctl setenv MM_EMAIL_PASSWORD $MM_EMAIL_PASSWORD
```
* Reboot your IDE.

#### Mac Setup

On MacOS, after the Sierra update a function call to "InetAddress.getLocalHost" becomes very slow (5000ms).
Adding the following to the /etc/hosts will solve the problem:

```
127.0.0.1   localhost mbpro.local
::1         localhost mbpro.local
```

