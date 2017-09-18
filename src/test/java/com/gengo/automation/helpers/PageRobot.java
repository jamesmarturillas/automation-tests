package com.gengo.automation.helpers;

import org.openqa.selenium.WebDriver;

import java.awt.*;

public class PageRobot {

    private WebDriver driver;
    private Robot robot;

    public PageRobot(WebDriver driver) throws AWTException {
        this.driver = driver;
        robot = new Robot();
    }

    public void pressKeys(int[] keyCode) throws AWTException {
        for(int i = 0; i < keyCode.length; i++){
            robot.keyPress(keyCode[i]);
        }
        for(int i = 0; i < keyCode.length; i++){
            robot.keyRelease(keyCode[i]);
        }
    }
}
