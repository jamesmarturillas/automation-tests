package com.gengo.automation.tests.CoreTest;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.DecimalFormat;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

/**
 * @case Cancel A Job
 * Word To Character, Credit Payment, Cancel some jobs of a group job
 * @reference https://gengo.testrail.com/index.php?/cases/view/40344
 */
public class C40344_1_CancelJob extends AutomationBase {

    private String parentWindow, orderNo;
    private String[] itemToTranslate, cancelledExcerpt, jobNo = new String[3];
    private String excerpt, newExcerpt;
    private String[] excerpts, unitCount, translatedItem;
    private int jobCtr = 0;
    private DecimalFormat df = new DecimalFormat("#.00");

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
                var.getItemToTranslate(27)
        };
        unitCount = new String[] {
                var.getUnitCountSource(19),
                var.getUnitCountSource(25),
                var.getUnitCountSource(23),
                var.getUnitCountSource(26),
                var.getUnitCountSource(27)
        };
        translatedItem = new String[] {
                var.getTranslatedItem(25),
                var.getTranslatedItem(26),
                var.getTranslatedItem(27)
        };
    }

    public C40344_1_CancelJob() throws IOException {}

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
        loginPage.loginAccount(var.getCustomer(27), var.getDefaultPassword());

        // Ensure that the account type is set to 'Customer'
        global.selectCustomer();

        // Check if the prominent page elements can be seen on the Customer Dashboard Page
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());
        assertTrue(customerDashboardPage.checkCustomerDashboard());

        // Retrieve credits before placing an order
        double beforeOrderCredits = globalPage.getCurrentCredits();

        // Click the icon for placing an order
        global.clickOrderTranslationIcon();

        // Place the text to be translated on the order form and check for the word/character count
        customerOrderFormPage.inputItemToTranslate(itemToTranslate, unitCount, itemToTranslate.length, false);

        // Check if the credits before and during the ordering process is equal
        assertEquals(beforeOrderCredits, customerOrderFormPage.getCurrentCredits());

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

        // Retrieve the total price listed on the checkout page
        double totalPrice = Double.parseDouble(customerCheckoutPage.getOrderTotalPrice().getText().replaceAll("[^0-9.,]+",""));

        // Place payment via Credits
        customerCheckoutPage.payWithCredits();

        // Retrieve the order number
        orderNo = customerOrderCompletePage.orderNumber();

        // Return to dashboard page
        customerOrderCompletePage.clickGoToDashboard();

        // Check if the payment is deducted from the credits properly
        assertEquals(globalPage.getCurrentCredits(), Double.parseDouble(df.format(beforeOrderCredits - totalPrice)));
        assertEquals(Double.parseDouble(df.format(globalPage.getCurrentCredits() + totalPrice)), beforeOrderCredits);
        global.nonAdminSignOut();

        // Check if the redirected page contains the prominent Home Page elements
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(homePage.checkHomePage());
    }

    /**
     * openByTranslator --- a method for picking up a job and having the translator
     * open the job
     * */
    @Test(dependsOnMethods = "placeAnOrder")
    public void openByTranslator(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Log in a translator account
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslatorPro(4), var.getDefaultPassword());

        // Check if the page redirected to contains the elements that should be seen in a Translator Dashboard
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        // Navigate to the Jobs Page and look for the recently created job
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt);
        workbenchPage.closeWorkbenchModal();

        // Check if the collection contains all the text jobs
        for(String text: excerpts)
            assertTrue(workbenchPage.isSourceTextVisible(text), var.getElementIsNotDisplayedErrMsg());
        for(String text: cancelledExcerpt)
            assertTrue(workbenchPage.isSourceTextVisible(text), var.getElementIsNotDisplayedErrMsg());
        page.refresh();
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * cancelByCustomer --- this method checks cancels 2 of the jobs from the
     * grouped collection recently made
     * */
    @Test(dependsOnMethods = "openByTranslator")
    public void cancelByCustomer(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(27), var.getDefaultPassword());
        global.selectCustomer();
        assertTrue(customerDashboardPage.checkCustomerDashboard());
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());

        for(String text: cancelledExcerpt) {
            globalPage.goToOrdersPage();

            // Retrieve credits before Cancelling Job
            double credit = globalPage.getCurrentCredits();

            // Look for jobs to cancel
            customerOrdersPage.clickPendingOption();
            customerOrdersPage.findOrder(text);

            // Retrieve price of job to be cancelled
            double jobPrice = Double.parseDouble(customerOrderDetailsPage.getOrderPrice().getText().replaceAll("[^0-9.,]+",""));

            // Calculate the resulting credit after cancel
            double updatedCredit = Double.parseDouble(df.format(globalPage.getCurrentCredits() + jobPrice));

            // Cancel job
            customerOrderDetailsPage.cancelOrder();

            // Check if the cancellation refund was added to the credits
            assertEquals(updatedCredit, globalPage.getCurrentCredits());
            assertEquals(credit, Double.parseDouble(df.format(updatedCredit - jobPrice)));
        }
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * translateByTranslator --- this method has the translator pick up the job and
     * check for the changes in the collection
     * */
    @Test(dependsOnMethods = "cancelByCustomer")
    public void translateByTranslator(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslatorPro(4), var.getDefaultPassword());
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(newExcerpt);

        // Pick up the job
        workbenchPage.closeWorkbenchModal();
        workbenchPage.startTranslate();

        // Check if the cancelled jobs still appear on the page or not
        for (String text : excerpts)
            assertTrue(workbenchPage.isSourceTextVisible(text), var.getElementIsNotDisplayedErrMsg());
        for (String text : cancelledExcerpt)
            assertFalse(workbenchPage.isSourceTextVisible(text), var.getElementIsDisplayedErrMsg());
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * checkByCustomer --- this method checks if the the customer can cancel other jobs in the collection
     * */
    @Test(dependsOnMethods = "translateByTranslator")
    public void checkByCustomer(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(27), var.getDefaultPassword());
        global.selectCustomer();
        assertTrue(customerDashboardPage.checkCustomerDashboard());
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());
        for(String text: excerpts) {
            globalPage.goToOrdersPage();
            customerOrdersPage.clickPendingOption();
            customerOrdersPage.findOrder(text);
            // Job is approved
            assertTrue(customerOrderDetailsPage.cancelOrderNotFound(), var.getElementIsDisplayedErrMsg());
        }
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }


    /**
     * submitByTranslator --- this method has the translator pick up the job and
     * submit the translations
     * */
    @Test(dependsOnMethods = "checkByCustomer")
    public void submitByTranslator(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslatorPro(4), var.getDefaultPassword());
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt);
        workbenchPage.closeWorkbenchModal();

        // translated text is submitted
        workbenchPage.addMultipleTranslatedText(translatedItem, excerpts, translatedItem.length);
        workbenchPage.submitJob();
        workbenchPage.submitModalOk();
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * customerApprove --- this method has the customer approve the job
     * */
    @Test(dependsOnMethods = "submitByTranslator")
    public void customerApprove(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(27), var.getDefaultPassword());
        global.selectCustomer();
        assertTrue(customerDashboardPage.checkCustomerDashboard());
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());
        for(String text: excerpts) {
            globalPage.goToOrdersPage();
            customerOrdersPage.clickReviewableOption();
            customerOrdersPage.findOrder(text);

            // Retrieve job number
            jobNo[jobCtr] = customerOrderDetailsPage.getJobNumberReviewableJob();

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
            assertTrue(gmailInboxPage.checkJobForReview(jobNumber));
        }
    }
}
