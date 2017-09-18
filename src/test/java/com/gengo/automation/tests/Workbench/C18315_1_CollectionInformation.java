package com.gengo.automation.tests.Workbench;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @case Collection Information - (Word to Character, Paypal Payment, Business Tier)
 * @reference https://gengo.testrail.com/index.php?/cases/view/18315
 */
public class C18315_1_CollectionInformation extends AutomationBase{
    private String parentWindow, orderNo, excerpt;
    private String[] itemToTranslate, unitCount, excerpts, translatedItem, jobNo = new String[5];

    @BeforeClass
    public void initFields() throws IOException{
        excerpt = var.getExcerptEnglish(17);
        excerpts = new String[] {
                var.getExcerptEnglish(17),
                var.getExcerptEnglish(19),
                var.getExcerptEnglish(20),
                var.getExcerptEnglish(21),
                var.getExcerptEnglish(18),
        };
        itemToTranslate = new String[] {
                var.getItemToTranslate(17),
                var.getItemToTranslate(25),
                var.getItemToTranslate(26),
                var.getItemToTranslate(27),
                var.getItemToTranslate(21)
        };
        unitCount = new String[] {
                var.getUnitCountSource(17),
                var.getUnitCountSource(25),
                var.getUnitCountSource(26),
                var.getUnitCountSource(27),
                var.getUnitCountSource(21)
        };
        translatedItem = new String[] {
                var.getTranslatedItem(17),
                var.getTranslatedItem(25),
                var.getTranslatedItem(26),
                var.getTranslatedItem(27),
                var.getTranslatedItem(21),
        };
    }

    public C18315_1_CollectionInformation() throws IOException {}

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

        customerCheckoutPage.chooseTone(customerCheckoutPage.getToneBusiness());
        customerCheckoutPage.choosePurpose(customerCheckoutPage.getPurposeMediaPublishing());

        // Choose glossary file to be used
        customerCheckoutPage.addGlossary("en_to_ja");

        customerCheckoutPage.clickOneTranslator();

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
     * translatorOpenJob --- a method wherein the translator opens the job workbench and
     * checks for the collection information
     * */
    @Test(dependsOnMethods = "translatorFindJob")
    public void translatorOpenJob() throws AWTException {
        workbenchPage.closeWorkbenchModal();

        // Assert all information are present in the workbench.
        assertTrue(workbenchPage.getRewardTxt().isDisplayed(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.getAllottedTimeTxt().isDisplayed(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.getApprovalTimeTxt().isDisplayed(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.getTotalUnitsTxt().isDisplayed(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.getRewardPerUnitTxt().isDisplayed(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.getJobCountTxt().isDisplayed(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.getCollectionIdTxt().isDisplayed(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.getTierTxt().isDisplayed(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.getPurposeTxt().isDisplayed(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.getToneTxt().isDisplayed(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.getLanguagePairTxt().isDisplayed(), var.getElementIsNotDisplayedErrMsg());

        assertTrue(workbenchPage.getJobCountTxt().getText().contains(Integer.toString(itemToTranslate.length)));
        assertEquals(workbenchPage.getTierTxt().getText(), "Pro", var.getTextNotEqualErrMsg());
        assertEquals(workbenchPage.getPurposeTxt().getText(), "Media/Publishing", var.getTextNotEqualErrMsg());
        assertEquals(workbenchPage.getToneTxt().getText(), "Business", var.getTextNotEqualErrMsg());
        assertEquals(workbenchPage.getLanguagePairTxt().getText(), "enja", var.getTextNotEqualErrMsg());
    }

    /**
     * translatorPickUpJob - this method has the translator start translating the job and
     * submit after adding the translations
     * */
    @Test(dependsOnMethods = "translatorOpenJob")
    public void translatorPickUpJob() throws AWTException {
        // Start and Submit job

        workbenchPage.translateJobMultiple(translatedItem, excerpts, translatedItem.length);

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * customerApprove --- this method has the customer approve the job
     * */
    @Test(dependsOnMethods = "translatorPickUpJob")
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

        // Loops through the jobs and approves them all
        for(int ctr = 0; ctr < excerpts.length; ctr++) {
            globalPage.goToOrdersPage();
            customerOrdersPage.clickReviewableOption();
            customerOrdersPage.findOrder(excerpts[ctr]);

            // Retrieve the job Number of the order
            jobNo[ctr] = customerOrderDetailsPage.getJobNumberReviewableJob();

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
