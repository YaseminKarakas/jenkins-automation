import jenkins.model.*
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition
import hudson.plugins.git.*
import com.cloudbees.plugins.credentials.*
import org.jenkinsci.plugins.workflow.job.properties.PipelineTriggersJobProperty
import com.cloudbees.jenkins.GitHubPushTrigger

def jenkins = Jenkins.getInstanceOrNull()
if (jenkins != null) {
    def jobName = "webserver-iac-pipeline"

    if (jenkins.getItem(jobName) == null) {
        def job = jenkins.createProject(WorkflowJob, jobName)

        def repoURL = "https://github.com/YaseminKarakas/webserver-with-jenkins.git"

        def gitSCM = new GitSCM(
            [new UserRemoteConfig(repoURL, null, null, null)], // include credentials if needed
            [new BranchSpec("*/main")],
            false, [], null, null, []
        )

        def flowDefinition = new CpsScmFlowDefinition(gitSCM, "Jenkinsfile")
        job.definition = flowDefinition

        // Add GitHub trigger if you want auto-builds on push
        def trigger = new GitHubPushTrigger()
        def triggersProperty = new PipelineTriggersJobProperty([trigger])
        job.addProperty(triggersProperty)

        job.save()
    }
}
