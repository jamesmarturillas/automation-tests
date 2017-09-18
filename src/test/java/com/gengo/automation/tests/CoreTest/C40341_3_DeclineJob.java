package com.gengo.automation.tests.CoreTest;

import com.gengo.automation.fields.Constants;
import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * @case Test declining jobs with different scenarios.
 * @reference https://gengo.testrail.com/index.php?/cases/view/40341
 */
public class C40341_3_DeclineJob extends AutomationBase {

    private String excerpt;
    private String[] itemToTranslates;
    private String[] unitCounts;
    private List<String> fileNames = new ArrayList<>();
    private List<String> excerpts = new ArrayList<>();
    private List<String> jobIds = new ArrayList<>();

    public C40341_3_DeclineJob() throws IOException {}

    @BeforeClass
    public void initFields() throws IOException{
        this.newUser = csv.generateNewUser(var.SHEETNAME_CUSTOMERS, 0);

        excerpt = var.getExcerptFile(7);
        excerpts.add(excerpt);

        fileNames.add(var.getFileName(9));

        itemToTranslates = new String[] {
                var.getFile(9)
        };
        unitCounts = new String[] {
                var.getUnitCountSource(9),
        };
    }

    @AfterClass
    public void afterRun() throws IOException {
        csv.writeValue(this.newUser, var.SHEETNAME_CUSTOMERS, 0);
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test(description = "Use a new account as conditional case for database reset")
    public void createCustomerAccount() {
        globalMethods.createNewCustomer(this.newUser, true);
    }

    @Test(description = "File job")
    public void groupedJob() throws IOException {
        List<String> declineReasons = new ArrayList<>();
        declineReasons.add(Constants.WBFP_REASON_NOT_INTERESTED);
        declineReasons.add(Constants.WBFP_REASON_NO_TIME);

        for (String declineReason : declineReasons) {
            this.placeAnOrder(); // Place order by customer.
            this.pickUpByTranslator(declineReason); // Pickup the job by translator and decline it.
        }
    }

    public void placeAnOrder() {
        pluginPage.passThisPage();

        // Assert that homepage 'SIGN IN' link is displayed.
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        homePage.clickSignInButton();

        // Check that after clicking the 'SIGN IN' link, the URL is right and 'SIGN UP' button is displayed as well.
        assertTrue(page.getCurrentUrl().equals(loginPage.LOGIN_PAGE_URL),
                var.getWrongUrlErrMsg());
        assertTrue(loginPage.getSignUpBtn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        loginPage.loginAccount(this.newUser, var.getDefaultPassword());

        // Assert that page title is present in the DOM.
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Click the order translation icon beside the name dropdown.
        global.clickOrderTranslationIcon();

        // Input all item to translate.
        customerOrderFormPage.inputItemToTranslate(itemToTranslates, unitCounts, itemToTranslates.length, true);

        // Assert that the page url after submitting is correct.
        assertTrue(page.getCurrentUrl().equals(customerOrderLanguagesPage.ORDERLANGUAGES_URL),
                var.getWrongUrlErrMsg());

        // Assert that the source language is auto detected and English.
        assertTrue(customerOrderLanguagesPage.isSourceAutoDetected(),
                var.getTextNotEqualErrMsg());

        // Choose Japanese as target language.
        customerOrderLanguagesPage.choooseLanguage(var.getJapaneseTo());
        customerOrderLanguagesPage.clickNextOptions();

        customerCheckoutPage.clickPayNowAndConfirm(false, false, false, false, false);

        // Assert that order is complete by checking the return URL.
        assertTrue(page.getCurrentUrl().contains(customerOrderCompletePage.ORDERCOMPLETE_URL),
                var.getWrongUrlErrMsg());

        // Go back to the customer dashboard.
        customerOrderCompletePage.clickGoToDashboard();
        assertTrue(customerDashboardPage.getCustomerDashboardText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Navigate to 'Orders' page and find the Job ID.
        global.goToOrdersPage();
        customerOrdersPage.clickPendingOption();
        assertTrue(customerOrdersPage.getPendingHeader().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Extract the job id from pending jobs.
        jobIds = customerOrdersPage.extractJobIdFromPendingJobs(fileNames);

        // Sign out
        global.nonAdminSignOut();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    public void pickUpByTranslator(String declineReason) throws IOException {
        pluginPage.passThisPage();

        // Assert that homepage 'SIGN IN' link is displayed.
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        homePage.clickSignInButton();

        // Check that after clicking the 'SIGN IN' link, the URL is right and 'SIGN UP' button is displayed as well.
        assertTrue(page.getCurrentUrl().equals(loginPage.LOGIN_PAGE_URL),
                var.getWrongUrlErrMsg());
        assertTrue(loginPage.getSignUpBtn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        loginPage.loginAccount(var.getTranslatorStandard(5), var.getDefaultPassword());

        // Check if the page redirected to contains the elements that should be seen in a Translator Dashboard
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        // Navigate to the Jobs Page and look for the recently created job.
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findFileJob(excerpt, jobIds);

        // Start the job by downloading the file and cancel it.
        // After cancelling, assert that there is the message saying successfully cancelled the job.
        workbenchFilePage.downloadFile();
        workbenchFilePage.cancelJob(declineReason);
        assertTrue(workbenchFilePage.getFileJobCancelledMsg()
                .isDisplayed(), var.getElementIsNotDisplayedErrMsg());

        workbenchFilePage.clickMessageIconClose();

        // Sign out.
        global.nonAdminSignOut();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }
}
