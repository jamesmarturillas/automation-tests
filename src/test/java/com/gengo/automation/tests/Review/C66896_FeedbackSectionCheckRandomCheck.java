package com.gengo.automation.tests.Review;

import com.gengo.automation.global.AutomationBase;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @case Translators should (not) be able to see the "Feedback to the translator:" portion of review
 * @reference https://www.pivotaltracker.com/story/show/146507023
 */
public class C66896_FeedbackSectionCheckRandomCheck extends AutomationBase {

    private String gocheckURL, reviewURL, commentExistence;

    public C66896_FeedbackSectionCheckRandomCheck() throws IOException {}

    @Test
    public void selectRandomGocheck() {
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

        // Store Check Trigger and Job ID
        adminChecksPage.setCheckStatus();
        adminChecksPage.setTrigger();

        // Go to Actions > Check or Edit depends on check status
        adminChecksPage.navigateGoCheckPageviaStatus(adminChecksPage.retrieveCheckStatus());
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(page.getCurrentUrl().contains("gocheck."),
                var.getWrongUrlErrMsg());

        // Check the feedback area on gocheck if there is content
        commentExistence = goCheckPage.getGocheckFeedbackTextArea().getText();
        if (commentExistence.isEmpty()) {
            commentExistence = "commentAbsent";
        }else {
            commentExistence = "commentPresent";
        }

        // Get gocheck url and replace with review url
        gocheckURL = page.getCurrentUrl();
        reviewURL = gocheckURL.replace("gocheck", "review");
    }

    @Test(dependsOnMethods = "selectRandomGocheck")
    public void goToReviewPage() {
        page.launchUrl(reviewURL);
        assertTrue(page.getCurrentUrl().contains("review."),
                var.getWrongUrlErrMsg());
        wait.impWait(30, reviewPage.getTargetContent());

        // Validation depending on existence of feedback
        switch (commentExistence) {
            case "commentPresent":
                assertTrue(reviewPage.getFeedbackArea().isDisplayed());
                break;
            case "commentAbsent":
                assertFalse(reviewPage.getFeedbackArea().isDisplayed());
                break;
        }
    }
}
