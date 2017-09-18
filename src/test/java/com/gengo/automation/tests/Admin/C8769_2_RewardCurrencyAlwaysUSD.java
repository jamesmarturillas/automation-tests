package com.gengo.automation.tests.Admin;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.gengo.automation.fields.Constants.TRANSLATOR;
import static org.testng.Assert.assertTrue;

/**
 * @case Reward Currency Always in $
 * Translator Account with Rewards
 * @reference https://gengo.testrail.com/index.php?/cases/view/8769
 */
public class C8769_2_RewardCurrencyAlwaysUSD extends AutomationBase {

    public C8769_2_RewardCurrencyAlwaysUSD() throws IOException {}

    @AfterClass
    public void afterRun() {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    /**
     * adminCheckRewards --- this method logs in an Admin account,
     * goes to the Users Page, filters by Translator, and checks the rewards currency
     * */
    @Test
    public void adminCheckRewards(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        assertTrue(page.getCurrentUrl().equals(loginPage.LOGIN_PAGE_URL),
                var.getWrongUrlErrMsg());
        assertTrue(loginPage.getSignUpBtn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Input details of an employee account
        loginPage.loginAccount(var.getTestEmployee(), var.getDefaultPassword());

        // Assert that page title is present in the DOM.
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Select Admin user level.
        global.selectAdmin();

        // Assert that we are redirected to the right page.
        assertTrue(page.getCurrentUrl().contains("admin."),
                var.getWrongUrlErrMsg());

        // Hide the right panel.
        adminPage.hidePanel();

        // Go to Users Page
        adminPage.goToUsers();

        // Filter by Translator
        adminUsersPage.chooseRole(TRANSLATOR);
        adminUsersPage.getFilterBtn().click();

        // Click the First Translator with Rewards
        adminUsersPage.viewFirstTranslatorWithRewards();

        // Check that the Rewards amount is in USD
        assertTrue(adminUserPage.getRewardsAmt().getText().contains("$"));

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }
}
