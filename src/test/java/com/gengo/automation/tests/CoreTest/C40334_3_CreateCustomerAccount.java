package com.gengo.automation.tests.CoreTest;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @case Create new account with Google account -> Customer
 * @reference https://gengo.testrail.com/index.php?/cases/view/4
 */
public class C40334_3_CreateCustomerAccount extends AutomationBase {

    private String parentWindow;

    public C40334_3_CreateCustomerAccount() throws IOException {}

    @AfterClass
    public void afterRun() {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test
    public void editAccountInAdmin() {
        // Remove the permission of Gengo app from the Gmail account.
        globalMethods.removeGooglePermission();

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

        // Login as admin account.
        loginPage.loginAccount(var.getTestAdmin(), var.getDefaultPassword());

        global.goToAdminPage();

        // Assert that the redirect page is correct.
        assertTrue(page.getCurrentUrl().equals(global.ADMIN_URL),
                var.getWrongUrlErrMsg());

        // Search the account. [gengo.automationtest@gmail.com]
        adminPage.searchAccount(var.getGmailEmail());

        adminUserPage.editEmail();

        global.adminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    @Test(dependsOnMethods = "editAccountInAdmin")
    public void createNewCustomerAccountWithGoogle() {
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

        loginPage.clickSignUpButton();

        // Check that sign up page URL is correct.
        assertTrue(page.getCurrentUrl().equals(signUpPage.SIGNUP_PAGE_URL),
                var.getWrongUrlErrMsg());

        // Assert elements behave as expected.
        assertTrue(signUpPage.getParentDropDown().getText().equals("Customer"),
                var.getTextNotEqualErrMsg());
        assertFalse(signUpPage.getCreateAccountBtn().isEnabled(),
                var.getElementIsEnabledErrMsg());
        assertFalse(signUpPage.getFacebookLink().isEnabled(),
                var.getElementIsEnabledErrMsg());
        assertFalse(signUpPage.getGoogleLink().isEnabled(),
                var.getElementIsEnabledErrMsg());

        signUpPage.clickDropDownButton();

        // Assert elements behave as expected after clicking the sign up dropdown button for translator as option.
        assertTrue(signUpPage.getOptionCustomer().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertFalse(signUpPage.getCreateAccountBtn().isEnabled(),
                var.getElementIsEnabledErrMsg());
        assertFalse(signUpPage.getFacebookLink().isEnabled(),
                var.getElementIsEnabledErrMsg());
        assertFalse(signUpPage.getGoogleLink().isEnabled(),
                var.getElementIsEnabledErrMsg());

        signUpPage.clickCustomerOption();

        // Try click the Google link.
        signUpPage.clickGoogleLink();

        // Assert it's not clickable.
        assertFalse(signUpPage.getGoogleLink().isEnabled(),
                var.getElementIsEnabledErrMsg());

        signUpPage.tickCheckBoxAgree();

        assertTrue(signUpPage.getGoogleLink().isEnabled(),
                var.getElementIsEnabledErrMsg());

        signUpPage.clickGoogleLink();

        parentWindow = switcher.getWindowHandle();
        switcher.switchToPopUp();
        gmailAllowAppPage.clickAllow();
        switcher.switchToParentWindow(parentWindow);

        assertTrue(customerDashboardPage.getCustomerDashboardText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Sign out and sign in again.
        global.nonAdminSignOut();

        pluginPage.passThisPage();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
    }
}
