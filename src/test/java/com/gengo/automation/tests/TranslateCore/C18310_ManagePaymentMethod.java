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
 * @case Change the account's payment preference details.
 * @reference https://gengo.testrail.com/index.php?/cases/view/18310
 */
public class C18310_ManagePaymentMethod extends AutomationBase {

    public C18310_ManagePaymentMethod() throws IOException {}

    @AfterClass
    public void afterRun() throws IOException {
        csv.writeValue(this.newUser, var.SHEETNAME_CUSTOMERS, 0);
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test
    public void goToAccountSettings() {
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

    @Test(dependsOnMethods = "goToAccountSettings")
    public void setCreditCardAsPaymentMethod() {
        // Click the change payment preferences button.
        accountSettingsPage.clickChangePaymentPreferences();

        // Assert that update button has appeared in the page.
        assertTrue(accountSettingsPage.getUpdatePaymentMethodBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Choose credit card as payment method.
        accountSettingsPage.chooseCreditCardPaymentPreference();

        // Assert that the update is successful.
        assertTrue(accountSettingsPage.getUpdatePaymentMethodSuccessMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    @Test(dependsOnMethods = "setCreditCardAsPaymentMethod")
    public void addCreditCard() {
        // Click add credit card button.
        accountSettingsPage.clickAddCreditCardBtn();

        // @followingProcess
        //  Using other page object {TopUpPage.java} to interact with the stripe modal
        //  See AccountSettingsPage.java's addCreditCard() method.
        //  Check if the stripe modal appears.
        assertTrue(topUpPage.getStripeModalIframe().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        accountSettingsPage.addCreditCard();

        assertTrue(accountSettingsPage.getUpdateCreditCardSuccessMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        accountSettingsPage.removeCreditCard();

        assertTrue(accountSettingsPage.getRemoveCreditCardSuccessMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }
}
