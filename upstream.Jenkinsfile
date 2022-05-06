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
            }
        }
        stage("Run downstream") {
            steps {
                build(job:"test-proj", wait: true)
            }
        }
    }
}