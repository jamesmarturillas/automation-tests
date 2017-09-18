package com.gengo.automation.tests.Workbench;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @case Job ID And Status Info - (Word to Character, Paypal Payment, Business Tier)
 * @reference https://gengo.testrail.com/index.php?/cases/view/54343
 */
public class C54343_1_JobIDAndStatusInfo extends AutomationBase{
    private String parentWindow, orderNo, jobNo;
    private String[] itemToTranslate;
    private String translatedText1, translatedText1Revised, excerpt;
    private String[] unitCount;
    private final String AVAILABLE = "Available";
    private final String SUBMITTED = "Submitted";
    private final String NOT_SUBMITTED = "Not Submitted";
    private final String APPROVED = "Approved";
    private final String REVISING = "Revising";
    private final String ON_HOLD = "On Hold";
    private String jobID;

    @BeforeClass
    public void initFields() throws IOException{
        excerpt = var.getExcerptEnglish(17);
        itemToTranslate = new String[] {
                var.getItemToTranslate(17)
        };
        translatedText1 = var.getTranslatedItem(17).substring(1,20);
        translatedText1Revised = var.getTranslatedItem(17).substring(1,25);
        unitCount = new String[] { var.getUnitCountSource(17) };
    }

    public C54343_1_JobIDAndStatusInfo() throws IOException {}

    @AfterClass
    public void afterRun() {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    /**
     * placeAnOrder --- a method for checking the logging in of a customer account,
     * and placing an order with glossary chosen
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
        loginPage.loginAccount(var.getCustomer(26), var.getDefaultPassword());

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
        customerCheckoutPage.clickPayNowAndConfirm(false, true, true, true, true);

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
     * translatorOpenJob --- a method wherein the translator opens the job workbench and checks for
     * Job Info: ID and Status
     * */
    @Test(dependsOnMethods = "translatorFindJob")
    public void translatorOpenJob() {
        workbenchPage.closeWorkbenchModal();

        // Retrieves the Job ID and stores it in a variable for comparison later
        jobID = workbenchPage.getJobId();

        // Check if the Job status is correct
        assertEquals(workbenchPage.getJobStatus(), AVAILABLE);
    }

    /**
     * translatorPickUpJob - this method has the translator start translating the job and
     * check the corresponding job information
     * */
    @Test(dependsOnMethods = "translatorOpenJob")
    public void translatorPickUpJob() {
        // Translator picks up the job
        workbenchPage.startTranslateJob();

        // Refresh the page
        page.refresh();

        // Check the comments section
        workbenchPage.toggleCommentsSection();
        wait.impWait(15);

        // Check if the job ID is same as the one retrieved earlier
        assertEquals(workbenchPage.getJobId(), jobID);
        assertEquals(workbenchPage.getJobStatus(), NOT_SUBMITTED);
    }

    /**
     * translatorAddTranslatedText --- this method adds a correct translated item for the job
     * and checks for the job information
     * */
    @Test(dependsOnMethods = "translatorPickUpJob")
    public void translatorAddTranslatedText() {
        // Text to be translated is added
        workbenchPage.clickInactiveText();
        workbenchPage.translateTextArea(translatedText1);

        // Check for the job information
        workbenchPage.toggleCommentsSection();
        wait.untilElementIsClickable(workbenchPage.getJobInfo());
        assertEquals(workbenchPage.getJobId(), jobID);
        assertEquals(workbenchPage.getJobStatus(), NOT_SUBMITTED);
    }

    /**
     * translatorSubmitted --- this method has the translator submit the translation and opens the job workbench
     * after the translation has been submitted to check for the job information
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
        workbenchPage.closeWorkbenchModal();

        // Check for the job information
        assertEquals(workbenchPage.getJobId(), jobID);
        assertEquals(workbenchPage.getJobStatus(), SUBMITTED);

        // Translator account is logged out
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * customerRequestCorrection --- customer requests a correction for the job submitted
     * */
    @Test(dependsOnMethods = "translatorSubmitted")
    public void customerRequestCorrection() {
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(26), var.getDefaultPassword());
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

        // Check for the job information
        workbenchPage.closeWorkbenchModal();
        assertEquals(workbenchPage.getJobId(), jobID);
        assertEquals(workbenchPage.getJobStatus(), REVISING);

        // Add updated translated and submit job
        workbenchPage.translateAndSubmitJob(translatedText1Revised);

        // Translator account is logged out
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * customerReject --- customer rejects the job
     * */
    @Test(dependsOnMethods = "translatorRevise")
    public void customerReject() {
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(26), var.getDefaultPassword());
        global.selectCustomer();
        assertTrue(customerDashboardPage.checkCustomerDashboard());
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());
        globalPage.goToOrdersPage();
        customerOrdersPage.clickReviewableOption();
        customerOrdersPage.findOrder(excerpt);

        // Request correction for job
        customerOrderDetailsPage.rejectTranslation(var.getCustomerCorrection(), false, "Incomplete");

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * translatorRevise --- translator resolves the revision and submits the job
     * */
    @Test(dependsOnMethods = "customerReject")
    public void translatorHandle() {

        //ON HOLD
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
        translatorJobsPage.clickHeld();
        translatorJobsPage.findJob(excerpt);

        workbenchPage.closeWorkbenchModal();

        // Check for the job information
        assertEquals(workbenchPage.getJobId(), jobID);
        assertEquals(workbenchPage.getJobStatus(), ON_HOLD);

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * checkEmail --- this method logs in the on the email of the customer
     * and checks if the emails for order received, job review, flag, and
     * approval are visible
     * */
    @Test(dependsOnMethods = "translatorHandle")
    public void checkEmail() {
        page.launchUrl(var.getGmailUrl());
        assertTrue(gmailSignInEmailPage.getTxtBoxEmail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Log in on Gmail account
        gmailSignInEmailPage.inputEmail(var.getGmailEmail());
        gmailSignInPasswordPage.inputPasswordAndSubmit(var.getGmailPassword());

        // Check if the emails for Order Received, Review, and Approval are received
        assertTrue(gmailInboxPage.checkOrderReceived(orderNo));
        assertTrue(gmailInboxPage.checkJobForReview(jobNo));
    }
}
