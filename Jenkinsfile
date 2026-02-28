pipeline {
    agent any

    environment {
        SLACK_CHANNEL = '#automation-jobs'
        SLACK_CHANNEL_ID = 'C05R1CXD2Q4'
    }

    tools {
        maven 'Maven 3.9.11'
    }

    stages {
        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }

        stage('Checkout') {
            steps {
                git branch: 'main',
                        url: 'https://github.com/SaifAlAbabseh/All-Chat-UI-Test-Automation.git'
            }
        }

        stage('Set Env') {
            steps {
                script {
                    def props = readProperties file: '/var/Env/.env'
                    props.each { k, v -> env."$k" = v }
                }
            }
        }

        stage('Start Recording') {
            steps {
                sh '''
                #!/bin/bash
                Xvfb :1 -screen 0 1920x1080x24 -ac &
                export DISPLAY=:1
                
                mkdir -p $WORKSPACE/recordings
                chmod 777 $WORKSPACE/recordings
                
                ffmpeg -y -probesize 100M -analyzeduration 100M -f x11grab -video_size 1920x1080 -i $DISPLAY \
                       -r 30 -codec:v libx264 -preset ultrafast \
                       $WORKSPACE/recordings/test.mp4 &
                FFMPEG_PID=$!
                
                mvn clean test -DsuiteXmlFile=suites/MainTestSuite.xml \
                               -Dbrowser=${browser} -DheadlessMode=${headlessMode} \
                               -Dmobile=${mobile} -DincludeAudio=${includeAudio}
                               
                kill -2 $FFMPEG_PID
                wait $FFMPEG_PID 2>/dev/null
                '''
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'recordings/*.mp4', allowEmptyArchive: false

            junit 'target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'target/surefire-reports/**, src/main/screenshots/*.png'
            script {
                def counts = sh(
                        script: '''
                        FILE="target/surefire-reports/testng-results.xml"
                        TOTAL=$(grep -oP '(?<=total=")[0-9]+' "$FILE")
                        PASSED=$(grep -oP '(?<=passed=")[0-9]+' "$FILE")
                        FAILED=$(grep -oP '(?<=failed=")[0-9]+' "$FILE")
                        SKIPPED=$(grep -oP '(?<=skipped=")[0-9]+' "$FILE")
                        IGNORED=$(grep -oP '(?<=ignored=")[0-9]+' "$FILE")
                        echo "$TOTAL,$PASSED,$FAILED,$SKIPPED,$IGNORED"
                    ''',
                        returnStdout: true
                ).trim()

                def parts = counts.split(',')
                def TOTAL_TESTS   = parts[0]
                def PASSED_TESTS  = parts[1]
                def FAILED_TESTS  = parts[2]
                def SKIPPED_TESTS = parts[3]
                def IGNORED_TESTS = parts[4]

                def isSuccess = currentBuild.result == 'SUCCESS'
                def failedScreenshots = isSuccess ? "" : "* 📸 Screenshots: <${env.BUILD_URL}artifact/src/main/screenshots|Click here>\n"

                def jobStatusOverall = isSuccess ? '✅  PASSED JOB ✅' : '❌ FAILED JOB ❌'
                def platformTestedOn = params.mobile ? '📱 Mobile' : '🖥️ Desktop'
                def slackMessage = """
************************************************************
                        ${jobStatusOverall}
************************************************************
* 💼 Job: ${env.JOB_NAME}
* 🔨 Build #: ${env.BUILD_NUMBER}
* 🔨 Build Link: <${env.BUILD_URL}|Click here>
************************************************************
                        📊 Total Tests = ${TOTAL_TESTS}
************************************************************
* ✅ PASSED: ${PASSED_TESTS}
* ❌ FAILED: ${FAILED_TESTS}
* ⏩ SKIPPED: ${SKIPPED_TESTS}
* ⏩ IGNORED: ${IGNORED_TESTS}
************************************************************
* 🌐 Browser: ${browser}
* ⚙️ Platform: ${platformTestedOn}
${failedScreenshots}
* 📸 Test video recording is attached
* 📋 Test Report: <${env.BUILD_URL}artifact/target/surefire-reports/index.html|Click here>
""";

                // Choose color based on build result
                def color = isSuccess ? 'good' : 'danger'

                def testVideoRecordingPath = "recordings/test.mp4"

                // Send Slack message
                slackUploadFile(
                        channel: "${env.SLACK_CHANNEL_ID}",
                        initialComment: slackMessage,
                        filePath: testVideoRecordingPath
                )
            }
        }
    }
}