package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for Gmail inbox activate gengo account page.
 * Contains PageFactory initialization.
 */
public class GmailActivateGengoPageFactory {

    protected WebDriver driver;

    public GmailActivateGengoPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//a[contains(text(), 'Activate Account')]")
    WebElement activateAccountBtn;

    /**
     * Getters
     */
    public WebElement getActivateAccountBtn() {
        return activateAccountBtn;
    }

}
