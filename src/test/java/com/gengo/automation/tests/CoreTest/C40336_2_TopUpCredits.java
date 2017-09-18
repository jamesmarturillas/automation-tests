package com.gengo.automation.tests.CoreTest;

import com.gengo.automation.global.AutomationBase;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * @case Verify that Top-up credits using STRIPE payment works well.
 * @reference https://gengo.testrail.com/index.php?/cases/view/40336
 */
public class C40336_2_TopUpCredits extends AutomationBase {

    public C40336_2_TopUpCredits() throws IOException {}

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

    @Test(description = "Top-up credits using STRIPE", priority = 2)
    public void topUpCreditsUsingStripe() {
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

        // Go to top-up page and assert that the navigated page is correct by URL return.
        global.goToTopUp();
        assertTrue(page.getCurrentUrl()
                .contains(topUpPage.TOPUP_URL), var.getWrongUrlErrMsg());

        // Top-up with successful process
        topUpPage.selectAmount(var.getTopUpAmountUSD(10));
        topUpPage.selectPaymentMode(topUpPage.chooseStripe());
        topUpPage.clickPay(false);
        topUpPage.processPayment(var.getCreditCard());
        wait.impWait(30);
        switcher.switchToDefaultContent();
        wait.impWait(10, topUpPage.getTopUpSuccessMsg());
        assertTrue(topUpPage.getTopUpSuccessMsg().isDisplayed(),
                var.getTextNotEqualErrMsg());

        // Navigate through top-up page again and try invalid cards.
        global.goToTopUp();

        // Top-up with CVC error.
        topUpPage.selectAmount(var.getTopUpAmountUSD(10));
        topUpPage.selectPaymentMode(topUpPage.chooseStripe());
        topUpPage.clickPay(false);
        topUpPage.processPayment(var.getCreditCard(8));
        assertTrue(topUpPage.getErrorBoxRight().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Top-up with card declined.
        topUpPage.processPayment(var.getCreditCard(9));
        wait.impWait(10, topUpPage.getTopUpDeclinedCardErrorMsg());
        assertTrue(topUpPage.getTopUpDeclinedCardErrorMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Top-up with second CVC error.
        topUpPage.processPayment(var.getCreditCard(10));
        assertTrue(topUpPage.getErrorBoxRight().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Top-up with credit card that has expired code.
        topUpPage.processPayment(var.getCreditCard(11));
        assertTrue(topUpPage.getErrorBoxLeft().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Top-up with error processing card.
        topUpPage.processPayment(var.getCreditCard(12));
        wait.impWait(10, topUpPage.getTopUpProcessingCardError());
        assertTrue(topUpPage.getTopUpProcessingCardError().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Top-up with error due to fraudulent.
        topUpPage.processPayment(var.getCreditCard(13));
        wait.impWait(30);
        switcher.switchToDefaultContent();
        wait.impWait(10, topUpPage.getTopUpPageErrorMsg());
        assertTrue(topUpPage.getTopUpPageErrorMsg().getText().contains(var.getTopUpFailedErrMsg()),
                var.getTextNotEqualErrMsg());

        // Top-up with error charge.
        topUpPage.selectAmount(var.getTopUpAmountUSD(25));
        topUpPage.selectPaymentMode(topUpPage.chooseStripe());
        topUpPage.clickPay(false);
        topUpPage.processPayment(var.getCreditCard(14));
        wait.impWait(30);
        switcher.switchToDefaultContent();
        wait.impWait(10, topUpPage.getTopUpPageErrorMsg());
        assertTrue(topUpPage.getTopUpPageErrorMsg().getText().contains(var.getTopUpFailedErrMsg()),
                var.getTextNotEqualErrMsg());

        // Sign out.
        global.nonAdminSignOut();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }
}
