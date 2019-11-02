pipeline {
    agent any

    environment {
        MAVEN_INTALLATION = 'maven-installation'
        MAVEN_SETTINGS_CONFIG = 'maven-settings.xml'
    }

    stages {
        stage('Build') {
            steps {
                withMaven(maven: MAVEN_INTALLATION, mavenSettingsConfig: MAVEN_SETTINGS_CONFIG) {
                    sh 'mvn -DskipTests clean package'
                }
            }
        }

        stage('Test') {
            steps {
                withMaven(maven: MAVEN_INTALLATION, mavenSettingsConfig: MAVEN_SETTINGS_CONFIG) {
                    sh 'mvn test'
                }
            }
        }
    }
}
