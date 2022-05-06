#!groovy

pipeline {
    agent any

    options {
        skipDefaultCheckout()
        timestamps()
    }

    stages {
        stage("Init") {
            steps {
                println "Init"
                checkout scm
            }
        }
        stage("Run downstream") {
            steps {
                build(job:"test-proj", wait: false)
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