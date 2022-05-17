#!groovy

def stageRunning() {
    return BuildStatus(currentBuild.getPreviousBuild(), ['Wait for subtask', 'Run script'])
}