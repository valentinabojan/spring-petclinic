import hudson.tasks.test.AbstractTestResultAction

pipeline {
    agent any

    environment {
        MAVEN_INSTALLATION = 'maven-installation'
        MAVEN_SETTINGS_CONFIG = 'maven-settings.xml'
    }

    stages {
        stage('Build') {
            steps {
                withMaven(maven: MAVEN_INSTALLATION, mavenSettingsConfig: MAVEN_SETTINGS_CONFIG) {
                    buildApplication()
                }
                sendSlackNotificationForRegularStage()
            }
        }

        stage('Test') {
            steps {
                withMaven(maven: MAVEN_INSTALLATION, mavenSettingsConfig: MAVEN_SETTINGS_CONFIG) {
                    runAllTests()
                }
                sendSlackNotificationForRegularStage()
            }
        }

        stage('Deploy') {
            input {
                message 'Do you want to deploy?'
                submitterParameter 'responder'
            }
            steps {
                deployApplication()
                sendSlackNotificationForApprovedStage(responder)
            }
        }
    }

    post {
        success {
            sendSlackNotificationForSuccessfulBuild()
        }
        failure {
            sendSlackNotificationForFailedBuild()
        }
        aborted {
            sendSlackNotificationForAbortedBuild()
        }
    }

}

void buildApplication() {
    sh 'mvn -DskipTests clean package'
}

void runAllTests() {
    sh 'mvn test'
}

void deployApplication() {
    echo "Deploying application..."
}

void sendSlackNotificationForRegularStage() {
    statusComment = "[${env.JOB_NAME}] <${env.BUILD_URL}|#${env.BUILD_NUMBER}> ${env.STAGE_NAME} stage completed succesfully for ${env.GIT_BRANCH}"
    slackSend color: '#0000ff', message: statusComment
}

void sendSlackNotificationForApprovedStage(String responder) {
    statusComment = "[${env.JOB_NAME}] <${env.BUILD_URL}|#${env.BUILD_NUMBER}> ${env.STAGE_NAME} stage was approved by ${responder} for ${env.GIT_BRANCH}"
    slackSend color: '#0000ff', message: statusComment
}

void sendSlackNotificationForSuccessfulBuild() {
    statusComment = "[${env.JOB_NAME}] <${env.BUILD_URL}|#${env.BUILD_NUMBER}> completed succesfully for ${env.GIT_BRANCH} :tada:"
    slackSend color: 'good', message: statusComment
}

void sendSlackNotificationForFailedBuild() {
    statusComment = getTestResultsMessage()
    slackSend color: 'danger', message: statusComment
}

void sendSlackNotificationForAbortedBuild() {
    statusComment = "[${env.JOB_NAME}] <${env.BUILD_URL}|#${env.BUILD_NUMBER}> for ${env.GIT_BRANCH} was aborted by ${getBuildUser()}"
    slackSend message: statusComment
}

String getTestResultsMessage() {
    AbstractTestResultAction testResultAction = currentBuild.rawBuild.getAction(AbstractTestResultAction.class)
    if (testResultAction != null) {
        def total = testResultAction.totalCount
        def failed = testResultAction.failCount
        def skipped = testResultAction.skipCount
        return "[${env.JOB_NAME}] <${env.BUILD_URL}|#${env.BUILD_NUMBER}> had test failures for ${env.GIT_BRANCH}.\n  Total: ${total}, Failed: ${failed}, Skipped: ${skipped}"
    } else {
        return "[${env.JOB_NAME}] <${env.BUILD_URL}|#${env.BUILD_NUMBER}> failed for ${env.GIT_BRANCH}"
    }
}

String getBuildUser() {
    return currentBuild.rawBuild.getCause(Cause.UserIdCause).getUserId()
}
