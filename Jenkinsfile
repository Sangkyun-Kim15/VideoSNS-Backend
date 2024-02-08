pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Sangkyun-Kim15/VideoSNS-Backend'
            }
        }
        stage('Build') {
            steps {
                script {
                    // Specify Maven tool as configured in Jenkins
                    def mvnHome = tool name: 'mvn-3.8.8', type: 'maven'
                    sh "${mvnHome}/bin/mvn clean package"
                }
            }
        }
       // stage('Test') {
       //     steps {
       //         script {
       //             // Assuming tests are run with Maven
       //             sh 'mvn test'
       //         }
       //     }
       // }
       stage('Docker Login') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'dockerHubCredentials', usernameVariable: 'DOCKER_HUB_USERNAME', passwordVariable: 'DOCKER_HUB_PASSWORD')]) {
    				// Inside this block, DOCKER_HUB_USERNAME and DOCKER_HUB_PASSWORD are set
    				sh 'echo $DOCKER_HUB_PASSWORD | docker login -u $DOCKER_HUB_USERNAME --password-stdin'
}
                }
            }
        }
        stage('Docker Build and Push') {
            steps {
                script {
                    // Build and push Docker image; ensure Docker is installed on Jenkins agent
                    sh 'docker build -t sabackwon/videosns-backend .'
                    sh 'docker push sabackwon/videosns-backend'
                }
            }
        }
        stage('Deploy') {
            steps {
                // Add deployment steps here
                echo 'Deployment step to be implemented'
            }
        }
    }
}
