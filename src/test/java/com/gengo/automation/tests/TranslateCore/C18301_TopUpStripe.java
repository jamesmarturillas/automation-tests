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
 * @case Top up credits using Stripe as payment mode.
 * @reference https://gengo.testrail.com/index.php?/cases/view/18301
 */
public class C18301_TopUpStripe extends AutomationBase {

    public C18301_TopUpStripe() throws IOException {}

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
        this.topUpCredits();
        this.checkEmail();
    }

    private void topUpCredits() {
        topUpPage.selectAmount(var.getTopUpAmountUSD(10));
        topUpPage.selectPaymentMode(topUpPage.chooseStripe());
        topUpPage.clickPay(false);
        topUpPage.processPayment(var.getCreditCard());
        wait.impWait(30);
        switcher.switchToDefaultContent();
        wait.impWait(10, topUpPage.getTopUpSuccessMsg());
        assertTrue(topUpPage.getTopUpSuccessMsg().isDisplayed(),
                var.getTextNotEqualErrMsg());
    }

    private void checkEmail() {
        // Go to Gmail account
        page.launchUrl(var.getGmailUrl());

        assertTrue(gmailSignInEmailPage.getTxtBoxEmail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        gmailSignInEmailPage.inputEmail(var.getGmailEmail());

        gmailSignInPasswordPage.inputPasswordAndSubmit(var.getGmailPassword());

        assertTrue(gmailInboxPage.getGmailComposeBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(gmailInboxPage.getTopUpCompleteEmail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Open the email.
        gmailInboxPage.clickTopUpCompleteEmail();
    }
}
