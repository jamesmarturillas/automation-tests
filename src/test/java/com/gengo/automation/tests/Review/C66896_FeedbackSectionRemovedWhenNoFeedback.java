package com.gengo.automation.tests.Review;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @case Translators should not be able to see the "Feedback to the translator:" portion of review
 * @reference https://www.pivotaltracker.com/story/show/146507023
 */
public class C66896_FeedbackSectionRemovedWhenNoFeedback extends AutomationBase {

    public C66896_FeedbackSectionRemovedWhenNoFeedback() throws IOException {}

    private String parentWindow, orderNo, excerpt, translatedItem, jobNo, price, reviewURL;
    private String[] unitCount, itemToTranslate;

    @BeforeClass
    public void initFields() throws IOException{
        excerpt = var.getExcerptEnglish(33);
        itemToTranslate = new String[] {
                var.getItemToTranslate(64)
        };
        unitCount = new String[] {
                var.getUnitCountSource(64)
        };
        translatedItem = var.getTranslatedItem(64);
    }

    @AfterClass
    public void afterRun() throws IOException {
        csv.writeValue(this.newUser, var.SHEETNAME_CUSTOMERS, 0);
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test
    public void placeAnOrder() throws IOException {
        pluginPage.passThisPage();

        // Check if the prominent page elements can be seen on the Home Page
        assertTrue(homePage.checkHomePage());
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Logging in a customer account
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(102), var.getDefaultPassword());

        // Check if the prominent page elements can be seen on the Customer Dashboard Page
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());
        assertTrue(customerDashboardPage.checkCustomerDashboard());

        // Click the icon for placing an order
        global.clickOrderTranslationIcon();

        // Place the text to be translated on the order form and check for the word/character count
        customerOrderFormPage.inputItemToTranslate(itemToTranslate, unitCount, itemToTranslate.length, false);

        // Check if source language is auto detected by the system
        assertTrue(page.getCurrentUrl().equals(customerOrderLanguagesPage.ORDERLANGUAGES_URL),
                var.getWrongUrlErrMsg());
        assertTrue(customerOrderLanguagesPage.isSourceAutoDetected(),
                var.getTextNotEqualErrMsg());

        // Set the target language to Japanese`
        customerOrderLanguagesPage.choooseLanguage(var.getJapaneseTo());
        customerOrderLanguagesPage.clickNextOptions();

        // Retrieve the total order price for comparison across pages later
        price = customerCheckoutPage.getOrderTotalPrice().getText();

        // Check the 'View Full Quote' page and the generated pdf File
        customerCheckoutPage.clickViewFullQuote();
        parentWindow = switcher.getWindowHandle();
        switcher.switchToPopUp();
        wait.impWait(3);
        assertTrue(page.getCurrentUrl().equals(customerOrderQuotePage.CUSTOMERORDERQUOTE_URL),
                var.getWrongUrlErrMsg());
        customerOrderQuotePage.typeAdress();
        assertTrue(customerOrderQuotePage.getAddressEmbedded().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Check if the price displayed on the full quote page is consistent with total order price
        assertEquals(price, customerCheckoutPage.getFullQuoteTotalPrice().getText(), var.getTextNotEqualErrMsg());

        // Download Quote File
        customerOrderQuotePage.downloadQuote();
        switcher.switchToParentWindow(parentWindow);

        // Place payment via Stripe
        customerCheckoutPage
                .clickPayNowAndConfirm(false, true, true, true, true);

        // Check if the price on the Stripe Modal is consistent with the total order price
        assertEquals(price, customerCheckoutPage.returnStripePrice(), var.getTextNotEqualErrMsg());

        // Retrieve the order number
        orderNo = customerOrderCompletePage.orderNumber();

        // Return to dashboard page
        customerOrderCompletePage.clickGoToDashboard();

        // Open the recently created job
        globalPage.goToOrdersPage();
        customerOrdersPage.clickPendingOption();
        customerOrdersPage.findOrder(excerpt);

        // Check if the price is consistent with the one displayed on the order checkout page
        assertEquals(price, customerOrderDetailsPage.getOrderPrice().getText(), var.getAmountIsNotRightErrMsg());

        // Customer sign out
        global.nonAdminSignOut();

        // Check if the redirected page contains the prominent Home Page elements
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(homePage.checkHomePage());
    }

