package com.gengo.automation.tests.Workbench;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @case Nav - (Word to Character, Paypal Payment, Business Tier)
 * @reference https://gengo.testrail.com/index.php?/cases/view/268
 */
public class C268_1_Nav extends AutomationBase{

    private String parentWindow, orderNo, currentUrl;
    private String[] jobNo = new String[3];
    private String excerpt;
    private String[] itemToTranslate, unitCount, translatedItem, excerpts;

    @BeforeMethod
    public void initFields() throws IOException{
        excerpt = var.getExcerptEnglish(19);
        excerpts = new String[] {
                var.getExcerptEnglish(19),
                var.getExcerptEnglish(20),
                var.getExcerptEnglish(21)
        };
        itemToTranslate = new String[] {
                var.getItemToTranslate(25),
                var.getItemToTranslate(26),
                var.getItemToTranslate(27)
        };
        unitCount = new String[] {
                var.getUnitCountSource(25),
                var.getUnitCountSource(26),
                var.getUnitCountSource(27)
        };
        translatedItem = new String[] {
                var.getTranslatedItem(25),
                var.getTranslatedItem(26),
                var.getTranslatedItem(27)
        };
    }

    public C268_1_Nav() throws IOException {}

    @AfterMethod
    public void afterRun() {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    /**
     * placeAnOrder --- a method for checking the logging in of a customer account,
     * and placing a grouped order with 3 text jobs
     * */
    @Test
    public void placeAnOrder() {
        pluginPage.passThisPage();

        // Check if the prominent page elements can be seen on the Home Page
        assertTrue(homePage.checkHomePage());
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Logging in a customer account
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(25), var.getDefaultPassword());

        // Ensure that the account type is set to 'Customer'
        global.selectCustomer();

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

        // Set the target language to Japanese
        customerOrderLanguagesPage.choooseLanguage(var.getJapaneseTo());
        customerOrderLanguagesPage.clickNextOptions();

        // Click the Business Tier Radio button
        customerCheckoutPage.businessTier(true);

        // Mark the order as grouped job
        customerCheckoutPage.orderAsAGroup();

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
        customerOrderQuotePage.downloadQuote();
        switcher.switchToParentWindow(parentWindow);

        // Place payment via Paypal
        customerCheckoutPage
                .clickPayNowAndConfirm(false, true, true, true, true);

        // Retrieve the order number
        orderNo = customerOrderCompletePage.orderNumber();

        // Return to dashboard page
        customerOrderCompletePage.clickGoToDashboard();

        // Customer sign out
        global.nonAdminSignOut();

        // Check if the redirected page contains the prominent Home Page elements
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(homePage.checkHomePage());
    }

    /**
     * translatorFindJob --- a method wherein a translator signs in and looks for the recently
     * created job and opens the workbench; this method also checks for the navigation menu on the workbench page
     * */
    @Test(dependsOnMethods = "placeAnOrder")
    public void translatorFindJob() {
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Log in a translator account
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslator(26), var.getDefaultPassword());

