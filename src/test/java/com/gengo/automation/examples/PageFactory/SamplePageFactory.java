package com.gengo.automation.examples.PageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Sample template for PageFactory class.
 * Contains PageFactory initialization.
 */
public class SamplePageFactory {

    protected WebDriver driver;

    public SamplePageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//your/xpath")
    WebElement yourXpath;

    /**
     * Getters
     */
    public WebElement getYourXpath() {
        return yourXpath;
    }
}
