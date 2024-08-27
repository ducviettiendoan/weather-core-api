pipeline {
    agent any
    tools {
        maven 'Maven 3.9.7'
    }
    environment {
        PUSHING_BRANCH = "${env.GIT_BRANCH}"
        BRANCH_REMOTE = "origin"
    }
    stages {
        stage('Clone the repo') {
            steps {
                echo 'clone the repo'
                sh 'rm -rf weather-core-api'
                sh 'git clone https://github.com/ducviettiendoan/weather-core-api.git'
            }
        }
        stage('pull main') {
            steps {
                dir('weather-core-api'){
                    echo 'pull origin main'
                    sh 'pwd'
                    sh 'git pull origin main'
                    sh 'git fetch'
                }
            }
        }
        stage('pull pushing branch and build project') {
            steps {
                dir('weather-core-api'){=
                    sh 'pwd'
//                     sh 'git checkout -b temp_branch ${BRANCH_REMOTE}/${PUSHING_BRANCH}'
//                     sh 'git pull'
                    sh 'mvn clean'
                    sh 'mvn install'
                }
            }
        }
    }
}