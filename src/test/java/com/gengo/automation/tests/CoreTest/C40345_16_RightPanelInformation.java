package com.gengo.automation.tests.CoreTest;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * @case Right Panel Information - (Word to Character, Credits Payment, Business Tier, With Glossary, With Validation, Ungrouped)
 * @reference https://gengo.testrail.com/index.php?/cases/view/40345
 */
public class C40345_16_RightPanelInformation extends AutomationBase {

    private String parentWindow, orderNo;
    private String[] unitCount,itemToTranslate, excerpts, translatedItem, jobNo = new String[3],
            glossarySourceText, glossaryTargetText, highlightGlossaryText, highlightTagText, highlightCommentText,
            glossaryTexts = {"test","record","Gengo"}, // contains the glossary source texts
            glossaryTexts2 = {"test","Gengo","record"}, // contains the glossary source texts
            glossaryTexts3 = {"TEST","record","Gengo"}, // contains the glossary source texts
            glossaryMatches = {"テスト","記録","言語"}, // contains the glossary target texts
            glossaryMatches2 = {"テスト","言語","記録"}, // contains the glossary target texts
            glossaryMatches3 = {"テスト","記録","言語"}, // contains the glossary target texts
            highlightGlossary = {"test","record","Gengo","TEST"}, // contains the words in the source text that has glossary matches
            highlightGlossary2 = {"test","Gengo","record","Test"}, // contains the words in the source text that has glossary matches
            highlightGlossary3 = {"TEST","record","Gengo"}, // contains the words in the source text that has glossary matches
            highlightWarning = {"1", "two"}, // texts that trigger warning validation
            highlightTag = {"{1}", "{/1}"}, // tags that are on the source text
            highlightTag2 = {"{1}", "{/1}"}, // tags that are on the source text
            highlightComment = {"[[[ comment here ]]]"}, // comments on the source text
            highlightComment2 = {"[[[ like this one test record inside ]]]"}, // comments on the source text
            resolveCaution = {"2"}; // the remaining number validation warning not resolved

    @BeforeClass
    public void initFields() throws IOException{
        excerpts = new String[] {
                var.getExcerptEnglish(18),
                var.getExcerptEnglish(26),
                var.getExcerptEnglish(27)
        };
        itemToTranslate = new String[] {
                var.getItemToTranslate(21),
                var.getItemToTranslate(42),
                var.getItemToTranslate(43)
        };
        unitCount = new String[] {
                var.getUnitCountSource(21),
                var.getUnitCountSource(42),
                var.getUnitCountSource(43)
        };
        translatedItem = new String[] {
                var.getTranslatedItem(21),
                var.getTranslatedItem(42),
                var.getTranslatedItem(43)
        };
    }

    public C40345_16_RightPanelInformation() throws IOException {}

    @AfterClass
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
        loginPage.loginAccount(var.getCustomer(27), var.getDefaultPassword());

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

        // Click the Business Tier Radio button
        customerCheckoutPage.businessTier(true);

        // Choose glossary file to be used
        customerCheckoutPage.addGlossary("en_to_ja");

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

        // Place payment with Credits
        customerCheckoutPage.payWithCredits();

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

