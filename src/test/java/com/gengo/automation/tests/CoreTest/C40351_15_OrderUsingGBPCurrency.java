package com.gengo.automation.tests.CoreTest;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.gengo.automation.fields.Constants.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @case Order Using GBP Currency
 * 	Pro, Group, Credits, Word to Word - English to Japanese, 0.013 GBP per word rate
 * @reference https://gengo.testrail.com/index.php?/cases/view/40351
 */
public class C40351_15_OrderUsingGBPCurrency extends AutomationBase{

    private String parentWindow, orderNo, excerpt, price, instruction;
    private String[] itemToTranslate, unitCount, translatedItem, excerpts, jobNo = new String[2];

    @BeforeMethod
    public void initFields() throws IOException{
        instruction = var.getCustomerInstruction();
        excerpt = var.getExcerptEnglish(32);
        excerpts = new String[] {
                var.getExcerptEnglish(32),
                var.getExcerptEnglish(31)
        };
        itemToTranslate = new String[] {
                var.getItemToTranslate(54),
                var.getItemToTranslate(55)
        };
        unitCount = new String[] {
                var.getUnitCountSource(54),
                var.getUnitCountSource(55)
        };
        translatedItem = new String[] {
                var.getTranslatedItem(54),
                var.getTranslatedItem(55)
        };
    }

    public C40351_15_OrderUsingGBPCurrency() throws IOException {}

    @AfterMethod
    public void afterRun() {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    /**
     * placeAnOrder --- a method for checking the logging in of a customer account,
     * and placing a grouped order
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
        loginPage.loginAccount(var.getCustomer(32), var.getDefaultPassword());

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

        // Retrieve the total order price for comparison across pages later
        wait.untilElementVisible(customerCheckoutPage.getOrderTotalPrice());
        price = customerCheckoutPage.getOrderTotalPrice().getText();

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

        // Check if the price displayed on the full quote page is consistent with total order price
        assertEquals(price.replaceAll("[.]",""), customerCheckoutPage.getFullQuoteTotalPrice().getText(), var.getTextNotEqualErrMsg());

        // Check for the Tier displayed on the Quote Page
        assertTrue(customerOrderQuotePage.getUnitPriceText().getText().contains(QUALIFICATION_RNK_BUSINESS));

        // Check if the unit price is as expected
        assertEquals(customerOrderQuotePage.getUnitPriceText().getText().replaceAll("[A-z -]",""), GBP013);

        // Download quote
        customerOrderQuotePage.downloadQuote();
        switcher.switchToParentWindow(parentWindow);

        // Check if the unit price for the order is as expected
        assertEquals(customerCheckoutPage.getUnitPriceText().getText().replaceAll("[A-z /]",""), GBP013);

        // Check if the unit prices for Standard and Business tiers are as expected
        assertEquals(customerCheckoutPage.getStandardUnitPriceText().getText().replaceAll("[A-z /]",""), GBP07);
        assertEquals(customerCheckoutPage.getBusinessUnitPriceText().getText().replaceAll("[A-z /]",""), GBP013);

        // Add instructions
        customerCheckoutPage.addInstructions(instruction);

        // Place payment with Credits
        customerCheckoutPage.payWithCredits();

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

        // Add translated items and submit
        workbenchPage.translateJobMultiple(translatedItem, excerpts, translatedItem.length);
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * customerApprove --- this method has the customer approve the job
     * */
    @Test(dependsOnMethods = "translatorFindJob")
    public void customerApprove() {
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(32), var.getDefaultPassword());
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
        wait.untilElementVisible(gmailInboxPage.getGmailComposeBtn());

        // Check if the emails for Order Received, Review, and Approval are received
        assertTrue(gmailInboxPage.checkOrderReceived(orderNo));
        for(String jobNum : jobNo) {
            assertTrue(gmailInboxPage.checkJobForReview(jobNum));
            assertTrue(gmailInboxPage.checkJobApproved(jobNum));
        }
    }
}
