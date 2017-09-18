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
 * @case Verify that an approved job cannot be edited once submitted.
 * @reference https://gengo.testrail.com/index.php?/cases/view/297
 */
public class C297_CannotEditSubmittedAndApprovedJob extends AutomationBase {

    private String excerpt;
    private List<String> jobIds = new ArrayList<>();
    private List<String> excerpts = new ArrayList<>();
    private String[] itemToTranslates;
    private String[] unitCounts;
    private String[] translatedItem;

    public C297_CannotEditSubmittedAndApprovedJob() throws IOException {}

    @BeforeClass
    public void initFields() throws IOException{
        itemToTranslates = new String[] {
                var.getItemToTranslate(25),
                var.getItemToTranslate(26),
                var.getItemToTranslate(27),
        };
        unitCounts = new String[] {
                var.getUnitCountSource(25),
                var.getUnitCountSource(26),
                var.getUnitCountSource(27),
        };
        translatedItem = new String[] {
                var.getTranslatedItem(25),
                var.getTranslatedItem(26),
                var.getTranslatedItem(27),
        };

        excerpts.add(var.getExcerptEnglish(19));
        excerpts.add(var.getExcerptEnglish(20));
        excerpts.add(var.getExcerptEnglish(21));

        excerpt = var.getExcerptEnglish(19);
    }

    @AfterClass
    public void afterRun() {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test
    public void editSubmittedAndApprovedJob() throws IOException {
        this.newUser = csv.generateNewUser(var.SHEETNAME_CUSTOMERS, 0);
        globalMethods.createNewCustomer(this.newUser);
        this.placeAnOrder(this.newUser);
        csv.writeValue(this.newUser, var.SHEETNAME_CUSTOMERS, 0);
        this.translateSomeJobsByTranslator(this.jobIds);
        this.customerApproveTheTranslatedJob(this.newUser, this.jobIds);
        this.translatorEditTheApprovedJob(this.jobIds);
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

        loginPage.loginAccount(user, var.getDefaultPassword());

        // Assert that page title is present in the DOM.
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Assert that the order icon is present for ordering process.
        assertTrue(global.getOrderTranslationIcon().isDisplayed(),
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
        customerOrderLanguagesPage.choooseLanguage(var.getJapaneseTo());
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

        jobIds = customerOrdersPage.extractJobIdFromPendingJobs(excerpts);

        // Sign out
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void translateSomeJobsByTranslator(List<String> jobIds) throws IOException {
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

        // Assert that page title is present in the DOM.
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Check if the page redirected to contains the elements that should be seen in a Translator Dashboard
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        // Navigate to the Jobs page and look for the recently created job
        global.clickJobsTab();

        translatorJobsPage.findJob(excerpt, jobIds);

        // In this case, the grouped job is just consist of 3 jobs. So it will only translate the first one.
        workbenchPage.translateJobMultiple(translatedItem, excerpts, true, false, excerpts.size()-2, 0);

        // Will go to the reviewable jobs and check if it's existing there.
        global.goToTranslatorReviewableJobs();
        translatorJobsPage.findJob(excerpt, jobIds);

        // Assert that the translation text area is not editable.
        assertTrue(workbenchPage.isTranslationTextAreaNotEditable(),
                var.getElementIsEnabledErrMsg());

        // Sign out.
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void customerApproveTheTranslatedJob(String user, List<String> jobIds) {
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

        loginPage.loginAccount(user, var.getDefaultPassword());

        // Assert that page title is present in the DOM.
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Find the job in reviewables and approve.
        global.goToCustomerReviewableJob(excerpt, jobIds);

        // Approve job.
        customerOrderDetailsPage.approveJob();

        // Assert that the approval was success.
        assertTrue(customerOrderDetailsPage.getInPageAlertApprove().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Sign out.
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void translatorEditTheApprovedJob(List<String> jobIds) throws IOException {
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

        // Assert that page title is present in the DOM.
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Check if the page redirected to contains the elements that should be seen in a Translator Dashboard
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        // Navigate to the Jobs page and look for the recently created job
        global.clickJobsTab();
        global.goToTranslatorCompletedJobs();

        translatorJobsPage.findJob(excerpt, jobIds);

        // Assert that the translation text area is not editable.
        assertTrue(workbenchPage.isTranslationTextAreaNotEditable(),
                var.getElementIsEnabledErrMsg());

        workbenchPage.translateJobMultiple(false, translatedItem, excerpts, false, excerpts.size()-1, 1);

        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // TODO : polish clicking the 'Submit job' button.
    }
}
