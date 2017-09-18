package com.gengo.automation.tests.CoreTest;

import com.gengo.automation.global.AutomationBase;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @case Create new translator account - GOOGLE
 * @reference https://gengo.testrail.com/index.php?/cases/view/40335
 */
public class C40335_3_CreateTranslatorAccount extends AutomationBase {

    private String parentWindow;
    private Select selectCountry;

    public C40335_3_CreateTranslatorAccount() throws IOException {}

    @AfterClass
    public void afterRun() {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test
    public void editAccountInAdmin() {
        // Remove the permission of Gengo app from the Gmail account.
        globalMethods.removeGooglePermission();

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

        // Login as admin account.
        loginPage.loginAccount(var.getTestAdmin(), var.getDefaultPassword());

        global.goToAdminPage();

        // Assert that the redirect page is correct.
        assertTrue(page.getCurrentUrl().equals(global.ADMIN_URL),
                var.getWrongUrlErrMsg());

        // Search the account. [gengo.automationtest@gmail.com]
        adminPage.searchAccount(var.getGmailEmail());

        adminUserPage.editEmail();

        global.adminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    @Test(dependsOnMethods = "editAccountInAdmin")
    public void createNewTranslatorAccountWithGoogle() {
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

        loginPage.clickSignUpButton();

        // Check that sign up page URL is correct.
        assertTrue(page.getCurrentUrl().equals(signUpPage.SIGNUP_PAGE_URL),
                var.getWrongUrlErrMsg());

        // Assert elements behave as expected.
        assertTrue(signUpPage.getParentDropDown().getText().equals("Customer"),
                var.getTextNotEqualErrMsg());
        assertFalse(signUpPage.getCreateAccountBtn().isEnabled(),
                var.getElementIsEnabledErrMsg());
        assertFalse(signUpPage.getFacebookLink().isEnabled(),
                var.getElementIsEnabledErrMsg());
        assertFalse(signUpPage.getGoogleLink().isEnabled(),
                var.getElementIsEnabledErrMsg());

        signUpPage.clickDropDownButton();

        // Assert elements behave as expected after clicking the sign up dropdown button for translator as option.
        assertTrue(signUpPage.getOptionCustomer().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertFalse(signUpPage.getCreateAccountBtn().isEnabled(),
                var.getElementIsEnabledErrMsg());
        assertFalse(signUpPage.getFacebookLink().isEnabled(),
                var.getElementIsEnabledErrMsg());
        assertFalse(signUpPage.getGoogleLink().isEnabled(),
                var.getElementIsEnabledErrMsg());

        signUpPage.clickTranslatorOption();

        // Try click the Google link.
        signUpPage.clickGoogleLink();

        // Assert it's not clickable.
        assertFalse(signUpPage.getGoogleLink().isEnabled(),
                var.getElementIsEnabledErrMsg());

        signUpPage.tickCheckBoxAgree();

        assertTrue(signUpPage.getGoogleLink().isEnabled(),
                var.getElementIsEnabledErrMsg());

        signUpPage.clickGoogleLink();

        parentWindow = switcher.getWindowHandle();
        switcher.switchToPopUp();
        gmailAllowAppPage.clickAllow();
        switcher.switchToParentWindow(parentWindow);

        assertTrue(translatorOnboardPage.getTranslatorOnboardText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Onboard the translator
        assertTrue(page.getCurrentUrl().equals(translatorOnboardPage.TRANSLATOR_ONBOARD_URL),
                var.getWrongUrlErrMsg());

        translatorOnboardPage.clickContinue();

        assertFalse(translatorOnboardPage.isContinueButtonEnabled(),
                var.getElementIsEnabledErrMsg());

        // Fill out translator onboard form and check button.
        translatorOnboardPage.fillOutForm(true);

        assertFalse(translatorOnboardPage.isContinueButtonEnabled(),
                var.getElementIsEnabledErrMsg());

        // Clear out translator onboard form and click the tax declaration buttons.
        translatorOnboardPage.clearOutForm();

        translatorOnboardPage.clickTaxDeclarationRadioButtonsNo();

        // Assert the 'Continue' button is enabled.
        assertTrue(translatorOnboardPage.getBtnTranslatorOnboardContinue().isEnabled(),
                var.getElementIsNotEnabledErrMsg());

        translatorOnboardPage.clickContinue();

        assertTrue(translatorOnboardPage.getEmptyFieldsOnboardFail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        translatorOnboardPage.clickTaxDeclarationRadioButtonsYesNo();

        assertTrue(translatorOnboardPage.getCannotWorkOnboardFail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Fill out again translator onboard form and it's ready to submit.
        translatorOnboardPage.fillOutForm(true);
        translatorOnboardPage.clickTaxDeclarationRadioButtonsNoYes();

        assertTrue(translatorOnboardPage.isContinueButtonEnabled(),
                var.getElementIsEnabledErrMsg());

        // Click tax declaration with both `Yes` statement.
        translatorOnboardPage.clickTaxDeclarationRadioButtonsYes();
        assertTrue(translatorOnboardPage.isContinueButtonEnabled(),
                var.getElementIsEnabledErrMsg());

        // Click tax declaration with both `No` statement.
        translatorOnboardPage.clickTaxDeclarationRadioButtonsNo();
        assertTrue(translatorOnboardPage.isContinueButtonEnabled(),
                var.getElementIsEnabledErrMsg());

        // Clear out form and fill again except zip code.
        translatorOnboardPage.clearOutForm();
        translatorOnboardPage.fillOutForm(false);
        translatorOnboardPage.clickTaxDeclarationRadioButtonsNo();
        translatorOnboardPage.clickContinue();

        assertTrue(translatorOnboardPage.getNoZipCodeOnboardFail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Fill out whole form.
        translatorOnboardPage.clearOutForm();
        translatorOnboardPage.fillOutForm(true);
        translatorOnboardPage.clickTaxDeclarationRadioButtonsNo();
        translatorOnboardPage.clickContinue();

        assertTrue(translatorExperiencePage.getTranslatorExperienceText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        page.goBack();

        assertTrue(translatorOnboardPage.getTranslatorOnboardText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Try selecting Ireland as country without ZIP CODE {should pass}
        translatorOnboardPage.clearOutForm();
        translatorOnboardPage.fillOutForm(false);
        selectCountry = new Select(translatorOnboardPage.getSelectCountryDropDown());
        selectCountry.selectByValue("IE"); // Test Ireland if it will pass without zip code.
        translatorOnboardPage.clickTaxDeclarationRadioButtonsNo();
        translatorOnboardPage.clickContinue();

        assertTrue(translatorExperiencePage.getTranslatorExperienceText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Test the radio buttons for experience.
        translatorExperiencePage.clickCasualExperienceRadioBtn();
        translatorExperiencePage.clickSpecialistExperienceRadioBtn();
        translatorExperiencePage.clickNewExperienceRadioBtn();

        translatorExperiencePage.clickContinue();

        assertTrue(translatorExperiencePage.getNoPreferredTranslationErrMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        translatorExperiencePage.selectTopics();

        translatorExperiencePage.removeATopic();

        assertTrue(translatorExperiencePage.isSpecializationAmountRight(4),
                var.getAmountIsNotRightErrMsg());

        translatorExperiencePage.returnRemovedSpecialization();

        translatorExperiencePage.clickBackBtn();

        switcher.clickAlert("dismiss");

        assertTrue(translatorExperiencePage.getTranslatorExperienceText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        translatorExperiencePage.clickBackBtn();

        switcher.clickAlert("accept");

        assertTrue(translatorOnboardPage.getTranslatorOnboardText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        translatorOnboardPage.clickContinue();

        assertTrue(translatorExperiencePage.getTranslatorExperienceText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        assertTrue(translatorExperiencePage.isSpecializationAmountRight(0),
                var.getAmountIsNotRightErrMsg());

        translatorExperiencePage.selectTopics();

        translatorExperiencePage.clickContinue();

        assertTrue(translatorTestsPage.getTranslatorTestsPageText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        page.goBack();

        translatorExperiencePage.selectFieldOfStudy();

        translatorExperiencePage.clickContinue();

        assertTrue(translatorTestsPage.getTranslatorTestsPageText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Logout and sign in again.
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        pluginPage.passThisPage();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        homePage.clickSignInButton();

        assertTrue(page.getCurrentUrl().equals(loginPage.LOGIN_PAGE_URL),
                var.getWrongUrlErrMsg());

        loginPage.clickGoogleLoginLink();

        assertTrue(translatorTestsPage.getTranslatorTestsPageText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }
}
