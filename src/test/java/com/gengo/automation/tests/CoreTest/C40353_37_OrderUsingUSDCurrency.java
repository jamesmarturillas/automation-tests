package com.gengo.automation.tests.CoreTest;

import com.gengo.automation.fields.Constants;
import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * @case Verify that the placement of job using USD currency is working as expected.
 * @reference https://gengo.testrail.com/index.php?/cases/view/40353
 */
public class C40353_37_OrderUsingUSDCurrency extends AutomationBase {

    private String[] itemToTranslates;
    private String[] unitCounts;

    public C40353_37_OrderUsingUSDCurrency() throws IOException {}

    @BeforeClass
    public void initFields() throws IOException{
        this.newUser = csv.generateNewUser(var.SHEETNAME_CUSTOMERS, 0);
        itemToTranslates  = new String[] {
                var.getItemToTranslate(10),
        };
        unitCounts = new String[] {
                var.getUnitCountSource(10),
        };
    }

    @AfterClass
    public void afterRun() throws IOException {
        csv.writeValue(this.newUser, var.SHEETNAME_CUSTOMERS, 0);
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test(description = "Use a new account as conditional case for database reset")
    public void createCustomerAccount() {
        globalMethods.createNewCustomer(this.newUser, true, Constants.CURRENCY_USD);
    }

    @Test(dependsOnMethods = "createCustomerAccount", description = "WITH CREDITS - Retail - SINGLE JOB (STANDARD)")
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

        page.refresh();

        customerCheckoutPage.clickPayNowAndConfirm(false, false, false, false, false);

        // Assert that order is complete by checking the return URL.
        assertTrue(page.getCurrentUrl().contains
                (customerOrderCompletePage.ORDERCOMPLETE_URL), var.getWrongUrlErrMsg());

        // Assert that the price deducted is right.
        assertTrue(customerOrderCompletePage.isDeductedRight
                (unitCounts[0], Constants.USD_STD_EN_JA_POINT08), var.getTextNotEqualErrMsg());

        // Go back to the customer dashboard.
        customerOrderCompletePage.clickGoToDashboard();
        assertTrue(customerDashboardPage.getCustomerDashboardText()
                .isDisplayed(), var.getElementIsNotDisplayedErrMsg());

        // Sign out
        global.nonAdminSignOut();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }
}
