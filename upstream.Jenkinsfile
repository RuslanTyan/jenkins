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
    println currentBuild.previousBuild.description
//     def prevBuild = rawBuild.getPreviousBuildInProgress()
//     println "Print PreviousBuildInfo"
//     def exec = prevBuild.getExecutor()
//     if (exec != null) {
//         echo "!! Aborting older build #${previousBuild.number}"
//         exec.interrupt(Result.ABORTED, new UserInterruption("Aborted by newer build #${newId}"))
//     }
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