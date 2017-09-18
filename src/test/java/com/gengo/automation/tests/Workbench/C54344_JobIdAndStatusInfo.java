package com.gengo.automation.tests.Workbench;

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
 * @case Verify that the Job ID and status information is visible in a collection.
 * @reference https://gengo.testrail.com/index.php?/cases/view/54344
 */
public class C54344_JobIdAndStatusInfo extends AutomationBase {

    private String[] itemToTranslates;
    private String[] unitCounts;
    private String[] translatedItem;
    private String excerpt;
    private List<String> excerpts = new ArrayList<>();
    private List<String> jobId = new ArrayList<>();
    private List<String> jobStatuses = new ArrayList<>();

    public C54344_JobIdAndStatusInfo() throws IOException {}

    @BeforeClass
    public void initFields() throws IOException{
        excerpt = var.getExcerptEnglish(5);

        itemToTranslates  = new String[] {
                var.getItemToTranslate(5),
                var.getItemToTranslate(6),
                var.getItemToTranslate(7),
                var.getItemToTranslate(8),
        };

        unitCounts = new String[] {
                var.getUnitCountSource(5),
                var.getUnitCountSource(6),
                var.getUnitCountSource(7),
                var.getUnitCountSource(8),
        };

        translatedItem = new String[] {
                var.getTranslatedItem(5),
                var.getTranslatedItem(6),
                var.getTranslatedItem(7),
                var.getTranslatedItem(8),
        };

        // Shortened appending the values to List typed field 'excerpts'.
        // @expression i < 9 : The loop will stop when it reaches 8.
        for (int i = 5; i < 9; i++) {
            excerpts.add(var.getExcerptEnglish(i));
        }
    }

    @AfterClass
    public void afterRun() {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test
    public void checkJobIdAndStatusInfo() throws IOException, InterruptedException {
        this.newUser = csv.generateNewUser(var.SHEETNAME_CUSTOMERS, 0);
        globalMethods.createNewCustomer(this.newUser);
        this.placeAnOrder(this.newUser);
        csv.writeValue(this.newUser, var.SHEETNAME_CUSTOMERS, 0);
        this.checkJobInfoByTranslator(this.jobId);
        this.customerRequestAndRejectJobs(this.newUser, this.jobId);
        this.declineJobByTranslator(this.jobId);
    }

    private void placeAnOrder(String user) {
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

        loginPage.loginAccount(user, var.getDefaultPassword(), false);

        // Assert that page title is present in the DOM.
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Click the order translation icon beside the name dropdown.
        global.clickOrderTranslationIcon();

        // Input all item to translate.
        customerOrderFormPage.inputItemToTranslate(itemToTranslates, unitCounts, itemToTranslates.length, false);

        // Assert that the page url after submitting is correct.
        assertTrue(page.getCurrentUrl().equals(customerOrderLanguagesPage.ORDERLANGUAGES_URL),
                var.getWrongUrlErrMsg());

        // Assert that the source language is auto detected and English.
        assertTrue(customerOrderLanguagesPage.isSourceAutoDetected(),
                var.getTextNotEqualErrMsg());

        // Choose Japanese as target language.
        customerOrderLanguagesPage.choooseLanguage(var.getJapaneseFrom());
        customerOrderLanguagesPage.clickNextOptions();

        customerCheckoutPage.orderAsAGroup();

        page.refresh();

        customerCheckoutPage.clickPayNowAndConfirm(false, true, true, true, true);

        // Assert that order is complete by checking the return URL.
        assertTrue(page.getCurrentUrl().contains(customerOrderCompletePage.ORDERCOMPLETE_URL),
                var.getWrongUrlErrMsg());

        // Go back to the customer dashboard.
        customerOrderCompletePage.clickGoToDashboard();

        // Assert that customer dashboard is the redirected page.
        assertTrue(customerDashboardPage.getCustomerDashboardText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        global.goToOrdersPage();

        customerOrdersPage.clickPendingOption();

        assertTrue(customerOrdersPage.getPendingHeader().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        jobId = customerOrdersPage.extractJobIdFromPendingJobs(excerpts);

        // Sign out
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void checkJobInfoByTranslator(List<String> jobId) throws IOException, InterruptedException {
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

        loginPage.loginAccount(var.getTranslatorStandard(5), var.getDefaultPassword(), true);

        // Check if the page redirected to contains the elements that should be seen in a Translator Dashboard
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        // Navigate to the Jobs page and look for the recently created job
        global.clickJobsTab();

        translatorJobsPage.findJob(excerpt, jobId);

        jobStatuses.add(var.getAvailableJobStatus());

        // Assert that all job status are 'Available'
        workbenchPage.checkJobInfo(jobStatuses, jobId, 4);

        workbenchPage.closeWorkbenchModal();
        workbenchPage.startTranslate();

        jobStatuses.remove(var.getAvailableJobStatus());
        jobStatuses.add(var.getNotSubmittedJobStatus());

        // Assert that all job status are 'Not Submitted'
        workbenchPage.checkJobInfo(jobStatuses, jobId, 4);

        // Translate the job.
        workbenchPage.checkJobInfoAndTranslate(translatedItem, excerpts, jobStatuses, jobId, false, 4,translatedItem.length - 1, 0);

        jobStatuses.remove(var.getNotSubmittedJobStatus());
        jobStatuses.add(var.getSubmittedJobStatus());
        jobStatuses.add(var.getSubmittedJobStatus());
        jobStatuses.add(var.getSubmittedJobStatus());
        jobStatuses.add(var.getNotSubmittedJobStatus());

        workbenchPage.checkJobInfo(jobStatuses, jobId, 4);

        // Sign out.
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void customerRequestAndRejectJobs(String user, List<String> jobId) {
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

        loginPage.loginAccount(user, var.getDefaultPassword(), false);

        // Assert that page title is present in the DOM.
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        global.goToCustomerReviewableJob(jobId, 1);

        customerOrderDetailsPage.requestCorrection(var.getCustomerComment(), true, true, true);

        global.goToCustomerReviewableJob(jobId, 2);

        customerOrderDetailsPage.rejectTranslation(var.getCustomerCorrection(), false, "Quality");

        // Sign out.
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void declineJobByTranslator(List<String> jobId) throws IOException {
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

        loginPage.loginAccount(var.getTranslatorStandard(5), var.getDefaultPassword(), true);

        // Check if the page redirected to contains the elements that should be seen in a Translator Dashboard
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        // Navigate to the Jobs page and look for the recently created job
        global.clickJobsTab();

        translatorJobsPage.findJob(excerpt, jobId);

        workbenchPage.declineJob();

        // Sign out.
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // TODO : Polish way of assertions and flow of the test case.
    }
}
