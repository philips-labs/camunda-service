Create the database if it does not exist:
```
# cf create-service hsdp-rds mysql-medium-dev camunda-db-dev
```

You can watch for the create to complete with this command:
```
# watch -g "cf service camunda-db-dev | grep status"
```

Clone the project
```
# git clone https://github.com/PhilipsRespironics/camunda-service.git
```

Change to project directory
```
# cd camunda-service
```

Build the project:
```
# ./gradlew clean build
```

Deploy to CF
```
# cf push --vars-file vars-dev.yml -n camunda-service-dev
```
Replace ```camunda-service-dev``` with any preferred subdomain of .cloud.pcftest.com

