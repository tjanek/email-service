# Email service
[![Build Status](https://travis-ci.org/tjanek/email-service.svg?branch=master)](https://travis-ci.org/tjanek/email-service)

## Requirements
You need to have **Java 1.8** installed.

## Installation instructions
To start your Email service application:

1. Setup MailGun API Endpoint
```
export MAILGUN_URL=https://api.mailgun.net/v3/YOUR_DOMAIN_NAME/messages
```

2. Setup MailGun API Key
```
export MAILGUN_API-KEY=YOUR_API_KEY
```

3. Run Application
```
./gradlew bootRun
```
