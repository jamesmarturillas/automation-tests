package com.gengo.automation.tests.Workbench;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @case Verify that the translator cannot submit an empty translation.
 * @reference https://gengo.testrail.com/index.php?/cases/view/796
 */
public class C796_TranslatorCantSubmitEmptyTranslation extends AutomationBase {

    private String[] itemToTranslates;
    private String[] unitCounts;
    private String excerpt;

    public C796_TranslatorCantSubmitEmptyTranslation() throws IOException {}

    @BeforeClass
    public void initFields() throws IOException{
        excerpt = var.getExcerptEnglish(17);

        itemToTranslates  = new String[] {
                var.getItemToTranslate(17),
                var.getItemToTranslate(18),
        };

        unitCounts = new String[] {
                var.getUnitCountSource(17),
                var.getUnitCountSource(18),
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

    private void pickUpByTranslator() throws IOException {
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

        workbenchPage.startTranslate();

        // Submit job without any translations inputted.
        workbenchPage.submitJobWithoutWait();

        assertTrue(workbenchPage.getSubmitButtonParent().getText().contains("No jobs to submit"),
                var.getTextNotEqualErrMsg());

        // Input proper translation.
        workbenchPage.translateTextArea(var.getTranslatedItem(17));

        // Verify that the submit button is now clickable.
        assertTrue(workbenchPage.getSubmitButton().isEnabled(),
                var.getElementIsNotEnabledErrMsg());

        workbenchPage.clearTranslateTextArea();

        wait.untilElementVisible(workbenchPage.getSubmitButtonParent());

        // Submit job without any translations inputted.
        workbenchPage.submitJobWithoutWait();

        assertTrue(workbenchPage.getSubmitButtonParent().getText().contains("No jobs to submit"),
                var.getTextNotEqualErrMsg());

        // Input proper translation.
        workbenchPage.translateTextArea(var.getTranslatedItem(17));


        // Verify that the submit button is now clickable.
        assertTrue(workbenchPage.getSubmitButton().isEnabled(),
                var.getElementIsNotEnabledErrMsg());

        workbenchPage.submitJob();
        workbenchPage.submitModalOk();

        // Assert that after submission, it redirects to the correct page.
        assertTrue(page.getCurrentUrl().equals(translatorDashboardPage.TRANSLATOR_DASHBOARD_URL),
                var.getWrongUrlErrMsg());

        // Sign out.
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }
}
