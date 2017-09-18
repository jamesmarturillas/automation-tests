package com.gengo.automation.tests.Workbench;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @case Change Layout - (Character to Word, Paypal Payment, Business Tier)
 * @reference https://gengo.testrail.com/index.php?/cases/view/235
 */
public class C235_4_ChangeLayout extends AutomationBase{

    private String parentWindow, orderNo, jobNo;
    private String[] itemToTranslate;
    private String translatedText1, excerpt;
    private String[] unitCount;

    /**
     * pickUpCases --- a Object Data Provider that returns three values to represent the 3 stages of checking
     * of layout on the workbench:
     * > Before Picking Up Job
     * > After Picking Up Job
     * > After Placing a Translated Text
     * */
    @DataProvider(name = "pickUpCases")
    public Object[][] pickUpCases() {
        return new Object[][] {
                { "notPickedUp" },
                { "pickUpOnly" },
                { "pickUpAndAddText" }
        };
    }

    @BeforeMethod
    public void initFields() throws IOException{
        excerpt = var.getExcerptNonEnglish(18);
        itemToTranslate = new String[] {
                var.getItemToTranslate(22)
        };
        translatedText1 = var.getTranslatedItem(23);
        unitCount = new String[] { var.getUnitCountSource(22) };
    }

    public C235_4_ChangeLayout() throws IOException {}

