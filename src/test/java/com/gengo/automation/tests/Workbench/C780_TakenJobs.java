package com.gengo.automation.tests.Workbench;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * @case Taken Jobs
 * (Word to Character, Stripe Payment, Standard Tier)
 * @reference https://gengo.testrail.com/index.php?/cases/view/275
 */
public class C780_TakenJobs extends AutomationBase {

    private String parentWindow, orderNo, currentURL;
    private String[] itemToTranslate, jobNo = new String[6];
    private String excerpt, newExcerpt;
    private String[] excerpts, excerpt2, excerpt1, unitCount, translatedItem, translatedItem2;

    @BeforeClass
    public void initFields() throws IOException{
        excerpt = var.getExcerptEnglish(17);
        newExcerpt = var.getExcerptEnglish(18);
        excerpts = new String[] {
                var.getExcerptEnglish(17),
                var.getExcerptEnglish(19),
                var.getExcerptEnglish(18),
                var.getExcerptEnglish(20),
                var.getExcerptEnglish(21),
                var.getExcerptEnglish(26)
        };
        excerpt1 = new String[] {
                var.getExcerptEnglish(17),
                var.getExcerptEnglish(19)
        };
        excerpt2 = new String[] {
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
                var.getTranslatedItem(17),
                var.getTranslatedItem(25)
        };
        translatedItem2 = new String[] {
                var.getTranslatedItem(17),
                var.getTranslatedItem(25),
                var.getTranslatedItem(21),
                var.getTranslatedItem(26),
                var.getTranslatedItem(27),
                var.getTranslatedItem(42)
        };
    }

    public C780_TakenJobs() throws IOException {}

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

    @Test(dependsOnMethods = "placeAnOrder")
    public void pickUpAndTranslateByTranslator1(){
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

        currentURL = page.getCurrentUrl();
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    @Test(dependsOnMethods = "pickUpAndTranslateByTranslator1")
    public void pickUpByTranslator2(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslatorPro(4), var.getDefaultPassword());
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        page.launchUrl(currentURL);

        // Check if the modal is displayed
        assertTrue(workbenchPage.checkUnavailableCollection());

        // Check if redirected to translator dashboard
        translatorDashboardPage.checkTranslatorDashboard();

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    @Test(dependsOnMethods = "pickUpAndTranslateByTranslator1")
    public void submitByTranslator(){
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

        workbenchPage.addMultipleTranslatedText(translatedItem2, excerpts, translatedItem2.length);

        // Submit Job
        workbenchPage.submitJob();
        workbenchPage.submitModalOk();

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * customerApprove --- this method checks if the jobs submitted by
     * the 1st Translator is for review
     * */
    @Test(dependsOnMethods = "submitByTranslator")
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
