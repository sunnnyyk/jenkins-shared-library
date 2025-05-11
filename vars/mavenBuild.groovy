def call(Map config = [:]) {
    // Validate required inputs
    if (!config.repoUrl) {
        error "Missing required parameter: repoUrl"
    }

    def branch = config.branch ?: 'main'
    def mavenCommand = config.mavenCommand ?: 'clean package'

    node {
        // Ensure Maven is available
        def MAVEN_HOME = tool 'Maven 3'  // Ensure this matches the configured Maven tool name in Jenkins
        env.PATH = "${MAVEN_HOME}/bin:${env.PATH}"

        stage('Clone Repository') {
            git branch: branch, url: config.repoUrl
        }

        stage('Run Maven Command') {
            sh 'mvn clean install'
        }
    }
}
