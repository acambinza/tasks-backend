pipeline {
    agent any

    environment {
        JAVA_HOME = tool name: 'JAVA_LOCAL_11', type: 'jdk'
        SONAR_SCANNER_OPTS = '--add-opens java.base/java.lang=ALL-UNNAMED'
    }
    
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
                        sh "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=TasksBackEnd -Dsonar.host.url=http://localhost:9000 -Dsonar.java.binaries=target/classes,target -Dsonar.login=2215737480e8ac632e4db370a944aa1692f272d2 -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java,**RootController.java"
                    }
            }
        }
        stage('Quality Gate') {
            steps {
                sleep(10)
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Deploy Backend') {
            steps {
                deploy adapters: [tomcat9(credentialsId: 'TOMCAT_ID', path: '', url: 'http://localhost:8000')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }
    }
}

