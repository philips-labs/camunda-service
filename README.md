 # **Camunda Ecosystem Exploration**


#### Contents of this README:
  1. **Create a database**
  2. **Build the source**
      - *Clone the repo*
      - *Build the source*
  3. **Deploy to HSDP (Cloud Foundry)**
      - Docker vs Spring Boot
      - CF Push
  4. **License**

---

## **1. Create a database**

```
# cf create-service hsdp-rds mysql-medium-dev camunda-db-dev
# watch -g "cf service camunda-db-dev | grep status"
```

---

## **2. Build the source**

Clone the project
```
# git clone https://github.com/PhilipsRespironics/camunda-service.git
```

Build the project:
```
# cd camunda-service
# ./gradlew clean build
```

## **3. Deploy to HSDP**

Considerations:
* Docker versus Spring:
    * Additional dependency (Docker) to maintain
    * PCF patches for stemcells and application dependencies not applied for Docker

Deploy to CF:
```
# cf push --vars-file vars-dev.yml -n camunda-service-dev
```
Replace ```camunda-service-dev``` with any preferred subdomain of .cloud.pcftest.com

## **4. Import Workflows**
Upload workflows for example use cases:
* Open one of the workflows (`workflows` dir) in Modeler, e.g. *SleepStudy.bpmn*
* Click the "Deploy current diagram" button to upload the workflow to the HSDP-deployed instance
    * The *__REST Endpoint__* field should be `http://camunda-service-dev.cloud.pcftest.com//engine-rest`
    * The *__Deployment Name__* field should be "SleepStudy", or similar

## **5. License**

Copyright (c) 2019-2020 Respironics Inc, a Philips company
