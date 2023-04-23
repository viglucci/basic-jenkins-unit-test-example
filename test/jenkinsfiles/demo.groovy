/**
 * This is a real Jenkinsfile under test.
 */

node() {
  stage('Checkout') {
    // checkout scm
    def command = 'git clean -xdf'
    say(text: command)
    sh command
  }

  stage('Build and test') {
    sh './gradlew build'
  }
}