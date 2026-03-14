pipeline {
    agent any

    environment {
        SLACK_CHANNEL = '#automation-jobs'
        SLACK_CHANNEL_ID = 'C05R1CXD2Q4'
        MOBILE_VIEW_RESOLUTION = '500x900'
        DESKTOP_VIEW_RESOLUTION = '1920x1080'
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
                    // Read the .env file as UTF-8
                    def lines = readFile(file: '/var/Env/.env', encoding: 'UTF-8').readLines()
                    lines.each { line ->
                        line = line.trim()
                        if (!line || line.startsWith("#")) return  // skip empty lines and comments
                        def (key, value) = line.split("=", 2)
                        env."$key" = value
                    }
                }
            }
        }

        stage('Set DISPLAY_NUM') {
            steps {
                script {
                    // Combine BUILD_ID and JOB_NAME
                    def combined = "${env.BUILD_ID}${env.JOB_NAME}"

                    // Sum bytes as unsigned
                    def hash = combined.bytes.collect { it & 0xFF }.sum()   // & 0xFF converts byte to 0..255

                    // Generate DISPLAY_NUM in a safe range
                    env.DISPLAY_NUM = (100 + (hash % 200)).toString()

                    echo "Generated DISPLAY_NUM: ${env.DISPLAY_NUM}"
                }
            }
        }

        stage('Start Tests & Video Recording') {
            steps {
                script {
                    def resolution = (params.mobileMode) ? env.MOBILE_VIEW_RESOLUTION : env.DESKTOP_VIEW_RESOLUTION
                    def resolution_with_color_value = resolution + "x24"
                    sh """
                    #!/bin/bash
                    set +e
                    
                    Xvfb :$DISPLAY_NUM -screen 0 $resolution_with_color_value -ac &
                    export DISPLAY=:$DISPLAY_NUM
                    
                    mkdir -p \$WORKSPACE/recordings
                    chmod 777 \$WORKSPACE/recordings
                    
                    ffmpeg -y -probesize 100M -analyzeduration 100M -f x11grab -video_size $resolution -i \$DISPLAY \
                           -r 30 -codec:v libx264 -preset ultrafast \
                           \$WORKSPACE/recordings/test.mp4 &
                    FFMPEG_PID=\$!
                    
                    cleanup() {
                        echo ">>> CLEANUP RUNNING"
                        if [[ -n "\$FFMPEG_PID" ]]; then
                            kill -2 "\$FFMPEG_PID" 2>/dev/null || true
                            wait "\$FFMPEG_PID" 2>/dev/null || true
                        fi
                        pkill Xvfb 2>/dev/null || true
                    }
                    
                    trap cleanup EXIT
                    
                    mvn clean test -DsuiteXmlFile=suites/MainTestSuite.xml \
                   -Dbrowser=\${browser} -DheadlessMode=\${headlessMode} -DmobileMode=\${mobileMode}
                                   
                    TEST_EXIT_CODE=\$?
                    echo "Maven exited with \$TEST_EXIT_CODE"
                    exit \$TEST_EXIT_CODE
                    """
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'recordings/*.mp4', allowEmptyArchive: true

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

                def isSuccess = (currentBuild.result == null || currentBuild.result == 'SUCCESS')
                def failedScreenshots = isSuccess ? "" : "* 📸 Screenshots: <${env.BUILD_URL}artifact/src/main/screenshots|Click here>\n"

                def jobStatusOverall = isSuccess ? '✅  PASSED JOB ✅' : '❌ FAILED JOB ❌'
                def platformTestedOn = params.mobileMode ? '📱 Mobile' : '🖥️ Desktop'
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
* 📋 Test Report: <${env.BUILD_URL}artifact/target/surefire-reports/index.html|Click here>
* ⬇️⬇️ Test video recording can be found below ⬇️⬇️
"""

                def testVideoRecordingPath = "recordings/test.mp4"

                def resultColor = isSuccess ? "good" : "danger"

                // Send Slack message
                slackSend(
                        channel:"${env.SLACK_CHANNEL}",
                        color: resultColor,
                        message: slackMessage
                )

                // Send Slack test video file
                slackUploadFile(
                        channel: "${env.SLACK_CHANNEL_ID}",
                        filePath: testVideoRecordingPath
                )
            }
        }
    }
}