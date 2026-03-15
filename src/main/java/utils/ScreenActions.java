package utils;


import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;

public class ScreenActions {

    public static ATUTestRecorder recorder;

    public static void takeScreenshot(String browserName, String whichPlatform, String testCaseName) {
        File file = ((TakesScreenshot)Driver.getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            String fileName = String.format("src/main/screenshots/%s-%s-%s.png", browserName, whichPlatform, testCaseName);
            FileUtils.copyFile(file, new File(fileName));
        } catch (IOException e) {
            System.err.println("Could not save screenshot, ERROR: " + e.getMessage());
        }
    }

    public static void startVideoRecording(boolean includeAudio) {
        String videoPath = "src/main/videos";
        String fileName = "test";
        try {
            recorder = new ATUTestRecorder(videoPath, fileName, includeAudio);
            recorder.start();
        } catch (ATUTestRecorderException e) {
            System.err.println("Could not start video recording, ERROR: " + e.getMessage());
        }
    }

    public static void stopVideoRecording() {
        try {
            recorder.stop();
        } catch (ATUTestRecorderException e) {
            System.err.println("Could not stop video recording, ERROR: " + e.getMessage());
        }
    }
}
