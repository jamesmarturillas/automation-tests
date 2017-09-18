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
 * Word to Character, Credits Payment, Standard Tier, Grouped Job
 * @reference https://gengo.testrail.com/index.php?/cases/view/18359
 */
public class C18359_1_CancelAvailableGroupedOrders extends AutomationBase {

    private String parentWindow;
    private String excerpt;
    private String jobURL;
    private String orderNumber;
    private String orderPrice;
    private String customerCredits;
    private String newCredits;
    private String credits;
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

    public C18359_1_CancelAvailableGroupedOrders() throws IOException {}

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

        // Store the credits to a variable for comparison later
        customerCredits = customerDashboardPage.getCredits().getText();

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

        // Store order price to a variable for comparison later
        orderPrice = customerCheckoutPage.getOrderTotalPrice().getText();

        // Place payment via credits
        customerCheckoutPage.payWithCredits();

        // Return to dashboard page
        orderNumber = customerOrderCompletePage.orderNumber();
        customerOrderCompletePage.clickGoToDashboard();
        credits = customerDashboardPage.getCredits().getText();
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

        // Cancel order and check for confirmation text
        adminOrdersPage.clickSingleCheckbox(orderNumber);
        adminOrdersPage.ChooseActionsDropdown("cancel");
        adminOrdersPage.clickCancelYesBtnModal();
        assertTrue(adminOrdersPage.checkCancelConfirmation());

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

        // Check if job is cancelled
        assertTrue(workbenchPage.collectionUnavailableCheck());
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * checkByCustomer --- this method logs in the customer and checks if the credits
     * are refunded successfully
     * */
    @Test(dependsOnMethods = "pickUpByTranslatorAgain")
    public void checkByCustomer(){
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

        // Store current credits to a variable
        newCredits = customerDashboardPage.getCredits().getText();

        // Compare the credits before and after order
        assertTrue(globalMethods.compareCredits(customerCredits, credits, orderPrice));

        // Compare the credits before and after cancellation
        assertTrue(globalMethods.compareCredits(credits, newCredits, orderPrice));
    }
}
