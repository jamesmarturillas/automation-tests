package com.gengo.automation.helpers;

import com.gengo.automation.config.Invoker;
import org.openqa.selenium.WebDriver;

/**
 * @class Actions from Selenium API are shortened for explicit perform to the current page.
 */
public class PageActions extends Invoker {

    private WebDriver driver;
    public PageActions(WebDriver driver) {
        this.driver = driver;
    }

    public void refresh() {
        driver.navigate().refresh();
        wait.impWait(30);
    }

    public void goBack() {
        driver.navigate().back();
        wait.impWait(10);
    }

    public void goForward() {
        driver.navigate().forward();
        wait.impWait(10);
    }

    public void launchUrl(String url) {
        driver.get(url);
        wait.impWait(10);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isTitle(String url) {
        return driver.getTitle().equals(url);
    }

    public void maximizeWindow() {
        driver.manage().window().maximize();
    }
}
