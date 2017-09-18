package com.gengo.automation.tests.Admin;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.gengo.automation.fields.Constants.FILTER_AVAILABLE;
import static junit.framework.TestCase.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @case Edit Job Title
 * Filter Available Jobs
 * @reference https://gengo.testrail.com/index.php?/cases/view/18296
 */
public class C18297_5_EditJobTitle extends AutomationBase {

    public C18297_5_EditJobTitle() throws IOException {}

    @AfterClass
    public void afterRun() {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    /**
     * adminCheckEditTitle --- this method logs in an Admin account,
     * goes to the Jobs Page, filters by Available, and checks
     * if the Edit option is available
     * */
    @Test
    public void adminCheckEditTitle(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        assertTrue(page.getCurrentUrl().equals(loginPage.LOGIN_PAGE_URL),
                var.getWrongUrlErrMsg());
        assertTrue(loginPage.getSignUpBtn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Input details of an employee account
        loginPage.loginAccount(var.getTestAdmin(), var.getDefaultPassword());

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

        // Go to Jobs Page
        adminPage.goToJobs();

        // Filter by Available
        adminJobsPage.selectStatusFilter(FILTER_AVAILABLE);
        adminJobsPage.getFilterBtn().click();

        // Go to the Job Details page of the first Job result
        adminJobsPage.getFirstJobEntryID().click();

        // Click the Actions dropdown button and checks if Edit option is visible
        adminJobDetailsPage.getActionsBtn().click();
        assertFalse(globalMethods.isElementDisplayed(adminJobDetailsPage.getEditBtn()));

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }
}
