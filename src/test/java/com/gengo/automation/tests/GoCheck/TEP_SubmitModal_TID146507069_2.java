package com.gengo.automation.tests.GoCheck;

import com.gengo.automation.global.AutomationBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @case Checkers should be able to see a completion notice upon finishing a check
 * @reference https://www.pivotaltracker.com/story/show/146507069
 */
public class TEP_SubmitModal_TID146507069_2 extends AutomationBase {

    public TEP_SubmitModal_TID146507069_2() throws IOException {}

    @Test
    public void submitRevisedGoCheck() {
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        homePage.clickSignInButton();

        // Check that after clicking the 'SIGN IN' link, the URL is right and 'SIGN UP' button is displayed as well.
        assertTrue(page.getCurrentUrl().equals(loginPage.LOGIN_PAGE_URL),
                var.getWrongUrlErrMsg());
        assertTrue(loginPage.getSignUpBtn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        loginPage.loginAccount(var.getTestAdmin(), var.getDefaultPassword());
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        global.selectAdmin();
        assertTrue(page.getCurrentUrl().contains("admin."),
                var.getWrongUrlErrMsg());

        // Hide debug panel
        adminPage.hidePanel();

        // Go to Checks list page
        adminPage.goToChecks();

        // Filter checks to overdue status
        adminChecksPage.filterChecksStatus("overdue");
        adminPage.clickFilterButton();

        // Store check ID
        adminChecksPage.setCheckID();

        // Access check details page of first check on list
        adminChecksPage.accessFirstCheckDetails();
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(adminChecksPage.getCheckDetailsPage().isDisplayed());

        // Store Check Trigger and Job ID
        adminChecksPage.setTrigger();
        adminChecksPage.setJobID();

        // Go to Actions > Check
        adminChecksPage.goToGoCheckPage();
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(page.getCurrentUrl().contains("gocheck."),
                var.getWrongUrlErrMsg());

        // Add markings on GoCheck
        goCheckPage.highlightText(getDriver(), goCheckPage.getGocheckTargetContent());
        assertTrue(goCheckPage.getAnnotatorButton().isDisplayed());
        goCheckPage.addQualityIssueAnnotation("punctuationErrorMinor");
        goCheckPage.addCommentAnnotation("This is a comment from Gengo gocheck.");
        goCheckPage.saveAnnotation();

        // Add feedback
        goCheckPage.writeFeedbackToTranslator(var.getCheckerFeedback());

        // Submit less than marked score check with Feedback
        goCheckPage.clickSubmitGocheck();
        assertTrue(goCheckPage.getSubmitConfirmationModal().isDisplayed());

        // Click "X" button/link
        goCheckPage.clickSubmitConfirmationModal("close");
        assertTrue(goCheckPage.getGocheckTargetContent().isDisplayed());

        // Click Submit button again then Cancel
        goCheckPage.clickSubmitGocheck();
        assertTrue(goCheckPage.getSubmitConfirmationModal().isDisplayed());
        goCheckPage.clickSubmitConfirmationModal("cancel");
        assertTrue(goCheckPage.getGocheckTargetContent().isDisplayed());

        // Check link to email
        assertEquals(goCheckPage.getEmailLinkConfirmationModal().getAttribute("href"),
                "mailto:%74r%61nslator-tea%6D@g%65ngo.c%6Fm");

        // Complete submission of gocheck
        goCheckPage.clickSubmitGocheck();
        assertTrue(goCheckPage.getSubmitConfirmationModal().isDisplayed());
        goCheckPage.clickSubmitConfirmationModal("confirm");

        // Assert page returned to admin and gochecked ID is on list with status "complete"
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(page.getCurrentUrl().contains("admin."),
                var.getWrongUrlErrMsg());

        // Conditional validation on redirection page after check submission
        // Rejected Job triggered check will be redirected to Job details page
        // Others are redirected to Checks list page
        if(adminChecksPage.retrieveTrigger().equals("Rejected job review")) {
            assertTrue(adminJobsPage.getJobDetailsJobID().isDisplayed());
            assertEquals(adminJobsPage.jobIDNumber(),
                    adminChecksPage.retrieveJobID());
        } else {
            adminChecksPage.filterChecksJobID(adminChecksPage.retrieveJobID());
            adminChecksPage.filterChecksStatus("complete");
            adminPage.clickFilterButton();
            assertEquals(adminChecksPage.getSelectFirstCheck().getText(),
                    adminChecksPage.retrieveCheckID());
            assertEquals(adminChecksPage.getCheckStatusOnList().getText(),
                    "Complete");
        }
    }
}
