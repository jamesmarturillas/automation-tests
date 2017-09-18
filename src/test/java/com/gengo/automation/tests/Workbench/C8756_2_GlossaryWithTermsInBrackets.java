package com.gengo.automation.tests.Workbench;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertFalse;

/**
 * @case Uploading of Glossary File with Triple Brackets on Terms
 * Character to Word
 * @reference https://gengo.testrail.com/index.php?/cases/view/8756
 */
public class C8756_2_GlossaryWithTermsInBrackets extends AutomationBase {

    private String[] unitCount,itemToTranslate;

    @BeforeClass
    public void initFields() throws IOException{
        itemToTranslate = new String[] {
                var.getItemToTranslate(22)
        };
        unitCount = new String[] {
                var.getUnitCountSource(22)
        };
    }

    public C8756_2_GlossaryWithTermsInBrackets() throws IOException {}

    @AfterClass
    public void afterRun() {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    /**
     * uploadInvalidGlossary --- a method for checking the logging in of a customer account,
     * and the upload of a bracketed glossary file
     * */
    @Test
    public void uploadInvalidGlossary() throws IOException {
        pluginPage.passThisPage();

        // Check if the prominent page elements can be seen on the Home Page
        assertTrue(homePage.checkHomePage());
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Logging in a customer account
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(26), var.getDefaultPassword());

        // Check if the prominent page elements can be seen on the Customer Dashboard Page
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());
        assertTrue(customerDashboardPage.checkCustomerDashboard());

        // Go to Glossaries Page
        global.clickGlossariesTab();

        // Upload glossary file containing bracketed terms
        glossaryPage.uploadInvalidGlossary(var.getJaToEnBracketedGlossary());

        // Check if a validation error is displayed
        assertTrue(glossaryPage.isBracketedErrorDisplayed(), var.getElementIsNotDisplayedErrMsg());
    }

    /**
     * checkGlossaryOnOrderPage --- a method for checking the absence of the invalid glossary uploaded on the order page
     * */
    @Test(dependsOnMethods = "uploadInvalidGlossary")
    public void checkGlossaryOnOrderPage() throws IOException {
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

        // Check that csv has not been uploaded and not showing on order page
        assertFalse(customerCheckoutPage.isGlossaryPresent(var.getJaToEnBracketedGlossary().replace(".csv", "")));

        // Customer sign out
        global.nonAdminSignOut();

        // Check if the redirected page contains the prominent Home Page elements
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(homePage.checkHomePage());
    }
}