    @Test(dependsOnMethods = "placeAnOrder")
    public void translatorPickUpJobs() throws AWTException {
        // Navigate to the Jobs Page and look for the recently created job
        homePage.clickSignInButton();

        // Check that after clicking the 'SIGN IN' link, the URL is right and 'SIGN UP' button is displayed as well.
        assertTrue(page.getCurrentUrl().equals(loginPage.LOGIN_PAGE_URL),
                var.getWrongUrlErrMsg());
        assertTrue(loginPage.getSignUpBtn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        loginPage.loginAccount(var.getTranslatorPro(4), var.getDefaultPassword());

        int ctr = 0;
        for(String excerpt : excerpts){
            if(ctr == 0) {
                glossarySourceText = glossaryTexts;
                glossaryTargetText = glossaryMatches;
                highlightGlossaryText = highlightGlossary;
                highlightTagText = highlightTag;
                highlightCommentText = highlightComment;
            }
            else if (ctr == 1){
                glossarySourceText = glossaryTexts2;
                glossaryTargetText = glossaryMatches2;
                highlightGlossaryText = highlightGlossary2;
                highlightTagText = highlightTag2;
                highlightCommentText = highlightComment2;
            }
            else if (ctr == 2){
                glossarySourceText = glossaryTexts3;
                glossaryTargetText = glossaryMatches3;
                highlightGlossaryText = highlightGlossary3;
            }

            // Look for the job
            translatorDashboardPage.clickJobsTab();
            translatorJobsPage.findJob(excerpt);

            // Close the workbench modal
            workbenchPage.closeWorkbenchModal();

            // Check if the glossary section is visible by default
            assertTrue(workbenchPage.isGlossaryVisible());

            // Check if the Glossary section contains all the source and target matches
            assertTrue(workbenchPage.checkGlossaryMatches(glossarySourceText, glossaryTargetText));

            // Check if the correct texts are highlighted
            assertTrue(workbenchPage.checkHighlightGlossary(highlightGlossaryText));

            if(ctr != 2) {
                // Check if the tags are highlighted on the source text
                assertTrue(workbenchPage.checkHighlightTag(highlightTagText));

                // Check if the triple bracket comments are highlighted
                assertTrue(workbenchPage.checkHighlightComment(highlightCommentText));
            }

            if(ctr == 0)
                // Check if the Numbers are highlighted in the source text
                assertTrue(workbenchPage.checkHighlightWarning(highlightWarning));

            // Translator picks up the job
            workbenchPage.startTranslateJob();

            wait.impWait(10);
            // Check if the glossary section is visible by default
            assertTrue(workbenchPage.isGlossaryVisible());

            // Check if the Glossary section contains all the source and target matches
            assertTrue(workbenchPage.checkGlossaryMatches(glossarySourceText, glossaryTargetText));

            // Add content to translation area to toggle highlighting of errors/matches
            workbenchPage.addTextSpecificArea(" ", excerpt);

            if(ctr == 0)
                // Toggle the Issues Section
                workbenchPage.openErrorIssuesSection();

            if(ctr == 1)
                workbenchPage.openWarningErrorIssuesSection();

            if(ctr != 2) {
                // Check if the corresponding texts are highlighted
                assertTrue(workbenchPage.checkHighlightGlossaryWarning(highlightGlossaryText));
                assertTrue(workbenchPage.checkHighlightTagError(highlightTagText));
                assertTrue(workbenchPage.checkHighlightCommentError(highlightCommentText));
            }
            if(ctr == 0)
                assertTrue(workbenchPage.checkHighlightWarning(highlightWarning));

            // Click the texts with glossary matches on the source text to append them on the translation area
            workbenchPage.addGlossaryToText(highlightGlossaryText, glossarySourceText, excerpt);

            if(ctr != 2) {
                // Check if the issues section shows the validation errors
                assertTrue(workbenchPage.checkTagsIssue(highlightTagText));
                assertTrue(workbenchPage.checkCommentIssue(highlightCommentText));
            }

            if(ctr == 0)
                assertTrue(workbenchPage.checkCautionIssue(highlightWarning));

            if(ctr != 2) {
                // Click the triple bracket texts to add them to the translation area
                workbenchPage.addCommentsToText(highlightComment);

                // Check if the issues section shows the validation errors
                workbenchPage.checkTagsIssue(highlightTag);
            }

            if (ctr == 0)
                workbenchPage.checkCautionIssue(highlightWarning);

            if(ctr != 2)
                // Click the tags to add them to the translation text area
                workbenchPage.addTagsToText(highlightTag);

            if (ctr == 0)
                // Add the remaining numbers to the translation area
                workbenchPage.addRemainingCautionIssues(resolveCaution);

            // Add translated text and submit
            workbenchPage.translateTextArea(translatedItem[ctr]);
            page.refresh();
            workbenchPage.submitJob();
            workbenchPage.submitModalOk();

            // Check if the Glossary section contains all the source and target matches
            ctr++;
        }
        // Translator account is logged out
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * customerApprove --- this method has the customer approve the job
     * */
    @Test(dependsOnMethods = "translatorPickUpJobs")
    public void customerApprove() {
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(27), var.getDefaultPassword());
        //global.selectCustomer();
        assertTrue(customerDashboardPage.checkCustomerDashboard());
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());
        // Loops through the jobs and approves them all
        for(int ctr = 0; ctr < excerpts.length; ctr++) {
            globalPage.goToOrdersPage();
            customerOrdersPage.clickReviewableOption();
            customerOrdersPage.findOrder(excerpts[ctr]);

            // Retrieve the job Number of the order
            jobNo[ctr] =customerOrderDetailsPage.getJobNumberReviewableJob();

            // Job is approved
            customerOrderDetailsPage.approveJob();
        }
        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * checkEmail --- this method logs in the on the email of the customer
     * and checks if the emails for order received, job review, flag, and
     * approval are visible
     * */
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
        for(String jobNumber : jobNo) {
            assertTrue(gmailInboxPage.checkJobForReview(jobNumber));
            assertTrue(gmailInboxPage.checkJobApproved(jobNumber));
        }
    }
}
