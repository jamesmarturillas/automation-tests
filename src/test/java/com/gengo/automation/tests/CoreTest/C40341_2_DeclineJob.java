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
 * @case Test declining jobs with different scenarios.
 * @reference https://gengo.testrail.com/index.php?/cases/view/40341
 */
public class C40341_2_DeclineJob extends AutomationBase {

    public C40341_2_DeclineJob() throws IOException {}

    private String[] itemToTranslates;
    private String[] unitCounts;
    private String excerpt;
    private List<String> excerpts = new ArrayList<>();
    private List<String> jobId;

    @BeforeClass
    public void initFields() throws IOException{
        this.newUser = csv.generateNewUser(var.SHEETNAME_CUSTOMERS, 0);
        excerpt = var.getExcerptEnglish(1);
        excerpts.add(excerpt);
        excerpts.add(var.getExcerptEnglish(2));
        excerpts.add(var.getExcerptEnglish(3));

        itemToTranslates  = new String[] {
                var.getItemToTranslate(1),
                var.getItemToTranslate(2),
                var.getItemToTranslate(3),
        };
        unitCounts = new String[] {
                var.getUnitCountSource(1),
                var.getUnitCountSource(2),
                var.getUnitCountSource(3),
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

    @Test(description = "Grouped job.")
    public void groupedJob() throws IOException {
        // Perform placing of single order for the translator to pick up.
        this.placeAnOrder();
        // Pick up the job and decline it by picking `I’m unable to finish`
        this.pickUpByTranslator("unable");
        // Perform placing of single order for the translator to pick up.
        this.placeAnOrder();
        // Pick up the job and decline it by picking `Unreasonable requests`
        this.pickUpByTranslator("unreasonable");
    }

    private void placeAnOrder() throws IOException {
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

        // Order as a group.
        customerCheckoutPage.orderAsAGroup();

        page.refresh();

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

        loginPage.loginAccount(var.getTranslatorStandard(5), var.getDefaultPassword(), true);

        // Check if the page redirected to contains the elements that should be seen in a Translator Dashboard
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        // Navigate to the Jobs Page and look for the recently created job.
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt, jobId);

        // Decline the job by choosing the "I’m unable to finish" reason.
        workbenchPage.closeWorkbenchModal();
        workbenchPage.startTranslate();
        workbenchPage.declineJob(declineReason, var.getTranslatorComment());

        // Sign out.
        global.nonAdminSignOut();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }
}
