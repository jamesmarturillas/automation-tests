package com.gengo.automation.tests.CoreTest;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.gengo.automation.fields.Constants.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @case Order Using GBP Currency
 * 	Standard, File, Stripe, Word to Word - English to Danish, 0.09 GBP per word rate
 * @reference https://gengo.testrail.com/index.php?/cases/view/40351
 */
public class C40351_17_OrderUsingGBPCurrency extends AutomationBase {

    private String parentWindow, orderNo, excerpt, jobNo, translatedItem, price, instruction;
    private String[] unitCount, itemToTranslate;

    @BeforeClass
    public void initFields() throws IOException{
        instruction = var.getCustomerInstruction();
        excerpt = var.getExcerptFile(10);
        translatedItem = var.getFile(91);
        itemToTranslate = new String[] {
                var.getFile(90)
        };
        unitCount = new String[] {
                var.getUnitCountSource(54)
        };
    }

    public C40351_17_OrderUsingGBPCurrency() throws IOException {}

    @AfterClass
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
        loginPage.loginAccount(var.getCustomer(31), var.getDefaultPassword());

        // Check if the prominent page elements can be seen on the Customer Dashboard Page
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());
        assertTrue(customerDashboardPage.checkCustomerDashboard());

        // Click the icon for placing an order
        global.clickOrderTranslationIcon();

        // Place the text to be translated on the order form and check for the word/character count
        customerOrderFormPage.inputItemToTranslate(itemToTranslate, unitCount, itemToTranslate.length, true);

        // Check if source language is auto detected by the system
        assertTrue(page.getCurrentUrl().equals(customerOrderLanguagesPage.ORDERLANGUAGES_URL),
                var.getWrongUrlErrMsg());
        assertTrue(customerOrderLanguagesPage.isSourceAutoDetected(),
                var.getTextNotEqualErrMsg());

        // Set the target language to Danish
        customerOrderLanguagesPage.choooseLanguage(var.getDanishTo());
        customerOrderLanguagesPage.clickNextOptions();

        // Retrieve the total order price for comparison across pages later
        price = customerCheckoutPage.getOrderTotalPrice().getText();

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
        assertEquals(price, customerCheckoutPage.getFullQuoteTotalPrice().getText(), var.getTextNotEqualErrMsg());

        // Check for the Tier displayed on the Quote Page
        assertTrue(customerOrderQuotePage.getUnitPriceText().getText().contains(QUALIFICATION_RNK_STANDARD));

        // Check if the unit price is as expected
        assertEquals(customerOrderQuotePage.getUnitPriceText().getText().replaceAll("[A-z -]",""), GBP09);

        // Download Quote File
        customerOrderQuotePage.downloadQuote();
        switcher.switchToParentWindow(parentWindow);

        // Check if the unit price for the order is as expected
        assertEquals(customerCheckoutPage.getUnitPriceText().getText().replaceAll("[A-z /]",""), GBP09);

        // Check if the unit prices for Standard and Business tiers are as expected
        assertEquals(customerCheckoutPage.getStandardUnitPriceText().getText().replaceAll("[A-z /]",""), GBP09);
        assertEquals(customerCheckoutPage.getBusinessUnitPriceText().getText().replaceAll("[A-z /]",""), GBP014);

        // Add instructions
        customerCheckoutPage.addInstructions(instruction);

        // Place payment via Paypal
        customerCheckoutPage
                .clickPayNowAndConfirm(false, true, true, true, true);

        // Check if the price on the Stripe Modal is consistent with the total order price
        assertEquals(price, customerCheckoutPage.returnStripePrice(), var.getTextNotEqualErrMsg());

        // Retrieve the order number
        orderNo = customerOrderCompletePage.orderNumber();

        // Check if the price on the order complete page is consistent with the total order price
        assertEquals(price, customerOrderCompletePage.totalPrice(), var.getTextNotEqualErrMsg());

        // Return to dashboard page
        customerOrderCompletePage.clickGoToDashboard();

        // Go to Customer's Pending Orders Page
        globalPage.goToOrdersPage();
        customerOrdersPage.clickPendingOption();

        // Look for the recently created job
        customerOrdersPage.findOrder(excerpt);

        // Check if the instructions is displayed on the page
        assertTrue(customerOrderDetailsPage.checkInstruction(instruction), var.getElementIsNotDisplayedErrMsg());

        // Retrieve Job Number for use later
        jobNo = customerOrderDetailsPage.getJobNumberAvailableJob();

        // Customer sign out
        global.nonAdminSignOut();

        // Check if the redirected page contains the prominent Home Page elements
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(homePage.checkHomePage());
    }

    /**
     * translatorFindJob --- a method wherein a translator signs in and looks for the recently
     * created job and opens the workbench file page
     * */
    @Test(dependsOnMethods = "placeAnOrder")
    public void translatorFindJob() {
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
    }

    /**
     * translatorOpenJob --- a method which checks the layout of the workbench and checks the glossary section
     * */
    @Test(dependsOnMethods = "translatorFindJob")
    public void translatorOpenJob() {

        // Check if instruction is viewable by the translator
        assertTrue(workbenchFilePage.checkComment(instruction), var.getElementIsNotDisplayedErrMsg());

        // Upload translated file and submit
        workbenchFilePage.translateFileJob(translatedItem);
        page.refresh();
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * customerApprove --- this method has the customer approve the job
     * */
    @Test(dependsOnMethods = "translatorOpenJob")
    public void customerApprove() {
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(31), var.getDefaultPassword());
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
