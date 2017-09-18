package com.gengo.automation.helpers;

import org.openqa.selenium.WebDriver;

/**
 * @class Stores all the firm and customized
 * helpers that will check whether the element is present or not
 */
public class CheckElementPresence {

    private WebDriver driver;

    public CheckElementPresence(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isElementPresentBySource(WebDriver driver, String toCheck) {
        String src = driver.getPageSource();
        return src.contains(toCheck);
    }
}
