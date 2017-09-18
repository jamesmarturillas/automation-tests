package com.gengo.automation.global;

import com.gengo.automation.fields.Constants;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.gengo.automation.fields.Constants.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @class All methods that will be used in multiple
 * test cases will be stored here.
 */
public class GlobalMethods extends AutomationBase {

    private final String GMAIL_URL = "https://mail.google.com/";
    private final String GMAIL_PERMISSION_PAGE = "https://myaccount.google.com/permissions";
    private final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private SecureRandom rnd = new SecureRandom();
    private static final String CUSTOMER = "customer";
    private static final String TRANSLATOR = "translator";
    private static final String ADMIN = "admin";

    private List<String> translatorIdNumbers = new ArrayList<>();
    private List<String> translators = new ArrayList<>();
    private List<String> sources = new ArrayList<>();
    private List<String> targets = new ArrayList<>();
    private List<String> languagePairs = new ArrayList<>();
    private List<String> ranks = new ArrayList<>();

    public GlobalMethods(WebDriver driver) throws IOException {}

    public String randomString(int len){
        StringBuilder sb = new StringBuilder( len );
        for(int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    public void removeGooglePermission() {
        page.launchUrl(GMAIL_URL);
        assertTrue(gmailSignInEmailPage.getTxtBoxEmail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        gmailSignInEmailPage.inputEmail(var.getGmailEmail());
        wait.impWait(30);
        gmailSignInPasswordPage.inputPasswordAndSubmit(var.getGmailPassword());
        wait.impWait(30, gmailInboxPage.getGmailComposeBtn());
        page.launchUrl(GMAIL_PERMISSION_PAGE);
        googleAppPermissionPage.removeGoogleAppInGmailAcct();
        page.launchUrl(conf.getString("url"));
    }

    public void createNewCustomer(String username) {
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

        // Fill out the form and submit.
        signUpPage.fillOutSignUpForm(username, var.getDefaultPassword());

        if (signUpPage.hasTakenError()) {
            page.launchUrl(baseURL);
            return;
        }

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

        // Activate the account
        gmailActivateGengoPage.clickActivateAccount();

        assertTrue(customerOrderFormPage.getOrderFormTextArea().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Go back to Gmail account and click 'Welcome to Gengo' email.
        page.launchUrl(var.getGmailUrl());

        assertTrue(gmailInboxPage.getGmailComposeBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        gmailInboxPage.clickWelcomeToGengoEmail();

        assertTrue(gmailWelcomeToGengoPage.getOrderTranslationBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        gmailWelcomeToGengoPage.clickOrderTranslation();

        assertTrue(page.getCurrentUrl().equals(customerOrderFormPage.ORDER_FORM_URL),
                var.getWrongUrlErrMsg());

        // Logout
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        pluginPage.passThisPage();
    }

    /**
     * @param username
     *  Accepts string parameter for newly created email.
     * @param withCredits
     *  Accepts boolean parameter to test whether the method should perform topping up of credits or not.
     */
    public void createNewCustomer(String username, boolean withCredits) {
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

        // Fill out the form and submit.
        signUpPage.fillOutSignUpForm(username, var.getDefaultPassword());

        if (signUpPage.hasTakenError()) {
            page.launchUrl(baseURL);
            return;
        }

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

        // Activate the account
        gmailActivateGengoPage.clickActivateAccount();

        assertTrue(customerOrderFormPage.getOrderFormTextArea().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Go back to Gmail account and click 'Welcome to Gengo' email.
        page.launchUrl(var.getGmailUrl());

        assertTrue(gmailInboxPage.getGmailComposeBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        gmailInboxPage.clickWelcomeToGengoEmail();

        assertTrue(gmailWelcomeToGengoPage.getOrderTranslationBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        gmailWelcomeToGengoPage.clickOrderTranslation();

        assertTrue(page.getCurrentUrl().equals(customerOrderFormPage.ORDER_FORM_URL),
                var.getWrongUrlErrMsg());

        if (withCredits)
            this.topUpCredits();

        // Logout
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        pluginPage.passThisPage();
    }

    public void createNewCustomer(String username, boolean withCredits, String currency) {
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

        // Fill out the form and submit.
        signUpPage.fillOutSignUpForm(username, var.getDefaultPassword());

        if (signUpPage.hasTakenError()) {
            page.launchUrl(baseURL);
            return;
        }

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

        // Activate the account
        gmailActivateGengoPage.clickActivateAccount();

        assertTrue(customerOrderFormPage.getOrderFormTextArea().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Go back to Gmail account and click 'Welcome to Gengo' email.
        page.launchUrl(var.getGmailUrl());

        assertTrue(gmailInboxPage.getGmailComposeBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        gmailInboxPage.clickWelcomeToGengoEmail();

        assertTrue(gmailWelcomeToGengoPage.getOrderTranslationBtn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        gmailWelcomeToGengoPage.clickOrderTranslation();

        assertTrue(page.getCurrentUrl().equals(customerOrderFormPage.ORDER_FORM_URL),
                var.getWrongUrlErrMsg());

        // Set account currency first.
        this.setAccountCurrency(currency);

        // Top-up credits.
        if (withCredits)
            this.topUpCredits(currency);

        // Logout
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        pluginPage.passThisPage();
    }

    public void createNewTranslator(String username) {
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
        signUpPage.fillOutSignUpForm(username, var.getDefaultPassword());

        if (signUpPage.hasTakenError()) {
            page.launchUrl(baseURL);
            return;
        }

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

        // Fill out whole form.
        translatorOnboardPage.clearOutForm();
        translatorOnboardPage.fillOutForm(true);
        translatorOnboardPage.clickTaxDeclarationRadioButtonsNo();
        translatorOnboardPage.clickContinue();

        assertTrue(translatorExperiencePage.getTranslatorExperienceText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Test the radio buttons for experience.
        translatorExperiencePage.clickCasualExperienceRadioBtn();
        translatorExperiencePage.clickSpecialistExperienceRadioBtn();
        translatorExperiencePage.clickNewExperienceRadioBtn();

        translatorExperiencePage.selectTopics();

        translatorExperiencePage.selectFieldOfStudy();

        translatorExperiencePage.clickContinue();

        assertTrue(translatorTestsPage.getTranslatorTestsPageText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Logout and sign in again.
        global.nonAdminSignOut();

        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        pluginPage.passThisPage();
    }

    public void topUpCredits() {
        // Navigate through top up page.
        global.goToTopUp();

        // Assert that the navigated page is correct by URL return.
        assertTrue(page.getCurrentUrl().contains(topUpPage.TOPUP_URL));

        topUpPage.selectAmount(var.getTopUpAmountUSD(8000)); // $8,000 amount as default.
        topUpPage.selectPaymentMode(topUpPage.chooseStripe());
        topUpPage.clickPay(false);
        topUpPage.processPayment(var.getCreditCard());
        wait.impWait(30);
        switcher.switchToDefaultContent();
        wait.impWait(10, topUpPage.getTopUpSuccessMsg());
        assertTrue(topUpPage.getTopUpSuccessMsg().isDisplayed(),
                var.getTextNotEqualErrMsg());
    }

    public void setAccountCurrency(String currency) {
        // Navigate through top up page again.
        global.goToAcctSettings();

        // Assert that the return page is correct.
        assertTrue(page.getCurrentUrl().contains(accountSettingsPage.ACCOUNT_SETTINGS_URL),
                var.getWrongUrlErrMsg());

        // Check if the currency selection dropdown is enabled.
        accountSettingsPage.clickChangePaymentPreferences();

        // Select among "USD", "EUR", "JPY", "GBP" string values.
        accountSettingsPage.selectCurrency(currency);
        assertTrue(accountSettingsPage.getChangeCurrencySuccessMsg().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    public void topUpCredits(String currency) {
        // Navigate through top up page.
        global.goToTopUp();

        // Assert that the navigated page is correct by URL return.
        assertTrue(page.getCurrentUrl().contains(topUpPage.TOPUP_URL));

        switch (currency) {
            case Constants.CURRENCY_EUR:
                topUpPage.selectAmount(var.getTopUpAmountEUR(8000));
                break;
            case Constants.CURRENCY_GBP:
                topUpPage.selectAmount(var.getTopUpAmountGBP(8000));
                break;
            case Constants.CURRENCY_JPY:
                topUpPage.selectAmount(var.getTopUpAmountJPY(8000));
                break;
            default:
                topUpPage.selectAmount(var.getTopUpAmountUSD(8000));
                break;
        }

        topUpPage.selectPaymentMode(topUpPage.chooseStripe());
        topUpPage.clickPay(false);
        topUpPage.processPayment(var.getCreditCard());
        wait.impWait(30);
        switcher.switchToDefaultContent();
        wait.impWait(10, topUpPage.getTopUpSuccessMsg());
        assertTrue(topUpPage.getTopUpSuccessMsg().isDisplayed(),
                var.getTextNotEqualErrMsg());
    }

    public void addPreferredTranslatorThruAdmin(String generatedUser) throws InterruptedException {
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        homePage.clickSignInButton();

        assertTrue(page.getCurrentUrl().equals(loginPage.LOGIN_PAGE_URL),
                var.getWrongUrlErrMsg());
        assertTrue(loginPage.getSignUpBtn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        loginPage.loginAccount(var.getTestAdmin(), var.getDefaultPassword());

        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        global.selectAdmin();
        assertTrue(page.getCurrentUrl()
                .contains(global.ADMIN_URL), var.getWrongUrlErrMsg());

        global.hideAdminRightPanel();

        addTranslatorQualifications(false);

        adminPage.searchAccount(generatedUser);

        adminPage.clickUserActions(Constants.USER_ACTION_ADD_PREFERRED);
        assertTrue(adminPage.getPreferredTranslatorModalTxt()
                .isDisplayed(), var.getElementIsNotDisplayedErrMsg());

        translators.add(var.getTranslatorStandard(5));
        translators.add(var.getTranslatorPro(5));
        translators.add(var.getTranslatorPro(4));

        setPreferredTranslatorInTheForm(languagePairs.get(0), ranks.get(0), translators);

        adminPage.clickUserActions(Constants.USER_ACTION_ADD_PREFERRED);
        assertTrue(adminPage.getPreferredTranslatorModalTxt()
                .isDisplayed(), var.getElementIsNotDisplayedErrMsg());

        setPreferredTranslatorInTheForm(languagePairs.get(1), ranks.get(0), translators);

        translators.remove(0);

        adminPage.clickUserActions(Constants.USER_ACTION_ADD_PREFERRED);
        assertTrue(adminPage.getPreferredTranslatorModalTxt()
                .isDisplayed(), var.getElementIsNotDisplayedErrMsg());

        setPreferredTranslatorInTheForm(languagePairs.get(0), ranks.get(1), translators);

        adminPage.clickUserActions(Constants.USER_ACTION_ADD_PREFERRED);
        assertTrue(adminPage.getPreferredTranslatorModalTxt()
                .isDisplayed(), var.getElementIsNotDisplayedErrMsg());

        setPreferredTranslatorInTheForm(languagePairs.get(1), ranks.get(1), translators);

        driver.get(Constants.ADMIN_QUALIFICATIONS_URL);

        global.adminSignOut(false);
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
    }

    private void setPreferredTranslatorInTheForm(String languagePair, String rank, List<String> translators) throws InterruptedException {
        Select selectLanguagePair = new Select(adminPage.getSelectLanguagePair());
        Select selectRank = new Select(adminPage.getSelectQualificationsRank());

        selectLanguagePair.selectByVisibleText(languagePair);
        selectRank.selectByVisibleText(rank);

        adminPage.getTxtAreaAddPreferredTranslators().clear();

        for (int i = 0; i < translators.size(); i++) {
            adminPage.getTxtAreaAddPreferredTranslators().sendKeys(translators.get(i));
            if (i != (translators.size() - 1)) {
                adminPage.getTxtAreaAddPreferredTranslators().sendKeys(",");
            }
        }

        adminPage.getBtnAddPreferredUpdate().click();
        wait.impWait(30);
        assertTrue(adminPage.getPreferredTranslatorSetMsg()
                .isDisplayed(), var.getElementIsNotDisplayedErrMsg());
        Thread.sleep(3000);
    }

    public void addTranslatorQualifications(boolean hasToLogin) {
        if (hasToLogin) {
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

            loginPage.loginAccount(var.getTestAdmin(), var.getDefaultPassword());

            // Assert that page title is present in the DOM.
            assertTrue(global.getPageTitle().isDisplayed(),
                    var.getElementIsNotDisplayedErrMsg());
        }

        translatorIdNumbers.add(getTranslatorIdNumber(var.getTranslatorStandard(5)));
        translatorIdNumbers.add(getTranslatorIdNumber(var.getTranslatorPro(5)));
        translatorIdNumbers.add(getTranslatorIdNumber(var.getTranslatorPro(4)));

        sources.add(Constants.QUALIFICATION_SRC_EN);
        sources.add(Constants.QUALIFICATION_TGT_JA);

        targets.add(Constants.QUALIFICATION_TGT_JA);
        targets.add(Constants.QUALIFICATION_SRC_EN);

        languagePairs.add(Constants.EN_JA);
        languagePairs.add(Constants.JA_EN);

        ranks.add(Constants.QUALIFICATION_RNK_STANDARD);
        ranks.add(Constants.QUALIFICATION_RNK_PRO);

        adminPage.goToQualifications();

        adminPage.clickCreateNewQualificationsBtn();
        assertTrue(adminPage.getPageTitle().getText()
                .contains("Create Qualification"), var.getTextNotEqualErrMsg());

        // First translator {translatorstandard5} is set to source EN and target JA with rank STANDARD.
        adminPage.createQualification(translatorIdNumbers.get(0), sources.get(0), targets.get(0), ranks.get(0));
        // Second translator {translatorpro5} is set to source EN and target JA with rank STANDARD.
        adminPage.createQualification(translatorIdNumbers.get(1), sources.get(0), targets.get(0), ranks.get(0));
        // First translator {translatorstandard5} is set to source JA and target EN with rank STANDARD.
        adminPage.createQualification(translatorIdNumbers.get(0), sources.get(1), targets.get(1), ranks.get(0));
        // Second translator {translatorpro5} is set to source JA and target EN with rank STANDARD.
        adminPage.createQualification(translatorIdNumbers.get(1), sources.get(1), targets.get(1), ranks.get(0));

        // Second translator {translatorpro5} is set to source EN and target JA with rank PRO.
        adminPage.createQualification(translatorIdNumbers.get(1), sources.get(0), targets.get(0), ranks.get(1));
        // Second translator {translatorpro4} is set to source EN and target JA with rank PRO.
        adminPage.createQualification(translatorIdNumbers.get(2), sources.get(0), targets.get(0), ranks.get(1));
        // Second translator {translatorpro5} is set to source JA and target EN with rank PRO.
        adminPage.createQualification(translatorIdNumbers.get(1), sources.get(1), targets.get(1), ranks.get(1));
        // Second translator {translatorpro5} is set to source JA and target EN with rank PRO.
        adminPage.createQualification(translatorIdNumbers.get(2), sources.get(1), targets.get(1), ranks.get(1));
    }

    private String getTranslatorIdNumber(String translatorId) {
        adminPage.searchAccount(translatorId);
        return adminUserPage.getUserID().getText().substring(6);
    }

    /**
     * @param path
     * @return true once the click is successful, otherwise false.
     */
    public boolean retryingFindClick(WebElement path) {
        boolean result = false;
        int attempts = 0;
        while(attempts < 2) {
            try {
                path.click();
                result = true;
                break;
            } catch(StaleElementReferenceException e) {}
            attempts++;
        }
        return result;
    }

    public String AbbrToFullLanguage(String language) {
        switch(language.toLowerCase()){
            case LANG_BG:
                return var.getBulgarianTo();
            case LANG_ZH:
                return var.getChineseSimplifiedTo();
            case LANG_ZHTW:
                return var.getChineseTraditionalTo();
            case LANG_CS:
                return var.getCzechTo();
            case LANG_DA:
                return var.getDanishTo();
            case LANG_NL:
                return var.getDutchTo();
            case LANG_ENGB:
                return var.getEnglishBritishTo();
            case LANG_FI:
                return var.getFinnishTo();
            case LANG_FR:
                return var.getFrenchTo();
            case LANG_FRCA:
                return var.getFrenchCanadaTo();
            case LANG_DE:
                return var.getGermanTo();
            case LANG_EL:
                return var.getGreekTo();
            case LANG_HE:
                return var.getHebrewTo();
            case LANG_HU:
                return var.getHungarianTo();
            case LANG_ID:
                return var.getIndonesianTo();
            case LANG_IT:
                return var.getItalianTo();
            case LANG_JA:
                return var.getJapaneseTo();
            case LANG_KO:
                return var.getKoreanTo();
            case LANG_MS:
                return var.getMalayTo();
            case LANG_NO:
                return var.getNorwegianTo();
            case LANG_PL:
                return var.getPolishTo();
            case LANG_PTBR:
                return var.getPortugueseBrazilTo();
            case LANG_PT:
                return var.getPortugueseEuropeTo();
            case LANG_RU:
                return var.getRussianTo();
            case LANG_RO:
                return var.getRomanianTo();
            case LANG_SR:
                return var.getSerbianTo();
            case LANG_SK:
                return var.getSlovakTo();
            case LANG_ESLA:
                return var.getSpanishLatinAmericaTo();
            case LANG_ES:
                return var.getSpanishSpainTo();
            case LANG_SV:
                return var.getSwedishTo();
            case LANG_TL:
                return var.getTagalogTo();
            case LANG_TH:
                return var.getThaiTo();
            case LANG_TR:
                return var.getTurkishTo();
            case LANG_UK:
                return var.getUkrainianTo();
            case LANG_VI:
                return var.getVietnameseTo();
            case LANG_EN:
                return var.getEnglishTo();
        }
        return null;
    }

    public boolean isElementDisplayed(WebElement element) {
        boolean state = true;
        try{
            element.isDisplayed();
        }
        catch (NoSuchElementException e) {
            state = false;
        }
        return state;
    }

    public String uniqueIdentifier(){
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return sdf.format(date);
    }

    public boolean compareCredits(String oldCredits, String newCredits, String difference) {
        Double prevCredit = Double.parseDouble(oldCredits.substring(1).replaceAll("[,]",""));
        Double newCredit = Double.parseDouble(newCredits.substring(1).replaceAll("[,]",""));
        return difference.substring(1).equals(String.format( "%.2f", Math.abs(prevCredit - newCredit) ));
    }
}
