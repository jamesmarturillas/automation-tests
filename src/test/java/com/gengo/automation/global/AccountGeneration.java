package com.gengo.automation.global;

import com.gengo.automation.fields.Variables;
import com.gengo.automation.helpers.CSVManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

import static org.testng.Assert.assertTrue;

/**
 * @class All methods that will be used for
 * account generation is placed here
 */
public class AccountGeneration extends AutomationBase {

    Variables var = new Variables();
    CSVManager sdata = new CSVManager();
    private String NEW_QUALIFICATION_URL = "https://admin.staging.gengo.com/qualifications/create";
    public AccountGeneration(WebDriver driver) throws IOException {}

    /**
     * generateUsers --- this is the method called in the Invoker class; this method contains method
     * calls needed for the generation of user accounts
     * */
    public void generateUsers() throws IOException, AWTException {
        super.initObjects();
        this.generateCustomers();
        this.generateTranslators();
        this.addQualifications();
        this.translatorAgreeToTerms();
    }

    /**
     * generateCustomers --- this method goes through the listed customers to be generated
     * (with or without top up) and attempts to create these accounts
     * */
    public void generateCustomers() throws IOException, AWTException {
        logInToGmail();
        page.launchUrl(baseURL);

        /* Generate customers that do not need to be topped up */

        // loops through each of the listed customer emails from the DataSource file
        for (int ctr = 1 ; ctr <= calculateRowCount(var.customerAccounts()); ctr++) {
            createCustomer(var.getCustomerToGenerate(ctr));
        }

        /* Generate customers that needs to have default currency */

        // loops through each of the listed customer emails and their designated top up details from the DataSource file
        for(int ctr = 1; ctr <= calculateRowCount(var.customerCurrency()); ctr++) {

            // Call the createCustomer method for the email
            createCustomer(var.getCustomerToGenerateCurrency(ctr));

            // Sign in using the email provided
            homePage.clickSignInButton();
            loginPage.loginAccount(var.getCustomerToGenerateCurrency(ctr), var.getDefaultPassword());
            global.selectCustomer();

            // Navigate account settings and go to 'Payment Preferences'.
            global.goToAcctSettings();
            if(accountSettingsPage.getSelectCurrencyDropDown().isEnabled()) {
                accountSettingsPage.clickChangePaymentPreferences();
                accountSettingsPage.selectCurrency(var.getCustomerCurrency(ctr));
            }
            global.nonAdminSignOut();
        }

        /* Generate customer that needs to be topped up */

        // loops through each of the listed customer emails and their designated top up details from the DataSource file
        for(int ctr = 1; ctr <= calculateRowCount(var.customerAccountsWithTopUp()); ctr++) {

            // Checks if the 'isDone' field indicates "No" to determine if the Top Up has been done before
            if(var.getIsDone(ctr).equals("No")){

                // Call the createCustomer method for the email
                createCustomer(var.getCustomerToGenerateWithTopUp(ctr));

                // Sign in using the email provided
                homePage.clickSignInButton();
                loginPage.loginAccount(var.getCustomerToGenerateWithTopUp(ctr), var.getDefaultPassword());
                global.selectCustomer();

                // Navigate account settings and go to 'Payment Preferences'.
                global.goToAcctSettings();
                accountSettingsPage.clickChangePaymentPreferences();

                // Select the currency based on the provided information
                if (!accountSettingsPage.getSelectCurrencyDropDown().isEnabled()) {
                    //If currency is already chosen, go through with default Top Up Values
                    // Navigate through top up page and assert that the redirected page is correct.
                    global.goToTopUp();
                    assertTrue(page.getCurrentUrl()
                            .contains(topUpPage.TOPUP_URL), var.getWrongUrlErrMsg());

                    // If the payment preferences has been updated before, then the default top ups would be used
                    switch (topUpPage.getTopUpCurrency().getText()) {
                        case "USD":
                            topUpPage.selectAmount(var.getTopUpAmountUSD(500));
                            break;
                        case "EUR":
                            topUpPage.selectAmount(var.getTopUpAmountEUR(500));
                            break;
                        case "JPY":
                            topUpPage.selectAmount(var.getTopUpAmountJPY(50));
                            break;
                        case "GBP":
                            topUpPage.selectAmount(var.getTopUpAmountGBP(500));
                            break;
                    }
                } else {
                    accountSettingsPage.selectCurrency(var.getCurrency(ctr));
                    assertTrue(accountSettingsPage.getChangeCurrencySuccessMsg()
                            .isDisplayed(), var.getElementIsNotDisplayedErrMsg());

                    // Navigate through top up page and assert that the redirected page is correct.
                    global.goToTopUp();
                    assertTrue(page.getCurrentUrl()
                            .contains(topUpPage.TOPUP_URL), var.getWrongUrlErrMsg());

                    // If the currency could still be chosen, the top up amount follows what's on the data provided
                    switch (var.getCurrency(ctr)) {
                        case "USD":
                            topUpPage.selectAmount(var.getTopUpAmountUSD(Integer.parseInt(var.getAmount(ctr))));
                            break;
                        case "EUR":
                            topUpPage.selectAmount(var.getTopUpAmountEUR(Integer.parseInt(var.getAmount(ctr))));
                            break;
                        case "JPY":
                            topUpPage.selectAmount(var.getTopUpAmountJPY(Integer.parseInt(var.getAmount(ctr))));
                            break;
                        case "GBP":
                            topUpPage.selectAmount(var.getTopUpAmountGBP(Integer.parseInt(var.getAmount(ctr))));
                            break;
                    }
                }
                // Pay the top up process using Stripe
                topUpPage.selectPaymentMode(topUpPage.chooseStripe());
                topUpPage.clickPay(false);
                topUpPage.processPayment(var.getCreditCard());
                wait.impWait(30);
                switcher.switchToDefaultContent();
                wait.impWait(10, topUpPage.getTopUpSuccessMsg());
                assertTrue(topUpPage.getTopUpSuccessMsg().isDisplayed(),
                        var.getTextNotEqualErrMsg());
                global.nonAdminSignOut();

                // Replace value to "Yes" in order to indicate that the top up process is done
                sdata.replaceValue("Yes", var.SHEETNAME_GENERATECUSTOMERWITHTOPUP, ctr, 3);
            }
            else{
                // Indicate that the top up process has not been continued since it is indicated that it
                // has already been done before and needs not to be done again
                Reporter.log("Skipped top up process for " + var.getCustomerToGenerateWithTopUp(ctr));
            }
        }
    }

