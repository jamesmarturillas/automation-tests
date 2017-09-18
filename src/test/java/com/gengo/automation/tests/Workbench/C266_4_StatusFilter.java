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
 * @case Status Filter - (Character to Character, Paypal Payment, Standard Tier)
 * @reference https://gengo.testrail.com/index.php?/cases/view/266
 */
public class C266_4_StatusFilter extends AutomationBase{

    private String parentWindow, orderNo;
    private String[] jobNo = new String[3];
    private String excerpt;
    private String[] itemToTranslate, unitCount, translatedItem,
            translatedItemRevised, translatedItemError, excerpts;

    @BeforeMethod
    public void initFields() throws IOException{
        excerpt = var.getExcerptNonEnglish(19);
        excerpts = new String[] {
                var.getExcerptNonEnglish(19),
                var.getExcerptNonEnglish(20),
                var.getExcerptNonEnglish(21)
        };
        itemToTranslate = new String[] {
                var.getItemToTranslate(34),
                var.getItemToTranslate(35),
                var.getItemToTranslate(36)
        };
        unitCount = new String[] {
                var.getUnitCountSource(34),
                var.getUnitCountSource(35),
                var.getUnitCountSource(36)
        };

        translatedItemError = new String[] {
                var.getTranslatedItem(20),
                var.getTranslatedItem(20),
                var.getTranslatedItem(20)
        };

        translatedItem = new String[] {
                var.getTranslatedItem(35),
                var.getTranslatedItem(35),
                var.getTranslatedItem(36)
        };

        translatedItemRevised = new String[] {
                var.getTranslatedItem(34),
                var.getTranslatedItem(35),
                var.getTranslatedItem(36)
        };
    }

    public C266_4_StatusFilter() throws IOException {}

