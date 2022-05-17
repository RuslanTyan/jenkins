#!groovy

// Build status functions from code snippet https://gist.github.com/GuillaumeSmaha/fdef2088f7415c60adf95d44073c3c88

import hudson.model.Action

import com.cloudbees.workflow.flownode.FlowNodeUtil
import com.cloudbees.workflow.rest.external.StatusExt

import org.jenkinsci.plugins.workflow.graph.FlowNode
import org.jenkinsci.plugins.workflow.cps.nodes.StepStartNode
import org.jenkinsci.plugins.workflow.cps.nodes.StepEndNode
import org.jenkinsci.plugins.workflow.actions.LabelAction


@NonCPS

def __flowNodeHasLabelAction(FlowNode flowNode){
    def actions = flowNode.getActions()

    for (Action action: actions){
        if (action instanceof LabelAction) {
            return true
        }
    }

    return false
}

def __getBuildStages(List<FlowNode> flowNodes, data = [startNodes: [], stages: []]) {
    def currentFlowNode = null

    for (FlowNode flowNode: flowNodes){
        currentFlowNode = flowNode
        if (flowNode instanceof StepEndNode) {
		def startNode = flowNode.getStartNode()
		if (__flowNodeHasLabelAction(startNode)) {
			data.startNodes.add(0, startNode)
			data.stages.add(0, [name: startNode.getDisplayName(), status: FlowNodeUtil.getStatus(flowNode)])
		}
        }
	else if(flowNode instanceof StepStartNode && __flowNodeHasLabelAction(flowNode) && !data.startNodes.contains(flowNode)) {
		data.startNodes.add(0, flowNode)
		data.stages.add(0, [name: flowNode.getDisplayName(), status: StatusExt.IN_PROGRESS])
        }
    }

    if (currentFlowNode == null) {
        return data.stages
    }

    return __getBuildStages(currentFlowNode.getParents(), data)
}

def getBuildInformations(build){
    def rawBuild = build.getRawBuild()
    def execution = rawBuild.getExecution()
    def executionHeads = execution.getCurrentHeads()
    def data = [
	status: build.result,
	stages: __getBuildStages(executionHeads)
    ]
    return data
}

def getBuildCurrentStage(build){
    def data = getBuildInformations(build)
    return data.stages.get(data.stages.size() - 1);
}

def getBuildCurrentStages(build){
    def data = getBuildInformations(build)
    return data.stages;
}

def call(build, String stageName){
    def isRunning = false
    def stages = getBuildCurrentStages(build)
    if (stages != null) {
        stages.each {
            if (it.name == stageName) {
                if (it.status == StatusExt.IN_PROGRESS) {
                    isRunning = true
                }
            }
        }
    }
    return isRunning
}