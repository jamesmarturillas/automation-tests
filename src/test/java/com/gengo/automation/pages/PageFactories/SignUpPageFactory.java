package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for sign up page.
 * Contains PageFactory initialization.
 */
public class SignUpPageFactory {

    protected WebDriver driver;
    public final String SIGNUP_PAGE_URL = "https://staging.gengo.com/auth/form/signup/",
    TERMS_AND_CONDITIONS_URL = "https://staging.gengo.com/legal",
    PRIVACY_POLICY_URL = "https://staging.gengo.com/legal/privacy-policy";


    public SignUpPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//div[@class='btn-group btn-block']") // Parent element for user option dropdown
    WebElement parentDropDown;

    @FindBy(xpath = "//ul[@class='dropdown-menu']/li/a/p[contains(text(), 'Customer')]")
    WebElement optionCustomer;

    @FindBy(xpath = "//ul[@class='dropdown-menu']/li/a/p[contains(text(), 'Translator')]")
    WebElement optionTranslator;

    @FindBy(id ="agreed")
    WebElement checkboxAgree;

    @FindBy(id = "register-email")
    WebElement txtBoxEmail;

    @FindBy(xpath = "//input[@name='signup_password' and @class='real-password login-password form-control']")
    WebElement txtBoxRegistrationPassword;

    @FindBy(xpath = "//button[contains(@class, 'agree-required btn btn-primary btn-lg btn-block')]")
    WebElement createAccountBtn;

    @FindBy(id = "facebook_signup")
    WebElement facebookLink;

    @FindBy(id = "google_signup")
    WebElement googleLink;

    @FindBy(xpath = "//a[@href='https://staging.gengo.com/auth/form/login/']")
    WebElement signInLink;

    @FindBy(xpath = "//div[@class='alert flashdata red-background alert-warning'][contains(.,'Both email and password are required.')]")
    WebElement failedToSignUpEmptyFieldsMsg;

    @FindBy(xpath = "//div[@class='alert flashdata red-background alert-warning'][contains(.,'This email address is already taken')]")
    WebElement failedToSignUpAccountTakenMsg;

    @FindBy(xpath = "//div[@class='alert flashdata red-background alert-warning'][contains(.,'This account has already been deleted once before')]")
    WebElement failedToSignUpAccountDeletedMsg;

    @FindBy(xpath = "//a[contains(.,'Terms & Conditions ')]")
    WebElement termsAndConditionsLink;

    @FindBy(xpath = "//a[contains(.,'Privacy Policy')]")
    WebElement privacyPolicyLink;

    /**
     * Getters
     */
    public WebElement getParentDropDown() {
        return parentDropDown;
    }

    public WebElement getOptionCustomer() {
        return optionCustomer;
    }

    public WebElement getOptionTranslator() {
        return optionTranslator;
    }

    public WebElement getCheckboxAgree() {
        return checkboxAgree;
    }

    public WebElement getTxtBoxEmail() {
        return txtBoxEmail;
    }

    public WebElement getTxtBoxRegistrationPassword() {
        return txtBoxRegistrationPassword;
    }

    public WebElement getCreateAccountBtn() {
        return createAccountBtn;
    }

    public WebElement getFacebookLink() {
        return facebookLink;
    }

    public WebElement getGoogleLink() {
        return googleLink;
    }

    public WebElement getSignInLink() {
        return signInLink;
    }

    public WebElement getFailedToSignUpEmptyFieldsMsg() {
        return failedToSignUpEmptyFieldsMsg;
    }

    public WebElement getFailedToSignUpAccountTakenMsg() {
        return failedToSignUpAccountTakenMsg;
    }

    public WebElement getFailedToSignUpAccountDeletedMsg() {
        return failedToSignUpAccountDeletedMsg;
    }

    public WebElement getTermsAndConditionsLink() {
        return termsAndConditionsLink;
    }

    public WebElement getPrivacyPolicyLink() {
        return privacyPolicyLink;
    }
}
