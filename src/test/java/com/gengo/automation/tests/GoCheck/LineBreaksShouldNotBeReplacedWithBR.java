package com.gengo.automation.tests.GoCheck;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

/**
 * @case [GoCheck] Line breaks are replaced with "<br/>"
 * @reference https://www.pivotaltracker.com/story/show/145984795
 */
public class LineBreaksShouldNotBeReplacedWithBR extends AutomationBase{

    public LineBreaksShouldNotBeReplacedWithBR() throws IOException {}

    private String orderNo, excerpt, collectionId, jobId,  translatedText;
    private String[] itemToTranslate, unitCount;
    private List<String> excerpts = new ArrayList<>();

    @BeforeClass
    public void initFields() throws IOException{
        this.newUser = csv.generateNewUser(var.SHEETNAME_CUSTOMERS, 0);
        excerpt = var.getExcerptEnglish(33);
        excerpts.add(excerpt);

        itemToTranslate  = new String[] {
                var.getItemToTranslate(64),
        };
        unitCount = new String[] {
                var.getUnitCountSource(64),
        };
        translatedText = var.getTranslatedItem(64);
    }

    @AfterClass
    public void afterRun() throws IOException {
        csv.writeValue(this.newUser, var.SHEETNAME_CUSTOMERS, 0);
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
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

        // Place payment via Stripe
        customerCheckoutPage
                .clickPayNowAndConfirm(false, true, true, true, true);

        // Retrieve the order number
        orderNo = customerOrderCompletePage.orderNumber();

        // Return to dashboard page
        customerOrderCompletePage.clickGoToDashboard();

        // Retrieve the job ID
        jobId = customerOrdersPage.extractJobIdFromPendingJobs(excerpt);

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

        // Close workbench modal and start translation
        workbenchPage.closeWorkbenchModal();
        workbenchPage.startTranslate();

        // The collection ID is retrieved for use in finding the job in Admin
        collectionId = workbenchPage.getCollectionIdTxt().getText().replaceAll("[A-z#]", "");

        // Text to be translated is added
        workbenchPage.translateAndSubmitJob(translatedText);

        // Check if page is redirected to the translator Dashboard
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    @Test(dependsOnMethods = "pickUpByTranslator")
    public void verifyGoCheck() {
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        assertTrue(page.getCurrentUrl().equals(loginPage.LOGIN_PAGE_URL),
                var.getWrongUrlErrMsg());
        loginPage.loginAccount(var.getTestAdmin(), var.getDefaultPassword());
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        global.selectAdmin();
        assertTrue(page.getCurrentUrl().contains("admin."),
                var.getWrongUrlErrMsg());
        adminPage.hidePanel();

        // Access Job details page of ordered job
        adminPage.goToJobs();
        adminJobsPage.getFilterJobsCollectionID().sendKeys(collectionId);
        adminPage.clickFilterButton();
        adminJobsPage.openJobIDDetailsPage(jobId);

        // Request check for the job
        adminJobsPage.clickJobActions("requestCheck");

        // On Checks list page, filter to get the generated check
        adminChecksPage.filterChecksJobID(jobId);
        adminPage.clickFilterButton();

        // Store check ID
        adminChecksPage.setCheckID();

        // Access check details page of first check on list
        adminChecksPage.accessFirstCheckDetails();
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(adminChecksPage.getCheckDetailsPage().isDisplayed());

        // Store Check Trigger and Job ID
        adminChecksPage.setJobID();

        // Go to Actions > Check
        adminChecksPage.goToGoCheckPage();
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(page.getCurrentUrl().contains("gocheck."),
                var.getWrongUrlErrMsg());

        // Validate target content where it should not have <br/> included
        assertFalse(goCheckPage.getGocheckTargetContent().getText().contains("<br/>"));
    }
}
