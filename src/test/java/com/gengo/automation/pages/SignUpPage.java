package com.gengo.automation.pages;

import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.SignUpPageFactory;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @class Object repository for sign up page.
 * Contains element operations.
 */
public class SignUpPage extends SignUpPageFactory {

    public SignUpPage(WebDriver driver) {
        super(driver);
    }
    private Wait wait = new Wait(driver);
    private DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
    private Date date = new Date();
    
    public void clickDropDownButton() {
        getParentDropDown().click();
        wait.impWait(10);
    }

    public void clickTranslatorOption() {
        getOptionTranslator().click();
        wait.impWait(10);
    }

    public void clickCustomerOption() {
        getOptionCustomer().click();
        wait.impWait(10);
    }

    public void tickCheckBoxAgree() {
        getCheckboxAgree().click();
        wait.impWait(10);
    }

    public void clickCreateAccountButton() {
        getCreateAccountBtn().click();
        wait.impWait(10);
    }

    public void fillOutSignUpForm(String email, String password) {
        getTxtBoxEmail().sendKeys(email);
        getTxtBoxRegistrationPassword().sendKeys(password);
        getCreateAccountBtn().click();
        wait.impWait(30);
    }

    public boolean hasTakenError() {
        try {
            return getFailedToSignUpAccountTakenMsg().isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickFbLink() {
        getFacebookLink().click();
        wait.impWait(5);
    }

    public void clickGoogleLink() {
        getGoogleLink().click();
        wait.impWait(5);
    }

    public String newCustomer() {
        return "gengo.automationtest+c" + dateFormat.format(date) + "@gmail.com";
    }

    public String newTranslator() {
        return "gengo.automationtest+t" + dateFormat.format(date) + "@gmail.com";
    }

    public String termsAndConditionsLink(){
        return getTermsAndConditionsLink().getAttribute("href");
    }

    public String privacyPolicyLink(){
        return getPrivacyPolicyLink().getAttribute("href");
    }
}
