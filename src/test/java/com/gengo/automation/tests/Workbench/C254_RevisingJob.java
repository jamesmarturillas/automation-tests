package com.gengo.automation.tests.Workbench;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * @case Revise a created job.
 * @reference https://gengo.testrail.com/index.php?/cases/view/254
 */
public class C254_RevisingJob extends AutomationBase {

    private String[] itemToTranslates;
    private String[] unitCounts;
    private String excerpt;

    public C254_RevisingJob() throws IOException {}

    @BeforeClass
    public void initFields() throws IOException{
        excerpt = var.getExcerptEnglish(16);

        itemToTranslates  = new String[] {
                var.getItemToTranslate(16),
                var.getItemToTranslate(17),
        };

        unitCounts = new String[] {
                var.getUnitCountSource(16),
                var.getUnitCountSource(17),
        };
    }

    @AfterClass
    public void afterRun() {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test
    public void revisingJob() throws IOException {
        this.newUser = csv.generateNewUser(var.SHEETNAME_CUSTOMERS, 0);
        globalMethods.createNewCustomer(this.newUser);
        this.placeAnOrder(this.newUser);
        csv.writeValue(this.newUser, var.SHEETNAME_CUSTOMERS, 0);
        this.pickUpByTranslator();
        this.customerSubmitCorrection(this.newUser);
        this.openCorrectionRequestAsTranslator();
        this.customerApproveJob(this.newUser);
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

        // Assert that the logout button is present in the DOM.
        assertTrue(global.getNonAdminSignOutBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Navigate to the Jobs Page and look for the recently created job
        global.clickJobsTab();
        translatorJobsPage.findJob(excerpt);

        workbenchPage.translateJob(var.getTranslatedItem(16), true);

        // Assert that after submission, it redirects to the correct page.
        assertTrue(page.getCurrentUrl().equals(translatorDashboardPage.TRANSLATOR_DASHBOARD_URL),
                var.getWrongUrlErrMsg());

        // Sign out.
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void customerSubmitCorrection(String user) {
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

        global.selectCustomer();
        assertTrue(customerDashboardPage.checkCustomerDashboard());
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());

        globalPage.goToOrdersPage();

        customerOrdersPage.clickReviewableOption();
        customerOrdersPage.findOrder(excerpt);

        // Request correction for job
        customerOrderDetailsPage.requestCorrection(var.getCustomerCorrection(), true, true, true);

        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void openCorrectionRequestAsTranslator() {
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

        assertTrue(global.getTranslatorJobsTab().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Navigate through jobs tab.
        global.clickJobsTab();

        // Click the revising option.
        global.clickTranslatorRevisingBtn();

        // Find the job.
        translatorJobsPage.findJob(excerpt);

        // Assert that comment panel is displayed.
        assertTrue(workbenchPage.getWorkbenchCommentsTextArea().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Add a whitespace to translation to enable submission of job.
        workbenchPage.translateTextAreaAddItem(" ");

        workbenchPage.submitJob();
        workbenchPage.submitModalOk();

        wait.impWait(30, translatorDashboardPage.getWorkDashboardHeader());

        assertTrue(page.getCurrentUrl().contains(translatorDashboardPage.TRANSLATOR_DASHBOARD_URL), var.getWrongUrlErrMsg());

        global.nonAdminSignOut();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void customerApproveJob(String user) {
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

        // Login customer account.
        loginPage.loginAccount(user, var.getDefaultPassword());

        // Assert that page title is present in the DOM.
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Assert that the order icon is present for ordering process.
        assertTrue(global.getOrderTranslationIcon().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        global.selectCustomer();

        // Assert that the redirected page is correct as expected.
        assertTrue(customerDashboardPage.checkCustomerDashboard());
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());

        // Navigate through orders page.
        globalPage.goToOrdersPage();

        customerOrdersPage.clickReviewableOption();

        // Find the order and approve.
        customerOrdersPage.findOrder(excerpt);

        customerOrderDetailsPage.approveJob();

        wait.impWait(30, customerOrderDetailsPage.getInPageAlertApprove());

        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }
}
