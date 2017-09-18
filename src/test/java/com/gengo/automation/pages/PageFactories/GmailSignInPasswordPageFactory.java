package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for Gmail password inputting page.
 * Contains PageFactory initialization.
 */
public class GmailSignInPasswordPageFactory {

    protected WebDriver driver;

    public GmailSignInPasswordPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//span[@id='email-display']")
    WebElement userEmail;

    @FindBy(xpath = "//input[@id='Passwd' or @name='password']")
    WebElement txtBoxGmailPassword;

    @FindBy(xpath = "//*[@id='signIn'])")
    WebElement gmailSignInBtn;

    /**
     * Getters
     */
    public WebElement getUserEmail() {
        return userEmail;
    }

    public WebElement getTxtBoxGmailPassword() {
        return txtBoxGmailPassword;
    }

    public WebElement getGmailSignInBtn() {
        return gmailSignInBtn;
    }

}
