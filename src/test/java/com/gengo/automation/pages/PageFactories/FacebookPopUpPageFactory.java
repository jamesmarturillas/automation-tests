package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Object repository for Facebook pop up page.
 * Contains PageFactory initialization.
 */
public class FacebookPopUpPageFactory {

    protected WebDriver driver;

    public FacebookPopUpPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//h2[@id='homelink']")
    WebElement facebookLogo;

    @FindBy(xpath = "//span[contains(.,'Gengo.com - Staging')]")
    WebElement facebookPageGengoText;

    @FindBy(xpath = "//input[@id='email']")
    WebElement txtBoxEmail;

    @FindBy(xpath = "//input[@id='pass']")
    WebElement txtBoxPassword;

    @FindBy(xpath = "//input[@name='login']")
    WebElement loginBtn;

    @FindBy(xpath = "//a[contains(text(), 'Create New Account')]")
    WebElement createAccountBtn;

    /**
     * Getters
     */
    public WebElement getFacebookLogo() {
        return facebookLogo;
    }

    public WebElement getFacebookPageGengoText() {
        return facebookPageGengoText;
    }

    public WebElement getTxtBoxEmail() {
        return txtBoxEmail;
    }

    public WebElement getTxtBoxPassword() {
        return txtBoxPassword;
    }

    public WebElement getLoginBtn() {
        return loginBtn;
    }

    public WebElement getCreateAccountBtn() {
        return createAccountBtn;
    }

}
