package com.gengo.automation.tests.TranslateCore;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @case See dashboard navidation dedicated for checkers
 * @reference https://www.pivotaltracker.com/story/show/147901357
 */
public class C66895_SeeCheckerDashboardNavEN extends AutomationBase {

    public C66895_SeeCheckerDashboardNavEN() throws IOException {}

    @Test
    public void seeCheckerDashLanguageSpecialist() {
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        homePage.clickSignInButton();

        // Check that after clicking the 'SIGN IN' link, the URL is right and 'SIGN UP' button is displayed as well.
        assertTrue(page.getCurrentUrl().equals(loginPage.LOGIN_PAGE_URL),
                var.getWrongUrlErrMsg());
        assertTrue(loginPage.getSignUpBtn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Login as Language Specialist/Contractor/Senior Translator
        loginPage.loginAccount(var.getTestContractor(), var.getDefaultPassword());
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Validations to check if checker tab exists
        assertTrue(translatorDashboardPage.getCheckerTab().isDisplayed());
        translatorDashboardPage.clickCheckerTab();
        assertTrue(translatorChecksPage.getTranslatorChecksPageHeaderAll().isDisplayed());

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    @Test(dependsOnMethods = "seeCheckerDashLanguageSpecialist")
    public void seeCheckerDashTranslatorOnly() {
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        homePage.clickSignInButton();

        // Check that after clicking the 'SIGN IN' link, the URL is right and 'SIGN UP' button is displayed as well.
        assertTrue(page.getCurrentUrl().equals(loginPage.LOGIN_PAGE_URL),
                var.getWrongUrlErrMsg());
        assertTrue(loginPage.getSignUpBtn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Login as Normal translator
        loginPage.loginAccount(var.getTranslatorStandard(3), var.getDefaultPassword());
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Validations to check if checker tab is not existing
        assertFalse(translatorDashboardPage.getTabNavBar().getText().contains("Checks"));

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    @Test(dependsOnMethods = "seeCheckerDashTranslatorOnly")
    public void seeCheckerDashCustomerOnly() {
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        homePage.clickSignInButton();

        // Check that after clicking the 'SIGN IN' link, the URL is right and 'SIGN UP' button is displayed as well.
        assertTrue(page.getCurrentUrl().equals(loginPage.LOGIN_PAGE_URL),
                var.getWrongUrlErrMsg());
        assertTrue(loginPage.getSignUpBtn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Login as Customer
        loginPage.loginAccount(var.getCustomer(102), var.getDefaultPassword());
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Validations to check if checker tab is not existing
        assertFalse(customerDashboardPage.getNavBar().getText().contains("Checks"));

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    @Test(dependsOnMethods = "seeCheckerDashCustomerOnly")
    public void seeCheckerDashAdmin() {
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        homePage.clickSignInButton();

        // Check that after clicking the 'SIGN IN' link, the URL is right and 'SIGN UP' button is displayed as well.
        assertTrue(page.getCurrentUrl().equals(loginPage.LOGIN_PAGE_URL),
                var.getWrongUrlErrMsg());
        assertTrue(loginPage.getSignUpBtn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Login as Admin user
        loginPage.loginAccount(var.getTestAdmin(), var.getDefaultPassword());
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Validations to check if checker tab exists
        assertTrue(translatorDashboardPage.getCheckerTab().isDisplayed());
        translatorDashboardPage.clickCheckerTab();
        assertTrue(translatorChecksPage.getTranslatorChecksPageHeaderAll().isDisplayed());
    }

    @Test(dependsOnMethods = "seeCheckerDashAdmin")
    public void goToPriorityPanel() {
        assertTrue(translatorChecksPage.getAllLeftPanel().isDisplayed());
        assertTrue(translatorChecksPage.getPriorityLeftPanel().isDisplayed());

        // Click "Priority" left panel
        translatorChecksPage.clickPriorityChecks();
        assertTrue(translatorChecksPage.getTranslatorChecksPageHeaderPriority().isDisplayed());

        // Click "All" left panel
        translatorChecksPage.clickAllChecks();
        assertTrue(translatorChecksPage.getTranslatorChecksPageHeaderAll().isDisplayed());

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }
}
