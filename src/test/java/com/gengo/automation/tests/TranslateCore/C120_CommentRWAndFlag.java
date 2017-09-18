package com.gengo.automation.tests.TranslateCore;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @case Comment read, write, and flag single job
 * @reference https://gengo.testrail.com/index.php?/cases/view/120
 */
public class C120_CommentRWAndFlag extends AutomationBase {

    public C120_CommentRWAndFlag() throws IOException {
    }

    private String excerpt;
    private String orderNo;
    private String jobNo;
    private String translatedText;
    private String[] itemToTranslate;
    private String[] unitCount;
    private List<String> excerpts = new ArrayList<>();
    private List<String> commentList = new ArrayList<String>();

    @BeforeClass
    public void initFields()throws IOException {
        excerpt = var.getExcerptEnglish(35);
        excerpts.add(excerpt);
        itemToTranslate  = new String[] {
                var.getItemToTranslate(72),
        };
        unitCount = new String[] {
                var.getUnitCountSource(72),
        };
        translatedText = var.getTranslatedItem(72);
    }

    @Test
    public void placeAnOrder() {
        pluginPage.passThisPage();

        // Check if the prominent page elements can be seen on the Home Page
        assertTrue(homePage.checkHomePage());
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Logging in a customer account
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(5), var.getDefaultPassword());

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

        // Set the target language to Tagalog
        customerOrderLanguagesPage.choooseLanguage(var.getTagalogTo());
        customerOrderLanguagesPage.clickNextOptions();

        // Place payment via Stripe
        customerCheckoutPage
                .clickPayNowAndConfirm(false, true, true, true, true);

        // Retrieve the order number
        orderNo = customerOrderCompletePage.orderNumber();

        // Return to dashboard page
        customerOrderCompletePage.clickGoToDashboard();
        globalPage.goToOrdersPage();
        customerOrdersPage.clickPendingOption();
        customerOrdersPage.findOrder(excerpt);

        // Retrieve the job ID
        jobNo = customerOrderDetailsPage.getJobNumberAvailableJob();

        // Add comment on the job
        customerOrderDetailsPage.clickAddReadComments();
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
     * addCommentAsTranslator --- a method for checking the logging in of a translator account,
     * picking up the recently created job and viewing and adding of comments
     * */
    @Test(dependsOnMethods = "placeAnOrder")
    public void pickUpByTranslator(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Log in a translator account
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslatorStandard(4), var.getDefaultPassword());

        // Check if the page redirected to contains the elements that should be seen in a Translator Dashboard
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        // Navigate to the Jobs Page and look for the recently created job
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt);
        workbenchPage.closeWorkbenchModal();

        // Check if the comment is visible by default and check if the customer comment is visible
        assertTrue(workbenchPage.isCommentsSectionVisible(),var.getElementNotFoundErrMsg());
        assertTrue(workbenchPage.checkCommentVisible(commentList), var.getElementNotFoundErrMsg());

        // Add comment by translator
        workbenchPage.toggleCommentsSection();
        workbenchPage.addTranslatorsComment(var.getTranslatorCommentNo(1));
        commentList.add(var.getTranslatorCommentNo(1));

        // Translator account is logged out and the Home Page is checked
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    @Test(dependsOnMethods = "pickUpByTranslator")
    public void customerAddComment(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(5), var.getDefaultPassword());
        global.selectCustomer();
        assertTrue(customerDashboardPage.checkCustomerDashboard());
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());
        globalPage.goToOrdersPage();
        customerOrdersPage.clickPendingOption();
        customerOrdersPage.findOrder(excerpt);

        // Check if the comments can be seen by the customer
        customerOrderDetailsPage.checkCommentVisible(commentList);

        // Add a comment by the customer
        customerOrderDetailsPage.addComment(var.getCustomerCommentNo(2));
        commentList.add(var.getCustomerCommentNo(2));
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    @Test(dependsOnMethods = "customerAddComment")
    public void pickUpByTranslatorAndFlag(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslatorStandard(4), var.getDefaultPassword());
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt);

        // Check if all the comments are still visible on the page
        assertTrue(workbenchPage.isCommentsSectionVisible(),var.getElementNotFoundErrMsg());
        assertTrue(workbenchPage.checkCommentVisible(commentList), var.getElementNotFoundErrMsg());

        // Flag job by translator
        workbenchPage.flagJob("notEnglish", var.getTranslatorFlagComment());
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    @Test(dependsOnMethods = "pickUpByTranslatorAndFlag")
    public void customerResolveFlag(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(5), var.getDefaultPassword());
        global.selectCustomer();
        assertTrue(customerDashboardPage.checkCustomerDashboard());
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());
        globalPage.goToOrdersPage();
        customerOrdersPage.clickPendingOption();
        customerOrdersPage.findOrder(excerpt);

        // Check visibility of comments and add another comment
        customerOrderDetailsPage.clickAddReadComments();
        customerOrderDetailsPage.addComment(var.getCustomerCommentNo(3));
        commentList.add(var.getCustomerCommentNo(3));

        // Check if the flag comment by the translator is visible
        assertTrue(customerOrderDetailsPage.isFlagVisible());

        // Resolve the flag
        customerOrderDetailsPage.resolveFlag();
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    @Test(dependsOnMethods = "customerResolveFlag")
    public void pickUpByTranslatorAndSubmit(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslatorStandard(4), var.getDefaultPassword());
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt);

        // Check if the flag is resolved
        assertTrue(workbenchPage.isFlagResolved());

        // Add comment by Translator
        workbenchPage.closeWorkbenchModal();
        workbenchPage.addTranslatorsComment(var.getTranslatorCommentNo(3));
        commentList.add(var.getTranslatorCommentNo(3));

        // Translated text is added and submitted
        workbenchPage.startTranslateJob();
        workbenchPage.translateAndSubmitJob(translatedText);
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    @Test(dependsOnMethods = "pickUpByTranslatorAndSubmit")
    public void checkEmail(){
        page.launchUrl(var.getGmailUrl());
        assertTrue(gmailSignInEmailPage.getTxtBoxEmail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Log in on Gmail account
        gmailSignInEmailPage.inputEmail(var.getGmailEmail());
        gmailSignInPasswordPage.inputPasswordAndSubmit(var.getGmailPassword());

        // Check if the emails for Order Received, Review, and Flag are received
        assertTrue(gmailInboxPage.checkOrderReceived(orderNo));
        assertTrue(gmailInboxPage.checkJobForReview(jobNo));
        assertTrue(gmailInboxPage.checkJobFlagged(jobNo));
    }
}
