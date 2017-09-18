package com.gengo.automation.tests.Admin;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.gengo.automation.fields.Constants.*;
import static junit.framework.TestCase.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @case Can Force Approve Jobs
 * Filter Auto Approve Jobs
 * @reference https://gengo.testrail.com/index.php?/cases/view/18361
 */
public class C18361_5_CanForceApproveJob extends AutomationBase {

    private String jobID, source, target, dateCreated, status, level;

    public C18361_5_CanForceApproveJob() throws IOException {}

    @AfterClass
    public void afterRun() {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    /**
     * adminCheckTitle --- this method logs in an Admin account,
     * goes to the Jobs Page, filters by Auto Approved, and checks the first entry
     * if it displays the Title of the job
     * */
    @Test
    public void adminCheckTitle(){
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

        // Go to Jobs Page
        adminPage.goToJobs();

        // Filter by Auto Approve
        adminJobsPage.selectStatusFilter(FILTER_AUTO_APPROVED);
        adminJobsPage.getFilterBtn().click();

        // Retrieve the job Number of the first entry
        jobID = adminJobsPage.getFirstJobEntryID().getText();

        // Retrieve the date created of the first entry
        dateCreated = adminJobsPage.getFirstJobEntryDateCreated().getText();

        // Retrieve the source language of the first entry
        source = adminJobsPage.getFirstJobEntrySource().getText();

        // Retrieve the target language of the first entry
        target = adminJobsPage.getFirstJobEntryTarget().getText();

        // Retrieve the level of the first entry
        level = adminJobsPage.getFirstJobEntryLevel().getText();

        // Retrieve the status of the first entry
        status = adminJobsPage.getFirstJobEntryStatus().getText();

        // Check if the job status is equal to the filter keyword
        assertTrue(FILTER_AUTO_APPROVED.contains(status));

        // Go to the Job Details page of the first Job result
        adminJobsPage.getFirstJobEntryID().click();

        // Check if the job ID from the results page is equal to the one on the job details page
        assertEquals(jobID, adminJobDetailsPage.jobNumber());

        // Check if the job level from the results page is equal to the one on the job details page
        assertEquals(level, adminJobDetailsPage.getJobLevel().getText());

        // Check if the job date created from the results page is equal to the one on the job details page
        assertTrue(adminJobDetailsPage.getJobDateCreated().getText().contains(dateCreated));

        // Check if the job status from the results page is equal to the one on the job details page
        assertEquals(status, adminJobDetailsPage.getJobStatus().getText());

        // Check that Job Title is displayed and it is not empty
        assertTrue(adminJobDetailsPage.getJobTitle().isDisplayed());
        assertTrue(!adminJobDetailsPage.getJobTitle().getText().isEmpty());

        // Check if the job target and source languages from the results page are equal to the ones on the job details page
        assertEquals(globalMethods.AbbrToFullLanguage(source), adminJobDetailsPage.getJobSource().getText());
        assertEquals(globalMethods.AbbrToFullLanguage(target), adminJobDetailsPage.getJobTarget().getText());

        adminPage.getActionBtn().click();
        assertFalse(globalMethods.isElementDisplayed(adminJobsPage.getJobActionBtnForceApprove()));

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }
}
