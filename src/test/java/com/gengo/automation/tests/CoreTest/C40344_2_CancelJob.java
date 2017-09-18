package com.gengo.automation.tests.CoreTest;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.DecimalFormat;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

/**
 * @case Cancel A Job
 * Word to Character, With Credits, Single Text
 * @reference https://gengo.testrail.com/index.php?/cases/view/40344
 */
public class C40344_2_CancelJob extends AutomationBase {

    private String parentWindow, jobURL, excerpt;
    private String[] itemToTranslate, unitCount;
    private DecimalFormat df = new DecimalFormat("#.00");

    @BeforeMethod
    public void initFields() throws IOException{
        excerpt = var.getExcerptEnglish(17);
        itemToTranslate = new String[] {
                var.getItemToTranslate(17)
        };
        unitCount = new String[] { var.getUnitCountSource(17) };
    }

    public C40344_2_CancelJob() throws IOException {}

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
     * pickUpByTranslator1 --- a method for checking the logging in of a translator account,
     * picking up the recently created job and viewing and adding of comments
     * */
    @Test(dependsOnMethods = "placeAnOrder")
    public void checkByTranslator1(){
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
        jobURL = getDriver().getCurrentUrl();

        // Translator account is logged out and the Home Page is checked
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * customerCancel --- This method has the customer cancel job previously created
     * */
    @Test(dependsOnMethods = "checkByTranslator1")
    public void customerCancel(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(27), var.getDefaultPassword());
        global.selectCustomer();
        assertTrue(customerDashboardPage.checkCustomerDashboard());
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());

        // Navigate to Orders page and look for the recently placed order
        globalPage.goToOrdersPage();

        // Retrieve credits before Cancelling Job
        double credit = globalPage.getCurrentCredits();

        // Look for jobs to cancel
        customerOrdersPage.clickPendingOption();
        customerOrdersPage.findOrder(excerpt);

        // Retrieve price of job to be cancelled
        double jobPrice = Double.parseDouble(customerOrderDetailsPage.getOrderPrice().getText().replaceAll("[^0-9.,]+",""));

        // Calculate the resulting credit after cancel
        double updatedCredit = Double.parseDouble(df.format(globalPage.getCurrentCredits() + jobPrice));

        // Cancel job
        customerOrderDetailsPage.cancelOrder();

        // Check if the cancellation refund was added to the credits
        assertEquals(updatedCredit, globalPage.getCurrentCredits());
        assertEquals(credit, Double.parseDouble(df.format(updatedCredit - jobPrice)));
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * translatorCheckCollection --- this method checks the if the collection cancelled by the customer does not appear
     * on the translator side anymore
     * */
    @Test(dependsOnMethods = "customerCancel")
    public void translatorCheckCollection(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslatorStandard(4), var.getDefaultPassword());
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        // View the workbench of job previously opened
        page.launchUrl(jobURL);

        // Check if job is cancelled
        assertTrue(workbenchPage.collectionUnavailableCheck());
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }
}
