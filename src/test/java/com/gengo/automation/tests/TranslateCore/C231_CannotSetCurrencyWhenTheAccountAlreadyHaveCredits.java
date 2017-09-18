package com.gengo.automation.tests.TranslateCore;

import com.gengo.automation.fields.Constants;
import com.gengo.automation.global.AutomationBase;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @case Won't be able to set another currency when the account has credits.
 * @reference https://gengo.testrail.com/index.php?/cases/view/231
 *
 * IMPORTANT NOTE : Need new account in the changeCurrencyOfNewAccount() method in order to make the test pass.
 */
public class C231_CannotSetCurrencyWhenTheAccountAlreadyHaveCredits extends AutomationBase {

    public C231_CannotSetCurrencyWhenTheAccountAlreadyHaveCredits() throws IOException {}

    @AfterClass
    public void afterRun() throws IOException {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test
    public void goToAccountTopUpPage() {
        this.newUser = csv.generateNewUser(var.SHEETNAME_CUSTOMERS, 0);
        globalMethods.createNewCustomer(newUser);

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

        // Navigate through top up page.
        global.goToTopUp();

        // Assert that the navigated page is correct by URL return.
        assertTrue(page.getCurrentUrl().contains(topUpPage.TOPUP_URL));
    }

    @Test(dependsOnMethods = "goToAccountTopUpPage")
    public void cannotSetCurrencyWhenTheAccountAlreadyHaveCredits() throws IOException {
        this.topUpCredits();
        this.tryChangeCurrency();
        this.changeCurrencyOfNewAccount();
    }

    private void topUpCredits() {
        topUpPage.selectAmount(var.getTopUpAmountUSD(50));
        topUpPage.selectPaymentMode(topUpPage.choosePayPal());
        topUpPage.clickPay(true);
        topUpPage.processPayment();
        wait.impWait(60, customerDashboardPage.getCustomerDashboardText());
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());
    }

    private void tryChangeCurrency() throws IOException {
        // Navigate through top up page again.
        global.goToAcctSettings();

        // Assert that the return page is correct.
        assertTrue(page.getCurrentUrl().contains(accountSettingsPage.ACCOUNT_SETTINGS_URL),
                var.getWrongUrlErrMsg());

        // Check if the currency selection dropdown is enabled.
        accountSettingsPage.clickChangePaymentPreferences();

        assertFalse(accountSettingsPage.getSelectCurrencyDropDown().isEnabled(),
                var.getElementIsEnabledErrMsg());

        csv.writeValue(this.newUser, var.SHEETNAME_CUSTOMERS, 0);

        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void changeCurrencyOfNewAccount() throws IOException {
        String anotherNewUser = csv.generateNewUser(var.SHEETNAME_CUSTOMERS, 0);

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
        loginPage.loginAccount(anotherNewUser, var.getDefaultPassword());

        // Navigate through top up page again.
        global.goToAcctSettings();

        // Assert that the return page is correct.
        assertTrue(page.getCurrentUrl().contains(accountSettingsPage.ACCOUNT_SETTINGS_URL),
                var.getWrongUrlErrMsg());

        // Check if the currency selection dropdown is enabled.
        accountSettingsPage.clickChangePaymentPreferences();

        // Select among "USD", "EUR", "JPY", "GBP" string values.
        accountSettingsPage.selectCurrency(Constants.CURRENCY_JPY);

        assertTrue(accountSettingsPage.getChangeCurrencySuccessMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        data.writeValue(anotherNewUser, var.SHEETNAME_CUSTOMERS, 0);

        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }
}
