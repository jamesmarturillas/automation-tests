package com.gengo.automation.tests.Admin;

import com.gengo.automation.global.AutomationBase;
import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.gengo.automation.fields.Constants.AVAILABLE;
import static com.gengo.automation.fields.Constants.CANCELLED;
import static org.testng.Assert.assertTrue;

/**
 * @case Cancel Job
 * Word to Character, Credits Payment, Standard Tier, Multiple Jobs
 * @reference https://gengo.testrail.com/index.php?/cases/view/18349
 */
public class C18349_3_CancelJobs extends AutomationBase {

    private String parentWindow, orderNo, excerpt, collectionId, jobURL;
    private String[] itemToTranslate;
    private String[] excerpts, unitCount, jobNo = new String[3], jobURLs = new String[3];

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

    public C18349_3_CancelJobs() throws IOException {}

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
        customerCheckoutPage.payWithCredits();

        // Retrieve the order number
        orderNo = customerOrderCompletePage.orderNumber();

        // Return to dashboard page
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

        for(int ctr = 0; ctr < jobURLs.length; ctr++) {
            // Navigate to the Jobs Page and look for the recently created job
            translatorDashboardPage.clickJobsTab();
            translatorJobsPage.findJob(excerpts[ctr]);
            jobURLs[ctr] = page.getCurrentUrl();
            workbenchPage.closeWorkbenchModal();
        }

        // The collection ID is retrieved for use in finding the job in Admin
        collectionId = workbenchPage.getCollectionIdTxt().getText().replaceAll("[A-z#]", "");

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * adminCancelJob --- this method logs in an Admin account,
     * looks for the collection and cancels it
     * */
    @Test(dependsOnMethods = "pickUpByTranslator")
    public void adminReassignCollection(){
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

        // Go to Jobs Page
        adminPage.goToJobs();
        for(String job : jobNo)
            driver.findElement(By.xpath("//input[@name='item_cb_" + job.substring(1) + "']")).isDisplayed();

        // Check status of jobs after every update
        assertTrue(adminJobsPage.checkJobStatus(jobNo, AVAILABLE));
        adminJobsPage.clickSingleCheckbox(jobNo[0]);
        adminJobsPage.ChooseActionsDropdown("cancel");
        adminJobsPage.clickCancelYesBtnModal();
        assertTrue(adminJobsPage.checkCancelConfirmation());

        page.refresh();
        assertTrue(adminJobsPage.checkJobStatus(jobNo[0], CANCELLED));
        assertTrue(adminJobsPage.checkJobStatus(jobNo[1], AVAILABLE));
        assertTrue(adminJobsPage.checkJobStatus(jobNo[2], AVAILABLE));
        adminJobsPage.clickSingleCheckbox(jobNo[1]);
        adminJobsPage.ChooseActionsDropdown("cancel");
        adminJobsPage.clickCancelYesBtnModal();
        assertTrue(adminJobsPage.checkCancelConfirmation());

        page.refresh();
        assertTrue(adminJobsPage.checkJobStatus(jobNo[0], CANCELLED));
        assertTrue(adminJobsPage.checkJobStatus(jobNo[1], CANCELLED));
        assertTrue(adminJobsPage.checkJobStatus(jobNo[2], AVAILABLE));

        adminJobsPage.clickSingleCheckbox(jobNo[2]);
        adminJobsPage.ChooseActionsDropdown("cancel");
        adminJobsPage.clickCancelYesBtnModal();
        assertTrue(adminJobsPage.checkCancelConfirmation());

        page.refresh();
        assertTrue(adminJobsPage.checkJobStatus(jobNo[0], CANCELLED));
        assertTrue(adminJobsPage.checkJobStatus(jobNo[1], CANCELLED));
        assertTrue(adminJobsPage.checkJobStatus(jobNo[2], CANCELLED));

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * pickUpByTranslatorAgain --- this method has the same translator log in and
     * check if collection has been cancelled or not
     * */
    @Test(dependsOnMethods = "adminReassignCollection")
    public void pickUpByTranslatorAgain(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslator(29), var.getDefaultPassword());
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());
        translatorDashboardPage.clickJobsTab();

        for(String url : jobURLs){
            page.launchUrl(url);

            // Check if job is cancelled
            assertTrue(workbenchPage.collectionUnavailableCheck());
        }

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }
}
