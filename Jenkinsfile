pipeline {
//    def eureka
//    def api
//    def users
//    def profile
//    def posts
//    def comments
//    def swagger
    agent {
        docker {
            image 'maven:3.6.2-jdk-8'
            args '--privileged -u="root" -v /tmp/.m2:/root/.m2 -v /usr/local/bin/docker:/usr/bin/docker -v /var/run/docker.sock:/var/run/docker.sock'
        }

    }
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
            parallel {
                stage('Eureka-Server') {
                    steps {
                        dir("eureka-server") {
                            sh 'mvn -B -DskipTests clean package'
                        }

                    }
                }

                stage('Api-Gateway') {
                    steps {
                        dir("api-gateway") {
                            sh 'mvn -B -DskipTests clean package'
                        }
                    }
                }

                stage('User') {
                    steps {
                        dir("users-api") {
                            sh 'mvn -B -DskipTests clean package'
                        }
                    }
                }

                stage('Profile') {
                    steps {
                        dir("profile-api") {
                            sh 'mvn -B -DskipTests clean package'
                        }
                    }
                }

                stage('Posts') {
                    steps {
                        dir("posts-api") {
                            sh 'mvn -B -DskipTests clean package'
                        }
                    }
                }

                stage('Comments') {
                    steps {
                        dir("comments-api") {
                            sh 'mvn -B -DskipTests clean package'
                        }
                    }
                }

                stage('Swagger') {
                    steps {
                        dir("swagger-app") {
                            sh 'mvn -B -DskipTests clean package'
                        }
                    }
                }
            }
        }

        stage('Test') {
            parallel {
                stage('Eureka-Server') {
                    steps {
                        dir("eureka-server") {
                            sh 'mvn test'
                        }
                    }
//                    post {
//                        always {
//                            dir("eureka-server") {
//                                junit 'target/surefire-reports/*.xml'
//                            }
//                        }
//                    }
                }

                stage('Api-Gateway') {
                    steps {
                        dir("api-gateway") {
                            sh 'mvn test'
                        }
                    }
//                    post {
//                        always {
//                            dir("api-gateway") {
//                                junit 'target/surefire-reports/*.xml'
//                            }
//                        }
//                    }
                }

                stage('Users') {
                    steps {
                        dir("users-api") {
                            sh 'mvn test'
                        }
                    }
                    post {
                        always {
                            dir("users-api") {
                                junit 'target/surefire-reports/*.xml'
                            }
                        }
                    }
                }

                stage('Profile') {
                    steps {
                        dir("profile-api") {
                            sh 'mvn test'
                        }
                    }
                    post {
                        always {
                            dir("profile-api") {
                                junit 'target/surefire-reports/*.xml'
                            }
                        }
                    }
                }

                stage('Posts') {
                    steps {
                        dir("posts-api") {
                            sh 'mvn test'
                        }
                    }
                    post {
                        always {
                            dir("posts-api") {
                                junit 'target/surefire-reports/*.xml'
                            }
                        }
                    }
                }

                stage('Comments') {
                    steps {
                        dir("comments-api") {
                            sh 'mvn test'
                        }
                    }
                    post {
                        always {
                            dir("comments-api") {
                                junit 'target/surefire-reports/*.xml'
                            }
                        }
                    }
                }

                stage('Swagger') {
                    steps {
                        dir("swagger-app") {
                            sh 'mvn test'
                        }
                    }
//                    post {
//                        always {
//                            dir("swagger-app") {
//                                junit 'target/surefire-reports/*.xml'
//                            }
//                        }
//                    }
                }
            }
        }

        stage('Coverage') {
            parallel {

                stage('Users') {
                    steps {
                        dir("users-api") {
                            sh 'mvn clean package jacoco:report'
                        }
                    }
                    post {
                        always {
                            dir("users-api") {
                                publishCoverage adapters: [jacocoAdapter('target/site/jacoco/jacoco.xml')]
                            }
                        }
                    }
                }

                stage('Profile') {
                    steps {
                        dir("profile-api") {
                            sh 'mvn clean package jacoco:report'
                        }
                    }
                    post {
                        always {
                            dir("profile-api") {
                                publishCoverage adapters: [jacocoAdapter('target/site/jacoco/jacoco.xml')]
                            }
                        }
                    }
                }

                stage('Posts') {
                    steps {
                        dir("posts-api") {
                            sh 'mvn clean package jacoco:report'
                        }
                    }
                    post {
                        always {
                            dir("posts-api") {
                                publishCoverage adapters: [jacocoAdapter('target/site/jacoco/jacoco.xml')]
                            }
                        }
                    }
                }

                stage('Comments') {
                    steps {
                        dir("comments-api") {
                            sh 'mvn clean package jacoco:report'
                        }
                    }
                    post {
                        always {
                            dir("comments-api") {
                                publishCoverage adapters: [jacocoAdapter('target/site/jacoco/jacoco.xml')]
                            }
                        }
                    }
                }

            }
        }

        stage('Build Image') {
            parallel {
                stage('Eureka-Server') {
                    steps {
                        dir("eureka-server") {
                            script {
                                eureka = docker.build("ghazanfar9131/eureka-server:0.0.1-SNAPSHOT")
                            }
                        }
                    }
//                    post {
//                        always {
//                            dir("eureka-server") {
//                                junit 'target/surefire-reports/*.xml'
//                            }
//                        }
//                    }
                }

                stage('Api-Gateway') {
                    steps {
                        dir("api-gateway") {
                            script {
                                api = docker.build("ghazanfar9131/api-gateway:0.0.1-SNAPSHOT")
                            }
                        }
                    }
//                    post {
//                        always {
//                            dir("api-gateway") {
//                                junit 'target/surefire-reports/*.xml'
//                            }
//                        }
//                    }
                }

                stage('Users') {
                    steps {
                        dir("users-api") {
                            script {
                                users = docker.build("ghazanfar9131/users-api:0.0.1-SNAPSHOT")
                            }
                        }
                    }
                }

                stage('Profile') {
                    steps {
                        dir("profile-api") {
                            script {
                                profile = docker.build("ghazanfar9131/profile-api:0.0.1-SNAPSHOT")
                            }
                        }
                    }
                }

                stage('Posts') {
                    steps {
                        dir("posts-api") {
                            script {
                                posts = docker.build("ghazanfar9131/posts-api:0.0.1-SNAPSHOT")
                            }
                        }
                    }
                }

                stage('Comments') {
                    steps {
                        dir("comments-api") {
                            script {
                                comments = docker.build("ghazanfar9131/comments-api:0.0.1-SNAPSHOT")
                            }
                        }
                    }
                }

                stage('Swagger') {
                    steps {
                        dir("swagger-app") {
                            script {
                                swagger = docker.build("ghazanfar9131/swagger-app:0.0.1-SNAPSHOT")
                            }
                        }
                    }
//                    post {
//                        always {
//                            dir("swagger-app") {
//                                junit 'target/surefire-reports/*.xml'
//                            }
//                        }
//                    }
                }
            }
        }

        stage('Deliver Image') {
            parallel {
                stage('Eureka-Server') {
                    steps {
                        script {
                            docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
                                eureka.push()
                            }
                        }
                    }
//                    post {
//                        always {
//                            dir("eureka-server") {
//                                junit 'target/surefire-reports/*.xml'
//                            }
//                        }
//                    }
                }

                stage('Api-Gateway') {
                    steps {
                        dir("api-gateway") {
                            script {
                                docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
                                    api.push()
                                }
                            }
                        }
                    }
//                    post {
//                        always {
//                            dir("api-gateway") {
//                                junit 'target/surefire-reports/*.xml'
//                            }
//                        }
//                    }
                }

                stage('Users') {
                    steps {
                        dir("users-api") {
                            script {
                                docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
                                    users.push()
                                }
                            }
                        }
                    }
                }

                stage('Profile') {
                    steps {
                        dir("profile-api") {
                            script {
                                docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
                                    profile.push()
                                }
                            }
                        }
                    }
                }

                stage('Posts') {
                    steps {
                        dir("posts-api") {
                            script {
                                docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
                                    posts.push()
                                }
                            }
                        }
                    }
                }

                stage('Comments') {
                    steps {
                        dir("comments-api") {
                            script {
                                docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
                                    comments.push()
                                }
                            }
                        }
                    }
                }

                stage('Swagger') {
                    steps {
                        dir("swagger-app") {
                            script {
                                docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
                                    swagger.push()
                                }
                            }
                        }
                    }
//                    post {
//                        always {
//                            dir("swagger-app") {
//                                junit 'target/surefire-reports/*.xml'
//                            }
//                        }
//                    }
                }
            }
        }
    }
}




















