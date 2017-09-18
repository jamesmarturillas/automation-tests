package com.gengo.automation.tests.CoreTest;

import com.gengo.automation.fields.Constants;
import com.gengo.automation.global.AutomationBase;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @case Verify that Top-up credits using PayPal payment works well.
 * @reference https://gengo.testrail.com/index.php?/cases/view/40336
 */
public class C40336_1_TopUpCredits extends AutomationBase {

    public C40336_1_TopUpCredits() throws IOException {}

    @BeforeClass
    public void initField() {
        this.newUser = csv.generateNewUser(var.SHEETNAME_CUSTOMERS, 0);
    }

    @AfterClass
    public void afterRun() throws IOException {
        csv.writeValue(this.newUser, var.SHEETNAME_CUSTOMERS, 0);
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test(description = "Use a new account as conditional case for database reset", priority = 1)
    public void createCustomerAccount() {
        globalMethods.createNewCustomer(this.newUser);
    }

    @Test(description = "Top-up credits using PayPal (this scenario is under observation since PayPal is intermittently troublesome)", priority = 2)
    public void topUpCreditsUsingPayPal() {
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

        loginPage.loginAccount(this.newUser, var.getDefaultPassword());

        // Assert that page title is present in the DOM.
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Assert that the order icon is present for ordering process.
        assertTrue(global.getOrderTranslationIcon().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        global.selectCustomer();

        // Navigate account settings and go to 'Payment Preferences'.
        global.goToAcctSettings();
        accountSettingsPage.clickChangePaymentPreferences();

        // Assert that you are able to change currency.
        assertTrue(accountSettingsPage.getSelectCurrencyDropDown()
                .isEnabled(), var.getElementIsNotEnabledErrMsg());

        // Change locale to 'Español'.
        global.chooseLanguage(var.getLangES());

        // Assert that the locale is 'Español'.
        assertTrue(global.getAccountLanguageSelectionDropDown()
                .getText().contains(var.getLangES()), var.getTextNotEqualErrMsg());

        // Click again the 'Payment Preferences' and assert that you are able to change currency.
        accountSettingsPage.clickChangePaymentPreferences();
        assertTrue(accountSettingsPage.getSelectCurrencyDropDown()
                .isEnabled(), var.getElementIsNotEnabledErrMsg());

        // Select among "USD", "EUR", "JPY", "GBP" string values and assert that changing of currency is successful.
        accountSettingsPage.selectCurrency(Constants.CURRENCY_EUR);
        assertTrue(accountSettingsPage.getChangeCurrencySuccessMsg()
                .isDisplayed(), var.getElementIsNotDisplayedErrMsg());

        // Navigate through top up page and assert that the redirected page is correct.
        global.goToTopUp();
        assertTrue(page.getCurrentUrl()
                .contains(topUpPage.TOPUP_URL), var.getWrongUrlErrMsg());

        // Choose amount {5,00} and choose PayPal as payment mode.
        topUpPage.selectAmount(var.getTopUpAmountEUR(5));
        topUpPage.selectPaymentMode(topUpPage.choosePayPal());

        // Process payment with PayPal.
        topUpPage.clickPay(true);
        topUpPage.processPayment();
        wait.impWait(30);
        switcher.switchToDefaultContent();
        wait.impWait(10);
        page.refresh();

        // Check that the currency is currently 'Euro'
        global.goToAcctSettings();
        accountSettingsPage.clickChangePaymentPreferences();
        wait.impWait(10, accountSettingsPage.getSelectCurrencyDropDown());
        Select currency = new Select(accountSettingsPage.getSelectCurrencyDropDown());
        assertTrue(currency.getFirstSelectedOption().getText().equals("Euro"),
                var.getTextNotEqualErrMsg());

        // Process top-up with PayPal using 'English' locale
        global.restoreLocaleToEnglish();
        global.goToTopUp();
        assertTrue(page.getCurrentUrl().contains(topUpPage.TOPUP_URL),
                var.getWrongUrlErrMsg());
        assertTrue(topUpPage.getCreditsBalance().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Choose amount {10.00} and choose PayPal as payment mode.
        topUpPage.selectAmount(var.getTopUpAmountUSD(10));
        topUpPage.selectPaymentMode(topUpPage.choosePayPal());

        // Process payment with PayPal.
        topUpPage.clickPay(true);
        topUpPage.processPayment();

        wait.impWait(30);
        switcher.switchToDefaultContent();
        wait.impWait(10);
        page.refresh();

        // Sign out.
        global.nonAdminSignOut();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }
}
