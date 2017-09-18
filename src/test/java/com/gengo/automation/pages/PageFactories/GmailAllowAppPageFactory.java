package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for Gmail allowing app access page.
 * Contains PageFactory initialization.
 */
public class GmailAllowAppPageFactory {

    protected WebDriver driver;

    public GmailAllowAppPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(id = "submit_approve_access")
    WebElement allowAccess;

    @FindBy(id = "submit_deny_access")
    WebElement denyAccess;

    @FindBy(xpath = "//*[contains(.,'Loading please wait')]")
    WebElement justLoading;

    /**
     * Getters
     */
    public WebElement getAllowAccess() {
        return allowAccess;
    }

    public WebElement getDenyAccess() {
        return denyAccess;
    }

    public WebElement getJustLoading() {
        return justLoading;
    }
}
