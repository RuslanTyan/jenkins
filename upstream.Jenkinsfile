#!groovy

import hudson.model.Result
import hudson.model.Run
import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper
import jenkins.model.CauseOfInterruption.UserInterruption


// @NonCPS
def getPreviousBuildInfo(RunWrapper build) {
    println build.toString()
    def currentResult = build.getCurrentResult()
    println currentResult.toString()
    def rawBuild = build.getRawBuild()
    def listener = rawBuild.getListener()
    def previousBuild = rawBuild.getPreviousBuildInProgress()
    testVar = previousBuild.getEnvironment(listener).get('TEST_VAR')
    println "testVar" + testVar
    testVarCurrent = currentBuild.previousBuild.buildVariables.TEST_VAR
    println "testVarCurrent" + testVarCurrent
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
                    env.TEST_VAR = "test"
                }
            }
        }
    }
}