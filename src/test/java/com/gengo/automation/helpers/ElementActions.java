package com.gengo.automation.helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * @class Actions class methods and operations storage.
 */
public class ElementActions {

    private WebDriver driver;
    private Actions actions;

    public ElementActions(WebDriver driver) {
        this.driver = driver;
        actions = new Actions(this.driver);
    }

    public void moveToElement(WebElement element) {
        actions.moveToElement(element).build().perform();
    }

    public void pressKeys(CharSequence[] keys, boolean[] isKey) {
        for(int i = 0; i < keys.length; i++){
            if(isKey[i])
                actions.keyDown(keys[i]);
            else
                actions.sendKeys(keys[i]);

        }
        actions.perform();
    }

    public void doubleClick(WebElement target) {
        actions.doubleClick(target).perform();
    }
}
