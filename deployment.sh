#!/bin/bash

# Building eureka-server
cd eureka-server/
mvn package

docker build -t ghazanfar9131/eureka-server:0.0.1-SNAPSHOT .
cd ..

# Building api-gateway
cd api-gateway/
mvn package

docker build -t ghazanfar9131/api-gateway:0.0.1-SNAPSHOT .
cd ..

# Building users-api
cd users-api/
mvn package

docker build -t ghazanfar9131/users-api:0.0.1-SNAPSHOT .
cd ..

# Building profile-api
cd profile-api/
mvn package

docker build -t ghazanfar9131/profile-api:0.0.1-SNAPSHOT .
cd ..

# Building posts-api
cd posts-api
mvn package

docker build -t ghazanfar9131/posts-api:0.0.1-SNAPSHOT .
cd ..

# Building comments-api
cd comments-api
mvn package

docker build -t ghazanfar9131/comments-api:0.0.1-SNAPSHOT .
cd ..

# Building swagger-app
cd swagger-app
mvn package
docker build -t ghazanfar9131/swagger-app:0.0.1-SNAPSHOT .
cd ..

# Pusing Docker-images
docker push ghazanfar9131/eureka-server:0.0.1-SNAPSHOT
docker push ghazanfar9131/api-gateway:0.0.1-SNAPSHOT
docker push ghazanfar9131/users-api:0.0.1-SNAPSHOT
docker push ghazanfar9131/profile-api:0.0.1-SNAPSHOT
docker push ghazanfar9131/posts-api:0.0.1-SNAPSHOT
docker push ghazanfar9131/comments-api:0.0.1-SNAPSHOT
docker push ghazanfar9131/swagger-app:0.0.1-SNAPSHOT

# CF Pushing
cf push

## CF Pushing
## Building eureka-server
#cd eureka-server/
##cf delete ghazanfar9131-eureka-server
#cf push
#cd ..
#
## Building api-gateway
#cd api-gateway/
##cf delete ghazanfar9131-api-gateway
#cf push
#cd ..
#
## Building users-api
#cd users-api/
##cf delete ghazanfar9131-users-api
#cf push
#cd ..
#
## # Building profile-api
## cd profile-api/
## cf delete ghazanfar9131-profile-api
## cf push
## cd ..
#
## # Building posts-api
## cd posts-api
## cf delete ghazanfar9131-posts-api
## cf push
## cd ..
#
## # Building comments-api
## cd comments-api
## cf delete ghazanfar9131-comments-api
## cf push
## cd ..
