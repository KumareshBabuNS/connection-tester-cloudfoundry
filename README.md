# A connection tester for Cloud Foundry
To validate if a certain external service is reachable from your Cloud Foundry instance, you normally would like to send a kind of `ping` from there. The easiest way to do so is [CF SSH](https://docs.cloudfoundry.org/devguide/deploy-apps/ssh-apps.html).

However, if your Ops admin disabled CF SSH, the Cloud Foundry Connection Tester becomes handy.  
The tester can also be used in other clouds and remote servers, since it is written in Java and reads [an environment variable](https://docs.oracle.com/javase/tutorial/essential/environment/env.html).

## Usage
1. Download the app [here](https://github.com/PeterWippermann/cloudfoundry-connection-tester/releases).
2. Push it to the cloud:  
```cf push connection-test --no-route --no-start -p cloudfoundry-connection-tester.jar -u none```
3. Configure the target URL (without schema):  
```cf set-env connection-test URL "www.google.com:80"```
4. Start the app:  
```cf start connection-test```  
CF will report the app as *crashed* because it shuts itself down after the test.
5. Stop the app:  
```cf stop connection-test```
6. Find the test result in the logs:  
```cf logs connection-test --recent```

## Understanding the test result

Being a good [12-factor-app](https://12factor.net/logs) the Connection Tester prints its logs to Standard Out and Standard Error.  
You can display the most recent logs by using: ```cf logs connection-test --recent```.  
Or you can monitor the log output live during the app's execution in a second terminal: ```cf logs connection-test``.

### Successful connection

> OUT Creating container  
> OUT Successfully created container  
> OUT Starting health monitoring of container  
> OUT Read URL from environment variables: "www.google.de:80"  
> OUT Trying to connect (timeout: 10000 milliseconds)...  
> OUT Connecting to "www.google.de:80" was successful!  
> OUT Exit status 0  

### Blocked connection

> OUT Creating container  
> OUT Successfully created container  
> OUT Starting health monitoring of container  
> OUT Read URL from environment variables: "www.google.de:80"  
> OUT Trying to connect (timeout: 10000 milliseconds)...  
> ERR Connecting to "www.google.de:80" failed!  
> OUT Exit status 0  

## Why does the app crash?

The Connection Tester exits normally after it tested the connection. It doesn't run any other background processes or keeps an HTTP endpoint open, so there is no need to keep running. This is signalled with exit code `0`.
However, Cloud Foundry expects the app to keep on running forever - even if the health check has been disabled on purpose and no route has been set up. This is considered to be [a feature, not a bug](https://discuss.pivotal.io/hc/en-us/articles/230141447-My-app-has-crashed-how-do-I-interpret-the-crash-log-entry-):
> Codependent step exited  
> Your application has exited normally and has returned an exit code of 0.  This often happens when a program finishes it's execution and is complete.  While this may seem like a normal thing, apps that run on PWS should never finish.  A well behaved application should continue listen for HTTP requests or work indefinitely.  If you see this error, check for cases where your application might finish without any errors.
