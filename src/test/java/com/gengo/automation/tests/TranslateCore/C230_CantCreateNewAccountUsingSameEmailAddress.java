package com.gengo.automation.tests.TranslateCore;

import com.gengo.automation.global.AutomationBase;
import com.gengo.automation.global.GlobalMethods;
import org.junit.BeforeClass;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @case Verify the ability to create account using same email address.
 * Expect that it cannot.
 * @reference https://gengo.testrail.com/index.php?/cases/view/230
 */
public class C230_CantCreateNewAccountUsingSameEmailAddress extends AutomationBase {

    public C230_CantCreateNewAccountUsingSameEmailAddress() throws IOException {}

    @BeforeClass
    public void initFields() throws IOException{
        this.newUser = csv.generateNewUser(var.SHEETNAME_CUSTOMERS, 0);
        globalMethods.createNewCustomer(newUser);
    }

    @AfterClass
    public void afterRun() throws IOException {
        csv.writeValue(this.newUser, var.SHEETNAME_CUSTOMERS, 0);
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test
    public void createAccount() {
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

        // Click the dropdown and select the 'Customer'
        // option then tick the checkbox
        // that you agree with the
        // Gengo's terms and conditions
        signUpPage.clickDropDownButton();
        assertTrue(signUpPage.getOptionCustomer().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        signUpPage.clickCustomerOption();
        signUpPage.tickCheckBoxAgree();

        // Assert elements behave as expected after clicking the sign up dropdown button for customer as option.
        assertTrue(signUpPage.getCreateAccountBtn().isEnabled(),
                var.getElementIsNotEnabledErrMsg());
        assertTrue(signUpPage.getFacebookLink().isEnabled(),
                var.getElementIsNotEnabledErrMsg());
        assertTrue(signUpPage.getGoogleLink().isEnabled(),
                var.getElementIsNotEnabledErrMsg());

        // Fill out the form with the same credentials we use in test case 'C1'
        signUpPage.fillOutSignUpForm(var.getCustomer(5),
                var.getDefaultPassword());

        // Expect that the page will return an error saying that the email is already taken.
        assertTrue(signUpPage.getFailedToSignUpAccountTakenMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Click the dropdown and select the 'Translator'
        // option then tick the checkbox
        // that you agree with the
        // Gengo's terms and conditions
        signUpPage.clickDropDownButton();
        assertTrue(signUpPage.getOptionTranslator().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        signUpPage.clickTranslatorOption();

        // Assert elements behave as expected after clicking the sign up dropdown button for translator as option.
        assertTrue(signUpPage.getCreateAccountBtn().isEnabled(),
                var.getElementIsNotEnabledErrMsg());
        assertTrue(signUpPage.getFacebookLink().isEnabled(),
                var.getElementIsNotEnabledErrMsg());
        assertTrue(signUpPage.getGoogleLink().isEnabled(),
                var.getElementIsNotEnabledErrMsg());

        // Fill out the form with the same credentials we use in test case 'C2'
        signUpPage.fillOutSignUpForm(var.getTranslator(1), var.getDefaultPassword());

        assertTrue(signUpPage.getFailedToSignUpAccountTakenMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    @Test(dependsOnMethods = "createAccount")
    public void createCustomerAcctWithTranslatorAcctAndViceVersa() {
        // Click the dropdown and select the 'Customer'
        // option then tick the checkbox
        // that you agree with the
        // Gengo's terms and conditions
        signUpPage.clickDropDownButton();
        assertTrue(signUpPage.getOptionCustomer().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        signUpPage.clickCustomerOption();

        // Assert elements behave as expected after clicking the sign up dropdown button for customer as option.
        assertTrue(signUpPage.getCreateAccountBtn().isEnabled(),
                var.getElementIsNotEnabledErrMsg());
        assertTrue(signUpPage.getFacebookLink().isEnabled(),
                var.getElementIsNotEnabledErrMsg());
        assertTrue(signUpPage.getGoogleLink().isEnabled(),
                var.getElementIsNotEnabledErrMsg());

        // Fill out the form with the same credentials we use in test case 'C2'
        signUpPage.fillOutSignUpForm(var.getTranslator(1),
                var.getDefaultPassword());

        // Expect that the page will return an error saying that the email is already taken.
        assertTrue(signUpPage.getFailedToSignUpAccountTakenMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Click the dropdown and select the 'Translator'
        // option then tick the checkbox
        // that you agree with the
        // Gengo's terms and conditions
        signUpPage.clickDropDownButton();
        assertTrue(signUpPage.getOptionTranslator().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        signUpPage.clickTranslatorOption();

        // Assert elements behave as expected after clicking the sign up dropdown button for translator as option.
        assertTrue(signUpPage.getCreateAccountBtn().isEnabled(),
                var.getElementIsNotEnabledErrMsg());
        assertTrue(signUpPage.getFacebookLink().isEnabled(),
                var.getElementIsNotEnabledErrMsg());
        assertTrue(signUpPage.getGoogleLink().isEnabled(),
                var.getElementIsNotEnabledErrMsg());

        // Fill out the form with the same credentials we use in test case 'C1'
        signUpPage.fillOutSignUpForm(var.getCustomer(1), var.getDefaultPassword());

        assertTrue(signUpPage.getFailedToSignUpAccountTakenMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }
}
