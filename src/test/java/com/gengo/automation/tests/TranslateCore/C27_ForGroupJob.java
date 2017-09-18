package com.gengo.automation.tests.TranslateCore;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * @case Order multiple job and classified as group job.
 * >> PayPal payment
 * @reference https://gengo.testrail.com/index.php?/cases/view/27
 */

public class C27_ForGroupJob extends AutomationBase {

    private String[] itemToTranslates;
    private String[] unitCounts;
    private String parentWindow;

    public C27_ForGroupJob() throws IOException {}

    @BeforeClass
    public void initFields() throws IOException{
        this.newUser = csv.generateNewUser(var.SHEETNAME_CUSTOMERS, 0);
        globalMethods.createNewCustomer(this.newUser);

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

        loginPage.loginAccount(var.getCustomer(27), var.getDefaultPassword());

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

        // Make order as a group job by clicking the checkbox from the 'Advanced Option'.
        customerCheckoutPage.orderAsAGroup();

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
        String convertedString = null;
        String toConvert = var.getTranslatedItem(13).replace("<b>", "{4}");
        toConvert = toConvert.replace("</b>", "{/4}");
        convertedString = toConvert;

        String[] translatedItem = {
                convertedString,
                var.getTranslatedItem(14),
                var.getTranslatedItem(15),
                var.getTranslatedItem(16),
        };

        String convertedExcerpt = null;
        String excerptConverted = var.getExcerptEnglish(13).replace("<b>", "{4}");
        excerptConverted = excerptConverted.replace("</b>", "{/4}");
        convertedExcerpt = excerptConverted;

        String[] excerpt = {
                convertedExcerpt,
                var.getExcerptEnglish(14),
                var.getExcerptEnglish(15),
                var.getExcerptEnglish(16),
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

        // Will look for the 13th index value from the DataSource.xls of sheet 'TranslationExcerpt'
        // >> "It's a <b> sample </b> {1} {2} {3} {/3} {/2} {/1} here."
        translatorJobsPage.findJob(var.getExcerptEnglish(13));

        // Assert that the workbench modal is shown. If so, close it.
        assertTrue(workbenchPage.getCloseWorkbenchModal().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Translate the job.
        workbenchPage.translateJobMultiple(translatedItem, excerpt, translatedItem.length);

        // Assert that after submission, it redirects to the correct page.
        assertTrue(page.getCurrentUrl().equals(translatorDashboardPage.TRANSLATOR_DASHBOARD_URL),
                var.getWrongUrlErrMsg());

        // Sign out.
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }
}
