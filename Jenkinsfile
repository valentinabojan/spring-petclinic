pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                withMaven(maven: 'maven-installation', mavenSettingsConfig: 'maven-settings.xml') {
                    sh 'mvn -DskipTests clean package'
                }
            }
        }
    }
}
