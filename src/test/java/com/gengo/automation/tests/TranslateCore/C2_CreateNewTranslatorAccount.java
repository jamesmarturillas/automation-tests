package com.gengo.automation.tests.TranslateCore;

import com.gengo.automation.global.AutomationBase;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @case Create new translator account
 * @reference https://gengo.testrail.com/index.php?/cases/view/2
 */
public class C2_CreateNewTranslatorAccount extends AutomationBase {

    private Select selectCountry;

    public C2_CreateNewTranslatorAccount() throws IOException {}

    @BeforeClass
    public void initField() {
        this.newUser = csv.generateNewUser(var.SHEETNAME_TRANSLATORS, 0);
    }

    @AfterClass
    public void afterRun() throws IOException {
        csv.writeValue(this.newUser, var.SHEETNAME_CUSTOMERS, 0);
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test
    public void createNewTranslatorAccount() {
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
        assertTrue(signUpPage.getOptionTranslator().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        signUpPage.clickTranslatorOption();

        // Assert elements behave as expected after clicking the sign up dropdown button for translator as option.
        assertFalse(signUpPage.getCreateAccountBtn().isEnabled(),
                var.getElementIsEnabledErrMsg());
        assertFalse(signUpPage.getFacebookLink().isEnabled(),
                var.getElementIsEnabledErrMsg());
        assertFalse(signUpPage.getGoogleLink().isEnabled(),
                var.getElementIsEnabledErrMsg());

        signUpPage.clickDropDownButton();

        assertTrue(signUpPage.getOptionCustomer().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        signUpPage.clickTranslatorOption();
        signUpPage.tickCheckBoxAgree();

        // Assert elements behave as expected after clicking the sign up dropdown button for translator as option.
        assertTrue(signUpPage.getCreateAccountBtn().isEnabled(),
                var.getElementIsNotEnabledErrMsg());
        assertTrue(signUpPage.getFacebookLink().isEnabled(),
                var.getElementIsNotEnabledErrMsg());
        assertTrue(signUpPage.getGoogleLink().isEnabled(),
                var.getElementIsNotEnabledErrMsg());

        // Submits the form without anything in it.
        signUpPage.clickCreateAccountButton();

        assertTrue(signUpPage.getFailedToSignUpEmptyFieldsMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Fill the form with existing account.
        signUpPage.fillOutSignUpForm(var.getTranslatorStandard(5),
                var.getDefaultPassword());

        assertTrue(signUpPage.getFailedToSignUpAccountTakenMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(signUpPage.getParentDropDown().getText().equals("Translator"),
                var.getTextNotEqualErrMsg());

        // Fill the form with deleted account.
        signUpPage.fillOutSignUpForm(var.getTranslatorDeleted(1),
                var.getDefaultPassword());

        assertTrue(signUpPage.getFailedToSignUpAccountDeletedMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(signUpPage.getParentDropDown().getText().equals("Translator"),
                var.getTextNotEqualErrMsg());

        // Fill out the form and submit.
        signUpPage.fillOutSignUpForm(this.newUser, var.getDefaultPassword());

        assertTrue(page.getCurrentUrl().contains(successRegistrationPage.SUCCESS_PAGE_URL),
                var.getWrongUrlErrMsg());
        assertTrue(successRegistrationPage.getSuccessPageGengoLogo().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(successRegistrationPage.getSuccessPageBody().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Go to Gmail account
        page.launchUrl(var.getGmailUrl());

        assertTrue(gmailSignInEmailPage.getTxtBoxEmail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        gmailSignInEmailPage.inputEmail(var.getGmailEmail());

        gmailSignInPasswordPage.inputPasswordAndSubmit(var.getGmailPassword());

        assertTrue(gmailInboxPage.getGmailComposeBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(gmailInboxPage.getActivateYourGengoEmail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        gmailInboxPage.clickActivateGengoEmail();

        assertTrue(gmailActivateGengoPage.getActivateAccountBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Activate the account.
        gmailActivateGengoPage.clickActivateAccount();

        assertTrue(translatorOnboardPage.getTranslatorOnboardText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Go back to Gmail account and click 'Welcome to Gengo' email.
        page.launchUrl(var.getGmailUrl());

        assertTrue(gmailInboxPage.getGmailComposeBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        gmailInboxPage.clickWelcomeToGengoEmail();

        assertTrue(gmailWelcomeToGengoPage.getTakeTestBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        gmailWelcomeToGengoPage.clickTakeTest();

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

        loginPage.loginAccount(this.newUser, var.getDefaultPassword());

        assertTrue(translatorTestsPage.getTranslatorTestsPageText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }
}