    @AfterMethod
    public void afterRun() {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    /**
     * placeAnOrder --- a method for checking the logging in of a customer account,
     * and placing a grouped order
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
        assertTrue(customerOrderLanguagesPage.isSourceAutoDetected(var.getJapaneseFrom()),
                var.getTextNotEqualErrMsg());

        // Set the target language to Chinese Simplified
        customerOrderLanguagesPage.choooseLanguage(var.getChineseSimplifiedTo());
        customerOrderLanguagesPage.clickNextOptions();

        // Mark order as grouped job
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
        customerCheckoutPage.clickPayNowAndConfirm(true, false, false, false, false);

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
     * created job and opens the workbench
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
    }

    /**
     * translatorOpenJob --- a method wherein the translator opens the job workbench and checks for the Status Filters
     * available and whether these reflect the correct information
     * */
    @Test(dependsOnMethods = "translatorFindJob")
    public void translatorOpenJob() {

        workbenchPage.closeWorkbenchModal();

        // Open filter dropdown
        workbenchPage.openFilterDropDown();

        // Check if All and Available option are visible
        assertTrue(workbenchPage.isAllVisible(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.isAvailableVisible(), var.getElementIsNotDisplayedErrMsg());

        // Check if both Available and All count is 1 each
        assertEquals(workbenchPage.countAll(), 3);
        assertEquals(workbenchPage.countAvailable(), 3);

        // Click Filter All
        workbenchPage.filterAll();

        // Check if the Filter button is activated
        assertFalse(workbenchPage.isFilterSelected(), var.getElementIsDisplayedErrMsg());

        // Open filter dropdown
        workbenchPage.openFilterDropDown();

        // Click Filter Available
        workbenchPage.filterAvailable();

        // Check if the Filter button is activated
        assertTrue(workbenchPage.isFilterSelected(), var.getElementIsNotDisplayedErrMsg());

        // Open filter dropdown
        workbenchPage.openFilterDropDown();

        // Click Filter All
        workbenchPage.filterAll();
    }

    /**
     * translatorPickUpJob - this method has the translator strart translating the job and
     * check the corresponding status filters available
     * */
    @Test(dependsOnMethods = "translatorOpenJob")
    public void translatorPickUpJob() {
        // Translator picks up the job
        workbenchPage.startTranslateJob();

        // Check if unfiltered by default
        assertFalse(workbenchPage.isFilterSelected());

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

        // Check for the corresponding counts
        assertEquals(workbenchPage.countAll(), 3);
        assertEquals(workbenchPage.countEmpty(), 3);
        assertEquals(workbenchPage.countUnsubmitted(), 0);
        assertEquals(workbenchPage.countSubmitted(), 0);
        assertEquals(workbenchPage.countRevising(), 0);
        assertEquals(workbenchPage.countError(), 0);

        // Filter Empty
        workbenchPage.filterEmpty();
        assertTrue(workbenchPage.isFilterSelected());

        // Filter All
        workbenchPage.openFilterDropDown();
        workbenchPage.filterAll();
        assertFalse(workbenchPage.isFilterSelected());

        // Filter Unsubmitted
        workbenchPage.openFilterDropDown();
        workbenchPage.filterUnsubmittedDisabled();
        assertFalse(workbenchPage.isFilterSelected());

        // Filter Submitted
        workbenchPage.openFilterDropDown();
        workbenchPage.filterSubmittedDisabled();
        assertFalse(workbenchPage.isFilterSelected());

        // Filter Revising
        workbenchPage.openFilterDropDown();
        workbenchPage.filterRevisingDisabled();
        assertFalse(workbenchPage.isFilterSelected());

        // Filter Error
        workbenchPage.openFilterDropDown();
        workbenchPage.filterErrorDisabled();
        assertFalse(workbenchPage.isFilterSelected());

    }

    /**
     * translatorAddErroneousTranslatedText --- this method checks for the status filters available and their
     * corresponding counts when an erroneous translated text is placed
     * */
    @Test(dependsOnMethods = "translatorPickUpJob")
    public void translatorAddErroneousTranslatedText() {
        // Text with errors is added
        workbenchPage.clickInactiveText();

        workbenchPage.addMultipleTranslatedText(translatedItemError, excerpts, translatedItemError.length);

        wait.impWait(10);
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
        assertFalse(workbenchPage.isErrorDisabledVisible(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.isErrorVisible(), var.getElementIsNotDisplayedErrMsg());

        // Check for the corresponding counts
        assertEquals(workbenchPage.countAll(), 3);
        assertEquals(workbenchPage.countEmpty(), 0);
        assertEquals(workbenchPage.countUnsubmitted(), 3);
        assertEquals(workbenchPage.countSubmitted(), 0);
        assertEquals(workbenchPage.countRevising(), 0);
        assertEquals(workbenchPage.countError(), 3);

        // Filter Unsubmitted
        workbenchPage.filterUnsubmitted();
        assertTrue(workbenchPage.isFilterSelected());

        // Filter Error
        workbenchPage.openFilterDropDown();
        workbenchPage.filterError();
        assertTrue(workbenchPage.isFilterSelected());

        // Filter All
        workbenchPage.openFilterDropDown();
        workbenchPage.filterAll();
        assertFalse(workbenchPage.isFilterSelected());

        // Filter Empty
        workbenchPage.openFilterDropDown();
        workbenchPage.filterEmptyDisabled();
        assertFalse(workbenchPage.isFilterSelected());

        // Filter Submitted
        workbenchPage.openFilterDropDown();
        workbenchPage.filterSubmittedDisabled();
        assertFalse(workbenchPage.isFilterSelected());

        // Filter Revising
        workbenchPage.openFilterDropDown();
        workbenchPage.filterRevisingDisabled();
        assertFalse(workbenchPage.isFilterSelected());

    }

    /**
     * translatorAddTranslatedText --- this method adds a correct translated item for the job
     * and the status filters available and their counts are checked
     * */
    @Test(dependsOnMethods = "translatorAddErroneousTranslatedText")
    public void translatorAddTranslatedText() {
        // Text to be translated is added
        workbenchPage.clickInactiveText();
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

        // Check for the corresponding counts
        assertEquals(workbenchPage.countAll(), 3);
        assertEquals(workbenchPage.countEmpty(), 0);
        assertEquals(workbenchPage.countUnsubmitted(), 3);
        assertEquals(workbenchPage.countSubmitted(), 0);
        assertEquals(workbenchPage.countRevising(), 0);
        assertEquals(workbenchPage.countError(), 0);

        // Filter Unsubmitted
        workbenchPage.filterUnsubmitted();
        assertTrue(workbenchPage.isFilterSelected());

        // Filter All
        workbenchPage.openFilterDropDown();
        workbenchPage.filterAll();
        assertFalse(workbenchPage.isFilterSelected());

        // Filter Empty
        workbenchPage.openFilterDropDown();
        workbenchPage.filterEmptyDisabled();
        assertFalse(workbenchPage.isFilterSelected());

        // Filter Submitted
        workbenchPage.openFilterDropDown();
        workbenchPage.filterSubmittedDisabled();
        assertFalse(workbenchPage.isFilterSelected());

        // Filter Revising
        workbenchPage.openFilterDropDown();
        workbenchPage.filterRevisingDisabled();
        assertFalse(workbenchPage.isFilterSelected());

        // Filter Error
        workbenchPage.openFilterDropDown();
        workbenchPage.filterErrorDisabled();
        assertFalse(workbenchPage.isFilterSelected());
    }

    /**
     * translatorSubmit --- this method has the translator submit the translation and log out afterwards
     * */
    @Test(dependsOnMethods = "translatorAddTranslatedText")
    public void translatorSubmitted() {
        // Translator submits job
        workbenchPage.submitJob();
        workbenchPage.submitModalOk();

        // Go to Jobs Page
        translatorDashboardPage.clickJobsTab();

        // Display reviewable jobs
        translatorJobsPage.clickReviewable();

        // Look for job
        translatorJobsPage.findJob(excerpt);

        // Check if unfiltered by default
        assertFalse(workbenchPage.isFilterSelected());

        // Open filter dropdown
        workbenchPage.openFilterDropDown();

        // Check if filter options are visible
        assertTrue(workbenchPage.isAllVisible(), var.getElementIsNotDisplayedErrMsg());
        assertFalse(workbenchPage.isAvailableVisible(), var.getElementIsNotDisplayedErrMsg());
        assertFalse(workbenchPage.isEmptyVisible(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.isEmptyDisabledVisible(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.isUnsubmittedDisabledVisible(), var.getElementIsNotDisplayedErrMsg());
        assertFalse(workbenchPage.isUnsubmittedVisible(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.isSubmittedVisible(), var.getElementIsNotDisplayedErrMsg());
        assertFalse(workbenchPage.isSubmittedDisabledVisible(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.isRevisingDisabledVisible(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.isErrorDisabledVisible(), var.getElementIsNotDisplayedErrMsg());

        // Check for the corresponding counts
        assertEquals(workbenchPage.countAll(), 3);
        assertEquals(workbenchPage.countEmpty(), 0);
        assertEquals(workbenchPage.countUnsubmitted(), 0);
        assertEquals(workbenchPage.countSubmitted(), 3);
        assertEquals(workbenchPage.countRevising(), 0);
        assertEquals(workbenchPage.countError(), 0);

        // Filter Submitted
        workbenchPage.filterSubmitted();
        assertTrue(workbenchPage.isFilterSelected());

        // Filter All
        workbenchPage.openFilterDropDown();
        workbenchPage.filterAll();
        assertFalse(workbenchPage.isFilterSelected());

        // Filter Empty
        workbenchPage.openFilterDropDown();
        workbenchPage.filterEmptyDisabled();
        assertFalse(workbenchPage.isFilterSelected());

        // Filter Unsubmitted
        workbenchPage.openFilterDropDown();
        workbenchPage.filterUnsubmittedDisabled();;
        assertFalse(workbenchPage.isFilterSelected());

        // Filter Revising
        workbenchPage.openFilterDropDown();
        workbenchPage.filterRevisingDisabled();
        assertFalse(workbenchPage.isFilterSelected());

        // Filter Error
        workbenchPage.openFilterDropDown();
        workbenchPage.filterErrorDisabled();
        assertFalse(workbenchPage.isFilterSelected());

        // Translator account is logged out
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * customerRequestCorrection --- customer requests a correction on 1 of the submitted jobs
     * from the grouped order
     * */
    @Test(dependsOnMethods = "translatorSubmitted")
    public void customerRequestCorrection() {
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(25), var.getDefaultPassword());
        global.selectCustomer();
        assertTrue(customerDashboardPage.checkCustomerDashboard());
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());
        globalPage.goToOrdersPage();
        customerOrdersPage.clickReviewableOption();
        customerOrdersPage.findOrder(excerpt);

        // Request correction for job
        customerOrderDetailsPage.requestCorrection(var.getCustomerCorrection(), true, true, true);

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * translatorRevise --- translator resolves the revision and submits the job
     * */
    @Test(dependsOnMethods = "customerRequestCorrection")
    public void translatorRevise() {
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

        workbenchPage.addMultipleTranslatedText(translatedItemRevised, excerpts, translatedItemRevised.length);

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

        // Check for the corresponding counts
        assertEquals(workbenchPage.countAll(), 3);
        assertEquals(workbenchPage.countEmpty(), 0);
        assertEquals(workbenchPage.countUnsubmitted(), 0);
        assertEquals(workbenchPage.countSubmitted(), 2);
        assertEquals(workbenchPage.countRevising(), 1);
        assertEquals(workbenchPage.countError(), 0);

        // Filter Unsubmitted
        workbenchPage.filterUnsubmitted();
        assertTrue(workbenchPage.isFilterSelected());

        // Filter All
        workbenchPage.openFilterDropDown();
        workbenchPage.filterAll();
        assertFalse(workbenchPage.isFilterSelected());

        // Filter Empty
        workbenchPage.openFilterDropDown();
        workbenchPage.filterEmptyDisabled();
        assertFalse(workbenchPage.isFilterSelected());

        // Filter Submitted
        workbenchPage.openFilterDropDown();
        workbenchPage.filterSubmittedDisabled();
        assertFalse(workbenchPage.isFilterSelected());

        // Filter Revising
        workbenchPage.openFilterDropDown();
        workbenchPage.filterRevisingDisabled();
        assertFalse(workbenchPage.isFilterSelected());

        // Filter Error
        workbenchPage.openFilterDropDown();
        workbenchPage.filterErrorDisabled();
        assertFalse(workbenchPage.isFilterSelected());

        // Translator submits job
        workbenchPage.submitJob();
        workbenchPage.submitModalOk();

        // Translator account is logged out
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * customerApprove --- this method has the customer approve the job
     * */
    @Test(dependsOnMethods = "translatorSubmitted")
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
