package com.gengo.automation.tests.Admin;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * @case Comment
 * Word to Character, Credits Payment, Standard Tier, Single Job
 * @reference https://gengo.testrail.com/index.php?/cases/view/8724
 */
public class C8724_1_Comment extends AutomationBase {

    private String parentWindow, jobNo, excerpt, jobURL, translatedItem;
    private String[] itemToTranslate, unitCount ;

    @BeforeMethod
    public void initFields() throws IOException {
        excerpt = var.getExcerptEnglish(17);
        itemToTranslate = new String[] {
                var.getItemToTranslate(17)
         };
        unitCount = new String[] {
                var.getUnitCountSource(17)
        };
        translatedItem = var.getTranslatedItem(17);
    }

    public C8724_1_Comment() throws IOException {}

    @AfterClass
    public void afterRun() {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    /**
     * placeAnOrder --- a method for checking the logging in of a customer account,
     * placing an order, and payment process
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
        customerCheckoutPage
                .payWithCredits();

        // Return to dashboard page
        customerOrderCompletePage.clickGoToDashboard();

        // Retrieve job number
        global.goToOrdersPage();
        customerOrdersPage.clickPendingOption();
        customerOrdersPage.findOrder(excerpt);
        jobNo = customerOrderDetailsPage.getJobNumberAvailableJob();

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
    public void pickUpByTranslator() throws IOException {
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Log in a translator account
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslator(29), var.getDefaultPassword());

        // Check if the page redirected to contains the elements that should be seen in a Translator Dashboard
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        // Navigate to the Jobs Page and look for the recently created job
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt);

        // Retrieve page URL for checking by the translator later
        jobURL = page.getCurrentUrl();

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * adminAddComment --- this method logs in an Admin account,
     * looks for the jobs and adds a comment to it
     * */
    @Test(dependsOnMethods = "pickUpByTranslator")
    public void adminAddComment(){
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

        // Go to the Job Details Page
        adminJobsPage.clickJob(jobNo);

        // Add Comment
        adminJobDetailsPage.chooseActions("comment");
        adminJobCommentPage.addComment(var.getCustomerComment());
        adminJobCommentPage.getCreateBtn().click();

        // Check if comment adding is successful
        assertTrue(adminJobCommentPage.getAddedCommentAlert().isDisplayed());

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * pickUpByTranslatorAgain --- translator goes to the job page and checks for the added comment,
     * translates the jobs and submits translations
     * */
    @Test(dependsOnMethods = "adminAddComment")
    public void pickUpByTranslatorAgain() throws IOException {
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Log in a translator account
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslator(29), var.getDefaultPassword());

        // Check if the page redirected to contains the elements that should be seen in a Translator Dashboard
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        // Go to the Job Page
        page.launchUrl(jobURL);

        // Check if comments section is visible
        wait.untilElementVisible(workbenchPage.getStartTranslateParentButton());
        assertTrue(workbenchPage.isCommentsSectionVisible());
        assertTrue(workbenchPage.checkCommentVisible(var.getCustomerComment()));

        // Translate item and submit
        workbenchPage.translateJob(translatedItem);

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * approveByCustomer -- approve jobs submitted by the translator
     * */
    @Test(dependsOnMethods = "pickUpByTranslatorAgain")
    public void approveByCustomer() {
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

        // Approve job
        global.goToOrdersPage();
        customerOrdersPage.clickReviewableOption();
        customerOrdersPage.findOrder(excerpt);
        customerOrderDetailsPage.approveJob();

        // Customer sign out
        global.nonAdminSignOut();

        // Check if the redirected page contains the prominent Home Page elements
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(homePage.checkHomePage());
    }

    /**
     * checkEmail --- this method logs in the on the email of the customer
     * and checks if the emails for job review, approved, and new comments
     * */
    @Test(dependsOnMethods = "approveByCustomer")
    public void checkEmail() {
        page.launchUrl(var.getGmailUrl());
        assertTrue(gmailSignInEmailPage.getTxtBoxEmail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Log in on Gmail account
        gmailSignInEmailPage.inputEmail(var.getGmailEmail());
        gmailSignInPasswordPage.inputPasswordAndSubmit(var.getGmailPassword());

        // Check if the emails for Job Review, and Approval are received
        assertTrue(gmailInboxPage.checkJobForReview(jobNo));
        assertTrue(gmailInboxPage.checkJobApproved(jobNo));
        assertTrue(gmailInboxPage.checkNewComment(jobNo));
        gmailInboxPage.clickNewComment(jobNo);
        assertTrue(gmailInboxPage.checkNewCommentVisible(var.getCustomerComment()));
    }
}
