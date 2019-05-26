pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Init') {
            steps {
                echo sh(returnStdout: true, script: 'env')
                script {
                    mavenInstallationName = 'M3'
                }
            }
        }
        stage('Build') {
            steps {
                withMaven(maven: mavenInstallationName, mavenSettingsConfig: 'default-maven-settings.xml', mavenOpts: '-Xmx1024m') {
                    sh 'mvn -B -DskipTests clean package'
                }
            }
        }
        stage('Test') {
            steps {
                withMaven(maven: mavenInstallationName, mavenSettingsConfig: 'default-maven-settings.xml', mavenOpts: '-Xmx1024m') {
                    sh 'mvn test'
                }
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }
}
