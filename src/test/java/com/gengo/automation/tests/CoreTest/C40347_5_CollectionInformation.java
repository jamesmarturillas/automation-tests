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
 * @case Trace all the information of a certain collection.
 * @reference https://gengo.testrail.com/index.php?/cases/view/40347
 */
public class C40347_5_CollectionInformation extends AutomationBase {

    private String[] itemToTranslates;
    private String[] translatedItems;
    private String[] unitCounts;
    private String excerpt;
    private List<String> excerpts = new ArrayList<>();
    private List<String> jobIds = new ArrayList<>();

    public C40347_5_CollectionInformation() throws IOException {}

    @BeforeClass
    public void initFields() throws IOException{
        this.newUser = csv.generateNewUser(var.SHEETNAME_CUSTOMERS, 0);
        excerpt = var.getExcerptEnglish(10);
        excerpts.add(excerpt);

        itemToTranslates  = new String[] {
                var.getItemToTranslate(10),
        };
        unitCounts = new String[] {
                var.getUnitCountSource(10),
        };
        translatedItems = new String[] {
                var.getTranslatedItem(10),
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

    @Test(dependsOnMethods = "createCustomerAccount", description = "SINGLE [With Tone/Purpose - Pro]")
    public void placeAnOrder() {
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

        // Set the tone and purpose.
        customerCheckoutPage.choosePurpose(customerCheckoutPage.getPurposeOnlineContent());
        customerCheckoutPage.chooseTone(customerCheckoutPage.getToneFriendly());

        // Order as Pro
        customerCheckoutPage.businessTier(true);

        customerCheckoutPage.clickPayNowAndConfirm(false, false, false, false, false);

        // Assert that order is complete by checking the return URL.
        assertTrue(page.getCurrentUrl().contains(customerOrderCompletePage.ORDERCOMPLETE_URL),
                var.getWrongUrlErrMsg());

        // Go back to the customer dashboard.
        customerOrderCompletePage.clickGoToDashboard();
        assertTrue(customerDashboardPage.getCustomerDashboardText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Navigate to 'Orders' page and find the Job ID.
        global.goToOrdersPage();
        customerOrdersPage.clickPendingOption();
        assertTrue(customerOrdersPage.getPendingHeader().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Extract the job id from pending jobs.
        jobIds = customerOrdersPage.extractJobIdFromPendingJobs(excerpts);

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

        loginPage.loginAccount(var.getTranslatorPro(5), var.getDefaultPassword());

        // Check if the page redirected to contains the elements that should be seen in a Translator Dashboard
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        // Navigate to the Jobs Page and look for the recently created job
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt, jobIds);

        workbenchPage.closeWorkbenchModal();

        // Assert all information are present in the workbench.
        assertTrue(workbenchPage.getRewardTxt().isDisplayed(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.getAllottedTimeTxt().isDisplayed(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.getApprovalTimeTxt().isDisplayed(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.getTotalUnitsTxt().isDisplayed(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.getRewardPerUnitTxt().isDisplayed(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.getJobCountTxt().isDisplayed(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.getCollectionIdTxt().isDisplayed(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.getTierTxt().isDisplayed(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.getPurposeTxt().isDisplayed(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.getToneTxt().isDisplayed(), var.getElementIsNotDisplayedErrMsg());
        assertTrue(workbenchPage.getLanguagePairTxt().isDisplayed(), var.getElementIsNotDisplayedErrMsg());

        // Translate the job and assert that after submission, it redirects to the correct page.
        workbenchPage.translateJob(translatedItems[translatedItems.length - 1], false);
        assertTrue(page.getCurrentUrl().equals(translatorDashboardPage.TRANSLATOR_DASHBOARD_URL),
                var.getWrongUrlErrMsg());

        // Sign out.
        global.nonAdminSignOut();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }
}
