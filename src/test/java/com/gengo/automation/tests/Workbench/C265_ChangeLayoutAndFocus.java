package com.gengo.automation.tests.Workbench;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @case Monitor the display of 'Saving...' and 'Saved' beside the workbench submit button.
 * @reference https://gengo.testrail.com/index.php?/cases/view/265
 */
public class C265_ChangeLayoutAndFocus extends AutomationBase {

    private String[] itemToTranslates;
    private String[] unitCounts;
    private String excerpt;

    public C265_ChangeLayoutAndFocus() throws IOException {}

    @BeforeClass
    public void initFields() throws IOException{
        excerpt = var.getExcerptEnglish(8);

        itemToTranslates  = new String[] {
                var.getItemToTranslate(8),
                var.getItemToTranslate(9),
                var.getItemToTranslate(10),
        };

        unitCounts = new String[] {
                var.getUnitCountSource(8),
                var.getUnitCountSource(9),
                var.getUnitCountSource(10),
        };
    }

    @AfterClass
    public void afterRun() {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test
    public void validateBehaviourWhenChangingLayoutAndFocus() throws IOException {
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

        loginPage.loginAccount(user, var.getDefaultPassword(), false);

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

        // Navigate to the Jobs page and look for the recently created job
        global.clickJobsTab();

        translatorJobsPage.findJob(excerpt);

        workbenchPage.closeWorkbenchModal();

        // Click the Start Translate button
        workbenchPage.startTranslate();

        // Play around 2 orientations of workbench.

        // Horizontal.
        workbenchPage.setToHorizontalOrientation();
        // Assert that the workbench is at vertical orientation.
        assertTrue(workbenchPage.getHorizontalSelected().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Try something, verify that nothing is strange.
        workbenchPage.clickJobCommentButton(true);

        assertTrue(workbenchPage.getWorkbenchCommentsTextArea().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        workbenchPage.clickValidationBtn(true);

        assertTrue(workbenchPage.getValidationText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        workbenchPage.clickJobCommentButton(false);
        workbenchPage.clickValidationBtn(false);

        // Vertical.
        workbenchPage.setToVerticalOrientation();
        // Assert that the workbench is at vertical orientation.
        assertTrue(workbenchPage.getVerticalSelected().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Try something, verify that nothing is strange.
        workbenchPage.clickJobCommentButton(true);

        assertTrue(workbenchPage.getWorkbenchCommentsTextArea().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        workbenchPage.clickValidationBtn(true);

        assertTrue(workbenchPage.getValidationText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        workbenchPage.clickJobCommentButton(false);
        workbenchPage.clickValidationBtn(false);

        assertFalse(workbenchPage.getValidationText().isDisplayed(),
                var.getElementIsDisplayedErrMsg());

        // Additional functionality :
        // Declining the job to prevent 'Incomplete Job' existence from other test cases.
        workbenchPage.declineJob();

        // Sign out.
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }
}