        // Check if the page redirected to contains the elements that should be seen in a Translator Dashboard
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        // Navigate to the Jobs Page and look for the recently created job
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt);

        workbenchPage.closeWorkbenchModal();

        // store URL of job for accessing later
        currentUrl = getDriver().getCurrentUrl();

        // click the logo
        globalPage.goToTranslatorDashboardPage();
        assertEquals(getDriver().getCurrentUrl(), globalPage.TRANSLATOR_DASHBOARD_URL);
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        // go to Jobs Page
        page.launchUrl(currentUrl);
        globalPage.clickJobsTab();
        assertEquals(getDriver().getCurrentUrl(), globalPage.TRANSLATOR_JOBS_PAGE_URL);

        // go to Tests Page
        page.launchUrl(currentUrl);
        globalPage.clickTestsTab();
        assertEquals(getDriver().getCurrentUrl(), globalPage.TRANSLATOR_TESTS_PAGE_URL);

        // go to Resources Page
        page.launchUrl(currentUrl);
        globalPage.clickResourcesTab();
        assertEquals(getDriver().getCurrentUrl(), globalPage.TRANSLATOR_RESOURCES_PAGE_URL);

        // go to Community Page
        page.launchUrl(currentUrl);
        globalPage.clickCommunityTab();
        assertEquals(getDriver().getCurrentUrl(), globalPage.COMMUNITY_PAGE_URL);

        // go to Support Page
        page.launchUrl(currentUrl);
        globalPage.clickSupportTab();
        assertEquals(getDriver().getCurrentUrl(), globalPage.SUPPORT_PAGE_URL);

        // Go back to workbench of previously searched job
        page.launchUrl(currentUrl);

        assertTrue(workbenchPage.isStartTranslatingVisible());
    }

    /**
     * translatorPickUpJob --- the translator picks up the job and the elements of the workbench
     * area are checked including the left and right information panels
     * */
    @Test(dependsOnMethods = "translatorFindJob")
    public void translatorPickUpJob() {
        // Translator picks up the job
        workbenchPage.startTranslateJob();

        // Check if the translate button changed after picking up the job
        assertTrue(workbenchPage.isNoJobToSubmitVisible());
        assertFalse(workbenchPage.isStartTranslatingVisible());

        // Check if Decline Job option is available
        workbenchPage.clickDeclineJobDropDown();
        assertTrue(workbenchPage.isDeclineButtonVisible());

        // Check if unfiltered by default
        assertFalse(workbenchPage.isFilterSelected());

        // Check if horizontal by default
        assertTrue(workbenchPage.isHorizontalSelected());

        // Open filter dropdown
        workbenchPage.openFilterDropDown();

        // Check if filter options are visible
        assertTrue(workbenchPage.isAllVisible(), var.getElementIsNotDisplayedErrMsg());
        assertFalse(workbenchPage.isAvailableVisible(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.isEmptyVisible(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.isUnsubmittedDisabledVisible(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.isSubmittedDisabledVisible(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.isRevisingDisabledVisible(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.isErrorDisabledVisible(), var.getElementIsNotDisplayedErrMsg());

        // Check More DropDown
        assertTrue(workbenchPage.checkMoreDropDown());

        // Check How to Use Option
        workbenchPage.clickMore();
        assertTrue(workbenchPage.checkMoreHowToUse());

        // Check Keyboard Shortcuts
        workbenchPage.clickMore();
        assertTrue(workbenchPage.checkMoreKeyboardShortcuts());

        // Check comments Section
        workbenchPage.toggleCommentsSection();
        assertTrue(workbenchPage.isCommentsSectionVisible());
        workbenchPage.toggleCommentsSection();
        assertFalse(workbenchPage.isCommentsSectionVisible());

        // Check Job Info
        assertTrue(workbenchPage.checkJobInfo());

        // Check Right Panel
        workbenchPage.openIssuesSection();
        assertTrue(workbenchPage.isIssuesVisible());
        workbenchPage.openGlossarySection();
        assertTrue(workbenchPage.isGlossaryVisible());
    }

    /**
     * translatorAddTranslatedText --- this method adds a translated text to the workbench and submits the job
     * */
    @Test(dependsOnMethods = "translatorPickUpJob")
    public void translatorAddTranslatedText() {
        // Translate the job.
        workbenchPage.addMultipleTranslatedText(translatedItem, excerpts, translatedItem.length);

        // Check if unfiltered by default
        assertFalse(workbenchPage.isFilterSelected());

        // Open filter dropdown
        workbenchPage.openFilterDropDown();

        // Check if filter options are visible
        assertTrue(workbenchPage.isAllVisible(), var.getElementIsNotDisplayedErrMsg());
        assertFalse(workbenchPage.isAvailableVisible(), var.getElementIsNotDisplayedErrMsg());
        assertFalse(workbenchPage.isEmptyVisible(), var.getElementIsNotDisplayedErrMsg());
        assertFalse(workbenchPage.isUnsubmittedDisabledVisible(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.isUnsubmittedVisible(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.isSubmittedDisabledVisible(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.isRevisingDisabledVisible(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.isErrorDisabledVisible(), var.getElementIsNotDisplayedErrMsg());

        // Translator submits job
        workbenchPage.submitJob();
        workbenchPage.submitModalOk();

        // Translator Sign Out
        global.nonAdminSignOut();
    }

    /**
     * customerApprove --- this method has the customer approve the job
     * */
    @Test(dependsOnMethods = "translatorAddTranslatedText")
    public void customerApprove() {
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(25), var.getDefaultPassword());
        global.selectCustomer();
        assertTrue(customerDashboardPage.checkCustomerDashboard());
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());

        // Loops through the jobs and approves them all
        for(int ctr = 0; ctr < excerpts.length; ctr++) {
            globalPage.goToOrdersPage();
            customerOrdersPage.clickReviewableOption();
            customerOrdersPage.findOrder(excerpts[ctr]);

            // Retrieve the job Number of the order
            jobNo[ctr] =customerOrderDetailsPage.getJobNumberReviewableJob();

            // Job is approved
            customerOrderDetailsPage.approveJob();
        }

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * checkEmail --- this method logs in the on the email of the customer
     * and checks if the emails for order received, job review, flag, and
     * approval are visible
     * */
    @Test(dependsOnMethods = "customerApprove")
    public void checkEmail() {
        page.launchUrl(var.getGmailUrl());
        assertTrue(gmailSignInEmailPage.getTxtBoxEmail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Log in on Gmail account
        gmailSignInEmailPage.inputEmail(var.getGmailEmail());
        gmailSignInPasswordPage.inputPasswordAndSubmit(var.getGmailPassword());

        // Check if the emails for Order Received, Review, and Approval are received
        assertTrue(gmailInboxPage.checkOrderReceived(orderNo));
        for(String jobNumber : jobNo) {
            assertTrue(gmailInboxPage.checkJobForReview(jobNumber));
            assertTrue(gmailInboxPage.checkJobApproved(jobNumber));
        }
    }
}
