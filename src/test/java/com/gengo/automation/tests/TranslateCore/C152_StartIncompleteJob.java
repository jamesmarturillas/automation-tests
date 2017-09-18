package com.gengo.automation.tests.TranslateCore;

import com.gengo.automation.global.AutomationBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * @case Start incomplete job
 * @reference https://gengo.testrail.com/index.php?/cases/view/152
 */
public class C152_StartIncompleteJob extends AutomationBase {

    public C152_StartIncompleteJob() throws IOException {
    }

    private String excerpt, orderNo, jobNo, translatedText;
    private String[] itemToTranslate, unitCount;
    private List<String> excerpts = new ArrayList<>();

    @BeforeClass
    public void initFields()throws IOException {
        excerpt = var.getExcerptEnglish(34);
        excerpts.add(excerpt);
        itemToTranslate  = new String[] {
                var.getItemToTranslate(71),
        };
        unitCount = new String[] {
                var.getUnitCountSource(71),
        };
        translatedText = var.getTranslatedItem(71);
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
        loginPage.loginAccount(var.getCustomer(4), var.getDefaultPassword());

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

        // Place payment via Stripe
        customerCheckoutPage
                .clickPayNowAndConfirm(false, true, true, true, true);

        // Retrieve the order number
        orderNo = customerOrderCompletePage.orderNumber();

        // Return to dashboard page
        customerOrderCompletePage.clickGoToDashboard();

        // Retrieve the job ID
        jobNo = customerOrdersPage.extractJobIdFromPendingJobs(excerpt);

        // Customer sign out
        global.nonAdminSignOut();

        // Check if the redirected page contains the prominent Home Page elements
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(homePage.checkHomePage());
    }

    @Test(dependsOnMethods = "placeAnOrder")
    public void pickUpByTranslator() {
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

        // Close workbench modal, start translation
        workbenchPage.closeWorkbenchModal();
        workbenchPage.startTranslate();

        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.clickIncomplete();
        translatorJobsPage.findJob(excerpt);
        workbenchPage.translateAndSubmitJob(translatedText);

        // Check if page is redirected to the translator Dashboard
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.clickReviewable();
        translatorJobsPage.findJob(excerpt);

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * checkEmail --- this method logs in the on the email of the customer
     * and checks if the emails for order received, and job review
     * are visible
     * */
    @Test(dependsOnMethods = "pickUpByTranslator")
    public void checkEmail(){
        page.launchUrl(var.getGmailUrl());
        assertTrue(gmailSignInEmailPage.getTxtBoxEmail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Log in on Gmail account
        gmailSignInEmailPage.inputEmail(var.getGmailEmail());
        gmailSignInPasswordPage.inputPasswordAndSubmit(var.getGmailPassword());

        // Check if Order Received and Job ready for review emails  are received
        assertTrue(gmailInboxPage.checkOrderReceived(orderNo));
        assertTrue(gmailInboxPage.checkJobForReview(jobNo));
    }
}
