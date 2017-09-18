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
 * @case Comment R/W and Flag Job - (Character to Word, Paypal Payment, Business Tier)
 * @reference https://gengo.testrail.com/index.php?/cases/view/234
 */
public class C234_4_CommentRWAndFlag extends AutomationBase {

    private String parentWindow, orderNo, jobNo;
    private String[] itemToTranslate;
    private String translatedText1, excerpt, translatedText2;
    private String[] unitCount;
    private List<String> commentList = new ArrayList<String>(); // stores all the comments

    @BeforeMethod
    public void initFields() throws IOException{
        excerpt = var.getExcerptNonEnglish(17);
        itemToTranslate = new String[] {
                var.getItemToTranslate(18)
        };
        // Only a portion of the translated text is stored on translatedText1
        translatedText1 = var.getTranslatedItem(18).substring(0, 20);
        translatedText2 = var.getTranslatedItem(18);
        unitCount = new String[] { var.getUnitCountSource(18) };
    }

    public C234_4_CommentRWAndFlag() throws IOException {}

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
        customerOrderLanguagesPage.getLanguageFrom(var.getJapaneseFrom());

        // Set source language to Japanese
        customerOrderLanguagesPage.chooseSourceLanguage(var.getJapaneseFrom());

        // Set the target language to English
        customerOrderLanguagesPage.choooseLanguage(var.getEnglishTo());
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
    public void pickUpByTranslator1(){
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

        // Translator picks up the job
        workbenchPage.startTranslateJob();

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

    /**
     * customerAddComment --- this method checks the recent comment of the assigned translator
     * on the job and an additional comment is placed by the customer
     * */
    @Test(dependsOnMethods = "pickUpByTranslator1")
    public void customerAddComment(){
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
        customerOrdersPage.clickPendingOption();
        customerOrdersPage.findOrder(excerpt);

        // Check if the comments can be seen by the customer
        customerOrderDetailsPage.checkCommentVisible(commentList);
        customerOrderDetailsPage.hideComments();

        // Add a comment by the customer
        customerOrderDetailsPage.addComment(var.getCustomerCommentNo(2));
        commentList.add(var.getCustomerCommentNo(2));
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * pickUpByTranslator1AndFlag --- this method checks the comments placed by both Customer and
     * Translator on the job, Translator adds another comment and flags the job.
     * */
    @Test(dependsOnMethods = "customerAddComment")
    public void pickUpByTranslator1AndFlag(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslator(26), var.getDefaultPassword());
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt);

        // Check if all the comments are still visible on the page
        assertTrue(workbenchPage.isCommentsSectionVisible(),var.getElementNotFoundErrMsg());
        assertTrue(workbenchPage.checkCommentVisible(commentList), var.getElementNotFoundErrMsg());
        workbenchPage.toggleCommentsSection();
        assertFalse(workbenchPage.isCommentsSectionVisible(),var.getElementNotFoundErrMsg());

        // Add comment by Translator
        workbenchPage.addTranslatorsComment(var.getTranslatorCommentNo(2));
        commentList.add(var.getTranslatorCommentNo(2));

        // Flag job by translator
        workbenchPage.flagJob("not", var.getTranslatorFlagComment());
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * customerResolveFlag --- this method checks the visibility of all comments
     * and the customer resolves the flag
     * */
    @Test(dependsOnMethods = "pickUpByTranslator1AndFlag")
    public void customerResolveFlag(){
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
        customerOrdersPage.clickPendingOption();
        customerOrdersPage.findOrder(excerpt);

        // Check visibility of comments and add another comment
        customerOrderDetailsPage.checkCommentVisible(commentList);
        customerOrderDetailsPage.hideComments();
        customerOrderDetailsPage.addComment(var.getCustomerCommentNo(3));
        commentList.add(var.getCustomerCommentNo(3));

        // Check if the flag comment by the translator is visible
        assertTrue(customerOrderDetailsPage.isFlagVisible());

        // Resolve the flag
        customerOrderDetailsPage.resolveFlag();
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * pickUpByTranslator1AndSubmit --- this method checks the comments placed by both Customer and
     * Translator on the job, Translator adds another comment, adds translated text and
     * submits the job
     * */
    @Test(dependsOnMethods = "customerResolveFlag")
    public void pickUpByTranslator1AndSubmit(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslator(26), var.getDefaultPassword());
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt);

        // Check if the flag is resolved
        assertTrue(workbenchPage.isFlagResolved());
        workbenchPage.toggleCommentsSection();

        // Add comment by Translator
        workbenchPage.addTranslatorsComment(var.getTranslatorCommentNo(3));
        commentList.add(var.getTranslatorCommentNo(3));

        // Translated text is added and submitted
        workbenchPage.translateAndSubmitJob(translatedText1);
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * customerRequestCorrection --- this method checks the visibility of all comments
     * and the customer requests correction for the job
     * */
    @Test(dependsOnMethods = "pickUpByTranslator1AndSubmit")
    public void customerRequestCorrection(){
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

        // Check for the visibility of all comments
        customerOrderDetailsPage.checkCommentVisible(commentList);

        // Request correction for job
        customerOrderDetailsPage.requestCorrection(var.getCustomerCorrection(), true, true, true);
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * translator1Decline --- this method checks the comments placed by both Customer and
     * Translator on the job and the Translator declines the job
     * */
    @Test(dependsOnMethods = "customerRequestCorrection")
    public void translator1Decline(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslator(26), var.getDefaultPassword());
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt);

        // Decline job by Translator
        workbenchPage.declineJob();
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * pickUpByTranslator2 --- this method has another translator pick up the job and
     * the visibility of all the previous comments are checked
     * */
    @Test(dependsOnMethods = "translator1Decline")
    public void pickUpByTranslator2(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslatorPro(4), var.getDefaultPassword());
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt);

        // Another translator picks up the job
        workbenchPage.startTranslateJob();

        // Checks the comments
        assertTrue(workbenchPage.isCommentsSectionVisible(),var.getElementNotFoundErrMsg());
        workbenchPage.checkCommentVisible(commentList);
        workbenchPage.toggleCommentsSection();

        // Add comment by 2nd translator
        workbenchPage.addTranslatorsComment(var.getTranslatorCommentNo(4));
        commentList.add(var.getTranslatorCommentNo(4));
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * customerViewComments --- this method checks if the comments placed by both Customer and
     * the 2 Translators are shown on the order details
     * */
    @Test(dependsOnMethods = "pickUpByTranslator2")
    public void customerViewComments(){
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
        customerOrdersPage.clickPendingOption();
        customerOrdersPage.findOrder(excerpt);

        // Check all comments on the page
        customerOrderDetailsPage.checkCommentVisible(commentList);
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * pickUpByTranslator2AndSubmit --- this method checks the comments placed by both Customer and
     * 2 Translators on the job, Translator adds another comment, adds translated text and
     * submits the job
     * */
    @Test(dependsOnMethods = "customerViewComments")
    public void pickUpByTranslator2AndSubmit(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslatorPro(4), var.getDefaultPassword());
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt);

        // Updated translated text is submitted
        workbenchPage.translateAndSubmitJob(translatedText2);
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * customerApprove --- this method checks if the comments placed by both Customer and
     * the 2 Translators are shown on the order details, and finally, the customer approves
     * the job
     * */
    @Test(dependsOnMethods = "pickUpByTranslator2AndSubmit")
    public void customerApprove(){
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
