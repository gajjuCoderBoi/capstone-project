pipeline {
    agent {
        docker {
            image 'maven:3.6.2-jdk-8'
            args '--privileged -u="root" -v /tmp/.m2:/root/.m2 -v /usr/local/bin/docker:/usr/bin/docker -v /var/run/docker.sock:/var/run/docker.sock'
        }

    }
    stages {
        stage('Build') {
            parallel {
                stage('Eureka-Server-Build') {
                    steps {
                        dir("eureka-server") {
                            sh 'mvn -B -DskipTests clean package'
                        }

                    }
                }

                stage('Api-Gateway-Build') {
                    steps {
                        dir("api-gateway") {
                            sh 'mvn -B -DskipTests clean package'
                        }
                    }
                }

                stage('Users-Build') {
                    steps {
                        dir("users-api") {
                            sh 'mvn -B -DskipTests clean package'
                        }
                    }
                }

                stage('Profile-Build') {
                    steps {
                        dir("profile-api") {
                            sh 'mvn -B -DskipTests clean package'
                        }
                    }
                }

                stage('Posts-Build') {
                    steps {
                        dir("posts-api") {
                            sh 'mvn -B -DskipTests clean package'
                        }
                    }
                }

                stage('Comments-Build') {
                    steps {
                        dir("comments-api") {
                            sh 'mvn -B -DskipTests clean package'
                        }
                    }
                }

                stage('Swagger-Build') {
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
                stage('Eureka-Server-Test') {
                    steps {
                        dir("eureka-server") {
                            sh 'mvn test'
                        }
                    }
                    post {
                        always {
                            dir("eureka-server") {
                                junit 'target/surefire-reports/*.xml'
                            }
                        }
                    }
                }

                stage('Api-Gateway-Test') {
                    steps {
                        dir("api-gateway") {
                            sh 'mvn test'
                        }
                    }
                    post {
                        always {
                            dir("api-gateway") {
                                junit 'target/surefire-reports/*.xml'
                            }
                        }
                    }
                }

                stage('Users-Test') {
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

                stage('Profile-Test') {
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

                stage('Posts-Test') {
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

                stage('Comments-Test') {
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

                stage('Swagger-Test') {
                    steps {
                        dir("swagger-app") {
                            sh 'mvn test'
                        }
                    }
                    post {
                        always {
                            dir("swagger-app") {
                                junit 'target/surefire-reports/*.xml'
                            }
                        }
                    }
                }
            }
        }
    }
}