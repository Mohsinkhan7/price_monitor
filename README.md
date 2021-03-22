# Coding Challenge: Price Monitor

This project is built in **Java 14** using **Spring Boot**. In addition to these main components, **Lambork** java library for a cleaner code; **Assert J**, **JUnit** and **Mockito** for Unit Testing and **Maven** is used for Build Automation. 


## Unit Test Cases

Unit test cases can be executed using maven command
 **mvn test**

## Execution 

Program can be executed via command line with following commands
**./mvnw spring-boot:run**
**java -jar price_monitor-0.0.1-SNAPSHOT.jar**
Jar is also placed in the Jar folder in the same repository.


## Architecture

Controller Service Repo design is implemented with DTO classes including validations. Also, observer design pattern is utilized for the relationship between the two main packages of ticks and statistics.

## Data Structure and Algorithm

Concurrent Hash Map is the main data structure. Custom Algorithm is implemented with custom garbage collector logic to obtain Ω(1) and O(60) for Memory. O(1) for GET /statistics and GET /statistics/{instrument_identifier} constant time. 

## Assumptions

Current milliseconds are always floor not ceiling function while converting to seconds. Sliding 60 interval includes the tick at second 1 and second 60.

## Improvements

Message broker like kafka or rabbit mq can be utilized for asynchronous communication channel among the packages. 

Exception handling can be much more expressive with custom exception handler classes. 

More scenarios can be added in Unit Testing. 


## Fun Part

I found this challenge very interesting as it includes everything from REST API, cleaner architecture design to performance optimized data structure and algorithm requirement.