    @AfterMethod
    public void afterRun() {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    /**
     * placeAnOrder --- a method for checking the logging in of a customer account,
     * and placing an order with glossary chosen
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
        loginPage.loginAccount(var.getCustomer(25), var.getDefaultPassword());

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
        assertTrue(customerOrderLanguagesPage.isSourceAutoDetected(var.getJapaneseFrom()),
                var.getTextNotEqualErrMsg());

        // Set the target language to English
        customerOrderLanguagesPage.choooseLanguage(var.getEnglishTo());
        customerOrderLanguagesPage.clickNextOptions();

        // Click the Business Tier Radio button
        customerCheckoutPage.businessTier(true);

        // Open the advanced options
        //customerCheckoutPage.clickAdvanceOptions();

        // Choose glossary file to be used
        customerCheckoutPage.addGlossary("ja_to_en");

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

        // Place payment via Paypal
        customerCheckoutPage
                .clickPayNowAndConfirm(true, false, false, false, false);

        // Retrieve the order number
        orderNo = customerOrderCompletePage.orderNumber();

        // Return to dashboard page
        customerOrderCompletePage.clickGoToDashboard();

        // Customer sign out
        global.nonAdminSignOut();

        // Check if the redirected page contains the prominent Home Page elements
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(homePage.checkHomePage());
    }

    /**
     * translatorFindJob --- a method wherein a translator signs in and looks for the recently
     * created job and opens the workbench
     * */
    @Test(dependsOnMethods = "placeAnOrder")
    public void translatorFindJob() {
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Log in a translator account
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslator(26), var.getDefaultPassword());

        // Check if the page redirected to contains the elements that should be seen in a Translator Dashboard
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        // Navigate to the Jobs Page and look for the recently created job
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt);
    }

    /**
     * translatorCheckLayout --- a method which checks the layout of the workbench for each of the 3 stages
     * in picking up and submitting a job; this method uses the Data Provider pickUpCases for the 3 cases
     * */
    @Test(dependsOnMethods = "translatorFindJob", dataProvider = "pickUpCases")
    public void translatorCheckLayout(String pickUpCase) {
        boolean isPickedUp = true, hasIssue = true;
        switch(pickUpCase) {
            case "notPickedUp":
                isPickedUp = false;
                break;
            case "pickUpOnly":
                // Translator picks up the job
                workbenchPage.startTranslateJob();
                break;
            case "pickUpAndAddText":
                hasIssue = false;
                // Text to be translated is added
                workbenchPage.translateTextArea(translatedText1);
                break;
        }

         /* Check Right Panel */

        // Close workbench modal if it appears
        workbenchPage.closeWorkbenchModal();

        // Check if glossary section is visible by default
        assertTrue(workbenchPage.isGlossaryVisible(), var.getElementNotFoundErrMsg());
        assertTrue(workbenchPage.checkGlossarySection(), var.getElementNotFoundErrMsg());

        // Check if Layout is Horizontal by default Text Area is visible
        assertTrue(workbenchPage.isHorizontalSelected(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.isTextAreaVisible(isPickedUp), var.getElementIsNotDisplayedErrMsg());

        // Toggle Issues panel and check for the prominent elements that should be visible
        workbenchPage.openIssuesSection();
        assertFalse(workbenchPage.isGlossaryVisible(), var.getElementIsDisplayedErrMsg());
        assertTrue(workbenchPage.checkIssueSection(hasIssue), var.getElementNotFoundErrMsg());

        // Change layout to vertical and check if text area is visible
        workbenchPage.changeLayoutToVertical();
        if (!isPickedUp)
            assertFalse(workbenchPage.isTextAreaVisible(isPickedUp), var.getElementIsDisplayedErrMsg());
        else
            assertTrue(workbenchPage.isTextAreaVisible(isPickedUp), var.getElementIsNotDisplayedErrMsg());

        //Change back to horizontal and check text area
        workbenchPage.changeLayoutToHorizontal();
        assertTrue(workbenchPage.isTextAreaVisible(isPickedUp), var.getElementIsNotDisplayedErrMsg());

        // Close right panel and check if right panel is visible
        workbenchPage.closeIssuesSection();
        assertFalse(workbenchPage.isGlossaryVisible(), var.getElementIsDisplayedErrMsg());
        assertFalse(workbenchPage.isIssuesVisible(), var.getElementIsDisplayedErrMsg());

        /* Check Left Panel */

        // Open the comments section and check if visible
        workbenchPage.openCommentSection();
        assertTrue(workbenchPage.isCommentsSectionVisible(), var.getElementIsNotDisplayedErrMsg());

        // Check if text area is visible by default
        assertTrue(workbenchPage.isTextAreaVisible(isPickedUp), var.getElementIsNotDisplayedErrMsg());

        // Change layout to vertical and check if text area is visible
        workbenchPage.changeLayoutToVertical();

        if(!isPickedUp)
            assertFalse(workbenchPage.isTextAreaVisible(isPickedUp), var.getElementIsDisplayedErrMsg());
        else
            assertTrue(workbenchPage.isTextAreaVisible(isPickedUp), var.getElementIsNotDisplayedErrMsg());

        //Change back to horizontal and check text area
        workbenchPage.changeLayoutToHorizontal();
        assertTrue(workbenchPage.isTextAreaVisible(isPickedUp), var.getElementIsNotDisplayedErrMsg());

        /* Check Right and Left Panel */

        // Open and Check Issues Panel
        workbenchPage.openIssuesSection();
        assertFalse(workbenchPage.isGlossaryVisible(), var.getElementIsDisplayedErrMsg());
        assertTrue(workbenchPage.checkIssueSection(hasIssue), var.getElementNotFoundErrMsg());

        // Open and Check Glossary Panel
        workbenchPage.openGlossarySection();
        assertFalse(workbenchPage.isIssuesVisible(), var.getElementIsDisplayedErrMsg());
        assertTrue(workbenchPage.checkGlossarySection(), var.getElementNotFoundErrMsg());

        // Check if text area is visible by default
        assertTrue(workbenchPage.isTextAreaVisible(isPickedUp), var.getElementIsNotDisplayedErrMsg());

        // Change layout to vertical and check if text area is visible
        workbenchPage.changeLayoutToVertical();
        if(!isPickedUp)
            assertFalse(workbenchPage.isTextAreaVisible(isPickedUp), var.getElementIsDisplayedErrMsg());
        else
            assertTrue(workbenchPage.isTextAreaVisible(isPickedUp), var.getElementIsNotDisplayedErrMsg());
        //Change back to horizontal and check text area
        workbenchPage.changeLayoutToHorizontal();
        assertTrue(workbenchPage.isTextAreaVisible(isPickedUp), var.getElementIsNotDisplayedErrMsg());

        /* Check Layout when All Panels are Closed */

        // Close comments section and Glossary Panel
        workbenchPage.closeCommentSection();
        workbenchPage.closeGlossarySection();
        assertFalse(workbenchPage.isCommentsSectionVisible(), var.getElementIsDisplayedErrMsg());
        assertFalse(workbenchPage.isGlossaryVisible(), var.getElementIsDisplayedErrMsg());

        // Check if text area is visible by default
        assertTrue(workbenchPage.isTextAreaVisible(isPickedUp), var.getElementIsNotDisplayedErrMsg());

        // Change layout to vertical and check if text area is visible
        workbenchPage.changeLayoutToVertical();
        if(!isPickedUp)
            assertFalse(workbenchPage.isTextAreaVisible(isPickedUp), var.getElementIsDisplayedErrMsg());
        else
            assertTrue(workbenchPage.isTextAreaVisible(isPickedUp), var.getElementIsNotDisplayedErrMsg());
        //Change back to horizontal and check text area
        workbenchPage.changeLayoutToHorizontal();
        assertTrue(workbenchPage.isTextAreaVisible(isPickedUp), var.getElementIsNotDisplayedErrMsg());

        // Open the issues section
        workbenchPage.openIssuesSection();
        assertTrue(workbenchPage.isIssuesVisible(), var.getElementIsNotDisplayedErrMsg());

        // Open the glossary section
        workbenchPage.openGlossarySection();
        assertTrue(workbenchPage.isGlossaryVisible(), var.getElementIsNotDisplayedErrMsg());
    }

    /**
     * translatorSubmit --- this method has the translator submit the translation and log out afterwards
     * */
    @Test(dependsOnMethods = "translatorCheckLayout")
    public void translatorSubmit() {
        // Translator submits job
        workbenchPage.submitJob();
        workbenchPage.submitModalOk();

        // Translator account is logged out
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * customerApprove --- this method has the customer approve the job
     * */
    @Test(dependsOnMethods = "translatorSubmit")
    public void customerApprove() {
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(25), var.getDefaultPassword());
        global.selectCustomer();
        assertTrue(customerDashboardPage.checkCustomerDashboard());
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());
        globalPage.goToOrdersPage();
        customerOrdersPage.clickReviewableOption();
        customerOrdersPage.findOrder(excerpt);

        // Retrieve the job Number of the order
        jobNo = customerOrderDetailsPage.getJobNumberReviewableJob();

        // Job is approved
        customerOrderDetailsPage.approveJob();
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    @Test(dependsOnMethods = "customerApprove")
    public void checkEmail() {
        page.launchUrl(var.getGmailUrl());
        assertTrue(gmailSignInEmailPage.getTxtBoxEmail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Log in on Gmail account
        gmailSignInEmailPage.inputEmail(var.getGmailEmail());
        gmailSignInPasswordPage.inputPasswordAndSubmit(var.getGmailPassword());

        // Check if the emails for Order Received, Review, and Approval are received
        assertTrue(gmailInboxPage.checkOrderReceived(orderNo));
        assertTrue(gmailInboxPage.checkJobForReview(jobNo));
        assertTrue(gmailInboxPage.checkJobApproved(jobNo));
    }
}
