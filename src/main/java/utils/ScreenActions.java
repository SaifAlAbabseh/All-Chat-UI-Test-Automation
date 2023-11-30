package utils;

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

public class ScreenActions {

    private static ATUTestRecorder recorder;

    public static void takeScreenshot(String browserName, String whichPlatform, String testCaseName) {
        File file = ((TakesScreenshot)Driver.getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            System.out.println("SaifSaifSaifSaifSaif");
            FileUtils.copyFile(file, new File("src/main/screenshots/" + browserName + "-" + whichPlatform + "-" + testCaseName + ".png"));
            System.out.println("SaifSaifSaifSaifSaif");
        } catch (IOException e) {
            System.out.println("failure");
            e.printStackTrace();
        }
    }

    public static void startVideoRecording(String fileName, boolean includeAudio) {
        String videoPath = "src/main/videos";
        try {
            recorder = new ATUTestRecorder(videoPath, fileName, includeAudio);
            recorder.start();
        } catch (ATUTestRecorderException e) {
            e.printStackTrace();
        }
    }

    public static void stopVideoRecording() {
        try {
            recorder.stop();
        } catch (ATUTestRecorderException e) {
            e.printStackTrace();
        }
    }
}
