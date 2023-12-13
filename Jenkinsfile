pipeline {
    agent any
    tools{
        maven 'Maven_3.9.6'
    }
    environment {
        POSTGRES_CREDS = credentials('postgres_user')
    }
    stages{
        stage('Build Maven'){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Romanthium/testcontainers-demo']])
                withEnv(["POSTGRES_USER=$POSTGRES_CREDS_USR", "POSTGRES_PASSWORD=$POSTGRES_CREDS_PSW", 'POSTGRES_HOST=localhost', 'POSTGRES_PORT=5432']) {
                    sh 'mvn clean install'
                }
            }
        }
        stage('Build Docker image'){
            steps{
                script{
                    sh 'docker build -t romanthium/testcontainers-demo .'
                }
            }
        }
        stage('Push image to DockerHub'){
            steps{
                script{
                    withCredentials([string(credentialsId: 'dockerhubpwd', variable: 'dockerhub')]) {
                         sh 'docker login -u romanthium -p ${dockerhub}'
                    }
                    sh 'docker push romanthium/testcontainers-demo'
                }
            }
        }
    }
}