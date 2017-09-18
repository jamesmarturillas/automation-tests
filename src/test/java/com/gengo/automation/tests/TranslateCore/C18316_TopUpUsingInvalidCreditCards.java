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
 * @case Top up credits using invalid credit cards.
 * @reference https://gengo.testrail.com/index.php?/cases/view/18316
 */
public class C18316_TopUpUsingInvalidCreditCards extends AutomationBase {

    public C18316_TopUpUsingInvalidCreditCards() throws IOException {}

    @AfterClass
    public void afterRun() throws IOException {
        csv.writeValue(this.newUser, var.SHEETNAME_CUSTOMERS, 0);
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test
    public void goToAccountTopUpPage() {
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

        // Navigate through top up page.
        global.goToTopUp();

        // Assert that the navigated page is correct by URL return.
        assertTrue(page.getCurrentUrl().contains(topUpPage.TOPUP_URL));
    }

    @Test(dependsOnMethods = "goToAccountTopUpPage")
    public void topUp() {
        this.topUpErrorCVC();
        this.topUpErrorCardDeclined();
        this.topUpErrorCVC2();
        this.topUpErrorExpiredCardCode();
        this.topUpErrorProcessingCard();
        this.topUpErrorFraudulent();
        this.topUpErrorCharge();
    }

    private void topUpErrorCVC() {
        topUpPage.selectAmount(var.getTopUpAmountUSD(10));
        topUpPage.selectPaymentMode(topUpPage.chooseStripe());
        topUpPage.clickPay(false);
        topUpPage.processPayment(var.getCreditCard(8));
        assertTrue(topUpPage.getErrorBoxRight().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void topUpErrorCardDeclined() {
        topUpPage.processPayment(var.getCreditCard(9));
        wait.impWait(10, topUpPage.getTopUpDeclinedCardErrorMsg());
        assertTrue(topUpPage.getTopUpDeclinedCardErrorMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void topUpErrorCVC2() {
        topUpPage.processPayment(var.getCreditCard(10));
        assertTrue(topUpPage.getErrorBoxRight().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void topUpErrorExpiredCardCode() {
        topUpPage.processPayment(var.getCreditCard(11));
        assertTrue(topUpPage.getErrorBoxLeft().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void topUpErrorProcessingCard() {
        topUpPage.processPayment(var.getCreditCard(12));
        wait.impWait(10, topUpPage.getTopUpProcessingCardError());
        assertTrue(topUpPage.getTopUpProcessingCardError().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void topUpErrorFraudulent() {
        topUpPage.processPayment(var.getCreditCard(13));
        wait.impWait(30);
        switcher.switchToDefaultContent();
        wait.impWait(10, topUpPage.getTopUpPageErrorMsg());
        assertTrue(topUpPage.getTopUpPageErrorMsg().getText().contains(var.getTopUpFailedErrMsg()),
                var.getTextNotEqualErrMsg());
    }

    private void topUpErrorCharge() {
        topUpPage.selectAmount(var.getTopUpAmountUSD(25));
        topUpPage.selectPaymentMode(topUpPage.chooseStripe());
        topUpPage.clickPay(false);
        topUpPage.processPayment(var.getCreditCard(14));
        wait.impWait(30);
        switcher.switchToDefaultContent();
        wait.impWait(10, topUpPage.getTopUpPageErrorMsg());
        assertTrue(topUpPage.getTopUpPageErrorMsg().getText().contains(var.getTopUpFailedErrMsg()),
                var.getTextNotEqualErrMsg());
    }
}
