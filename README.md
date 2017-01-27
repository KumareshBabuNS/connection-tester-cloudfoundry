# cloudfoundry-connection-tester
An easy-to-use diagnostic app. Designed for Cloud Foundry but applicable in other cloud environments as well, it allows you to test if an external URL is reachable from within the cloud.

cf push connection-test --no-route --no-start -p conn-test.jar
cf set-env connection-test URL "www.google.com:80"
cf start connection-test
