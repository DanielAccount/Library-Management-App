pipeline {
    agent any

    tools {
        maven "maven3.9"
    }

    environment {
        DOCKER_REPO = 'daniel-book-library'
        DOCKER_HOST_PORT = '8086'
        DOCKER_CONTAINER_PORT = '8080'

        // Postgres Specifics
        DB_USER = 'postgres'
        DB_PASSWORD = 'root'
        DB_NAME = 'specification'
        DB_CONTAINER_NAME = 'postgres-docker'
        DOCKER_NETWORK = 'library-network'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/DanielAccount/Library-Management-App.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    sh "docker build -t ${DOCKER_REPO}:${env.BUILD_NUMBER} ."
                }
            }
        }

        stage('Setup Environment') {
            steps {
                sh "docker network create ${DOCKER_NETWORK} || true"
                sh "docker stop ${DB_CONTAINER_NAME} ${DOCKER_REPO} || true"
                sh "docker rm ${DB_CONTAINER_NAME} ${DOCKER_REPO} || true"
            }
        }

        stage('Run Postgres') {
            steps {
                sh """
                docker run -d --name ${DB_CONTAINER_NAME} \
                --network ${DOCKER_NETWORK} \
                -e POSTGRES_USER=${DB_USER} \
                -e POSTGRES_PASSWORD=${DB_PASSWORD} \
                -e POSTGRES_DB=${DB_NAME} \
                -p 5432:5432 \
                postgres:latest
                """
                sh 'echo "Waiting for Postgres to start..." && sleep 20'
            }
        }

        stage('Run Spring Boot') {
            steps {
                sh """
                docker run -d --name ${DOCKER_REPO} \
                --network ${DOCKER_NETWORK} \
                -p ${DOCKER_HOST_PORT}:${DOCKER_CONTAINER_PORT} \
                -e SPRING_DATASOURCE_URL=jdbc:postgresql://${DB_CONTAINER_NAME}:5432/${DB_NAME} \
                -e SPRING_DATASOURCE_USERNAME=${DB_USER} \
                -e SPRING_DATASOURCE_PASSWORD=${DB_PASSWORD} \
                -e SPRING_JPA_HIBERNATE_DDL_AUTO=update \
                ${DOCKER_REPO}:${env.BUILD_NUMBER}
                """
                sh 'echo "Waiting for App to start..." && sleep 25'
            }
        }

        stage('Acceptance Test') {
            steps {
                // We point the test to the port mapped on the host (8086)
                sh 'mvn test -Dcucumber.options="classpath:features"'
            }
        }
    }

    post {
        always {
            sh "docker stop ${DOCKER_REPO} ${DB_CONTAINER_NAME} || true"
            sh "docker rm ${DOCKER_REPO} ${DB_CONTAINER_NAME} || true"
        }
        success {
            emailext to: 'danielepic42@gmail.com', subject: '✅ Build SUCCESS', body: 'Build completed successfully.'
        }
        failure {
            emailext to: 'danielepic42@gmail.com', subject: '❌ Build FAILED', body: 'Build failed. Check Jenkins logs.'
        }
    }
}