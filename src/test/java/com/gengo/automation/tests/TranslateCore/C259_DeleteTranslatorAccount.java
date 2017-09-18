package com.gengo.automation.tests.TranslateCore;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * @case Deleting a translator account.
 * @reference https://gengo.testrail.com/index.php?/cases/view/259
 */
public class C259_DeleteTranslatorAccount extends AutomationBase {

    public C259_DeleteTranslatorAccount() throws IOException {}

    @AfterClass
    public void afterRun() throws IOException {
        csv.writeValue(this.newUser, var.SHEETNAME_TRANSLATORS, 0);
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test
    public void deleteAccount() {
        this.newUser = csv.generateNewUser(var.SHEETNAME_TRANSLATORS, 0);
        globalMethods.createNewTranslator(this.newUser);

        pluginPage.passThisPage();

        // Assert that homepage 'SIGN IN' link is displayed.
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        homePage.clickSignInButton();

        // Check that after clicking the 'SIGN IN' link, the URL is right and 'SIGN UP' button is displayed as well.
        assertTrue(page.getCurrentUrl().equals(loginPage.LOGIN_PAGE_URL),
                var.getWrongUrlErrMsg());
        assertTrue(loginPage.getSignUpBtn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Login as translator
        loginPage.loginAccount(this.newUser, var.getDefaultPassword());

        // Assert that page title is present in the DOM.
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Navigate to account settings.
        global.goToAcctSettings();

        // Assert that there is the account settings text criteria.
        assertTrue(accountSettingsPage.getAccountSettingsTextBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        accountSettingsPage.clickCloseAccountLink();

        // Assert that modal with checkbox has appeared.
        assertTrue(accountSettingsPage.getChkBoxConfirmClose().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        accountSettingsPage.closeAccountPermanently();

        // Assert that after clicking the close account button, it will redirect to homepage.
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    @Test(dependsOnMethods = "deleteAccount")
    public void checkEmail() {
        // Go to GMAIL to check the closed account email.
        page.launchUrl(var.getGmailUrl());

        // Assert that text box for email is displayed.
        assertTrue(gmailSignInEmailPage.getTxtBoxEmail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Check the 'closed account' email if received.
        gmailSignInEmailPage.inputEmail(var.getGmailEmail());
        gmailSignInPasswordPage.inputPasswordAndSubmit(var.getGmailPassword());
        gmailInboxPage.clickClosedAccountEmail();

        // Assert that the text criteria for closed account is displayed.
        assertTrue(gmailClosedAccountPage.getClosedAccountText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    @Test(dependsOnMethods = "checkEmail")
    public void checkAccountIfStillCanLogin() {
        page.launchUrl(var.getBaseUrl());

        pluginPage.passThisPage();

        // Assert that homepage 'SIGN IN' link is displayed.
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        homePage.clickSignInButton();

        // Check that after clicking the 'SIGN IN' link, the URL is right and 'SIGN UP' button is displayed as well.
        assertTrue(page.getCurrentUrl().equals(loginPage.LOGIN_PAGE_URL),
                var.getWrongUrlErrMsg());
        assertTrue(loginPage.getSignUpBtn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Login as closed translator account.
        loginPage.loginAccount(this.newUser, var.getDefaultPassword());

        // Assert that the account cannot login anymore.
        assertTrue(loginPage.getLoginFailedErrMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }
}
