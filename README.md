# Capstone-Project

<p align="center">
  <a href="#">
    <img src="./img/logo.png" alt="Scandit logo" width="85" height="85">
  </a>
</p>

<h3 align="center">Scandit (Back-End Micro-services REST API)</h3>

<p align="center">
  Foodie is an a discussion forum where people post and comment on a food recipe. 
  <br>
    
  <br>
  ·
  <a href="https://www.pivotaltracker.com/n/projects/2416993">User Stories</a>
   ·
</p>

## Table of contents

- [User Stories](#user-stories)
- [Timeline](#timeline)
- [Architecture](#architecture)
- [API](#api)
- [API Responses](#responses)
- [Objects](#objects)
- [What's Included](#whats-included)
- [Creators](#developers)


## User Stories

[Pivotal Tracker (Back-End REST API)](https://www.pivotaltracker.com/n/projects/2416993)

Application Goals from User Stories are:
    
* Sign-up and Create Scandit User
* Anyone can See Posts and Comments
* Signed-in user able to post a Post and comment
* Signed-in user able to delete it's own Posts and Comments

## Technology Used

   - Spring boot is used for the backend Services.
   - Zuul Eureka client is used for load-balancing between Services.
   - Zuul Gateway for Api-Gateway to forward api requests to Services. 
   - Postgres is Used for the database. 
   - Docker to run the services. 
   - Swagger to Create API Documentation.
   - Jenkins for CI/CD
   - Pivotal Cloud Foundry is used to deploy the application.
   
## General Approach

We discussed the architecture of the project and decided to use a database for each component. We figured out
how to proceed with the development and created the corresponding user stories that will lead us to completion.

We used pair programming and collaborated with each other and tried to stick to our schedule.

## Major Hurdles

   - Communication between services.
   - Integrity of Data. 


## Installation Instruction

Follow these easy step:
    

   1. Clone the repository:
        ```
        https://github.com/gajjuCoderBoi/capstone-project.git
        ```
   2. Execute this on command line:

        ```
        $cd capstone-project
        $docker-compose up
        ```
      * if don't have Docker install from [here](https://docs.docker.com/v17.09/engine/installation/)
   3. Enjoy

## Jenkins Instruction
Follow these easy step:
   

  1. Run Jenkins by using following command:
       ```
       https://github.com/gajjuCoderBoi/capstone-project.git
       ```
      * if don't have Jenkins install from [here](https://jenkins.io/doc/book/installing/)
  
  2. Execute this on command line:
   
       ```
       $cd capstone-project
       $docker-compose up
       ```
  3. Enjoy

## Timeline

1. Requirement Analysis
    * Tuesday(Nov 12, 2019) - user stories
        
        We give our user stories points, so that we could prioritize our development steps. 
 
            https://www.pivotaltracker.com/n/projects/2416993
    
    * Wednesday(Nov 13, 2019) - ERD
        
            Pair mapped Architecture Diagram to include 5 services and their communications.
            
         [Architecture](#architecture)
         
    * Thursday(Nov 14, 2019) - User, comment, and post Service mapped.
    
            Added user, comment, and post creation functionality.
            
    * Friday(Nov 15, 2019) - Building.
    
            Running services on the Docker. And Building on the Docker and pushing the Docker images. 
        
    * Saturday(Nov 16, 2019) - Tested all route functionality and began testing.
    
            - All route functionality is working. Recorded results to ReadMe file.
            
            - Connect front-end with backend. 
            
    * Sunday(Oct 25, 2019) - Trying to deployee.
    
            *-*-*-*-*-*-*
        
      

  

## Architecture
##### Rough Sketch of Architecture Diagram.
![Architecture](./img/rough_architecture.jpg)

##### Soft Representation of Architecture Diagram.
![Architecture](./img/archi2.png)
    
    
     

## API
```text
Base URL: /, Version: 1.1

Default request content-types: application/json

Default response content-types: application/json

Schemes: http 
```
Documentation for the API can be found in the [swagger](./swagger) files and API Reference. There are a lot of tools available to automatically generate client from Swagger format. For more information about Swagger see official website - http://swagger.io/.

## What's included

Within the download you'll find the following directories and files, logically grouping common assets and providing both compiled and minified variations. You'll see something like this:

```text
capstone-project/
.
├── API-Gateway
│   ├── Dockerfile
│   ├── api-gateway.iml
│   ├── manifest.yml
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── example
│       │   │           └── APIGateway
│       │   │               ├── TestController.java
│       │   │               └── ZuulGatewayApplication.java
│       │   └── resources
│       │       ├── application-dev.properties
│       │       ├── application-pcf.properties
│       │       └── application.properties
│       └── test
│           └── java
│               └── com
│                   └── example
│                       └── APIGateway
│                           └── ZuulGatewayApplicationTests.java
├── Jenkinsfile
├── Jenkinsfile_api-gateway
├── Jenkinsfile_comments-api
├── Jenkinsfile_eureka-server
├── Jenkinsfile_posts-api
├── Jenkinsfile_profile-api
├── Jenkinsfile_swagger-app
├── Jenkinsfile_users-api
├── README.md
├── comments-api
│   ├── Dockerfile
│   ├── manifest.yml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── ga
│       │   │           └── commentsapi
│       │   │               ├── CommentsApplication.java
│       │   │               ├── bean
│       │   │               │   ├── Post.java
│       │   │               │   └── User.java
│       │   │               ├── config
│       │   │               │   ├── DatabaseLoader.java
│       │   │               │   ├── RabbitMQConfig.java
│       │   │               │   ├── SwaggerDocumentationConfiguration.java
│       │   │               │   └── WebSecurityConfig.java
│       │   │               ├── controller
│       │   │               │   └── CommentController.java
│       │   │               ├── exception
│       │   │               │   ├── CommentNotExistException.java
│       │   │               │   ├── ErrorResponse.java
│       │   │               │   ├── MyExceptionHandler.java
│       │   │               │   ├── PostNotExistException.java
│       │   │               │   ├── TokenException.java
│       │   │               │   └── UnauthorizeActionException.java
│       │   │               ├── messagequeue
│       │   │               │   ├── Receiver.java
│       │   │               │   └── Sender.java
│       │   │               ├── model
│       │   │               │   └── Comment.java
│       │   │               ├── repository
│       │   │               │   └── CommentRepository.java
│       │   │               └── service
│       │   │                   ├── CommentService.java
│       │   │                   └── CommentServiceImpl.java
│       │   └── resources
│       │       ├── application-dev.properties
│       │       ├── application-local.properties
│       │       ├── application-pcf.properties
│       │       └── application.properties
│       └── test
│           └── java
│               └── com
│                   └── ga
│                       └── commentsapi
│                           ├── CommentsApplicationTests.java
│                           ├── controller
│                           │   └── CommentControllerTest.java
│                           ├── exception
│                           │   └── MyExceptionHandlerTest.java
│                           └── service
│                               └── CommentsServiceTest.java
├── deployment.sh
├── docker-compose.yml
├── eureka-server
│   ├── Dockerfile
│   ├── README.md
│   ├── eureka-server.iml
│   ├── manifest.yml
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── example
│       │   │           └── eurekaserver
│       │   │               └── EurekaServerApplication.java
│       │   └── resources
│       │       ├── application-dev.properties
│       │       ├── application-pcf.properties
│       │       └── application.properties
│       └── test
│           └── java
│               └── com
│                   └── example
│                       └── eurekaserver
│                           └── EurekaServerApplicationTests.java
├── logstash.conf
├── manifest.yml
├── posts-api
│   ├── Dockerfile
│   ├── manifest.yml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── ga
│       │   │           └── postsapi
│       │   │               ├── PostsApiApplication.java
│       │   │               ├── bean
│       │   │               │   ├── Comment.java
│       │   │               │   ├── PostRequestBody.java
│       │   │               │   └── User.java
│       │   │               ├── cofig
│       │   │               │   ├── DatabaseLoader.java
│       │   │               │   ├── RabbitMQConfig.java
│       │   │               │   ├── SwaggerDocumentationConfiguration.java
│       │   │               │   └── WebSecurityConfig.java
│       │   │               ├── controller
│       │   │               │   └── PostController.java
│       │   │               ├── exception
│       │   │               │   ├── ErrorResponse.java
│       │   │               │   ├── MyExceptionHandler.java
│       │   │               │   ├── PostNotExistException.java
│       │   │               │   ├── TokenException.java
│       │   │               │   └── UnauthorizeActionException.java
│       │   │               ├── messagequeue
│       │   │               │   ├── Receiver.java
│       │   │               │   └── Sender.java
│       │   │               ├── model
│       │   │               │   └── Post.java
│       │   │               ├── repository
│       │   │               │   └── PostRepository.java
│       │   │               └── service
│       │   │                   ├── PostService.java
│       │   │                   └── PostServiceImpl.java
│       │   └── resources
│       │       ├── application-dev.properties
│       │       ├── application-local.properties
│       │       ├── application-pcf.properties
│       │       └── application.properties
│       └── test
│           └── java
│               └── com
│                   └── ga
│                       └── postsapi
│                           ├── controller
│                           │   └── PostControllerTest.java
│                           ├── exception
│                           │   └── MyExceptionHandlerTest.java
│                           └── service
│                               └── PostServiceTest.java
├── profile-api
│   ├── Dockerfile
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── ga
│       │   │           └── profileapi
│       │   │               ├── ProfileAPIApplication.java
│       │   │               ├── bean
│       │   │               │   └── User.java
│       │   │               ├── config
│       │   │               │   ├── RabbitMQConfig.java
│       │   │               │   ├── SwaggerDocumentationConfiguration.java
│       │   │               │   └── WebSecurityConfig.java
│       │   │               ├── controller
│       │   │               │   └── UserProfileController.java
│       │   │               ├── exception
│       │   │               │   ├── EntityNotCreatedException.java
│       │   │               │   ├── EntityNotFoundException.java
│       │   │               │   ├── ErrorResponse.java
│       │   │               │   ├── MyExceptionHandler.java
│       │   │               │   ├── ProfileNotFoundException.java
│       │   │               │   └── TokenException.java
│       │   │               ├── messagequeue
│       │   │               │   └── Sender.java
│       │   │               ├── model
│       │   │               │   └── Profile.java
│       │   │               ├── repository
│       │   │               │   └── ProfileRepository.java
│       │   │               └── service
│       │   │                   ├── ProfileService.java
│       │   │                   └── ProfileServiceImpl.java
│       │   └── resources
│       │       ├── application-dev.properties
│       │       ├── application-local.properties
│       │       ├── application-pcf.properties
│       │       └── application.properties
│       └── test
│           └── java
│               └── com
│                   └── ga
│                       └── profileapi
│                           ├── ProfileAPIApplicationTests.java
│                           ├── controller
│                           │   └── UserProfileControllerTest.java
│                           ├── exception
│                           │   └── MyExceptionHandlerTest.java
│                           └── service
│                               └── ProfileServiceTest.java
├── swagger
│   ├── comments-api.json
│   ├── posts-api.json
│   ├── profile-api.json
│   ├── swagger.iml
│   └── users-api.json
├── swagger-app
│   ├── Dockerfile
│   ├── HELP.md
│   ├── manifest.yml
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── com
│   │   │   │       └── ga
│   │   │   │           └── swaggerapp
│   │   │   │               ├── SwaggerAppApplication.java
│   │   │   │               ├── config
│   │   │   │               │   ├── ServiceDefinitionsContext.java
│   │   │   │               │   ├── ServiceDescriptionUpdater.java
│   │   │   │               │   └── SwaggerUIConfiguration.java
│   │   │   │               └── controller
│   │   │   │                   └── ServiceDefinitionController.java
│   │   │   └── resources
│   │   │       ├── application-dev.properties
│   │   │       ├── application-local.properties
│   │   │       ├── application-pcf.properties
│   │   │       └── application.properties
│   │   └── test
│   │       └── java
│   │           └── com
│   │               └── ga
│   │                   └── swaggerapp
│   │                       └── SwaggerAppApplicationTests.java
│   └── swagger-app.iml
└── users-api
    ├── Dockerfile
    ├── manifest.yml
    ├── mvnw
    ├── mvnw.cmd
    ├── pom.xml
    ├── src
    │   ├── main
    │   │   ├── java
    │   │   │   └── com
    │   │   │       └── ga
    │   │   │           └── usersapi
    │   │   │               ├── UsersApiApplication.java
    │   │   │               ├── config
    │   │   │               │   ├── DatabaseLoader.java
    │   │   │               │   ├── JwtRequestFilter.java
    │   │   │               │   ├── JwtUtil.java
    │   │   │               │   ├── RabbitMQConfig.java
    │   │   │               │   ├── SecurityConfig.java
    │   │   │               │   ├── SecurityConfigInitializer.java
    │   │   │               │   └── SwaggerConfig.java
    │   │   │               ├── controller
    │   │   │               │   └── UserController.java
    │   │   │               ├── exception
    │   │   │               │   ├── EntityNotCreatedException.java
    │   │   │               │   ├── EntityNotFoundException.java
    │   │   │               │   ├── ErrorResponse.java
    │   │   │               │   ├── LoginException.java
    │   │   │               │   ├── MyExceptionHandler.java
    │   │   │               │   └── UserAlreadyExistException.java
    │   │   │               ├── messagequeue
    │   │   │               │   └── Receiver.java
    │   │   │               ├── model
    │   │   │               │   ├── JwtResponse.java
    │   │   │               │   ├── User.java
    │   │   │               │   └── UserRole.java
    │   │   │               ├── repository
    │   │   │               │   ├── UserRepository.java
    │   │   │               │   └── UserRoleRepository.java
    │   │   │               └── service
    │   │   │                   ├── UserRoleService.java
    │   │   │                   ├── UserRoleServiceImpl.java
    │   │   │                   ├── UserService.java
    │   │   │                   └── UserServiceImpl.java
    │   │   └── resources
    │   │       ├── application-dev.properties
    │   │       ├── application-local.properties
    │   │       ├── application-pcf.properties
    │   │       ├── application-qa.properties
    │   │       └── application.properties
    │   └── test
    │       └── java
    │           └── com
    │               └── ga
    │                   └── usersapi
    │                       ├── UsersApiApplicationTests.java
    │                       ├── controller
    │                       │   └── UserControllerTest.java
    │                       ├── exception
    │                       │   └── MyExceptionHandlerTest.java
    │                       ├── integration
    │                       │   └── UserIntegrationTest.java
    │                       └── service
    │                           ├── UserRoleServiceTest.java
    │                           └── UserServiceTest.java



```
    
## Developers

**Carlos Kruger**

- <https://github.com/@carloskruger>

**Mohammad Javed**

- <https://github.com/gajjuCoderBoi>