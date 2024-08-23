pipeline {
    agent any
    environment {
        PUSHING_BRANCH = "${env.GIT_BRANCH}"
    }
    stages {
        stage('Clone the repo') {
            steps {
                echo 'clone the repo'
                sh 'rm -r weather-core-api'
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
                dir('weather-core-api'){
                    echo 'pull origin main'
                    sh 'pwd'
                    echo '${PUSHING_BRANCH}'
                    sh 'git checkout -b ${PUSHING_BRANCH} ${PUSHING_BRANCH}'
                    sh 'git pull'
                    sh 'mvn clean'
                    sh 'mvn install'
                }
            }
        }
    }
}