package com.gengo.automation.tests.Workbench;

import com.gengo.automation.global.AutomationBase;
import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * @case Verify that 2 translators can work in one collection at the same time.
 * @reference https://gengo.testrail.com/index.php?/cases/view/289
 */
public class C289_TwoTranslatorsAreWorkingForSameCollectionAtSameTime extends AutomationBase {

    private String[] itemToTranslates;
    private String[] translatedItems;
    private String[] unitCounts;
    private String excerpt;
    private List<String> excerpts = new ArrayList<>();
    private List<String> jobId;


    public C289_TwoTranslatorsAreWorkingForSameCollectionAtSameTime() throws IOException {}

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

        translatedItems = new String[] {
                var.getTranslatedItem(5),
                var.getTranslatedItem(6),
                var.getTranslatedItem(7),
                var.getTranslatedItem(8),
        };

        // Shortened appending of values to List typed field 'excerpts'.
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
    public void twoTranslatorsCanWorkInOneCollectionAtTheSameTime() throws IOException {
        this.newUser = csv.generateNewUser(var.SHEETNAME_CUSTOMERS, 0);
        globalMethods.createNewCustomer(this.newUser);
        this.placeAnOrder(this.newUser);
        csv.writeValue(this.newUser, var.SHEETNAME_CUSTOMERS, 0);
        this.pickUpByFirstTranslator(this.jobId);
        this.pickUpBySecondTranslator(this.jobId);
        this.customerReviseTwoJobs(this.newUser, this.jobId);
        this.pickUpBySecondTranslatorRefreshed(this.jobId);
        this.pickUpByFirstTranslatorRefreshedAndTranslate(this.jobId);
        this.pickUpBySecondTranslatorRefreshedAndTranslate(this.jobId);
        this.customerApproveJob(this.newUser, this.jobId);
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

        global.selectCustomer();

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

    private void pickUpByFirstTranslator(List<String> jobIds) throws IOException {
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

        // Assert that page title is present in the DOM.
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Navigate to the Jobs Page and look for the recently created job
        global.clickJobsTab();
        translatorJobsPage.findJob(excerpt, jobIds);

        // Translate the first 2 jobs.
        workbenchPage.translateJobMultiple(translatedItems, excerpts, true, false, 2, 0);

        // Decline the job.
        workbenchPage.declineJob();

        // Assert that after submission, it redirects to the correct page.
        assertTrue(page.getCurrentUrl().equals(translatorDashboardPage.TRANSLATOR_DASHBOARD_URL),
                var.getWrongUrlErrMsg());

        // Sign out.
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void pickUpBySecondTranslator(List<String> jobIds) throws IOException {
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

        loginPage.loginAccount(var.getTranslatorPro(5), var.getDefaultPassword(), true);

        // Assert that page title is present in the DOM.
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Navigate to the Jobs Page and look for the recently created job
        global.clickJobsTab();

        // Find the job according to the excerpt which is at the 2nd index (3rd job).
        translatorJobsPage.findJob(excerpts.get(2), jobIds);

        // Assert that it can translate the remaining job
        workbenchPage.startTranslate();

        // Sign out.
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void customerReviseTwoJobs(String user, List<String> jobIds) {
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

        global.selectCustomer();

        // Request correction for the first 2 jobs.
        for (int i = 0; i < jobIds.size() - 2; i++) {
            global.goToCustomerReviewableJob(jobIds, i);
            customerOrderDetailsPage.requestCorrection(var.getCustomerComment(), true, true, true);
        }

        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void pickUpBySecondTranslatorRefreshed(List<String> jobIds) throws IOException {
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

        loginPage.loginAccount(var.getTranslatorPro(5), var.getDefaultPassword(), true);

        // Assert that page title is present in the DOM.
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Navigate to the Jobs Page and look for the recently created job
        global.clickJobsTab();

        // Find the job according to the excerpt which is at the 2nd index (3rd job).
        translatorJobsPage.findJob(excerpt, jobIds);

        // Sign out.
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void pickUpByFirstTranslatorRefreshedAndTranslate(List<String> jobIds) throws IOException {
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

        // Assert that page title is present in the DOM.
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Navigate to the Jobs Page and look for the recently created job
        global.clickJobsTab();

        translatorJobsPage.findJob(excerpt, jobIds);

        workbenchPage.closeWorkbenchModal();

        assertTrue(workbenchPage.checkCommentVisible(var.getCustomerComment()), var.getElementIsNotDisplayedErrMsg());

        // Translate the first 2 jobs.
        workbenchPage.translateJobMultiple(itemToTranslates, excerpts, false, true, 2, 0);

        // Assert that after submission, it redirects to the correct page.
        assertTrue(page.getCurrentUrl().equals(translatorDashboardPage.TRANSLATOR_DASHBOARD_URL),
                var.getWrongUrlErrMsg());

        // Sign out.
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void pickUpBySecondTranslatorRefreshedAndTranslate(List<String> jobIds) throws IOException {
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

        loginPage.loginAccount(var.getTranslatorPro(5), var.getDefaultPassword(), true);

        // Assert that page title is present in the DOM.
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Navigate to the Jobs Page and look for the recently created job
        global.clickJobsTab();
        translatorJobsPage.findJob(excerpt, jobIds);

        assertTrue(workbenchPage.checkCommentVisible(var.getCustomerComment()), var.getElementIsNotDisplayedErrMsg());

        // Translate the first 2 jobs.
        workbenchPage.translateJobMultiple(itemToTranslates, excerpts, false, true, 2, 2);

        // Assert that after submission, it redirects to the correct page.
        assertTrue(page.getCurrentUrl().equals(translatorDashboardPage.TRANSLATOR_DASHBOARD_URL),
                var.getWrongUrlErrMsg());

        // Sign out.
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void customerApproveJob(String user, List<String> jobIds) {
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
}
