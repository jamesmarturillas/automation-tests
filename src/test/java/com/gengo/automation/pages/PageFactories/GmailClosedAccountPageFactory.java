package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for Gmail inbox close gengo account page.
 * Contains PageFactory initialization.
 */
public class GmailClosedAccountPageFactory {

    protected WebDriver driver;

    public GmailClosedAccountPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//h1[contains(text(), 'Your account is closed')]")
    WebElement closedAccountText;

    /**
     * Getters
     */
    public WebElement getClosedAccountText() {
        return closedAccountText;
    }

}
