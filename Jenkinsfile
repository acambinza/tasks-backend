pipeline {
    agent any
    stages {
        stage ('Build Backend'){
            steps {
                sh 'mvn clean package -DskipTests=true'
            }
        }
        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Sonar Analysis') {
            environment {
                scannerHome =  tool 'SONAR_SCANNER'
            }
            steps {
                    withSonarQubeEnv('SONAR_LOCAL') {
                        sh "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=TasksBackEnd -Dsonar.host.url=http://localhost:9000 -Dsonar.java.binaries=target/classes,target -Dsonar.login=2215737480e8ac632e4db370a944aa1692f272d2 -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java, **RootController.java"
                    }
            }
        }
    }
}

