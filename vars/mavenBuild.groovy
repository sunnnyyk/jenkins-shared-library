def call(Map config) {
    git branch: config.branch, url: config.repoUrl
    sh "mvn ${config.mavenCommand}"
}
