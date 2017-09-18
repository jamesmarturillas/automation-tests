package com.gengo.automation.tests.Workbench;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @case Left Panel Information - (Word to Character, Paypal Payment, Business Tier)
 * @reference https://gengo.testrail.com/index.php?/cases/view/248
 */
public class C248_LeftPanelInformation extends AutomationBase {

    private String parentWindow, orderNo, jobNo;
    private String[] itemToTranslate;
    private String translatedText, excerpt;
    private String[] unitCount;
    private List<String> commentList = new ArrayList<String>(); // stores all the comments

    @BeforeMethod
    public void initFields() throws IOException{
        excerpt = var.getExcerptEnglish(17);
        itemToTranslate = new String[] {
                var.getItemToTranslate(17)
        };
        translatedText = var.getTranslatedItem(17);
        unitCount = new String[] { var.getUnitCountSource(17) };
    }

    public C248_LeftPanelInformation() throws IOException {}

    @AfterClass
    public void afterRun() {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    /**
     * placeAnOrder --- a method for checking the logging in of a customer account,
     * placing of an order, and adding a comment on the order
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

        // Place payment via Stripe
        customerCheckoutPage
                .clickPayNowAndConfirm(false, true, true, true, true);

        // Retrieve the order number
        orderNo = customerOrderCompletePage.orderNumber();

        // Return to dashboard page
        customerOrderCompletePage.clickGoToDashboard();

        // Navigate to Orders page and look for the recently placed order
        globalPage.goToOrdersPage();
        customerOrdersPage.clickPendingOption();
        customerOrdersPage.findOrder(excerpt);

        // Retrieve the job Number of the order
        jobNo = customerOrderDetailsPage.getJobNumberAvailableJob();

        // Add comment on the job
        customerOrderDetailsPage.addComment(var.getCustomerCommentNo(1));
        commentList.add(var.getCustomerCommentNo(1));

        // Customer sign out
        global.nonAdminSignOut();

        // Check if the redirected page contains the prominent Home Page elements
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(homePage.checkHomePage());
    }

    /**
     * pickUpByTranslator1 --- a method for checking the logging in of a translator account,
     * picking up the recently created job and viewing and adding of comments
     * */
    @Test(dependsOnMethods = "placeAnOrder")
    public void pickUpByTranslator(){
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
        // Check if the comment is visible by default and check if the customer comment is visible
        assertTrue(workbenchPage.isCommentsSectionVisible(), var.getElementNotFoundErrMsg());
        assertTrue(workbenchPage.checkCommentVisible(commentList), var.getElementNotFoundErrMsg());

        workbenchPage.closeWorkbenchModal();

        // Add comment by translator
        workbenchPage.toggleCommentsSection();
        assertFalse(workbenchPage.isCommentsSectionVisible(), var.getElementIsDisplayedErrMsg());
        workbenchPage.addTranslatorsComment(var.getTranslatorCommentNo(2));
        commentList.add(var.getTranslatorCommentNo(2));

        // Translator picks up the job
        workbenchPage.startTranslateJob();
        workbenchPage.translateTextArea(translatedText);
        // Add comment by translator
       // workbenchPage.toggleCommentsSection();
        workbenchPage.addTranslatorsComment(var.getTranslatorCommentNo(1));
        commentList.add(var.getTranslatorCommentNo(1));

        // Open the comments section
        workbenchPage.toggleCommentsSection();
        assertTrue(workbenchPage.isCommentsSectionVisible(), var.getElementNotFoundErrMsg());

        // Close the comments section
        workbenchPage.toggleCommentsSection();
        assertFalse(workbenchPage.isCommentsSectionVisible(), var.getElementIsDisplayedErrMsg());

        workbenchPage.toggleCommentsSection();
        // Check if all comments are visible on the comments section
        assertTrue(workbenchPage.checkCommentVisible(commentList), var.getElementNotFoundErrMsg());
        workbenchPage.toggleCommentsSection();

        // Submit translation
        workbenchPage.submitJob();
        workbenchPage.submitModalOk();

        // Translator account is logged out and the Home Page is checked
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * customerApprove --- this method checks if the comments placed by both Customer and
     * the 2 Translators are shown on the order details, and finally, the customer approves
     * the job
     * */
    @Test(dependsOnMethods = "pickUpByTranslator")
    public void customerApprove(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(25), var.getDefaultPassword());
        assertTrue(customerDashboardPage.checkCustomerDashboard());
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());
        globalPage.goToOrdersPage();
        customerOrdersPage.clickReviewableOption();
        customerOrdersPage.findOrder(excerpt);

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
    public void checkEmail(){
        page.launchUrl(var.getGmailUrl());
        assertTrue(gmailSignInEmailPage.getTxtBoxEmail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Log in on Gmail account
        gmailSignInEmailPage.inputEmail(var.getGmailEmail());
        gmailSignInPasswordPage.inputPasswordAndSubmit(var.getGmailPassword());

        // Check if the emails for Order Received, Review, Flag, and Approval are received
        assertTrue(gmailInboxPage.checkOrderReceived(orderNo));
        assertTrue(gmailInboxPage.checkJobForReview(jobNo));
        assertTrue(gmailInboxPage.checkJobFlagged(jobNo));
        assertTrue(gmailInboxPage.checkJobApproved(jobNo));
    }
}
