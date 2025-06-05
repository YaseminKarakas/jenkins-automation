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
            [new UserRemoteConfig(repoURL, null, null, null)],
            [new BranchSpec("*/main")],
            false, [], null, null, []
        )

        def flowDefinition = new CpsScmFlowDefinition(gitSCM, "Jenkinsfile")
        job.definition = flowDefinition

        // Remove GitHubPushTrigger entirely

        job.save()

        // Trigger initial build
        job.scheduleBuild2(0)
    }
}
