# QUIZ APPLICATION
- Welcome to the Quiz Application, a comprehensive platform designed to test your knowledge across three categories: Java, Operating Systems (OS), and C programming.
  <br> 
- This application features a robust login-signup system, a versatile question service for managing questions, and a dynamic quiz service that leverages the question service to generate quizzes, calculate scores, and more.<br> 
- It is a robust quiz platform built with Java, utilizing Spring Boot and Spring Cloud for a microservices architecture. 

<br> <br>
## GETTING STARTED
**Prerequisites:**
- Java Development Kit (*JDK*) 8 or higher
- MySQL
- IntelliJ IDEA (*or any Java IDE*)
- Postman (*for API testing*)

<br>


## SERVICES ORDER AND OVERVIEW

1. **service-registry-server**  (*if there's a config server then first config server then service-registry-server(Eureka Discovery Service)* )
2. **AppGateway**  ( *API Gateway* )
3. **login-signup**  ( *micro-service 1* )
4. **question-service-ms**  ( *micro-service 2* )
5. **quiz-service-ms**  ( *micro-service 3* )
 <br>
 <br>
 
**Service Registry Server (Eureka Discovery Service):**
- The service registry server is responsible for service discovery within the microservices architecture. It allows services to find and communicate with each other.
<br>

**AppGateway (API Gateway):**
- The AppGateway acts as the single entry point for all client requests, routing them to the appropriate microservices.
<br>

**Login-Signup Service:**
- This service handles user authentication and registration. It provides endpoints for creating a new user, logging in, and managing user sessions.
<br>

**Question Service:**
- The Question Service is the backbone of the application, managing all aspects of questions. It allows for adding, retrieving, updating, and deleting questions. It also supports fetching questions by category and calculating scores.
<br> 

**Quiz Service:**                    
- The Quiz Service leverages the Question Service to generate quizzes based on user preferences. It provides endpoints for creating quizzes, retrieving quizzes, and calculating scores.
<br> 

<br>

## API DOCUMENTATION
**Detailed API documentation for each service is provided below. This includes endpoints, request formats, and response examples.**


### 1. Login-Signup Service:

**To create a new user i.e sign up -** <br>
**POST>** - *http://localhost:8083/users-ws/users* <br>
**Ex.**
```json
{
    "firstName": "My",
    "lastName" : "Account",
    "password" : "555666777888",
    "email" : "MytestAccount2024@gmail.com"
}

```

<br>

**To login-**
**POST>** - *http://localhost:8083/users-ws/users/login*
**Ex.** 
```json
{
    "email" : "MytestAccount2024@gmail.com",
    "password" : "555666777888"
}
```

<br>

It won't show anything , it Will give the STATUS:200 OK
<br>
To match it I can always match the userId. 
<br>

After login to make sure I can always match the generated user id
(*public user id i.e an alphanumeric id*) in the databse to the userId in the Headers.


<br> <br> 



### 2. Question Service:

**To get all questions-**
**GET>** - *http://localhost:8083/question-service/question/allquestions*

**To get the questions of a particular category-** <br> **GET** - */question-service/question/category/{category}* <br> 
**GET>** - *http://localhost:8083/question-service/question/category/c* <br> 
**GET>** - *http://localhost:8083/question-service/question/category/os* <br> 
**GET>** - *http://localhost:8083/question-service/question/category/java* <br> <br>

**To post a question(*add a question to DB*)** <br>

**POST>** - *http://localhost:8083/question-service/question/add* <br>

**Ex.**
```json
{
    "category": "Java",
    "question": "What created Java?",
    "option_1": "A. Bjarne",
    "option_2": "B. Bill Gates",
    "option_3": "C. Elon",
    "option_4": "D. James Gosling",
    "correct_ANSWER": "D. James Gosling",
    "difficulty_LEVEL": "Easy"
}
```
<br>


**To update a question in the DB-** <br> **PUT** -  */question-service/question/update/{category}/{id}* <br>

**PUT>** - *http://localhost:8083/question-service/question/update/java/121* <br>

**Ex.**
```json
{
    "category": "Java",
    "question": "What created Java?",
    "option_1": "A. Bjarne",
    "option_2": "B. Bill Gates",
    "option_3": "C. Elon",
    "option_4": "D. no one",
    "correct_ANSWER": "D. no one",
    "difficulty_LEVEL": "Easy"
}
```
<br>


**To delete a question in the DB** <br>  **DELETE** - */question-service/question/delete/{id}* <br>

**DELETE>** - *http://localhost:8083/question-service/question/delete/121*
<br>


(*I can use the other requests as well but since the quiz service will be hitting them anyways , I'll use the quiz service for that* )


<br>
<br>



### 3. Quiz Service:

**To create a quiz of a particular topic with the number of questions I want **
**POST>** - *http://localhost:8083/quiz-service/quiz/create* <br>

**Ex.** 
```json
{
    "category": "C",
    "numOfQ" : "5",
    "title" : "This-Is-My-C-Test"
}
```
<br>


**To get the quiz I generated I need to retrieve it with its id-** <br> **GET** -  */quiz-service/quiz/get/{id}* <br>

**GET>** - *http://localhost:8083/quiz-service/quiz/get/38* 
<br>

**With that I'll get the quiz named:** This-Is-My-C-Test , which has 5 C topic questions.

<br>

- Oh and If I want to get the questions of particular ids in question service , I've to use - <br>
**POST>** - *http://localhost:8083/question-service/question/getQuestions* <br>
And need to pass the ids in this form - **[51, 72, 64, 57, 46]** , as it expects a list of Integers.

<br>

**To get the score -** <br> **POST** - */quiz-service/quiz/submit/{id}* <br>
**POST>**-*http://localhost:8083/quiz-service/quiz/submit/37*

For this I need to give the id and my responses.
<br>

**Ex.**
```json
[
    {
    "id": 48,
    "response":"// This is a comment"
    },

    {
    "id": 58,
    "response": "11"
    },

    {
    "id": 63,
    "response": "4 3 2 1 0"
    },
 
    {
    "id": 76,
    "response": "!"
    },
    
    {
    "id": 78,
    "response":"Perform assertions in testing"
}
]
```
<br> <br>



## TECHNOLOGIES USED
- **Spring Boot:** For building the microservices.
- **MySQL:** As the database for storing questions and user data.
- **JDBC:** For direct database access, providing a deeper understanding of database communication.
- **Feign:** As a declarative web service client, simplifying communication between microservices.
- **IntelliJ IDEA:**  As the IDE for development.
- **Postman:** For testing the API endpoints.

<br>
<br>

<!--

## DEPENDENCIES

### Service Registry Server
- **spring-boot-starter-web:** Provides web application development with Spring MVC.
- **spring-cloud-starter-netflix-eureka-server:** Enables the application to act as a Eureka server for service discovery.
- **spring-boot-starter-test:** Provides testing support for Spring Boot applications.\

<br>
<br>

### AppGateway
- **spring-boot-starter-webflux:** Provides web application development with Spring WebFlux.
- **spring-cloud-starter-gateway:** Enables the application to act as a gateway for routing requests to appropriate microservices.
- **spring-cloud-starter-netflix-eureka-client:** Allows the application to register with and discover services from a Eureka server.
- **reactor-test:** Provides testing support for Spring WebFlux applications.

<br> <br>

### Login-Signup Service
- **spring-boot-starter-web:** Provides web application development with Spring MVC.
- **spring-cloud-starter-netflix-eureka-client:** Allows the application to register with and discover services from a Eureka server.
- **spring-boot-devtools:** Provides automatic restart and live reload for development.
- **spring-boot-starter-data-jdbc:** Provides JDBC support for database access.
- **mysql-connector-j:** MySQL JDBC driver for database connectivity.
- **spring-boot-starter-security:** Provides security features for Spring Boot applications.
- **spring-boot-starter-validation:** Provides validation support for Spring Boot applications.
- **jackson-dataformat-xml:** Provides XML data format support for Jackson.
- **jjwt-api:** Provides JSON Web Token (JWT) support for authentication and authorization.
- **jjwt-impl:** Implementation of JWT support.
- **jjwt-jackson:** Provides Jackson support for JWT.

<br> <br>

### Question Service
- **spring-boot-starter-data-jdbc:** Provides JDBC support for database access.
- **spring-boot-starter-web:** Provides web application development with Spring MVC.
- **spring-cloud-starter-openfeign:** Enables the application to use Feign clients for declarative web service clients.
- **spring-cloud-starter-netflix-eureka-client:** Allows the application to register with and discover services from a Eureka server.
- **mysql-connector-j:** MySQL JDBC driver for database connectivity.
- **spring-boot-starter-test:** Provides testing support for Spring Boot applications.

<br> <br>

### Quiz Service
- **spring-boot-starter-data-jdbc:** Provides JDBC support for database access.
- **spring-boot-starter-web:** Provides web application development with Spring MVC.
- **spring-cloud-starter-openfeign:** Enables the application to use Feign clients for declarative web service clients.
- **spring-cloud-starter-netflix-eureka-client:** Allows the application to register with and discover services from a Eureka server.
- **mysql-connector-j:** MySQL JDBC driver for database connectivity.
- **spring-boot-starter-test:** Provides testing support for Spring Boot applications.

<br> <br>

-->

## CONTACT
For any questions, feedback, or contributions, please feel free to reach out. <br>  You can contact me through-
**Email:** *[anukulmaurya18@gmail.com](mailto:anukulmaurya18@gmail.com)*

<br> <br>

<hr>

**While this project was inspired by a short tutorial, it has been significantly expanded and refined with my own work.**
