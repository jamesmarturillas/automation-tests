package com.gengo.automation.tests.Workbench;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * @case Trace all the information of a certain collection.
 * @reference https://gengo.testrail.com/index.php?/cases/view/18314
 */
public class C18314_CollectionInformation extends AutomationBase {

    private String[] itemToTranslates;
    private String[] unitCounts;
    private String excerpt;

    public C18314_CollectionInformation() throws IOException {}

    @BeforeClass
    public void initFields() throws IOException{
        excerpt = var.getExcerptEnglish(13);

        itemToTranslates  = new String[] {
                var.getItemToTranslate(13),
                var.getItemToTranslate(14),
                var.getItemToTranslate(15),
                var.getItemToTranslate(16),
        };

        unitCounts = new String[] {
                var.getUnitCountSource(13),
                var.getUnitCountSource(14),
                var.getUnitCountSource(15),
                var.getUnitCountSource(16),
        };
    }

    @AfterClass
    public void afterRun() {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test
    public void traceCollectionInformation() throws IOException {
        this.newUser = csv.generateNewUser(var.SHEETNAME_CUSTOMERS, 0);
        globalMethods.createNewCustomer(this.newUser);
        this.placeAnOrder(this.newUser);
        csv.writeValue(this.newUser, var.SHEETNAME_CUSTOMERS, 0);
        this.pickUpByTranslator();
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

        customerCheckoutPage.choosePurpose(customerCheckoutPage.getPurposeOnlineContent());

        customerCheckoutPage.chooseTone(customerCheckoutPage.getToneFriendly());

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

        // Sign out
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void pickUpByTranslator() {
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

        // Check if the page redirected to contains the elements that should be seen in a Translator Dashboard
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        // Navigate to the Jobs Page and look for the recently created job
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt);

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

        // Sign out.
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }
}
