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
 * @case Change the account's password.
 * @reference https://gengo.testrail.com/index.php?/cases/view/43933
 */
public class C43933_ChangePassword extends AutomationBase {

    public C43933_ChangePassword() throws IOException {}
    private String[] passwords = new String[3];

    @AfterClass
    public void afterRun() throws IOException {
        csv.writeValue(this.newUser, var.SHEETNAME_CUSTOMERS, 0);
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test
    public void updatePassword() {
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

        // Click the change password button.
        accountSettingsPage.clickChangePasswordBtn();

        // Click update button to submit the form without putting anything in it.
        accountSettingsPage.clickUpdatePasswordBtn();

        // Verify that the error message is correct as expected.
        assertTrue(accountSettingsPage.getErrorMessage().getText().contains(var.getUpdatePasswordBlankFieldsErrMsg()),
                var.getTextNotEqualErrMsg());
    }

    @Test(dependsOnMethods = "updatePassword")
    public void updateNewPasswordsNotMatched() {
        // Click the change password button.
        accountSettingsPage.clickChangePasswordBtn();

        setPasswords(var.getDefaultPassword(),
                var.getDefaultPassword() + "not",
                var.getDefaultPassword() + "match");

        // Input the unmatched passwords.
        accountSettingsPage.fillPasswordsFields(getPasswords());

        // Assert that the return error is correct as expected.
        assertTrue(accountSettingsPage.getErrorMessage().getText().contains(var.getUpdatePasswordNotMatchErrMsg()),
                var.getTextNotEqualErrMsg());
    }

    @Test(dependsOnMethods = "updateNewPasswordsNotMatched")
    public void updateWithIncorrectCurrentPassword() {
        // Click the change password button.
        accountSettingsPage.clickChangePasswordBtn();


        setPasswords(var.getDefaultPassword() + "notMatched",
                var.getDefaultPassword(),
                var.getDefaultPassword());

        // Input the unmatched passwords.
        accountSettingsPage.fillPasswordsFields(getPasswords());

        // Assert that the return error is correct as expected.
        assertTrue(accountSettingsPage.getErrorMessage().getText().contains(var.getUpdatePasswordIncorrectErrMsg()),
                var.getTextNotEqualErrMsg());
    }

    @Test(dependsOnMethods = "updateWithIncorrectCurrentPassword")
    public void updateWithCorrectPasswords() {
        // Click the change password button.
        accountSettingsPage.clickChangePasswordBtn();

        setPasswords(var.getDefaultPassword(),
                var.getAlternatePassword(),
                var.getAlternatePassword());

        // Input the unmatched passwords.
        accountSettingsPage.fillPasswordsFields(getPasswords());

        // Assert that the password change is successful.
        assertTrue(accountSettingsPage.getUpdatePasswordSuccessMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    @Test(dependsOnMethods = "updateWithCorrectPasswords")
    public void signOutAndSignInAgainUsingOldPassword() {
        // Sign out.
        global.nonAdminSignOut();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        page.launchUrl(baseURL);
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

        loginPage.loginAccount(this.newUser, var.getDefaultPassword()); // Login with the old password.

        // Assert that the login attempt is failed.
        assertTrue(loginPage.getLoginFailedErrMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    @Test(dependsOnMethods = "signOutAndSignInAgainUsingOldPassword")
    public void signOutAndSignInAgainUsingNewPassword() {
        // URL still remain the same.
        assertTrue(page.getCurrentUrl().equals(loginPage.LOGIN_PAGE_URL),
                var.getWrongUrlErrMsg());
        assertTrue(loginPage.getSignUpBtn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        loginPage.loginAccount(this.newUser, var.getAlternatePassword()); // Login with the new password.

        // Assert that the login attempt is success.
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());
    }

    @Test(dependsOnMethods = "signOutAndSignInAgainUsingNewPassword")
    public void setOneCharacterPassword() {
        // Navigate through account settings page to interact with the account's preferences.
        global.goToAcctSettings();

        // Click the change password button.
        accountSettingsPage.clickChangePasswordBtn();

        setPasswords(var.getAlternatePassword(),
                "a",
                "a");

        // Input the 1 character password.
        accountSettingsPage.fillPasswordsFields(getPasswords());

        // Assert that the return error is correct as expected.
        assertTrue(accountSettingsPage.getErrorMessage().getText().contains(var.getUpdatePasswordInsufficientLengthErrMsg()),
                var.getTextNotEqualErrMsg());
    }

    @Test(dependsOnMethods = "setOneCharacterPassword")
    public void restoreCorrectPassword() {
        // Click the change password button.
        accountSettingsPage.clickChangePasswordBtn();

        setPasswords(var.getAlternatePassword(),
                var.getDefaultPassword(),
                var.getDefaultPassword());

        // Input the unmatched passwords.
        accountSettingsPage.fillPasswordsFields(getPasswords());

        // Assert that the password change is successful.
        assertTrue(accountSettingsPage.getUpdatePasswordSuccessMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    @Test(dependsOnMethods = "restoreCorrectPassword")
    public void checkPasswordBySigningInAgain() {
        // Sign out.
        global.nonAdminSignOut();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        page.launchUrl(baseURL);
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

        loginPage.loginAccount(this.newUser, var.getDefaultPassword()); // Login with the restored password.

        // Assert that the login is successful.
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());
    }

    private void setPasswords(String currentPassword, String newPassword, String newPasswordAgain) {
        passwords = new String[] {
                currentPassword,
                newPassword,
                newPasswordAgain
        };
    }

    private String[] getPasswords() {
        return passwords;
    }
}
