package com.gengo.automation.tests.TranslateCore;

import com.gengo.automation.global.AutomationBase;
import com.gengo.automation.global.GlobalMethods;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * @case Top up credits using PayPal as payment mode.
 * @reference https://gengo.testrail.com/index.php?/cases/view/31
 */
public class C31_TopUpPayPal extends AutomationBase {

    public C31_TopUpPayPal() throws IOException {}

    @BeforeClass
    public void initFields() throws IOException{
        this.newUser = csv.generateNewUser(var.SHEETNAME_CUSTOMERS, 0);
        globalMethods.createNewCustomer(newUser);
    }

    @AfterClass
    public void afterRun() {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test
    public void goToAccountTopUpPage() {
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
        loginPage.loginAccount(var.getCustomer(12), var.getDefaultPassword());

        // Assert that page title is present in the DOM.
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Navigate through top up page.
        global.goToTopUp();

        // Assert that the navigated page is correct by URL return.
        assertTrue(page.getCurrentUrl().contains(topUpPage.TOPUP_URL));

        // Perform the test with PayPal.
        this.topUp();

        // Sign out.
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void topUp() {
        this.changeLocale(var.getLangES());
        this.topUpCredits(var.getTopUpAmountEUR(5));
        this.checkCurrency();
        this.testWithEnglishLocale();
        this.topUpCredits(var.getTopUpAmountUSD(10));
    }

    private void changeLocale(String locale) {
        global.restoreLocaleToEnglish();
        global.chooseLanguage(locale);
        wait.impWait(30);
    }

    private void topUpCredits(String strAmount) {
        topUpPage.selectAmount(strAmount);
        topUpPage.selectPaymentMode(topUpPage.choosePayPal());
        topUpPage.clickPay(true);
        topUpPage.processPayment();
        wait.impWait(30);
        switcher.switchToDefaultContent();
        wait.impWait(10);
        page.refresh();
    }

    private void checkCurrency() {
        global.goToAcctSettings();
        accountSettingsPage.clickChangePaymentPreferences();
        wait.impWait(10, accountSettingsPage.getSelectCurrencyDropDown());
        Select currency = new Select(accountSettingsPage.getSelectCurrencyDropDown());
        assertTrue(currency.getFirstSelectedOption().getText().equals("Euro"),
                var.getTextNotEqualErrMsg());
    }

    private void testWithEnglishLocale() {
        global.restoreLocaleToEnglish();
        global.goToTopUp();
        assertTrue(page.getCurrentUrl().contains(topUpPage.TOPUP_URL),
                var.getWrongUrlErrMsg());
        assertTrue(topUpPage.getCreditsBalance().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }
}