    @Test(dependsOnMethods = "placeAnOrder")
    public void pickUpByTranslator() throws IOException {
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Log in a translator account
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslatorStandard(3), var.getDefaultPassword());

        // Check if the page redirected to contains the elements that should be seen in a Translator Dashboard
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        // Navigate to the Jobs Page and look for the recently created job
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt);

        // Close workbench modal and start translation
        workbenchPage.closeWorkbenchModal();
        workbenchPage.startTranslate();

        // Text to be translated is added
        workbenchPage.translateAndSubmitJob(translatedItem);

        // Check if page is redirected to the translator Dashboard
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        // Translator account is logged out
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    @Test(dependsOnMethods = "pickUpByTranslator")
    public void customerRejectsJob() {
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(102), var.getDefaultPassword());
        global.selectCustomer();
        assertTrue(customerDashboardPage.checkCustomerDashboard());
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());
        globalPage.goToOrdersPage();
        customerOrdersPage.clickReviewableOption();
        customerOrdersPage.findOrder(excerpt);

        // Retrieve the job Number of the order
        jobNo = customerOrderDetailsPage.getJobNumberReviewableJob();

        // Job is rejected
        customerOrderDetailsPage.rejectTranslation(var.getCustomerCorrection(),false, "Quality");
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    @Test(dependsOnMethods = "customerRejectsJob")
    public void gocheckWithoutFeedback() {
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

        // Filter checks to trigger rejected job
        adminChecksPage.filterChecksTrigger("rejectedJob");
        adminChecksPage.filterChecksStatus("pending");
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

        // Submit 10.0 score check without Feedback
        goCheckPage.clickSubmitGocheck();
        assertTrue(goCheckPage.getSubmitConfirmationModal().isDisplayed());
        goCheckPage.clickSubmitConfirmationModal("confirm");

        // Assert page returned to admin and gochecked ID is on list with status "complete"
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(page.getCurrentUrl().contains("admin."),
                var.getWrongUrlErrMsg());

        // Rejected Job triggered check will be redirected to Job details page
        assertTrue(adminJobsPage.getJobDetailsJobID().isDisplayed());
        assertEquals(adminJobsPage.jobIDNumber(),
                adminChecksPage.retrieveJobID());
    }

    @Test(dependsOnMethods = "gocheckWithoutFeedback")
    public void goToReview() {
        page.launchUrl(var.getGmailUrl());
        assertTrue(gmailSignInEmailPage.getTxtBoxEmail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Log in on Gmail account
        gmailSignInEmailPage.inputEmail(var.getGmailEmail());
        gmailSignInPasswordPage.inputPasswordAndSubmit(var.getGmailPassword());

        // Check email for Job reviewed email
        assertTrue(gmailInboxPage.checkJobReviewed(jobNo));

        // Click link on email to review page
        gmailInboxPage.clickJobReviewed(adminChecksPage.retrieveJobID());

        // Go to review page and Store the review URL for translator's use
        assertTrue(gmailJobReviewedPage.getCheckFeedback().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        reviewURL = gmailJobReviewedPage.getCheckFeedback().getAttribute("href");
        gmailJobReviewedPage.clickCheckFeedback();

        // Assert page is on review page
        assertTrue(page.getCurrentUrl().contains("review."),
                var.getWrongUrlErrMsg());

        // Check existence of feedback area if removed
        wait.impWait(30, reviewPage.getTargetContent());
        assertFalse(reviewPage.getFeedbackArea().isDisplayed());
    }

    @Test(dependsOnMethods = "goToReview")
    public void goToReviewAsTranslator() {
        page.launchUrl(var.getBaseUrl());
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Log out existing account
        homePage.clickSignInButton();
        global.nonAdminSignOut();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Log in the translator account
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslatorStandard(3), var.getDefaultPassword());

        // Check if the page redirected to contains the elements that should be seen in a Translator Dashboard
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        // Go to review page as translator
        page.launchUrl(reviewURL);
        assertTrue(page.getCurrentUrl().contains("review."),
                var.getWrongUrlErrMsg());

        // Check existence of feedback area if removed
        wait.impWait(30, reviewPage.getTargetContent());
        assertFalse(reviewPage.getFeedbackArea().isDisplayed());
    }
}
