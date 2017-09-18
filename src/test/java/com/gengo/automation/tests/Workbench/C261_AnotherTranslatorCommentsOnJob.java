package com.gengo.automation.tests.Workbench;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * @case Another Translator Comments on A Translated Job
 * (Word to Character, Stripe Payment, Standard Tier)
 * @reference https://gengo.testrail.com/index.php?/cases/view/261
 */
public class C261_AnotherTranslatorCommentsOnJob extends AutomationBase {

    private String parentWindow, orderNo, currentURL, comment;
    private String[] itemToTranslate, jobNo = new String[6];
    private String excerpt, newExcerpt;
    private String[] excerpts, excerpt2, excerpt1, unitCount, translatedItem, translatedItem2;

    @BeforeClass
    public void initFields() throws IOException {
        excerpt = var.getExcerptEnglish(17);
        newExcerpt = var.getExcerptEnglish(19);
        excerpts = new String[] {
                var.getExcerptEnglish(17),
                var.getExcerptEnglish(19),
                var.getExcerptEnglish(18),
                var.getExcerptEnglish(20),
                var.getExcerptEnglish(21),
                var.getExcerptEnglish(26)
        };
        excerpt1 = new String[] {
                var.getExcerptEnglish(17)
        };
        excerpt2 = new String[] {
                var.getExcerptEnglish(19),
                var.getExcerptEnglish(18),
                var.getExcerptEnglish(20),
                var.getExcerptEnglish(21),
                var.getExcerptEnglish(26)
        };
        itemToTranslate = new String[] {
                var.getItemToTranslate(17),
                var.getItemToTranslate(25),
                var.getItemToTranslate(21),
                var.getItemToTranslate(26),
                var.getItemToTranslate(27),
                var.getItemToTranslate(42)
        };
        unitCount = new String[] {
                var.getUnitCountSource(17),
                var.getUnitCountSource(25),
                var.getUnitCountSource(21),
                var.getUnitCountSource(26),
                var.getUnitCountSource(27),
                var.getUnitCountSource(42)
        };
        translatedItem = new String[] {
                var.getTranslatedItem(17)
        };
        translatedItem2 = new String[] {
                var.getTranslatedItem(25),
                var.getTranslatedItem(21),
                var.getTranslatedItem(26),
                var.getTranslatedItem(27),
                var.getTranslatedItem(42)
        };
        comment = var.getTranslatorCommentNo(2);
    }

    public C261_AnotherTranslatorCommentsOnJob() throws IOException {}

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
        }

        // Customer sign out
        global.nonAdminSignOut();

        // Check if the redirected page contains the prominent Home Page elements
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(homePage.checkHomePage());
    }

    /**
     * pickUpAndTranslateByTranslator1 --- Translator picks up a grouped job, translated one of the jobs
     * and then declines the collection
     * */
    @Test(dependsOnMethods = "placeAnOrder")
    public void pickUpAndTranslateByTranslator1() throws AWTException {
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

        // Add the text translated on the workbench
        workbenchPage.addMultipleTranslatedText(translatedItem, excerpt1, translatedItem.length);

        // Submit 1 job
        workbenchPage.submitJob();
        workbenchPage.submitModalOk(false);
        page.refresh();

        // Decline job
        workbenchPage.declineJob();
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * pickUpByTranslator2 --- Another translator picks up the job and adds a comment on the submitted job by
     * the first translator. Additional translations are added and submitted.
     * */
    @Test(dependsOnMethods = "pickUpAndTranslateByTranslator1")
    public void pickUpByTranslator2(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslatorPro(4), var.getDefaultPassword());
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        // Find job declined by the first translator
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(newExcerpt);

        // Pick up the job
        workbenchPage.startTranslate();

        // Add comment on submitted job
        workbenchPage.addTranslatorsComment(comment, excerpt);
        page.refresh();

        // Add translated texts to remaining jobs
        workbenchPage.addMultipleTranslatedText(translatedItem2, excerpt2, translatedItem2.length);

        // Submit Job
        workbenchPage.submitJob();
        workbenchPage.submitModalOk();
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * customerApprove --- this method checks if the jobs submitted by
     * the Translators are for review
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

        // Loop through the jobs for review and approve them
        for(String text: excerpts) {
            globalPage.goToOrdersPage();
            customerOrdersPage.clickReviewableOption();
            customerOrdersPage.findOrder(text);

            // Check for visibility of comment
            if(text.equals(excerpt))
                assertTrue(customerOrderDetailsPage.checkCommentVisible(comment));

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
