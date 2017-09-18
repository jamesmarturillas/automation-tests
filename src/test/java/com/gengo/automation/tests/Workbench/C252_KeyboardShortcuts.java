package com.gengo.automation.tests.Workbench;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @case Keyboard Shortcuts - (Word to Character, Paypal Payment, Business Tier)
 * @reference https://gengo.testrail.com/index.php?/cases/view/252
 */
public class C252_KeyboardShortcuts extends AutomationBase{
    private String parentWindow, orderNo, jobNo;
    private String[] itemToTranslate;
    private String translatedText1, translatedText2, excerpt;
    private String[] unitCount;

    @BeforeClass
    public void initFields() throws IOException{
        excerpt = var.getExcerptEnglish(17);
        itemToTranslate = new String[] {
                var.getItemToTranslate(17)
        };
        translatedText1 = var.getTranslatedItem(17).substring(0,20);
        translatedText2 = var.getTranslatedItem(17);
        unitCount = new String[] { var.getUnitCountSource(17) };
    }

    public C252_KeyboardShortcuts() throws IOException {}

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

        // Choose glossary file to be used
        customerCheckoutPage.addGlossary("en_to_ja");

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
     * translatorOpenJob --- a method wherein the translator opens the job workbench and checks for the Status Filters
     * available and whether these reflect the correct information
     * */
    @Test(dependsOnMethods = "translatorFindJob")
    public void translatorOpenJob() throws AWTException {
        workbenchPage.closeWorkbenchModal();

        // open shortcut
        workbenchPage.keyboardShortcutOpenKey(true);
        assertTrue(workbenchPage.isKeyboardShortcutVisible());
        workbenchPage.keyboardShortcutOpenKey(false);
        assertFalse(workbenchPage.isKeyboardShortcutVisible());

    }

    /**
     * translatorPickUpJob - this method has the translator start translating the job and
     * check the corresponding status filters available
     * */
    @Test(dependsOnMethods = "translatorOpenJob")
    public void translatorPickUpJob() throws AWTException {
        // start job
        workbenchPage.toggleStartSubmitKey(true);

        // source to target -- check target and source
        workbenchPage.copySourceToTargetKey();
        assertEquals(workbenchPage.getSource(), workbenchPage.getTarget());
        assertEquals(workbenchPage.getJobStatus(), "Not Submitted");

        // close comment
        workbenchPage.toggleCommentsKey(true);
        assertTrue(workbenchPage.isCommentsSectionVisible());

        // open issues
        workbenchPage.toggleIssuesKey(true);
        assertTrue(workbenchPage.isIssuesVisible());

        // open glossary
        workbenchPage.toggleGlossaryKey(true);
        assertTrue(workbenchPage.isGlossaryVisible());

        // close glossary
        workbenchPage.toggleGlossaryKey(false);
        assertFalse(workbenchPage.isGlossaryVisible());

        // filter unsubmitted
        workbenchPage.filterUnsubmittedKey();
        assertTrue(workbenchPage.isJobFilteredVisible(excerpt));
        assertTrue(workbenchPage.isFilterSelected());

        // exit to dashboard
        workbenchPage.closeCollectionKey();

        // Check if the page redirected to contains the elements that should be seen in a Translator Dashboard
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());
    }

    /**
     * translatorAddTranslatedText --- this method adds a correct translated item for the job
     * and the status filters available and their counts are checked
     * */
    @Test(dependsOnMethods = "translatorPickUpJob")
    public void translatorAddTranslatedText() throws AWTException {
        // Navigate to the Jobs Page and look for the recently created job
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt);
        workbenchPage.closeWorkbenchModal();

        // Text to be translated is added
        workbenchPage.translateTextArea(translatedText1);

        // decline and check
        workbenchPage.declineCollectionKey();
        assertTrue(workbenchPage.isDeclineModalVisible());
        workbenchPage.cancelDecline();
        assertFalse(workbenchPage.isDeclineModalVisible());

        //submit
        workbenchPage.toggleStartSubmitKey(false);
        workbenchPage.submitModalOk();
    }

    /**
     * translatorSubmit --- this method has the translator submit the translation and log out afterwards
     * */
    @Test(dependsOnMethods = "translatorAddTranslatedText")
    public void translatorSubmitted() throws AWTException {
        // Go to Jobs Page
        translatorDashboardPage.clickJobsTab();

        // Display reviewable jobs
        translatorJobsPage.clickReviewable();

        // Look for job
        translatorJobsPage.findJob(excerpt);

        workbenchPage.closeWorkbenchModal();

        workbenchPage.filterSubmittedKey();
        assertTrue(workbenchPage.isJobFilteredVisible(excerpt));

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

        workbenchPage.clickInactiveText();
        workbenchPage.translateTextArea(translatedText2);

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
    @Test(dependsOnMethods = "translatorRevise")
    public void customerApprove() {
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

        // Retrieve the job Number of the order
        jobNo = customerOrderDetailsPage.getJobNumberReviewableJob();

        // Job is approved
        customerOrderDetailsPage.approveJob();
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
        assertTrue(gmailInboxPage.checkJobForReview(jobNo));
        assertTrue(gmailInboxPage.checkJobApproved(jobNo));
    }
}
