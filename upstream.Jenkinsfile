#!groovy

import hudson.model.Result
import hudson.model.Run
import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper
import jenkins.model.CauseOfInterruption.UserInterruption

library 'my-shared-library'

def subTask

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
                println Test1(currentBuild)
                println Test2.test2(currentBuild.getPreviousBuild())
            }
        }
        stage("Run downstream") {
            steps {
                script {
                    env.TEST_VAR = "test"
                    subTask = build(job:"test-proj", propagate: true, wait: false)
                }
            }
        }
        stage("Wait for subtask") {
            steps {
                script {
                    println BuildStatus.getBuildCurrentStages(currentBuild)
                    println BuildStatus.getBuildCurrentStages(currentBuild.getPreviousBuild())
                    println Wrapper.stageRunning()
                    def list1 = [1,2,3]
                    def list2 = ['one', 'two']
                    println TestIterate(list1, list2)

                    timeout(time: 4, unit: 'MINUTES') {
                        while (Wrapper.stageRunning()) {
                            sleep(time:5,unit:"SECONDS")
                            println "Previous build 'Wait for subtask' stage is not yet complete"
                        }
                    }
                    try {
                        timeout(time: 2, unit: 'MINUTES') {
                            while (subTask == null) {
                                sleep(time:3,unit:"SECONDS")
                            }
                            println "subTask:"
                            println subTask.getClass()
                            println subTask.dump()
                        }
                    } catch (Exception e) {
                        println e.toString()
                    }
                }
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