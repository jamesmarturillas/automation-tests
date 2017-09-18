package com.gengo.automation.tests.TranslateCore;

import com.gengo.automation.global.AutomationBase;
import com.gengo.automation.global.GlobalMethods;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @case Close an active account.
 * @reference https://gengo.testrail.com/index.php?/cases/view/30
 */
public class C30_CloseAccount extends AutomationBase {

    public C30_CloseAccount() throws IOException {}

    @BeforeClass
    public void initFields() throws IOException{
        this.newUser = csv.generateNewUser(var.SHEETNAME_CUSTOMERS, 0);
        globalMethods.createNewCustomer(newUser);
    }

    @AfterClass
    public void afterRun() throws IOException {
        csv.writeValue(this.newUser, var.SHEETNAME_CUSTOMERS, 0);
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test
    public void closeAccount() {
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

        // Assert that there is the account settings text criteria.
        assertTrue(accountSettingsPage.getAccountSettingsTextBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        accountSettingsPage.clickCloseAccountLink();

        // Assert that modal with checkbox has appeared.
        assertTrue(accountSettingsPage.getChkBoxConfirmClose().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        accountSettingsPage.clickCloseModalIcon();

        // Click the close account link again.
        accountSettingsPage.clickCloseAccountLink();

        // Assert that modal with checkbox has appeared.
        assertTrue(accountSettingsPage.getChkBoxConfirmClose().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        accountSettingsPage.clickModalCancelBtn();

        assertFalse(accountSettingsPage.getCloseModalIcon().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        accountSettingsPage.clickCloseAccountLink();

        // Assert that modal with checkbox has appeared.
        assertTrue(accountSettingsPage.getChkBoxConfirmClose().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Click the close account link again.
        accountSettingsPage.closeAccountPermanently();

        // Assert that after clicking the close account button, it will redirect to homepage.
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }
}
