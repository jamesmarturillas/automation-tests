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
 * @case Check if the comment in both customer and translator are visible and able to interact with them.
 * @reference https://gengo.testrail.com/index.php?/cases/view/40343
 */
public class C40343_CommentRWAndFlag extends AutomationBase {

    public C40343_CommentRWAndFlag() throws IOException {}

    private String[] itemToTranslates;
    private String[] translatedItems;
    private String[] unitCounts;
    private String excerpt;
    private List<String> excerpts = new ArrayList<>();
    private List<String> jobId;

    @BeforeClass
    public void initFields() throws IOException{
        this.newUser = csv.generateNewUser(var.SHEETNAME_CUSTOMERS, 0);
        excerpt = var.getExcerptEnglish(4);
        excerpts.add(excerpt);

        itemToTranslates  = new String[] {
                var.getItemToTranslate(4),
        };
        unitCounts = new String[] {
                var.getUnitCountSource(4),
        };
        translatedItems = new String[] {
                var.getTranslatedItem(4),
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

    @Test(dependsOnMethods = "createCustomerAccount")
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

    @Test(description = "Comment with HTML tags and brackets", dependsOnMethods = "placeAnOrder")
    public void addCommentAsTranslator() throws IOException {
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

        // Add comment and perform checking of the comment if it has "[[[", "]]]", "<", ">"
        workbenchPage.closeWorkbenchModal();
        workbenchPage.addTranslatorsComment(var.getCommentWithHtmlAndBracket(true), true, false);
        assertTrue(workbenchPage.commentHasBracketAndHtmlTags(true), var.getTextNotEqualErrMsg());

        // Sign out.
        global.nonAdminSignOut();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    @Test(description = "Comment with HTML tags and brackets", dependsOnMethods = "addCommentAsTranslator")
    public void customerChecksAndAddComment() {
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

        global.selectCustomer();

        // Navigate through the orders list and find the job; Assert that the found job is correct.
        global.clickOrdersTab();
        customerOrdersPage.findOrder(excerpt, jobId);
        assertTrue(customerOrderDetailsPage.getJobNumberAvailableJob()
                .contains(jobId.get(0)), var.getTextNotEqualErrMsg());

        // Verify that the comment from the translator is visible.
        assertTrue(customerOrderDetailsPage.checkCommentVisible
                (var.getCommentWithHtmlAndBracket(true)), var.getElementIsNotDisplayedErrMsg());

        // Add a comment as customer and assert if the added comment has bracket and html.
        customerOrderDetailsPage.addComment(var.getCommentWithHtmlAndBracket(false));
        assertTrue(customerOrderDetailsPage.lastCommentHasBracketAndHtml(), var.getTextNotEqualErrMsg());

        // Sign out.
        global.nonAdminSignOut();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    @Test(description = "Comment with plain texts.", dependsOnMethods = "customerChecksAndAddComment")
    public void addCommentAsTranslatorUsingPlainText() throws IOException {
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

        // Assert that the html and bracketed comment of the customer is not deflated.
        assertTrue(workbenchPage.commentHasBracketAndHtmlTags(false), var.getTextNotEqualErrMsg());

        // Add a plain text comment and assert if the comment added is plain and the same as inputted.
        workbenchPage.addTranslatorsComment(var.getTranslatorComment(), false, false);
        assertTrue(workbenchPage.isCommentPlain(var.getTranslatorComment(), true), var.getTextNotEqualErrMsg());

        // Sign out.
        global.nonAdminSignOut();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    @Test(description = "Check comment from translator and add comment via customer.", dependsOnMethods = "addCommentAsTranslatorUsingPlainText")
    public void checkTranslatorsCommentViaCustomer() {
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

        global.selectCustomer();

        // Navigate through the orders list and find the job; Assert that the found job is correct.
        global.clickOrdersTab();
        customerOrdersPage.findOrder(excerpt, jobId);
        assertTrue(customerOrderDetailsPage.getJobNumberAvailableJob()
                .contains(jobId.get(0)), var.getTextNotEqualErrMsg());

        // Verify that the comment from the translator is visible.
        assertTrue(customerOrderDetailsPage.checkCommentVisible
                (var.getTranslatorComment()), var.getTextNotEqualErrMsg());

        // Verify that the comment from the translator is plain.
        assertTrue(customerOrderDetailsPage.isCommentPlain
                (var.getTranslatorComment(), true), var.getTextNotEqualErrMsg());

        // Add a comment as customer and assert if the added comment is character.
        customerOrderDetailsPage.addComment(var.getCharacterComment());
//        assertTrue(customerOrderDetailsPage.lastCommentHasBracketAndHtml(), var.getTextNotEqualErrMsg());
//
//        // Sign out.
//        global.nonAdminSignOut();
//        assertTrue(homePage.getSignIn().isDisplayed(),
//                var.getElementIsNotDisplayedErrMsg());
    }
}
