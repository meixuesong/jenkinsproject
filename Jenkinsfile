#!groovy​

def mvnHome

node {
    stage('Preparation') { // for display purposes
        echo "Preparing....."

        checkout scm
    }

    stage('Build') {
        echo "Building....."
        mvnHome = tool 'apache-maven-3.0.4'  //defined in jenkins global tool configuration

        dir("${WORKSPACE}") {   //work in the same directory. workspace is a global env
            if (isUnix()) {
                sh "'${mvnHome}/bin/mvn' clean package"
                //sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
            } else {
                bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean package/)
            }
        }
    }

//checkstyle already included in the build phrase, this stage only collect the result
    stage('Code Analysis') {
        parallel(
                "CheckStyle": {
                    dir("${WORKSPACE}") {
                        checkstyle canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/checkstyle-result.xml', unHealthy: ''
                    }
                },

                "SonarQube": {
                    mvnHome = tool 'apache-maven-3.0.4'
                    dir("${WORKSPACE}") {
                        withSonarQubeEnv('Consultant02-SonarQube') {  //defined in Jenkins configure system
                            bat(/"${mvnHome}\bin\mvn" --debug org.sonarsource.scanner.maven:sonar-maven-plugin:3.3.0.603:sonar /)
                            //+
//                              '-f pom.xml ' +
//                              "-Dsonar.projectKey=oocl:${JOB_NAME} " +
//                              '-Dsonar.login=$SONAR_UN ' +
//                              '-Dsonar.password=$SONAR_PW ' +
//                              '-Dsonar.language=java ' +
//                              '-Dsonar.sources=. ' +
//                              '-Dsonar.tests=. ' +
//                              '-Dsonar.test.inclusions=**/*Test*/** ' +
//                              '-Dsonar.exclusions=**/*Test*/**'
                        }

                        timeout(time: 1, unit: 'MINUTES') {
                            def qg = waitForQualityGate()
                            if (qg.status != 'OK') {
                                error "Pipeline aborted due to quality gate failure: ${qg.status}"
                            }
                        }
                    }
                }
        )
    }

//    stage('SonarQube analysis') {
//    }

//need sonarqube setup webhooks, so that jenkins can receive the result.
//    stage("SonarQube Quality Gate") {
//        dir("${WORKSPACE}") {
//        }
//    }

    stage('Results') {
        echo "Resulting....."
        // bat "touch target/surefire-reports/*.xml"
        junit '**/target/surefire-reports/TEST-*.xml'
        archive 'target/*.jar'
    }
}
