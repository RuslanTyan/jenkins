#!groovy

def stageRunning() {
    def List protectedList = ['Wait for subtask', 'Run script']
    return BuildStatus(currentBuild.getPreviousBuild(), protectedList)
}