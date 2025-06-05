import com.cloudbees.plugins.credentials.CredentialsScope
import com.cloudbees.jenkins.plugins.awscredentials.AWSCredentialsImpl
import com.cloudbees.plugins.credentials.SystemCredentialsProvider

def creds = new AWSCredentialsImpl(
    CredentialsScope.GLOBAL,
    "aws-creds",
    System.getenv("AWS_ACCESS_KEY_ID"),
    System.getenv("AWS_SECRET_ACCESS_KEY"),
    "AWS credentials from env"
)

SystemCredentialsProvider.getInstance().getStore().addCredentials(
    com.cloudbees.plugins.credentials.domains.Domain.global(),
    creds
)
