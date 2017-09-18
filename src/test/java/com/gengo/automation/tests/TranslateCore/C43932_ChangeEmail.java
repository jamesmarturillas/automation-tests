package com.gengo.automation.tests.TranslateCore;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @case Change the account's email address.
 * @reference https://gengo.testrail.com/index.php?/cases/view/43932
 */
public class C43932_ChangeEmail extends AutomationBase {

    public C43932_ChangeEmail() throws IOException {}
    private String[] emails = new String[2];

    @AfterClass
    public void afterRun() throws IOException {
        csv.writeValue(this.newUser, var.SHEETNAME_CUSTOMERS, 0);
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test
    public void updateEmail() {
        this.newUser = csv.generateNewUser(var.SHEETNAME_CUSTOMERS, 0);
        globalMethods.createNewCustomer(this.newUser);

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

        // Login as customer.
        loginPage.loginAccount(this.newUser, var.getDefaultPassword());

        // Assert that page title is present in the DOM.
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Navigate through account settings page to interact with the account's preferences.
        global.goToAcctSettings();

        // Assert that the navigated page is correct.
        assertTrue(page.getCurrentUrl().contains(accountSettingsPage.ACCOUNT_SETTINGS_URL),
                var.getWrongUrlErrMsg());
    }

    @Test(dependsOnMethods = "updateEmail")
    public void updateWithEmptyFields() {
        // Click the change email button.
        accountSettingsPage.clickChangeEmailBtn();

        // Verify that the update button is there.
        assertTrue(accountSettingsPage.getUpdateEmailBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Click the update button without inputting anything in the form.
        accountSettingsPage.clickUpdateEmailBtn();

        // Verify that the return error message is related to blank fields.
        assertTrue(accountSettingsPage.getErrorMessage().getText().contains(var.getUpdateEmailBlankFieldsErrMsg()),
                var.getTextNotEqualErrMsg());
    }

    @Test(dependsOnMethods = "updateWithEmptyFields")
    public void updateWithUnmatchedEmail() {
        // Click the change email button.
        accountSettingsPage.clickChangeEmailBtn();

        // Verify that the update button is there.
        assertTrue(accountSettingsPage.getUpdateEmailBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        setEmails(this.newUser + "not", this.newUser + "match");

        // Fill out the email form with unmatched values.
        accountSettingsPage.fillEmailsField(getEmails());

        // Verify that the return error message is related to blank fields.
        assertTrue(accountSettingsPage.getErrorMessage().getText().contains(var.getUpdateEmailNotMatchErrMsg()),
                var.getTextNotEqualErrMsg());
    }

    @Test(dependsOnMethods = "updateWithUnmatchedEmail")
    public void updateWithMatchedEmail() {
        // Click the change email button.
        accountSettingsPage.clickChangeEmailBtn();

        // Verify that the update button is there.
        assertTrue(accountSettingsPage.getUpdateEmailBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        setEmails(this.newUser.replace("+c", "+cc"),
                this.newUser.replace("+c", "+cc"));

        // Fill out the email form with unmatched values.
        accountSettingsPage.fillEmailsField(getEmails());

        // Verify that the return message is successful.
        assertTrue(accountSettingsPage.getUpdateEmailRequestSuccessMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    @Test(dependsOnMethods = "updateWithMatchedEmail")
    public void checkEmailChangeRequest() {
        // Go to Gmail account
        page.launchUrl(var.getGmailUrl());

        assertTrue(gmailInboxPage.getGmailComposeBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(gmailInboxPage.getChangeEmail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Open the email.
        gmailInboxPage.clickChangeEmail();

        // Verify that the button is present in the page.
        assertTrue(gmailChangeEmailPage.getChangeEmailAddressBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Click the change email address button.
        gmailChangeEmailPage.clickChangeEmailBtn();

        assertTrue(accountSettingsPage.getUpdateEmailSuccessMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Go back to Gmail account
        page.launchUrl(var.getGmailUrl());

        // Open the email again.
        gmailInboxPage.clickChangeEmail();

        // Verify that the button is present in the page.
        assertTrue(gmailChangeEmailPage.getChangeEmailAddressBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Click the change email address button.
        gmailChangeEmailPage.clickChangeEmailBtn();

        // Assert that error message will return.
        assertTrue(accountSettingsPage.getErrorMessage().getText().contains(var.getUpdateEmailFailedErrMsg()),
                var.getTextNotEqualErrMsg());
    }

    @Test(dependsOnMethods = "checkEmailChangeRequest")
    public void changeEmailAgain() {
        // Click the change email button.
        accountSettingsPage.clickChangeEmailBtn();

        // Verify that the update button is there.
        assertTrue(accountSettingsPage.getUpdateEmailBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        setEmails(this.newUser.replace("+cc", "+c"),
                this.newUser.replace("+cc", "+c"));

        // Fill out the email form with unmatched values.
        accountSettingsPage.fillEmailsField(getEmails());

        // Verify that the return message is successful.
        assertTrue(accountSettingsPage.getUpdateEmailRequestSuccessMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Go to Gmail account
        page.launchUrl(var.getGmailUrl());

        // Open the email.
        gmailInboxPage.clickChangeEmail();

        // Verify that the button is present in the page.
        assertTrue(gmailChangeEmailPage.getChangeEmailAddressBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Click the change email address button.
        gmailChangeEmailPage.clickChangeEmailBtn();

        // Assert that update success message appears.
        assertTrue(accountSettingsPage.getUpdateEmailSuccessMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    @Test(dependsOnMethods = "changeEmailAgain")
    public void signInNewEmail() {
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

        // Login as customer.
        loginPage.loginAccount(this.newUser, var.getDefaultPassword());

        // Assert that you have signed in successfully.
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());

        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    @Test(dependsOnMethods = "signInNewEmail")
    public void signInOldEmail() {
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

        // Login as customer.
        loginPage.loginAccount(this.newUser.replace("+c", "+cc"), var.getDefaultPassword());

        // Assert that you have sign in failure.
        assertFalse(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());
    }

    private void setEmails(String newEmail, String newEmailAgain) {
        emails = new String[] {
                newEmail,
                newEmailAgain
        };
    }

    private String[] getEmails() {
        return emails;
    }
}