    /**
     * createCustomer --- this method goes to the process of signing up for a customer
     * account given an email as input
     * */
    public void createCustomer(String email) throws IOException {
        pluginPage.passThisPage();
        homePage.clickSignInButton();
        loginPage.clickSignUpButton();
        signUpPage.tickCheckBoxAgree();

        // Fill out the form and submit
        signUpPage.fillOutSignUpForm(email, var.getDefaultPassword());

        // Check if the sign up process results to a 'already registered' account
        if(signUpPage.hasTakenError()){
            Reporter.log("Customer account " + email + " is already existing" );
            page.launchUrl(baseURL);
        }
        else{
            // Revert the "Yes" and "No" sheet inputs to "No" to indicate that database has been reset since
            // a customer account needs to be signed up again
            revertToDBResetDefaults();
            var = new Variables();

            // Go to Gmail account
            page.launchUrl(var.getGmailUrl());
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
            Reporter.log("Successfully created customer account: " + email );
        }
    }

    /**
     * generateTranslators --- this method generates translators based
     * on the data provided on the sheet; this also goes through the process of onboarding
     * precreated translator accounts
     * */
    public void generateTranslators() throws IOException, AWTException {
        /*Loop through the translators to be generated */
        for (int ctr = 1; ctr <= calculateRowCount(var.translatorAccounts()); ctr++) {
            pluginPage.passThisPage();
            homePage.clickSignInButton();
            loginPage.clickSignUpButton();
            signUpPage.clickDropDownButton();
            signUpPage.clickTranslatorOption();
            signUpPage.tickCheckBoxAgree();

            // Fill out the form and submit.
            signUpPage.fillOutSignUpForm(var.getTranslatorToGenerate(ctr), var.getDefaultPassword());
            ;
            if(signUpPage.hasTakenError()){
                // If account is already existing, the creation process is skipped
                Reporter.log("Translator account " + var.getTranslatorToGenerate(ctr) + " is already existing" );
                page.launchUrl(baseURL);
            }
            else {
                // The translator email account is verified
                // Go to Gmail account
                page.launchUrl(var.getGmailUrl());
                page.refresh();
                gmailInboxPage.clickActivateGengoEmail();
                assertTrue(gmailActivateGengoPage.getActivateAccountBtn().isDisplayed(),
                        var.getElementIsNotDisplayedErrMsg());

                // Activate the account
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

                // Translator information are added
                addTranslatorDetails();

                // Logout
                global.nonAdminSignOut();
                Reporter.log("Successfully created translator account: " + var.getTranslatorToGenerate(ctr));
            }
        }

        /* Loop through the existing translators to be onboarded */

        for (int ctr = 1; ctr <= calculateRowCount(var.preCreatedTranslators()); ctr++) {

            // Check the sheet if it indicates whether translator has been onboarded or not
            if(var.getIsOnboarded(ctr).equals("No")) {
                pluginPage.passThisPage();
                homePage.clickSignInButton();
                loginPage.loginAccount(var.getPreCreatedTranslator(ctr), var.getDefaultPassword());
                translatorOnboardPage.onboardTranslator();
                globalPage.goToTranslatorDashboardPage();
                translatorDashboardPage.clickJobsTab();

                // have the account accept the Translator Agreement Terms
                if (translatorJobsPage.isJobTermsAgreementVisible()) {
                    translatorJobsPage.getAgreeTermsCheck().click();
                    translatorJobsPage.getAgreeTermsContinueBtn().click();
                }

                // Logout
                global.nonAdminSignOut();
                Reporter.log("Successfully onboarded translator account: " + var.getPreCreatedTranslator(ctr));

                // Replace the entry indicating whether the translator has been onboarded or not, and change it to "Yes"
                sdata.replaceValue("Yes", var.SHEETNAME_PRECREATED_TRANSLATORS, ctr, 1);
            }
            else {
                // Indicate that the translator has not been onboarded since it is indicated that the ID has already been
                // translator has been onboarded and is reflected on the sheet
                Reporter.log("Skipped the onboarding for " + var.getPreCreatedTranslator(ctr));
            }
        }
    }

