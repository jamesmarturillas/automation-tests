package com.gengo.automation.tests.Workbench;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @case Quick Submit Before Auto-Save Done - (Word to Character, Paypal Payment, Business Tier)
 * @reference https://gengo.testrail.com/index.php?/cases/view/244
 */
public class C244_QuickSubmitBeforeAutoSave extends AutomationBase{

    private String parentWindow, orderNo, jobNo;
    private String[] itemToTranslate;
    private String translatedText, excerpt;
    private String[] unitCount;

    @BeforeMethod
    public void initFields() throws IOException{
        excerpt = var.getExcerptEnglish(18);
        itemToTranslate = new String[] {
                var.getItemToTranslate(21)
        };
        translatedText = var.getTranslatedItem(21) + var.getTranslatedItem(17);
        unitCount = new String[] { var.getUnitCountSource(21) };
    }

    public C244_QuickSubmitBeforeAutoSave() throws IOException {}

    @AfterMethod
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
        customerCheckoutPage
                .clickPayNowAndConfirm(false, true, true, true, true);

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

        workbenchPage.closeWorkbenchModal();

        assertTrue(workbenchPage.isStartTranslatingVisible());
    }

    @Test(dependsOnMethods = "translatorFindJob")
    public void translatorPickUpJob() {
        // Translator picks up the job
        workbenchPage.startTranslateJob();

        // Check if the translate button changed after picking up the job
        assertTrue(workbenchPage.isNoJobToSubmitVisible());
        assertFalse(workbenchPage.isStartTranslatingVisible());

        workbenchPage.translateTextArea(translatedText);
        assertTrue(workbenchPage.checkSaving());


        // Check if unfiltered by default
        assertFalse(workbenchPage.isFilterSelected());

        assertTrue(workbenchPage.checkSaved());

        // Translator submits job
        workbenchPage.submitJob();
        workbenchPage.submitModalOk();

        // Translator Sign Out
        global.nonAdminSignOut();
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
        loginPage.loginAccount(var.getCustomer(25), var.getDefaultPassword());
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
