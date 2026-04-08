import groovy.json.JsonSlurper

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
                    if(!params.headlessMode) {
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
        }

        stage('Start Tests & Video Recording') {
            steps {
                script {
                    if(!params.headlessMode) {
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
                    else {
                        sh """
                        mvn clean test -DsuiteXmlFile=suites/MainTestSuite.xml \
                        -Dbrowser=\${browser} -DheadlessMode=\${headlessMode} -DmobileMode=\${mobileMode}
                        """
                    }
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'recordings/test.mp4, target/cucumber-report.html, target/cucumber-report.json, src/main/screenshots/*.png', allowEmptyArchive: true
            script {

                def cucumberJsonReport = readFile('target/cucumber-report.json')
                def jsonRaw = new JsonSlurper().parseText(cucumberJsonReport)
                def scenarios = jsonRaw.collectMany { feature ->
                    feature.elements.findAll { it.type == 'scenario' }
                }
                def TOTAL_TESTS = scenarios.size()
                def PASSED_TESTS = scenarios.count { s -> s.steps.every { it.result.status == 'passed' } }
                def FAILED_TESTS = scenarios.count { s -> s.steps.any { it.result.status == 'failed' } }
                def SKIPPED_TESTS = scenarios.count { s -> s.steps.any { it.result.status == 'skipped' } }
                def IGNORED_TESTS = scenarios.count { s -> s.steps.any { it.result.status == 'ignored' } }

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
* ⬇️⬇️ 📹 Test Video Recording  & 📋 Test Report can be found below ⬇️⬇️
"""
                cucumberJsonReport = null
                jsonRaw = null
                scenarios = null
                def testVideoRecordingPath = "recordings/test.mp4"
                def testReportPath = "target/cucumber-report.html"
                def resultColor = isSuccess ? "good" : "danger"

                // Send Slack message
                slackSend(
                        channel:"${env.SLACK_CHANNEL}",
                        color: resultColor,
                        message: slackMessage
                )

                def uploadFiles = params.headlessMode ? "${testReportPath}" : "${testVideoRecordingPath}, ${testReportPath}"

                // Send Slack test video file
                slackUploadFile(
                        channel: "${env.SLACK_CHANNEL_ID}",
                        filePath: uploadFiles
                )

            }
        }
    }
}