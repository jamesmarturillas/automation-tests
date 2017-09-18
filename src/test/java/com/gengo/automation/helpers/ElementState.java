package com.gengo.automation.helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @class Validates the element state if ready or not,
 * otherwise do required action.
 */
public class ElementState {

    private WebDriver driver;

    public ElementState (WebDriver driver) {
        this.driver = driver;
    }

    public boolean isElementPresent(List<WebElement> element) {
        return element.size() > 0;
    }

    public boolean isElementPresentInSource(String toCheck) {
        String src = driver.getPageSource();
        return src.contains(toCheck);
    }

    public boolean isElementPresentInSource(String toCheck, boolean isCriteriaMoreThanZero) {
        String src = driver.getPageSource();
        Pattern pattern = Pattern.compile(toCheck);
        Matcher matcher = pattern.matcher(src);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count > 1;
    }
    public void assureElementNotDisplayed(WebElement element) {
        WebElement thisElement = element;
        while (thisElement.getAttribute("style").contains("block")) {
            if (thisElement.getAttribute("style").contains("none")) {
                break;
            }
            thisElement = element;
        }
    }
}
