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
Documentation for the API can be found in the swagger.json files and API Reference. There are a lot of tools available to automatically generate client from Swagger format. For more information about Swagger see official website - http://swagger.io/.

## What's included

Within the download you'll find the following directories and files, logically grouping common assets and providing both compiled and minified variations. You'll see something like this:

```text
cognizant-p2/
├── API-Gateway
│   ├── Dockerfile
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
├── README.md
├── comments-api
│   ├── Dockerfile
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
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
│       │   │               │   └── WebSecurityConfig.java
│       │   │               ├── controller
│       │   │               │   └── CommentController.java
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
│       │       └── application.properties
│       └── test
│           └── java
│               └── com
│                   └── ga
│                       └── commentsapi
│                           └── CommentsApplicationTests.java
├── deployment.sh
├── docker-compose.yml
├── eureka-server
│   ├── Dockerfile
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
├── front-end
│   ├── css
│   │   ├── bootstrap-grid.css
│   │   ├── bootstrap-grid.css.map
│   │   ├── bootstrap-grid.min.css
│   │   ├── bootstrap-grid.min.css.map
│   │   ├── bootstrap-reboot.css
│   │   ├── bootstrap-reboot.css.map
│   │   ├── bootstrap-reboot.min.css
│   │   ├── bootstrap-reboot.min.css.map
│   │   ├── bootstrap.css
│   │   ├── bootstrap.css.map
│   │   ├── bootstrap.min.css
│   │   ├── bootstrap.min.css.map
│   │   ├── postwidget.css
│   │   └── style.css
│   ├── img
│   │   ├── Scanndit_Wire_Frame.png
│   │   ├── logo.png
│   │   ├── logo2.png
│   │   └── profile-placeholder.png
│   ├── index.html
│   ├── js
│   │   ├── api.js
│   │   ├── bootstrap.bundle.js
│   │   ├── bootstrap.bundle.js.map
│   │   ├── bootstrap.bundle.min.js
│   │   ├── bootstrap.bundle.min.js.map
│   │   ├── bootstrap.js
│   │   ├── bootstrap.js.map
│   │   ├── bootstrap.min.js
│   │   ├── bootstrap.min.js.map
│   │   ├── commentfeed.js
│   │   ├── index.js
│   │   ├── jquery-3.3.1.slim.min.js
│   │   ├── navbar.js
│   │   ├── popper.min.js
│   │   ├── post.js
│   │   ├── postfeed.js
│   │   └── profile.js
│   ├── post
│   │   └── index.html
│   ├── profile
│   │   ├── index.html
│   │   └── style.css
│   └── src
│       └── postwidget
│           └── postwidget.js
├── img
│   ├── ERD_2.png
│   ├── logo.png
│   └── rough_architecture.jpg
├── posts-api
│   ├── Dockerfile
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── ga
│       │   │           └── postsapi
│       │   │               ├── PostsApiApplication.java
│       │   │               ├── bean
│       │   │               │   ├── Comment.java
│       │   │               │   └── User.java
│       │   │               ├── cofig
│       │   │               │   └── WebSecurityConfig.java
│       │   │               ├── controller
│       │   │               │   └── PostController.java
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
│       │       └── application.properties
│       └── test
│           └── java
│               └── com
│                   └── ga
│                       └── postsapi
│                           └── PostsApiApplicationTests.java
├── profile-api
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── ga
│       │   │           └── profileapi
│       │   │               ├── ProfileAPIApplication.java
│       │   │               ├── config
│       │   │               │   └── WebSecurityConfig.java
│       │   │               ├── controller
│       │   │               │   └── ProfileController.java
│       │   │               ├── model
│       │   │               │   ├── Profile.java
│       │   │               │   └── User.java
│       │   │               ├── repository
│       │   │               │   └── ProfileRepository.java
│       │   │               └── service
│       │   │                   ├── ProfileService.java
│       │   │                   └── ProfileServiceImpl.java
│       │   └── resources
│       │       ├── application-dev.properties
│       │       ├── application-local.properties
│       │       └── application.properties
│       └── test
│           └── java
│               └── com
│                   └── ga
│                       └── profileapi
│                           └── ProfileAPIApplicationTests.java
└── users-api
    ├── Dockerfile
    ├── manifest.yml
    ├── mvnw
    ├── mvnw.cmd
    ├── pom.xml
    └── src
        ├── main
        │   ├── java
        │   │   └── com
        │   │       └── ga
        │   │           └── usersapi
        │   │               ├── UsersApiApplication.java
        │   │               ├── config
        │   │               │   ├── JwtRequestFilter.java
        │   │               │   ├── JwtUtil.java
        │   │               │   ├── SecurityConfig.java
        │   │               │   └── SecurityConfigInitializer.java
        │   │               ├── controller
        │   │               │   └── UserController.java
        │   │               ├── model
        │   │               │   ├── JwtResponse.java
        │   │               │   └── User.java
        │   │               ├── repository
        │   │               │   └── UserRepository.java
        │   │               └── service
        │   │                   ├── UserService.java
        │   │                   └── UserServiceImpl.java
        │   └── resources
        │       ├── application-dev.properties
        │       ├── application-local.properties
        │       ├── application-pcf.properties
        │       └── application.properties
        └── test
            └── java
                └── com
                    └── ga
                        └── usersapi
                            └── UsersApiApplicationTests.java



```
    
## Developers

**Carlos Kruger**

- <https://github.com/@carloskruger>

**Mohammad Javed**

- <https://github.com/gajjuCoderBoi>