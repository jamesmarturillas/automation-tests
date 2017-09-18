package com.gengo.automation.tests.TranslateCore;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * @case Order file job.
 * >> PayPal payment
 * @reference https://gengo.testrail.com/index.php?/cases/view/28
 */

public class C28_ForFileJob extends AutomationBase {

    private String[] itemToTranslates;
    private String[] unitCounts;
    private String parentWindow;

    public C28_ForFileJob() throws IOException {}

    @BeforeClass
    public void initFields() throws IOException{
        this.newUser = csv.generateNewUser(var.SHEETNAME_CUSTOMERS, 0);
        globalMethods.createNewCustomer(this.newUser);

        itemToTranslates  = new String[] {
                var.getFile(72),
                var.getFile(73),
        };

        unitCounts = new String[] {
                var.getUnitCountSource(13),
                var.getUnitCountSource(14),
        };
    }

    @AfterClass
    public void afterRun() throws IOException {
        csv.writeValue(this.newUser, var.SHEETNAME_CUSTOMERS, 0);
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test
    public void placeOrder() {
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
        customerOrderFormPage.inputItemToTranslate(itemToTranslates, unitCounts, itemToTranslates.length, true);

        // Assert that the page url after submitting is correct.
        assertTrue(page.getCurrentUrl().equals(customerOrderLanguagesPage.ORDERLANGUAGES_URL),
                var.getWrongUrlErrMsg());

        // Assert that the source language is auto detected and English.
        assertTrue(customerOrderLanguagesPage.isSourceAutoDetected(),
                var.getTextNotEqualErrMsg());

        // Choose Japanese as target language.
        customerOrderLanguagesPage.choooseLanguage(var.getJapaneseFrom());
        customerOrderLanguagesPage.clickNextOptions();

        // Click 'View Full Quote' button.
        customerCheckoutPage.clickViewFullQuote();

        // Popup will appear; switch to it.
        parentWindow = switcher.getWindowHandle();
        switcher.switchToPopUp();

        // Assert that the expected page URL is correct.
        assertTrue(page.getCurrentUrl().equals(customerOrderQuotePage.CUSTOMERORDERQUOTE_URL),
                var.getWrongUrlErrMsg());

        // Type the address in the address textarea element.
        customerOrderQuotePage.typeAdress();

        // Assert that the typed address is embedded after being submitted.
        assertTrue(customerOrderQuotePage.getAddressEmbedded().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Download the pdf file of quote.
        customerOrderQuotePage.downloadQuote();

        // Switch back to the parent window.
        switcher.switchToParentWindow(parentWindow);

        customerCheckoutPage.clickPayNowAndConfirm(true, false, false, false, false);

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

    @Test(dependsOnMethods = "placeOrder")
    public void pickUpByTranslator() throws IOException {
        String[] translatedItem = {
                var.getFile(72),
                var.getFile(73),
        };

        String[] excerpt = {
                var.getExcerptFile(1),
                var.getExcerptFile(2),
        };

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

        translatorDashboardPage.clickJobsTab();

        // Will look for the 1st index value from the DataSource.xls of sheet 'TranslationExcerpt' FILE column
        // >> "It's a <b> sample </b> {1} {2} {3} {/3} {/2} {/1} here."
        translatorJobsPage.findJob(excerpt[0]);

        // Translate the file job
        workbenchFilePage.translateFileJob(translatedItem[0]);

        assertTrue(workbenchFilePage.getFinishFileJobTxtCriteria().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        translatorDashboardPage.clickJobsTab();

        // Will look for the 2nd index value from the DataSource.xls of sheet 'TranslationExcerpt' FILE column
        // >> "[[[<a href="http://staging.gengo.com" alt="]]]please translate here[[[">]]]"
        translatorJobsPage.findJob(excerpt[1]);

        // Translate the file job
        workbenchFilePage.translateFileJob(translatedItem[1]);

        assertTrue(workbenchFilePage.getFinishFileJobTxtCriteria().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Sign out.
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }
}
