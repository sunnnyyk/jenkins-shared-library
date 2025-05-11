def call(Map config = [:]) {
def repoUrl = config.repoUrl
def branch = config.branch ?: 'master'
def mavenCommand = config.mavenCommand ?: 'clean package'

    if (!repoUrl) {
        error "Repository URL is required!"
    }

    pipeline {
        agent any

        stages {
            stage('Clone Repository') {
                steps {
                    git branch: branch, url: repoUrl
                }
            }

            stage('Run Maven Command') {
                steps {
                    sh "mvn ${mavenCommand}"
                }
            }
        }

        post {
            always {
                echo 'Build finished.'
            }
        }
    }
}
