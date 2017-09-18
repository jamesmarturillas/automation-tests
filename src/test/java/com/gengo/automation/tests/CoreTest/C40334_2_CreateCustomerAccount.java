package com.gengo.automation.tests.CoreTest;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @case Create new customer account - FACEBOOK
 * @reference https://gengo.testrail.com/index.php?/cases/view/40334
 */
public class C40334_2_CreateCustomerAccount extends AutomationBase {

    public C40334_2_CreateCustomerAccount() throws IOException {}
    private String parentWindow;

    @AfterClass
    public void afterRun() {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test
    public void editAccountInAdmin() {
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

        adminPage.hidePanel();

        // Search the account. [gengo.testcustomer@gmail.com]
        adminPage.searchAccount(var.getFbEmail(1));

        // Assert that the current email is linked on external apps.
        assertTrue(adminUserPage.getUserID().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(adminUserPage.getUnlink().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        adminUserPage.editEmail();

        // Assert that the edit is success.
        assertTrue(adminUserPage.getSuccessEditMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        global.adminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    @Test(dependsOnMethods = "editAccountInAdmin")
    public void createNewCustomerAccountWithFacebook() {
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

        // Try click the Facebook link.
        signUpPage.clickFbLink();

        // Assert it's not clickable.
        assertFalse(signUpPage.getFacebookLink().isEnabled(),
                var.getElementIsEnabledErrMsg());

        signUpPage.tickCheckBoxAgree();

        assertTrue(signUpPage.getFacebookLink().isEnabled(),
                var.getElementIsEnabledErrMsg());

        signUpPage.clickFbLink();

        parentWindow = switcher.getWindowHandle();
        switcher.switchToPopUp();
        facebookPopUpPage.login(var.getFbEmail(1), var.getFbPassword(1));
        switcher.switchToParentWindow(parentWindow);

        assertTrue(customerDashboardPage.getCustomerDashboardText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Sign out and sign in again.
        global.nonAdminSignOut();

        pluginPage.passThisPage();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        homePage.clickSignInButton();

        assertTrue(page.getCurrentUrl().equals(loginPage.LOGIN_PAGE_URL),
                var.getWrongUrlErrMsg());
        assertTrue(loginPage.getFbLoginLink().isDisplayed(),
                var.getElementNotFoundErrMsg());

        loginPage.clickFbLoginLink();

        assertTrue(customerDashboardPage.getCustomerDashboardText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        global.nonAdminSignOut();

        pluginPage.passThisPage();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
    }
}
