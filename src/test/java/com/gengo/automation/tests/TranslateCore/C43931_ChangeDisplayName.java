package com.gengo.automation.tests.TranslateCore;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * @case Change the account's display name.
 * @reference https://gengo.testrail.com/index.php?/cases/view/43931
 */
public class C43931_ChangeDisplayName extends AutomationBase {

    public C43931_ChangeDisplayName() throws IOException {}

    @BeforeClass
    public void initFields() throws IOException{
        this.newUser = csv.generateNewUser(var.SHEETNAME_CUSTOMERS, 0);
        globalMethods.createNewCustomer(this.newUser);
    }

    @AfterClass
    public void afterRun() throws IOException {
        csv.writeValue(this.newUser, var.SHEETNAME_CUSTOMERS, 0);
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test
    public void updateDisplayName() {
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

        // Click the change display name button.
        accountSettingsPage.clickChangeDisplayNameBtn();

        // Verify that update button is visible.
        assertTrue(accountSettingsPage.getDisplayNameUpdateBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Click the update button without changing anything.
        accountSettingsPage.clickUpdateDisplayNameBtn();

        // Verify that the update is still successful.
        assertTrue(accountSettingsPage.getDisplayNameSuccessMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Click the change display name button.
        accountSettingsPage.clickChangeDisplayNameBtn();

        // Verify that update button is visible.
        assertTrue(accountSettingsPage.getDisplayNameUpdateBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Change the display name.
        accountSettingsPage.changeDisplayName(var.getFullName());

        // Verify that the update is successful.
        assertTrue(accountSettingsPage.getDisplayNameSuccessMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Click the change display name button.
        accountSettingsPage.clickChangeDisplayNameBtn();

        // Verify that update button is visible.
        assertTrue(accountSettingsPage.getDisplayNameUpdateBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Clear the textbox.
        accountSettingsPage.clearDisplayNameTxtBox();

        // Click update even there's nothing in the textbox.
        accountSettingsPage.clickUpdateDisplayNameBtn();

        // Verify that error appears.
        assertTrue(accountSettingsPage.getErrorMessage().getText().contains(var.getUpdateDisplayNameErrMsg()),
                var.getTextNotEqualErrMsg());

        // Click the change display name button.
        accountSettingsPage.clickChangeDisplayNameBtn();

        // Set a random string display name.
        accountSettingsPage.changeDisplayName(global.randomString(10));

        // Verify that the update is successful.
        assertTrue(accountSettingsPage.getDisplayNameSuccessMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }
}
