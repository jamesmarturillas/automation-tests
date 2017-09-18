package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for Gmail inbox page.
 * Contains PageFactory initialization.
 */
public class GmailInboxPageFactory {

    protected WebDriver driver;
    public final String GMAIL_INBOX_URL = "https://mail.google.com/mail/#inbox";

    public GmailInboxPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//div[contains(text(), 'COMPOSE')]")
    WebElement gmailComposeBtn;

    @FindBy(xpath = "//span[contains(., 'Activate your Gengo account')]")
    WebElement activateYourGengoEmail;

    @FindBy(xpath = "//span[contains(., 'Welcome to Gengo')]")
    WebElement welcomeToGengoEmail;

    @FindBy(xpath = "//span[contains(., 'Your Gengo account is closed')]")
    WebElement acctClosedEmail;

    @FindBy(xpath = "//span[contains(., 'Change your Gengo email address')]")
    WebElement changeEmail;

    @FindBy(xpath = "//span[contains(., 'Gengo top-up complete')]")
    WebElement topUpCompleteEmail;

    /**
     * Getters
     */
    public WebElement getGmailComposeBtn() {
        return gmailComposeBtn;
    }

    public WebElement getActivateYourGengoEmail() {
        return activateYourGengoEmail;
    }

    public WebElement getWelcomeToGengoEmail() {
        return welcomeToGengoEmail;
    }

    public WebElement getAcctClosedEmail() {
        return acctClosedEmail;
    }

    public WebElement getChangeEmail() {
        return changeEmail;
    }

    public WebElement getTopUpCompleteEmail() {
        return topUpCompleteEmail;
    }
}