    /**
     * retrieveTranslatorIDs --- this method gets all of the translator account IDs
     * through the Admin page
     * */
    public void retrieveTranslatorIDs() throws IOException, AWTException {
        // loop through the list on the TranslatorID sheet
        for(int ctr = 1; ctr <= var.getTranslatorIDRows(); ctr++) {

            // Check the sheet if it indicates whether the ID has been retrieved or not
            if(var.getIsRetrieved(ctr).equals("No")) {

                // Retrieve ID by checking the hashmap containing the list of ID-Email pairings
                String ID = adminPage.getUserIDViaEmail(var.getTranslatorIDEmail(ctr));

                // Replace the ID entry across the email account on the sheet
                sdata.replaceValue(ID, var.SHEETNAME_TRANSLATORID, ctr, 0);

                // Replace the entry indicating whether the ID has been retrieved or not, and change it to "Yes"
                sdata.replaceValue("Yes", var.SHEETNAME_TRANSLATORID, ctr, 2);
            }
            else {
                // Indicate that the ID has not been retrieved since it is indicated that the ID has already been
                // retrieved and is reflected on the sheet
                Reporter.log("Skipped retrieval ID of " + var.getTranslatorIDEmail(ctr));
            }
            var = new Variables();
        }
    }

    /**
     * addQualifications --- this methods goes through the list of qualifications for translator accounts
     * and creates these qualifications via Admin account
     * */
    public void addQualifications() throws IOException, AWTException {

        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTestAdmin(), var.getDefaultPassword());

