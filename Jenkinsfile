#!groovy

pipeline {
    agent none

    options {
        skipDefaultCheckout()
        timestamps()
    }

    stages {
        stage("Init") {
            steps {
                println "Init"
                checkout scm([$class: 'GitSCM',
                    branches: "task-rtyan-test-downstream-status"
                ])
            }
        }
        stage("Run script") {
            steps {
                script {
                    sh "bash rtyan-test.sh"
                }
            }
        }
    }
}