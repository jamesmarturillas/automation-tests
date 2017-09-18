package com.gengo.automation.tests.CoreTest;

import com.gengo.automation.global.AutomationBase;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @case Verify steps on translator onboarding works as expected.
 * @reference https://gengo.testrail.com/index.php?/cases/view/53893
 */
public class C53893_TranslatorOnboarding extends AutomationBase {

    public C53893_TranslatorOnboarding() throws IOException {}

    @BeforeClass
    public void initField() {
        this.newUser = csv.generateNewUser(var.SHEETNAME_TRANSLATORS, 0);
    }

    @AfterClass
    public void afterRun() throws IOException {
        csv.writeValue(this.newUser, var.SHEETNAME_TRANSLATORS, 0);
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test(description = "Precondition to proceed the onboarding of translator", priority = 1)
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
    }

    @Test(description = "Starts the onboarding process", priority = 2)
    public void verifyTaxDeclarationFormResponse() {
        // Assert that after clicking the 'Take a test' button from email, it will redirect to the right URL.
        assertTrue(page.getCurrentUrl().equals(translatorOnboardPage.TRANSLATOR_ONBOARD_URL),
                var.getWrongUrlErrMsg());

        // Clicks the 'Continue' button without filling up anything in the form.
        translatorOnboardPage.clickContinue();

        // Assert that the behavior is correct as expected.
        assertFalse(translatorOnboardPage.isContinueButtonEnabled(),
                var.getElementIsEnabledErrMsg());

        // Fill out the form.
        translatorOnboardPage.fillOutForm(true);

        // 'Continue' button is still disabled.
        assertFalse(translatorOnboardPage.isContinueButtonEnabled(),
                var.getElementIsEnabledErrMsg());

        // Clear the form and click the the tax declaration elements.
        translatorOnboardPage.clearOutForm();
        translatorOnboardPage.clickTaxDeclarationRadioButtonsNo();

        // Assert that the 'Continue' button is enabled now.
        assertTrue(translatorOnboardPage.isContinueButtonEnabled(),
                var.getElementIsEnabledErrMsg());

        // Click the 'Continue' button with tax declaration clicked but fields are empty.
        translatorOnboardPage.clickContinue();

        // Assert that the submission is failed.
        assertTrue(translatorOnboardPage.getEmptyFieldsOnboardFail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Do you live in the US? YES Are you a US citizen or resident alien? NO
        translatorOnboardPage.clickTaxDeclarationRadioButtonsYesNo();
        translatorOnboardPage.clickContinue();
        assertTrue(translatorOnboardPage.getEmptyFieldsOnboardFail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Do you live in the US? NO Are you a US citizen or resident alien? YES
        translatorOnboardPage.clickTaxDeclarationRadioButtonsNoYes();
        translatorOnboardPage.clickContinue();
        assertTrue(translatorOnboardPage.getEmptyFieldsOnboardFail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Do you live in the US? YES Are you a US citizen or resident alien? YES
        translatorOnboardPage.clickTaxDeclarationRadioButtonsYes();
        translatorOnboardPage.clickContinue();
        assertTrue(translatorOnboardPage.getEmptyFieldsOnboardFail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Do you live in the US? NO Are you a US citizen or resident alien? NO
        translatorOnboardPage.clickTaxDeclarationRadioButtonsNo();
        translatorOnboardPage.clickContinue();
        assertTrue(translatorOnboardPage.getEmptyFieldsOnboardFail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    @Test(description = "Fills out the form w/ Zip Code; Submits the form", priority = 3)
    public void verifyFilledOutFormResponse() {
        // Fill up the form w/ zip code again; answers the tax declaration buttons and submit.
        translatorOnboardPage.fillOutForm(true);
        translatorOnboardPage.clickTaxDeclarationRadioButtonsNo();
        translatorOnboardPage.clickContinue();

        // Assert that the submission is success by checking the next URL.
        assertTrue(translatorExperiencePage.getTranslatorExperienceText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    @Test(description = "Interaction w/ translator experience page", priority = 4)
    public void verifyNewTranslatorExperienceFormResponse() {
        // Test that we can only select one radio button in 'experience' section.
        translatorExperiencePage.clickNewExperienceRadioBtn();
        translatorExperiencePage.clickCasualExperienceRadioBtn();
        translatorExperiencePage.clickSpecialistExperienceRadioBtn();

        // Assert that the checked option is only 1.
        assertTrue(translatorExperiencePage.isOnlyOneExperienceSelected(), var.getFailedSelectedAmount());

        // Go back to selecting the 'New Experience'
        translatorExperiencePage.clickNewExperienceRadioBtn();

        // Click continue without choosing preferred translation topics.
        translatorExperiencePage.clickContinue();

        // Assert that there is an error clicking continue without preferred translation.
        assertTrue(translatorExperiencePage.getNoPreferredTranslationErrMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Select 5 topics.
        translatorExperiencePage.selectTopics();

        // Remove 1 topic.
        translatorExperiencePage.removeATopic();

        // Return the removed specialization.
        translatorExperiencePage.returnRemovedSpecialization();

        // Click the 'Back' button
        translatorExperiencePage.clickBackBtn();

        // Alert will display and click 'Cancel'.
        switcher.clickAlert("dismiss");
        assertTrue(translatorExperiencePage.getTranslatorExperienceText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Click again the 'Back' button.
        translatorExperiencePage.clickBackBtn();

        // Alert will display and click 'Leave'
        switcher.clickAlert("accept");
        assertTrue(translatorOnboardPage.getTranslatorOnboardText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Got into 'Info' page and click 'Continue'.
        translatorOnboardPage.clickContinue();

        // Assert that we are redirected to the right page.
        assertTrue(translatorExperiencePage.getTranslatorExperienceText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Assert that the selected topics are gone.
        assertTrue(translatorExperiencePage.isSpecializationAmountRight(0),
                var.getAmountIsNotRightErrMsg());

        // Select topics again.
        translatorExperiencePage.selectTopics();

        // Click 'Continue' to verify that the 'Field of study' is not required.
        translatorExperiencePage.clickContinue();
        assertTrue(translatorTestsPage.getTranslatorTestsPageText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Go back to the 'Experience' page.
        page.goBack();

        // Select field of study and continue; Assert that the landing page is success.
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
