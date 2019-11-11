def buildApplication() {
    withMaven(maven: MAVEN_INSTALLATION, mavenSettingsConfig: MAVEN_SETTINGS_CONFIG) {
        sh 'mvn -DskipTests clean package'
    }
}

def runAllTests() {
    withMaven(maven: MAVEN_INSTALLATION, mavenSettingsConfig: MAVEN_SETTINGS_CONFIG) {
        sh 'mvn test'
    }
}

def deployApplication() {
    echo "Deploying application..."
}

return this
