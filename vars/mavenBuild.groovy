def call(Map config = [:]) {
    // Validate required inputs
    if (!config.repoUrl) {
        error "Missing required parameter: repoUrl"
    }

    def branch = config.branch ?: 'main'
    def mavenCommand = config.mavenCommand ?: 'clean package'

    pipeline {
        agent any

        environment {
            MAVEN_HOME = tool 'Maven 3'  // Ensure this matches the configured Maven tool name in Jenkins
            PATH = "${env.MAVEN_HOME}/bin:${env.PATH}"
        }

        stages {
            stage('Clone Repository') {
                steps {
                    git branch: branch, url: config.repoUrl
                }
            }

            stage('Run Maven Command') {
                steps {
                    sh "mvn ${mavenCommand}"
                }
            }
        }
    }
}
