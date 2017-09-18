package com.gengo.automation.examples.PageObject;

import com.gengo.automation.examples.PageFactory.SamplePageFactory;
import org.openqa.selenium.WebDriver;

/**
 * @class Object repository for sample template of a PageObject class.
 * Contains element operations.
 */
public class SamplePageObject extends SamplePageFactory {

    public SamplePageObject(WebDriver driver) {
        super(driver);
    }

    public void clickYourXpath() {
        getYourXpath().click();
    }
}
