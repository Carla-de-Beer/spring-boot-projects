# Spring Boot Project with JMS Messaging (ActiveMQ)

A simple Hello-World JMS Messaging project that makes use of Jackson with a JSON payload to allow for the possibility of communication with other languages and systems.

## Requirements
* Java 11
* Spring Boot 2.2.1.RELEASE
* Maven 3.6.2

## Getting started
### Docker

* Run the ActiveMQ Artemis docker image via the command: 
          
          docker run -it --rm \
            -p 8161:8161 \
            -p 61616:61616 \
            vromero/activemq-artemis
            
* At this point you can open the web server port at [`8161`](http://127.0.0.1:8161) and check the web console using the default username and password of `artemis` / `simetraehcapa`.
           
For more information on the use of ActiveMQ Artemis as docker image, follow the instructions [here](https://github.com/vromero/activemq-artemis-docker).
* In the project root, execute the command ```mvn spring-boot:run```.

### Locally
* To the parent POM, add the dependencies for the ```artemis-server``` and ```artemis-jms-server```.

* In the main method, instantiate a new ActiveMQServer object.

