package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for login page.
 * Contains PageFactory initialization.
 */
public class LoginPageFactory {

    protected WebDriver driver;
    public final String LOGIN_PAGE_URL = "https://staging.gengo.com/auth/form/login/";

    public LoginPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(name = "login_email")
    WebElement txtBoxUsername;

    @FindBy(xpath = "//input[@class='real-password login-password form-control']")
    WebElement txtBoxLoginPassword;

    @FindBy(xpath = "//button[@class='btn btn-primary btn-lg btn-block']")
    WebElement submitBtn;

    @FindBy(xpath = "//a[@href='https://staging.gengo.com/auth/form/signup/']")
    WebElement signUpBtn;

    @FindBy(id = "facebook_login")
    WebElement fbLoginLink;

    @FindBy(id = "google_login")
    WebElement googleLoginLink;

    @FindBy(xpath = "//div[@class='alert flashdata red-background alert-warning'][contains(.,'Please check email and password and try again.')]")
    WebElement loginFailedErrMsg;

    @FindBy(xpath = "//div[@class='alert flashdata red-background alert-warning'][contains(.,'Have you activated your account?')]")
    WebElement loginNonActivatedAcctErrMsg;

    /**
     * Getters
     */
    public WebElement getTxtBoxUsername() {
        return txtBoxUsername;
    }

    public WebElement getTxtBoxLoginPassword() {
        return txtBoxLoginPassword;
    }

    public WebElement getSubmitBtn() {
        return submitBtn;
    }

    public WebElement getSignUpBtn() {
        return signUpBtn;
    }

    public WebElement getFbLoginLink() {
        return fbLoginLink;
    }

    public WebElement getGoogleLoginLink() {
        return googleLoginLink;
    }

    public WebElement getLoginFailedErrMsg() {
        return loginFailedErrMsg;
    }

    public WebElement getLoginNonActivatedAcctErrMsg() {
        return loginNonActivatedAcctErrMsg;
    }
}
