#!groovy

import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper

@NonCPS
def getPreviousBuildInfo(RunWrapper build) {
    def rawBuild = build.getRawBuild()
    println rawBuild.toString()
}

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
                getPreviousBuildInfo(currentBuild)
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