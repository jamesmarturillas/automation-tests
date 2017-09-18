package com.gengo.automation.helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.util.Iterator;
import java.util.Set;

/**
 * @class Driver switcher from DOM content to content.
 */
public class Switcher {

    private WebDriver driver;

    public Switcher(WebDriver driver) {
        this.driver = driver;
    }

    public void switchToFrame(String frameModifier) {
        driver.switchTo().frame(frameModifier);
    }

    public void switchToFrame(WebElement element) {
        driver.switchTo().frame(element);
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public void switchToAlertAndAccept() {
        if (isALertPresent()) {
            driver.switchTo().alert().accept();
        }
    }

    public void switchToUnexpectedAlertAndAccept() {
        try {
            Reporter.log("No unexpected alert was detected");
        } catch (UnhandledAlertException f) {
            try {
                Alert alert = driver.switchTo().alert();
                alert.accept();
            } catch (NoAlertPresentException e) {
                e.printStackTrace();
            }
        }
    }

    public void detectUnexpectedAlertAndAccept() {
        boolean click = true;
        WebDriverWait wait = new WebDriverWait(driver, 20);
        while(click) {
            try {
                wait.until(ExpectedConditions.alertIsPresent()).accept();
            }
            catch (UnhandledAlertException f) {
                    driver.switchTo().alert().accept();
            }
            catch (NoAlertPresentException e) {
                    System.out.println("ydyb");
                    click = false;
                }
            catch (TimeoutException g) {
                click = false;
            }
        }
    }

    public void clickAlert(String toDo) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        if (toDo.equalsIgnoreCase("accept")) {
            wait.until(ExpectedConditions.alertIsPresent()).accept();
        }
        else if (toDo.equalsIgnoreCase("dismiss")) {
            wait.until(ExpectedConditions.alertIsPresent()).dismiss();
        }
    }

    private boolean isALertPresent() {
        Alert alert = ExpectedConditions.alertIsPresent().apply(driver);
        return alert != null;
    }

    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    public int getWindowHandles() {
        return driver.getWindowHandles().size();
    }

    public void switchToPopUp() {
        String subWindowHandler = null;

        Set<String> handles = driver.getWindowHandles(); // Get all window handles
        Iterator<String> iterator = handles.iterator();
        while (iterator.hasNext()){
            subWindowHandler = iterator.next();
        }
        driver.switchTo().window(subWindowHandler); // Switch to popup window
    }

    public void switchToParentWindow(String parentWindow) {
        driver.switchTo().window(parentWindow);
    }
}
