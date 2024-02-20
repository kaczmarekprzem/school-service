# School serbice

## Overview
The School Service is an API designed to calculate fees report for entire school and for given parent.


## Technologies Used
- Java 17
- Spring Boot
- Maven

### Run the application
Once the build is successful, you can run the application using:

```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.


## Running Tests
To run the test suite, execute:

```bash
mvn test
```

## API Documentation
After running the application, you can access the Swagger UI for the API documentation at `http://localhost:8080/swagger-ui.html`.

## Docker
`docker build -t your-image-name .` \
`docker run -p 8080:8080 your-image-name`


## Local usage
h2 console is available at: http://localhost:8080/h2-console/ <br>
Resources contains test data
