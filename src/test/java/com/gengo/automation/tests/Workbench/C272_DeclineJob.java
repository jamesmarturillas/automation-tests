package com.gengo.automation.tests.Workbench;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * @case Decline Job - (Word to Character, Stripe Payment, Standard Tier) - decline using default reason
 * @reference https://gengo.testrail.com/index.php?/cases/view/272
 */
public class C272_DeclineJob extends AutomationBase {

    private String parentWindow, orderNo;
    private String[] itemToTranslate, cancelledExcerpt, jobNo = new String[3];
    private String excerpt, newExcerpt;
    private String[] excerpts, unitCount, translatedItem;
    private List<String> commentList = new ArrayList<String>(); // stores all the comments

    @BeforeMethod
    public void initFields() throws IOException{
        excerpt = var.getExcerptEnglish(17);
        newExcerpt = var.getExcerptEnglish(19);
        excerpts = new String[] {
                var.getExcerptEnglish(19),
                var.getExcerptEnglish(20),
                var.getExcerptEnglish(21)
        };
        cancelledExcerpt = new String[] {
                var.getExcerptEnglish(17),
                var.getExcerptEnglish(18)
        };
        itemToTranslate = new String[] {
                var.getItemToTranslate(19),
                var.getItemToTranslate(25),
                var.getItemToTranslate(23),
                var.getItemToTranslate(26),
                var.getItemToTranslate(27),
                var.getItemToTranslate(27)
        };
        unitCount = new String[] {
                var.getUnitCountSource(19),
                var.getUnitCountSource(25),
                var.getUnitCountSource(23),
                var.getUnitCountSource(26),
                var.getUnitCountSource(27),
                var.getUnitCountSource(27)
        };
        translatedItem = new String[] {
                var.getTranslatedItem(26),
                var.getTranslatedItem(26),
                var.getTranslatedItem(27)
        };
    }

    public C272_DeclineJob() throws IOException {}

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

        // Place payment via Stripe
        customerCheckoutPage
                .clickPayNowAndConfirm(false, true, true, true, true);

        // Retrieve the order number
        orderNo = customerOrderCompletePage.orderNumber();

        // Return to dashboard page
        customerOrderCompletePage.clickGoToDashboard();

        for(int ctr = 0; ctr < excerpts.length; ctr++) {
            // Navigate to Orders page and look for the recently placed order
            globalPage.goToOrdersPage();
            customerOrdersPage.clickPendingOption();
            customerOrdersPage.findOrder(excerpts[ctr]);

            // Retrieve the job Number of the order
            jobNo[ctr] = customerOrderDetailsPage.getJobNumberAvailableJob();

            // Add comment on the job
            customerOrderDetailsPage.addComment(var.getCustomerCommentNo(1));
            commentList.add(var.getCustomerCommentNo(1));
        }
        // Customer sign out
        global.nonAdminSignOut();

        // Check if the redirected page contains the prominent Home Page elements
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(homePage.checkHomePage());
    }

    /**
     * pickUpAndDeclineByTranslator --- a method for picking up a job and having the translator
     * decline the job
     * */
    @Test(dependsOnMethods = "placeAnOrder")
    public void pickUpAndDeclineByTranslator(){
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

        // Translator picks up the job
        workbenchPage.startTranslateJob();

        // Check if the comment is visible by default and check if the customer comment is visible
        assertTrue(workbenchPage.isCommentsSectionVisible(),var.getElementNotFoundErrMsg());
        assertTrue(workbenchPage.checkCommentVisible(commentList), var.getElementNotFoundErrMsg());

        // Add comment by translator
        workbenchPage.toggleCommentsSection();
        workbenchPage.addTranslatorsComment(var.getTranslatorCommentNo(1));
        commentList.add(var.getTranslatorCommentNo(1));

        // Decline job by Translator
        workbenchPage.declineJob();
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * pickUpByTranslator2 --- this method has another translator pick up the job and
     * the visibility of all the previous comments are checked
     * */
    @Test(dependsOnMethods = "pickUpAndDeclineByTranslator")
    public void pickUpByTranslator2(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslatorPro(4), var.getDefaultPassword());
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt);

        workbenchPage.startTranslate();
        // Updated translated text is submitted
        workbenchPage.addMultipleTranslatedText(translatedItem, excerpts, translatedItem.length);
        workbenchPage.submitJob();
        workbenchPage.submitModalOk();
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * customerApprove --- this method checks if the comments placed by both Customer and
     * the 2 Translators are shown on the order details, and finally, the customer approves
     * the job
     * */
    @Test(dependsOnMethods = "pickUpByTranslator2")
    public void customerApprove(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(26), var.getDefaultPassword());
        global.selectCustomer();
        assertTrue(customerDashboardPage.checkCustomerDashboard());
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());

        for(String text: excerpts) {
            globalPage.goToOrdersPage();
            customerOrdersPage.clickReviewableOption();
            customerOrdersPage.findOrder(text);
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
    public void checkEmail(){
        page.launchUrl(var.getGmailUrl());
        assertTrue(gmailSignInEmailPage.getTxtBoxEmail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Log in on Gmail account
        gmailSignInEmailPage.inputEmail(var.getGmailEmail());
        gmailSignInPasswordPage.inputPasswordAndSubmit(var.getGmailPassword());

        // Check if the emails for Order Received, Review, Flag, and Approval are received
        assertTrue(gmailInboxPage.checkOrderReceived(orderNo));
        for(String jobNumber : jobNo) {
            assertTrue(gmailInboxPage.checkJobApproved(jobNumber));
        }
    }
}
