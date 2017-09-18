package com.gengo.automation.pages;

import com.gengo.automation.fields.Variables;
import com.gengo.automation.helpers.PageActions;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.LoginPageFactory;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * @class Object repository for login page.
 * Contains element operations.
 */
public class LoginPage extends LoginPageFactory {

    public LoginPage(WebDriver driver) throws IOException {
        super(driver);
    }
    private Wait wait = new Wait(driver);
    private TranslatorOnboardPage translatorOnboardPage = new TranslatorOnboardPage(driver);
    private GlobalPage global = new GlobalPage(driver);
    private PageActions page = new PageActions(driver);
    private Variables var = new Variables();
    private GmailSignInEmailPage gmailSignInEmailPage = new GmailSignInEmailPage(driver);
    private GmailSignInPasswordPage gmailSignInPasswordPage = new GmailSignInPasswordPage(driver);
    private GmailInboxPage gmailInboxPage = new GmailInboxPage(driver);
    private GmailActivateGengoPage gmailActivateGengoPage = new GmailActivateGengoPage(driver);
    private CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver);

    public void loginAccount(String username, String password) {
        getTxtBoxUsername().clear();
        getTxtBoxLoginPassword().clear();
        getTxtBoxUsername().sendKeys(username);
        getTxtBoxLoginPassword().sendKeys(password);
        getSubmitBtn().click();
        wait.impWait(30);
        if (isAcctNotActivated()) this.activateEmail();
    }

    public void loginAccount(String username, String password, boolean isTranslator){
        getTxtBoxUsername().clear();
        getTxtBoxLoginPassword().clear();
        getTxtBoxUsername().sendKeys(username);
        getTxtBoxLoginPassword().sendKeys(password);
        getSubmitBtn().click();
        wait.impWait(30);

        if (isAcctNotActivated()) this.activateEmail();

        if (isTranslator) {
            translatorOnboardPage.onboardTranslator();
        }
        else {
            global.getGengoLogoInOrderForm().click();
            wait.impWait(30);
            page.launchUrl(customerDashboardPage.CUSTOMER_DASHBOARD_URL);
        }
    }

    public boolean isAcctNotActivated() {
        try{
            return getLoginNonActivatedAcctErrMsg().isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickFbLoginLink() {
        getFbLoginLink().click();
        wait.impWait(10);
    }

    public void clickGoogleLoginLink() {
        getGoogleLoginLink().click();
        wait.impWait(10);
    }

    public void clickSignUpButton() {
        getSignUpBtn().click();
        wait.impWait(10);
    }

    public void activateEmail() {
        page.launchUrl(var.getGmailUrl());
        assertTrue(gmailSignInEmailPage.getTxtBoxEmail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        gmailSignInEmailPage.inputEmail(var.getGmailEmail());

        gmailSignInPasswordPage.inputPasswordAndSubmit(var.getGmailPassword());

        assertTrue(gmailInboxPage.getGmailComposeBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(gmailInboxPage.getActivateYourGengoEmail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        gmailInboxPage.clickActivateGengoEmail();

        assertTrue(gmailActivateGengoPage.getActivateAccountBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Activate the account
        gmailActivateGengoPage.clickActivateAccount();
    }
}
