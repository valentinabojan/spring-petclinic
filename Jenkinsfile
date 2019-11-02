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
                    sh 'mvn -DskipTests clean package'
                }
            }
        }

        stage('Test') {
            steps {
                withMaven(maven: MAVEN_INSTALLATION, mavenSettingsConfig: MAVEN_SETTINGS_CONFIG) {
                    sh 'mvn test'
                }
            }
        }
    }
}
