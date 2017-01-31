# A connection tester for Cloud Foundry
To validate if a certain external service is reachable from your Cloud Foundry instance, you would naturally like to send a kind of `ping` from there. The easiest way to do so is [CF SSH](https://docs.cloudfoundry.org/devguide/deploy-apps/ssh-apps.html).

However, if you Ops admin disabled CF SSH, the Cloud Foundry Connection Tester becomes handy.

## Usage
1. Download the app here.
2. Push it to the cloud: ```cf push connection-test --no-route --no-start -p cloudfoundry-connection-tester.jar```
3. Configure the target URL (without schema): ```cf set-env connection-test URL "www.google.com:80"```
4. Start the app: ```cf start connection-test```
5. Find the test result in the logs.
6. Stop the app: ```cf stop connection-test```