        // Assert that page title is present in the DOM.
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Select Admin user level.
        global.selectAdmin();

        // Assert that we are redirected to the right page.
        assertTrue(page.getCurrentUrl().contains("admin."),
                var.getWrongUrlErrMsg());

        // Hide the right panel.
        adminPage.hidePanel();

        //retrieve all the IDs listed on the Translator ID sheet
        retrieveTranslatorIDs();

        // Go through all of the qualifications listed
        for(int ctr = 1; ctr <= calculateRowCount(var.translatorQualifications()); ctr++) {
            // Check the sheet if it indicates that the qualification has been added or not
            if(var.getIsAdded(ctr).equals("No")) {

                // Go to New Qualifications page
                page.launchUrl(NEW_QUALIFICATION_URL);

                // Check if the email is not listed among the list of email-ID pairings
                if (var.getTranslatorID(var.getTranslatorEmail(ctr)) == null) {

                    // Retrieve ID of the translator
                    String ID = adminPage.getUserIDViaEmail(var.getTranslatorEmail(ctr));

                    String[] details = new String[] {
                            ID,
                            var.getTranslatorEmail(ctr),
                            "Yes"
                    };

                    // Add new entries to the sheet with the new email_ID pairing
                    sdata.writeValue(details, var.SHEETNAME_TRANSLATORID);

                    // Go back to New Qualifications Page
                    page.launchUrl(NEW_QUALIFICATION_URL);
                }
                // var is updated with the new changes in data content
                var = new Variables();
                // Input the qualification details (ID, Source Language, Target Language, and Rank)
                adminNewQualificationsPage.getTranslatorIdTextInput().sendKeys(var.getTranslatorID(var.getTranslatorEmail(ctr)));
                Select select = new Select(adminNewQualificationsPage.getSourceLanguageDropDown());
                select.selectByVisibleText(var.getSourceLanguage(ctr));
                select = new Select(adminNewQualificationsPage.getTargetLanguageDropDown());
                select.selectByVisibleText(var.getTargetLanguage(ctr));
                select = new Select(adminNewQualificationsPage.getRankDropDown());
                select.selectByVisibleText(var.getRank(ctr));

                // Submit New Qualifications Info
                adminNewQualificationsPage.getCreateBtn().click();

                // Check if a warning or error is visible
                if (adminNewQualificationsPage.isAlertVisible()) {

                    // Return to New Qualifications page if translator already has the qualification
                    page.launchUrl(NEW_QUALIFICATION_URL);
                }
                wait.impWait(5);

                // Change the marker for qualification addition to "Yes" to indicate that it has been done
                sdata.replaceValue("Yes", var.SHEETNAME_QUALIFICATIONS, ctr, 4);
            }
            else{
                // Log that addition of qualification is skipped because datasource file indicates that this has been done
                Reporter.log("Skipped Addition of Qualification since it has been indicated that this is already done.");
            }
        }
        global.adminSignOut(false);
        page.launchUrl(baseURL);
    }

    /**
     * translatorAgreeToTerms --- logs in newly created translators with qualifications
     * added and makes the account agree with the Translator Agreement on the Jobs Page
     * */
    public void translatorAgreeToTerms() throws IOException {
        // loop through the list of translator accounts
        for (int ctr = 1; ctr <= calculateRowCount(var.translatorAccounts()); ctr ++) {

            // check if the translator account has been onboarded or not
            if(var.getIsNewOnboarded(ctr).equals("No")){
                pluginPage.passThisPage();
                homePage.clickSignInButton();
                loginPage.loginAccount(var.getTranslatorToGenerate(ctr), var.getDefaultPassword());
                translatorDashboardPage.clickJobsTab();

                // Have the translator accept the Translator Agreement
                if(translatorJobsPage.isJobTermsAgreementVisible()) {
                    translatorJobsPage.getAgreeTermsCheck().click();
                    translatorJobsPage.getAgreeTermsContinueBtn().click();
                }

                // Logout
                global.nonAdminSignOut();

                // Change the marker for translator onboarding to "Yes" to indicate that it has been done
                sdata.replaceValue("Yes", var.SHEETNAME_GENERATETRANSLATOR, ctr,1);
                Reporter.log("Successfully onboarded translator account: " + var.getTranslatorToGenerate(ctr));
            }
            else {
                // Log that onboarding has been skipped because the datasource file indicates that this has been done
                Reporter.log("Skipped onboarding of translator account: " + var.getTranslatorToGenerate(ctr));
            }
        }
    }

    /**
     * addTranslatorDetails --- this method is used for adding Translator Details to
     * a newly created translator account
     * */
    public void addTranslatorDetails(){
        gmailWelcomeToGengoPage.clickTakeTest();
        assertTrue(page.getCurrentUrl().equals(translatorOnboardPage.TRANSLATOR_ONBOARD_URL),
                var.getWrongUrlErrMsg());
        translatorOnboardPage.clickContinue();

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
        translatorExperiencePage.clickContinue();

        translatorExperiencePage.selectTopics();
        translatorExperiencePage.selectFieldOfStudy();
        translatorExperiencePage.clickContinue();
    }

    /**
     * logInToGmail --- this method logs in the gmail account
     * for checking the emails received along the account
     * generation process
     * */
    public void logInToGmail() {
        // Go to Gmail account
        page.launchUrl(var.getGmailUrl());
        assertTrue(gmailSignInEmailPage.getTxtBoxEmail().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        gmailSignInEmailPage.inputEmail(var.getGmailEmail());
        gmailSignInPasswordPage.inputPasswordAndSubmit(var.getGmailPassword());
        wait.untilElementVisible(gmailInboxPage.getGmailComposeBtn());
    }

    /**
     * calculateRowCount --- calculates the row count based on multiple numbered
     * keys mapped; retrieves the highest number count indicated
     * on the keys
     * */
    private int calculateRowCount(HashMap<String, String> map) {
        int rowCount = 0, temp = 0;
        for (String key : map.keySet()) {
            temp = Integer.parseInt(key.replaceAll("[^x0-9]", ""));
            if (temp > rowCount)
                rowCount = temp;
        }
        return rowCount;
    }

    /**
     * revertToDBResetDefaults --- this method reverts all column entries
     * that has been marked as "Yes" to "No" when it is caught that the
     * database has been reset (marker for reset is when the first customer
     * account listed can't log in)
     * */
    private void revertToDBResetDefaults() throws IOException {
        for(int ctr = 1; ctr <= calculateRowCount(var.customerAccountsWithTopUp()); ctr++){
            sdata.replaceValue("No", var.SHEETNAME_GENERATECUSTOMERWITHTOPUP, ctr, 3);
        }
        for(int ctr = 1; ctr <= calculateRowCount(var.translatorQualifications()); ctr++){
            sdata.replaceValue("No", var.SHEETNAME_QUALIFICATIONS, ctr, 4);
        }
        for(int ctr = 1; ctr <= calculateRowCount(var.preCreatedTranslators()); ctr++){
            sdata.replaceValue("No", var.SHEETNAME_PRECREATED_TRANSLATORS, ctr, 1);
        }
        for(int ctr = 1; ctr <= calculateRowCount(var.translatorAccounts()); ctr++){
            sdata.replaceValue("No", var.SHEETNAME_GENERATETRANSLATOR, ctr, 1);
        }
        for(int ctr = 1; ctr <= var.getTranslatorIDRows(); ctr++){
            sdata.replaceValue("No", var.SHEETNAME_TRANSLATORID, ctr, 2);
        }

        // var is assigned with the updated Variables content
        var = new Variables();
    }

}
