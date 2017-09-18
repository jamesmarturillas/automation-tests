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
 * @case Change the account's address details.
 * @reference https://gengo.testrail.com/index.php?/cases/view/43929
 */
public class C43929_ChangeAddressDetails extends AutomationBase {

    public C43929_ChangeAddressDetails() throws IOException {}

    @AfterClass
    public void afterRun() throws IOException {
        csv.writeValue(this.newUser, var.SHEETNAME_CUSTOMERS, 0);
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test
    public void updateAccountAddress() {
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

        // Click the change Address details button.
        accountSettingsPage.clickChangeAddresBtn();

        // Verify that the update button has appeared.
        assertTrue(accountSettingsPage.getAddressUpdateBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Clear all data in the form.
        accountSettingsPage.clearAllAddressFieldsForm();

        // Click update button without putting anything in the form.
        accountSettingsPage.clickUpdateAddressDetailsBtn();

        // Verify that the update is failed.
        assertTrue(accountSettingsPage.getErrorMessage().getText().contains(var.getUpdateAddressErrMsg()),
                var.getTextNotEqualErrMsg());

        // Click the change Address details button.
        accountSettingsPage.clickChangeAddresBtn();

        // Verify that the update button has appeared.
        assertTrue(accountSettingsPage.getAddressUpdateBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        accountSettingsPage.fillOutAddressForm();

        // Click update button.
        accountSettingsPage.clickUpdateAddressDetailsBtn();

        assertTrue(accountSettingsPage.getAddressUpdateSuccessMsg().isDisplayed(),
                var.getElementIsDisplayedErrMsg());

        // Click the change Address details button.
        accountSettingsPage.clickChangeAddresBtn();

        // Verify that the update button has appeared.
        assertTrue(accountSettingsPage.getAddressUpdateBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }
}
