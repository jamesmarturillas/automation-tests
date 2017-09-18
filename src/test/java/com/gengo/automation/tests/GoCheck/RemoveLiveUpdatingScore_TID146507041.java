package com.gengo.automation.tests.GoCheck;


import com.gengo.automation.global.AutomationBase;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @case Checkers should not see a live updating score
 * @reference https://www.pivotaltracker.com/story/show/146507041
 */
public class RemoveLiveUpdatingScore_TID146507041 extends AutomationBase {

    public RemoveLiveUpdatingScore_TID146507041() throws IOException {}

    @Test
    public void checkScoreOnTopRight() {
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

        // Store check ID
        adminChecksPage.setCheckID();

        // Access check details page of first check on list
        adminChecksPage.accessFirstCheckDetails();
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(adminChecksPage.getCheckDetailsPage().isDisplayed());

        // Store the check status for conditional validation
        adminChecksPage.setCheckStatus();
        adminChecksPage.setTrigger();

        // Go to Actions > Check or Edit depends on check status
        adminChecksPage.navigateGoCheckPageviaStatus(adminChecksPage.retrieveCheckStatus());
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(page.getCurrentUrl().contains("gocheck."),
                var.getWrongUrlErrMsg());

        // Validate if score is removed on top-right corner of page
        assertFalse(goCheckPage.getGocheckNavBar().getText().contains("Score"));

        // Submit the gocheck and close
        goCheckPage.clickSubmitGocheck();
        assertTrue(goCheckPage.getSubmitConfirmationModal().isDisplayed());
        goCheckPage.clickSubmitConfirmationModal("close");

        // Validate if score is removed on top-right corner of page
        assertFalse(goCheckPage.getGocheckNavBar().getText().contains("Score"));

        // Click Submit button again then Cancel
        goCheckPage.clickSubmitGocheck();
        assertTrue(goCheckPage.getSubmitConfirmationModal().isDisplayed());
        goCheckPage.clickSubmitConfirmationModal("cancel");
        assertTrue(goCheckPage.getGocheckTargetContent().isDisplayed());

        // Validate if score is removed on top-right corner of page
        assertFalse(goCheckPage.getGocheckNavBar().getText().contains("Score"));

        // Complete submission of gocheck
        goCheckPage.clickSubmitGocheck();
        wait.impWait(30);
        assertTrue(goCheckPage.getSubmitConfirmationModal().isDisplayed());
        goCheckPage.clickSubmitConfirmationModal("confirm");

        // Assert page returned to admin and gochecked ID is on list with status "complete"
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(page.getCurrentUrl().contains("admin."),
                var.getWrongUrlErrMsg());

        // Conditional validation on redirection page after check submission
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

