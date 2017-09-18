package com.gengo.automation.config;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScreenshotListener extends TestListenerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(ScreenshotListener.class);

    @Override
    public void onTestFailure(ITestResult tr) {
        String[] testClass = tr.getTestClass().getName().split("\\.");
        File screenshot = new File("target" + File.separator + "surefire-reports" + File.separator +
                "screenshots" + File.separator + System.currentTimeMillis() + "_" +
                testClass[testClass.length -1] + "_" + tr.getName() + ".png");
        if (!screenshot.exists()) {
            new File(screenshot.getParent()).mkdirs();
            try {
                screenshot.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            new FileOutputStream(screenshot).write(((TakesScreenshot) ((Invoker) tr.getInstance()).getDriver()).getScreenshotAs(OutputType.BYTES));
        } catch (IOException e) {
            LOG.error("Error writing image", e);
        }
        LOG.debug("Written screenshot to " + screenshot.getAbsolutePath());
    }
}
