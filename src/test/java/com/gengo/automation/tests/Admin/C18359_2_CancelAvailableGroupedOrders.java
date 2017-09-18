package com.gengo.automation.tests.Admin;

import com.gengo.automation.global.AutomationBase;
import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * @case Cancel Available Grouped Orders
 * Word to Character, Credits Payment, Standard Tier, Grouped Job - Unavailable
 * @reference https://gengo.testrail.com/index.php?/cases/view/18359
 */
public class C18359_2_CancelAvailableGroupedOrders extends AutomationBase {

    private String parentWindow;
    private String excerpt;
    private String jobURL;
    private String orderNumber;
    private String[] itemToTranslate;
    private String[] excerpts;
    private String[] unitCount;
    private String[] jobNo = new String[3];

    @BeforeMethod
    public void initFields() throws IOException {
        excerpt = var.getExcerptEnglish(19);
        excerpts = new String[] {
                var.getExcerptEnglish(19),
                var.getExcerptEnglish(20),
                var.getExcerptEnglish(21)
        };
        itemToTranslate = new String[] {
                var.getItemToTranslate(25),
                var.getItemToTranslate(26),
                var.getItemToTranslate(27),
        };
        unitCount = new String[] {
                var.getUnitCountSource(25),
                var.getUnitCountSource(26),
                var.getUnitCountSource(27)
        };
    }

    public C18359_2_CancelAvailableGroupedOrders() throws IOException {}

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

        // Place payment via credits
        customerCheckoutPage.payWithCredits();

        // Return to dashboard page
        orderNumber = customerOrderCompletePage.orderNumber();
        customerOrderCompletePage.clickGoToDashboard();
        for(int i = 0; i < excerpts.length; i++) {
            global.goToOrdersPage();
            customerOrdersPage.clickPendingOption();
            customerOrdersPage.findOrder(excerpts[i]);
            jobNo[i] = customerOrderDetailsPage.getJobNumberAvailableJob();
        }
        // Customer sign out
        global.nonAdminSignOut();

        // Check if the redirected page contains the prominent Home Page elements
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(homePage.checkHomePage());
    }

    /**
     * addCommentAsTranslator --- a method for picking up a job
     * */
    @Test(dependsOnMethods = "placeAnOrder")
    public void pickUpByTranslator(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Log in a translator account
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslator(29), var.getDefaultPassword());

        // Check if the page redirected to contains the elements that should be seen in a Translator Dashboard
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        // Navigate to the Jobs Page and look for the recently created job
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt);
        jobURL = page.getCurrentUrl();
        workbenchPage.closeWorkbenchModal();
        workbenchPage.startTranslate();
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * adminCancelJob --- this method logs in an Admin account,
     * looks for the collection and cancels it
     * */
    @Test(dependsOnMethods = "pickUpByTranslator")
    public void adminCancelJob(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        assertTrue(page.getCurrentUrl().equals(loginPage.LOGIN_PAGE_URL),
                var.getWrongUrlErrMsg());
        assertTrue(loginPage.getSignUpBtn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        loginPage.loginAccount(var.getTestAdmin(), var.getDefaultPassword());

        // Assert that page title is present in the DOM.
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Select Admin user level.
        global.selectAdmin();

        // Assert that we are redirected to the right page.
        assertTrue(page.getCurrentUrl().contains("admin."),
                var.getWrongUrlErrMsg());

        // Hide the right panel.
        adminPage.hidePanel();

        // Go to Orders Page
        adminPage.goToOrders();

        // Check if the order is displayed
        driver.findElement(By.xpath("//input[@name='item_cb_" + orderNumber + "']")).isDisplayed();

        // Cancel order and check for failed cancellation message
        adminOrdersPage.clickSingleCheckbox(orderNumber);
        adminOrdersPage.ChooseActionsDropdown("cancel");
        adminOrdersPage.clickCancelYesBtnModal();
        assertTrue(adminOrdersPage.checkCancelFail());

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * pickUpByTranslatorAgain --- this method has the same translator log in and
     * check if collection has been cancelled or not
     * */
    @Test(dependsOnMethods = "adminCancelJob")
    public void pickUpByTranslatorAgain(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();

        loginPage.loginAccount(var.getTranslator(29), var.getDefaultPassword());
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        // View the workbench of job previously opened
        page.launchUrl(jobURL);
        workbenchPage.addMultipleTranslatedText(itemToTranslate, excerpts, itemToTranslate.length);
        workbenchPage.submitJob();
        workbenchPage.submitModalOk();

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * customerApprove --- log in as customer and approve recently submitted job
     * */
    @Test(dependsOnMethods = "pickUpByTranslatorAgain")
    public void customerApprove(){
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

        // Loops through the jobs and approves them all
        for(int ctr = 0; ctr < excerpts.length; ctr++) {
            globalPage.goToOrdersPage();
            customerOrdersPage.clickReviewableOption();
            customerOrdersPage.findOrder(excerpts[ctr]);

            // Retrieve the job Number of the order
            jobNo[ctr] =customerOrderDetailsPage.getJobNumberReviewableJob();

            // Job is approved
            customerOrderDetailsPage.approveJob();
        }
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
        assertTrue(gmailInboxPage.checkOrderReceived(orderNumber));
        for(String jobNumber : jobNo) {
            assertTrue(gmailInboxPage.checkJobForReview(jobNumber.substring(1)));
            assertTrue(gmailInboxPage.checkJobApproved(jobNumber.substring(1)));
        }
    }
}
