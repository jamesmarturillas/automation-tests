package com.gengo.automation.tests.Workbench;

import com.gengo.automation.global.AutomationBase;
import com.gengo.automation.helpers.ElementState;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * @case Comment R/W & set a flag for job(s).
 * @reference https://gengo.testrail.com/index.php?/cases/view/264
 * TODO : needs little polishing.
 */
public class C264_CommentRWAndFlagForJobs extends AutomationBase {

    private String[] itemToTranslates;
    private String[] unitCounts;
    private String[] translatedItem;
    private List<String> excerpts = new ArrayList<>();
    private String excerpt;
    private List<String> jobIds = new ArrayList<>();

    private ElementState elementState;

    public C264_CommentRWAndFlagForJobs() throws IOException {}

    @BeforeClass
    public void initFields() throws IOException{

        elementState = new ElementState(getDriver());

        excerpt = var.getExcerptEnglish(7);

        itemToTranslates  = new String[] {
                var.getItemToTranslate(7),
                var.getItemToTranslate(8),
                var.getItemToTranslate(9),
                var.getItemToTranslate(10),
        };

        unitCounts = new String[] {
                var.getUnitCountSource(7),
                var.getUnitCountSource(8),
                var.getUnitCountSource(9),
                var.getUnitCountSource(10),
        };

        translatedItem = new String[] {
                var.getTranslatedItem(7),
                var.getTranslatedItem(8),
                var.getTranslatedItem(9),
                var.getTranslatedItem(10),
        };

        excerpts.add(var.getExcerptEnglish(7));
        excerpts.add(var.getExcerptEnglish(8));
        excerpts.add(var.getExcerptEnglish(9));
        excerpts.add(var.getExcerptEnglish(10));
    }

    @AfterClass
    public void afterRun() {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test
    public void commentRwAndFlagForJobs() throws IOException {
        this.newUser = csv.generateNewUser(var.SHEETNAME_CUSTOMERS, 0);
        globalMethods.createNewCustomer(this.newUser);
        this.placeAnOrder(this.newUser);
        csv.writeValue(this.newUser, var.SHEETNAME_CUSTOMERS, 0);
        this.pickUpByFirstTranslator(jobIds);
        this.addCommentAsCustomer(this.newUser);
        this.checkCommentOfCustomerByTranslatorClicksTheFlagIssue(jobIds);
        this.customerChecksTheFlagIssueAndResolveIt(this.newUser);
        this.translatorChecksTheResolvedFlagAndDeclineTheJob(jobIds);
    }

    public void placeAnOrder(String user) {
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

        wait.impWait(30, customerOrderCompletePage.getBackToDashBoardBtn());
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

        this.addCommentToTheJobAsCustomer(jobIds, 0, 2);

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

        // Navigate to the Jobs Page and look for the recently created job
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt, jobIds);
        workbenchPage.translateJobMultiple(translatedItem, excerpts, excerpts.size()-2, true);

        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void addCommentAsCustomer(String user) {
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

        global.goToOrdersPage();

        // Goes to pending jobs and add a comment once checked that the comment from the translator is visible.
        global.goToPendingJob(excerpt, jobIds);

        customerOrderDetailsPage.checkCommentVisible(var.getTranslatorComment());

        customerOrderDetailsPage.addComment(var.getCustomerComment() + " [2]");

        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void checkCommentOfCustomerByTranslatorClicksTheFlagIssue(List<String> jobIds) throws IOException {
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

        // Navigate to the Jobs Page and look for the recently created job
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt, jobIds);

        // Checks the comment if visible.
        workbenchPage.checkCommentVisible(var.getCustomerComment() + " [2]");

        // Flag the job.
        workbenchPage.flagJob("notEnglish", var.getTranslatorFlagComment());

        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void customerChecksTheFlagIssueAndResolveIt(String user) {
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

        global.goToOrdersPage();

        // Goes to pending jobs and checks the flagged issue.
        global.goToPendingJob(excerpt, jobIds);

        customerOrderDetailsPage.clickAddReadComments();

        assertTrue(elementState.isElementPresentInSource("flagged"), var.getElementIsNotDisplayedErrMsg());

        customerOrderDetailsPage.resolveFlag();

        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void translatorChecksTheResolvedFlagAndDeclineTheJob(List<String> jobIds) throws IOException {
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

        // Navigate to the Jobs Page and look for the recently created job
        translatorDashboardPage.clickJobsTab();

        translatorJobsPage.findJob(excerpt, jobIds);

        workbenchPage.closeWorkbenchModal();

        workbenchPage.addTranslatorsComment(var.getTranslatorComment(), true, true);

        // Decline job by Translator
        workbenchPage.declineJob();

        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void addCommentToTheJobAsCustomer(List<String> jobIds, int startIndex, int amountOfJobsToComment) {
        for (int i = startIndex; i < jobIds.size() - amountOfJobsToComment; i++) {
            customerOrdersPage.openJob(jobIds.get(i));
            customerOrderDetailsPage.addComment(var.getCustomerComment());
            page.goBack();
        }
    }
}
