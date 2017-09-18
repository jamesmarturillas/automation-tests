package com.gengo.automation.tests.CoreTest;

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
 * @case Checks that the translator cannot pick another job when he has incomplete job.
 * @reference https://gengo.testrail.com/index.php?/cases/view/40342
 */
public class C40342_2_IncompleteJob extends AutomationBase {

    public C40342_2_IncompleteJob() throws IOException {}

    private String[] itemToTranslates;
    private String[] translatedItems;
    private String[] unitCounts;
    private String excerpt;
    private List<String> excerpts = new ArrayList<>();
    private List<String> jobId;
    private List<String> jobIds = new ArrayList<>();

    @BeforeClass
    public void initFields() throws IOException {
        this.newUser = csv.generateNewUser(var.SHEETNAME_CUSTOMERS, 0);
        excerpt = var.getExcerptEnglish(19);
        excerpts.add(excerpt);
        excerpts.add(var.getExcerptEnglish(20));

        itemToTranslates = new String[] {
                var.getItemToTranslate(25),
                var.getItemToTranslate(26),
        };
        unitCounts = new String[] {
                var.getUnitCountSource(25),
                var.getUnitCountSource(26),
        };
        translatedItems = new String[] {
                var.getTranslatedItem(25),
                var.getTranslatedItem(26),
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

    @Test(description = "Grouped job", dependsOnMethods = "createCustomerAccount")
    public void placeAnOrder() throws IOException {
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

        for (int i = 0; i < 2; i++) {
            // Assert that page title is present in the DOM.
            assertTrue(global.getPageTitle().isDisplayed(),
                    var.getElementIsNotDisplayedErrMsg());

            // Assert that the order icon is present for ordering process.
            assertTrue(global.getOrderTranslationIcon().isDisplayed(),
                    var.getElementIsNotDisplayedErrMsg());

            // Select 'Customer' account, navigate through the order form and assert that the redirected page is correct.
            global.selectCustomer();
            global.clickOrderTranslationIcon();
            assertTrue(page.getCurrentUrl().contains
                    (customerOrderFormPage.ORDER_FORM_URL), var.getWrongUrlErrMsg());

            // Place the order in the text area.
            customerOrderFormPage.inputItemToTranslate(itemToTranslates, unitCounts, itemToTranslates.length, false);

            // Assert that the page url after submitting is correct and
            // assert that the source language is auto detected and English.
            assertTrue(page.getCurrentUrl().equals
                    (customerOrderLanguagesPage.ORDERLANGUAGES_URL), var.getWrongUrlErrMsg());
            assertTrue(customerOrderLanguagesPage
                    .isSourceAutoDetected(), var.getTextNotEqualErrMsg());

            // Choose source language {Japanese this time}.
            customerOrderLanguagesPage.choooseLanguage(var.getJapaneseTo());
            customerOrderLanguagesPage.clickNextOptions();

            // Order as group.
            customerCheckoutPage.orderAsAGroup();

            // Place payment via existing credits and assert that the placement of order is successful.
            customerCheckoutPage.clickPayNowAndConfirm(false, false, false, false, false);
            assertTrue(page.getCurrentUrl().contains
                    (customerOrderCompletePage.ORDERCOMPLETE_URL), var.getWrongUrlErrMsg());

            // Go back to the customer dashboard and assert that customer dashboard is the redirected page.
            customerOrderCompletePage.clickGoToDashboard();
            assertTrue(customerDashboardPage.getCustomerDashboardText().isDisplayed(),
                    var.getElementIsNotDisplayedErrMsg());

            // Navigate to 'Orders' page and find the Job ID.
            global.goToOrdersPage();
            customerOrdersPage.clickPendingOption();
            assertTrue(customerOrdersPage.getPendingHeader().isDisplayed(),
                    var.getElementIsNotDisplayedErrMsg());

            // Extract the job id from pending jobs.
            jobId = customerOrdersPage.extractJobIdFromPendingJobs(excerpts);

            jobIds.addAll(jobId);
        }

        // Sign out
        global.nonAdminSignOut();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    @Test(dependsOnMethods = "placeAnOrder")
    public void pickUpByTranslator() throws IOException {
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

        // Navigate to the Jobs Page and look for the recently created job.
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt, jobIds);

        // Start the job.
        workbenchPage.closeWorkbenchModal();
        workbenchPage.startTranslate();

        // Click the Gengo Translator icon.
        global.clickTranslatorLogo();

        // Go to 'All' jobs and pick other job.
        global.goToTranslatorAllJobs();

        translatorJobsPage.findJob(excerpt, jobIds);

        // Try to pick-up the job and assert that the account cannot pick the job.
        workbenchPage.startTranslate(true, true);
        wait.impWait(30, translatorJobsPage.getModalCantPickAnotherJob());
        assertTrue(translatorJobsPage.getModalCantPickAnotherJob()
                .isDisplayed(), var.getElementIsNotDisplayedErrMsg());

        // Click OK and assert that it goes back to the translator dashboard.
        workbenchPage.submitModalOk();
        assertTrue(page.getCurrentUrl().equals(translatorDashboardPage.TRANSLATOR_DASHBOARD_URL),
                var.getWrongUrlErrMsg());

        // Goes back to the recently created
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt, jobIds);

        // Translate the job and assert that after submission, it redirects to the correct page.
        workbenchPage.translateJobMultiple(translatedItems, excerpts, false, translatedItems.length);
        assertTrue(page.getCurrentUrl().equals(translatorDashboardPage.TRANSLATOR_DASHBOARD_URL),
                var.getWrongUrlErrMsg());
    }

    @Test(dependsOnMethods = "pickUpByTranslator")
    public void pickTheSecondJob() throws IOException {
        // Navigate to the Jobs Page and look for the recently created job.
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt, jobIds);

        // Translate the job and assert that after submission, it redirects to the correct page.
        workbenchPage.translateJobMultiple(translatedItems, excerpts, translatedItems.length);
        assertTrue(page.getCurrentUrl().equals(translatorDashboardPage.TRANSLATOR_DASHBOARD_URL),
                var.getWrongUrlErrMsg());

        // Sign out
        global.nonAdminSignOut();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }
}
