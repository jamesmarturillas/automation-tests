package com.gengo.automation.fields;

import com.gengo.automation.helpers.CSVManager;
import com.gengo.automation.helpers.DataManager;

import java.io.IOException;
import java.util.HashMap;

/**
 * @class Callable fields are stored here.
 */
public class Variables {

    private CSVManager csvManager;
    private DataManager dataManager;
    public final String SHEETNAME_CUSTOMERS = "Customers";
    public final String SHEETNAME_TRANSLATORS = "Translators";
    public final String SHEETNAME_DELETED_TRANSLATORS = "DeletedTranslators";
    public final String SHEETNAME_PRECREATED_TRANSLATORS = "PreCreatedTranslators";
    public final String SHEETNAME_INPUTDATA = "InputData";
    public final String SHEETNAME_ERRORS = "ErrorMessages";
    public final String SHEETNAME_OTHERACCOUNTS = "OtherAccounts";
    public final String SHEETNAME_LANGUAGEPAIRS = "LanguagePairs";
    public final String SHEETNAME_TRANSLATIONDATA = "TranslationData";
    public final String SHEETNAME_ORDERLANGUAGES = "OrderLanguages";
    public final String SHEETNAME_CREDITCARD = "CreditCards";
    public final String SHEETNAME_EXCERPTS = "TranslationExcerpt";
    public final String SHEETNAME_TOPUPAMTS = "TopUpAmounts";
    public final String SHEETNAME_GENERATECUSTOMER = "CustomerGeneration";
    public final String SHEETNAME_GENERATECUSTOMERCURRENCY = "CustomerCurrency";
    public final String SHEETNAME_GENERATECUSTOMERWITHTOPUP = "CustomerGenerationWithTopUp";
    public final String SHEETNAME_GENERATETRANSLATOR = "TranslatorGeneration";
    public final String SHEETNAME_QUALIFICATIONS = "TranslatorQualifications";
    public final String SHEETNAME_TRANSLATORID = "TranslatorID";
    public final String DEFAULT_PASSWORD = "qwerty";
    public final String ALTERNATE_PASSWORD = "asdfgh";
    public final String GMAIL_URL = "https://gmail.com";
    public final String BASE_URL = "https://gengo:Gengo2017!@staging.gengo.com/";
    public final String LOGOUT = "staging.gengo.com/logout";

    private static final String FILE_PATH = System.getProperty("user.dir").replace("/", "//" ) + "//src//test//resources//files//";

    private HashMap<String, String> urls;
    private HashMap<String, String> accounts;
    private HashMap<String, String> inputData;
    private HashMap<String, String> language;
    private HashMap<String, String> languageFrom;
    private HashMap<String, String> languageTo;
    private HashMap<String, String> translationData;
    private HashMap<String, String> excerpt;
    private HashMap<String, String> topUpAmt;
    private HashMap<String, String> errorMsg;
    public HashMap<String, String> customerAccounts;
    public HashMap<String, String> customerCurrency;
    public HashMap<String, String> customerAccountsWithTopUp;
    public HashMap<String, String> translatorAccounts;
    public HashMap<String, String> translatorQualifications;
    public HashMap<String, String> translatorID;
    public HashMap<String, String> preCreatedTranslators;

    public Variables() throws IOException {
        this.csvManager = new CSVManager();
        this.dataManager = new DataManager();
        this.urls = new HashMap<>();
        this.accounts = new HashMap<>();
        this.inputData = new HashMap<>();
        this.language = new HashMap<>();
        this.languageFrom = new HashMap<>();
        this.languageTo = new HashMap<>();
        this.translationData = new HashMap<>();
        this.excerpt = new HashMap<>();
        this.topUpAmt = new HashMap<>();
        this.errorMsg = new HashMap<>();
        this.customerAccounts = new HashMap<>();
        this.customerCurrency = new HashMap<>();
        this.customerAccountsWithTopUp = new HashMap<>();
        this.translatorAccounts = new HashMap<>();
        this.translatorQualifications = new HashMap<>();
        this.translatorID = new HashMap<>();
        this.preCreatedTranslators = new HashMap<>();
    }

    /**
     * @return URL's
     */
    private HashMap<String, String> urlFields() {
        urls.put("gmailUrl", GMAIL_URL);
        urls.put("baseUrl", BASE_URL);
        urls.put("signOUt", LOGOUT);

        return urls;
    }

    public String getGmailUrl() {
        return urlFields().get("gmailUrl");
    }
    public String getBaseUrl() {
        return urlFields().get("baseUrl");
    }
    public String getSignOut() {
        return urlFields().get("signOut");
    }

    /**
     * @return Account details
     */
    private HashMap<String, String> accountsFields() {
        accounts.put("gmailEmail", csvManager.getCsvValue(SHEETNAME_OTHERACCOUNTS, 1, 0));
        accounts.put("gmailPassword", csvManager.getCsvValue(SHEETNAME_OTHERACCOUNTS, 1, 1));

        accounts.put("fbEmail1", csvManager.getCsvValue(SHEETNAME_OTHERACCOUNTS, 2, 0));
        accounts.put("fbPassword1", csvManager.getCsvValue(SHEETNAME_OTHERACCOUNTS, 2, 1));
        accounts.put("fbEmail2", csvManager.getCsvValue(SHEETNAME_OTHERACCOUNTS, 3, 0));
        accounts.put("fbPassword2", csvManager.getCsvValue(SHEETNAME_OTHERACCOUNTS, 3, 1));
        accounts.put("fbEmail3", csvManager.getCsvValue(SHEETNAME_OTHERACCOUNTS, 4, 0));
        accounts.put("fbPassword3", csvManager.getCsvValue(SHEETNAME_OTHERACCOUNTS, 4, 1));

        accounts.put("paypalEmail", csvManager.getCsvValue(SHEETNAME_OTHERACCOUNTS, 6, 0));
        accounts.put("paypalPassword", csvManager.getCsvValue(SHEETNAME_OTHERACCOUNTS, 6, 1));

        accounts.put("customer1", csvManager.getCsvValue(SHEETNAME_CUSTOMERS, 1, 0));
        accounts.put("customer4", csvManager.getCsvValue(SHEETNAME_CUSTOMERS, 4, 0));
        accounts.put("customer5", csvManager.getCsvValue(SHEETNAME_CUSTOMERS, 5, 0));
        accounts.put("customer6", csvManager.getCsvValue(SHEETNAME_CUSTOMERS, 6, 0));
        accounts.put("customer7", csvManager.getCsvValue(SHEETNAME_CUSTOMERS, 7, 0));
        accounts.put("customer8", csvManager.getCsvValue(SHEETNAME_CUSTOMERS, 8, 0));
        accounts.put("customer9", csvManager.getCsvValue(SHEETNAME_CUSTOMERS, 9, 0));
        accounts.put("customer10", csvManager.getCsvValue(SHEETNAME_CUSTOMERS, 10, 0));
        accounts.put("customer11", csvManager.getCsvValue(SHEETNAME_CUSTOMERS, 11, 0));
        accounts.put("customer12", csvManager.getCsvValue(SHEETNAME_CUSTOMERS, 12, 0));

        accounts.put("customer21", csvManager.getCsvValue(SHEETNAME_CUSTOMERS, 21, 0));
        accounts.put("customer25", csvManager.getCsvValue(SHEETNAME_CUSTOMERS, 25, 0));
        accounts.put("customer26", csvManager.getCsvValue(SHEETNAME_CUSTOMERS, 26, 0));
        accounts.put("customer27", csvManager.getCsvValue(SHEETNAME_CUSTOMERS, 27, 0));
        accounts.put("customer28", csvManager.getCsvValue(SHEETNAME_CUSTOMERS, 28, 0));
        accounts.put("customer29", csvManager.getCsvValue(SHEETNAME_CUSTOMERS, 29, 0));
        accounts.put("customer30", csvManager.getCsvValue(SHEETNAME_CUSTOMERS, 30, 0));
        accounts.put("customer102", csvManager.getCsvValue(SHEETNAME_CUSTOMERS, 103, 0));
        accounts.put("customer31", csvManager.getCsvValue(SHEETNAME_CUSTOMERS, 31, 0));
        accounts.put("customer32", csvManager.getCsvValue(SHEETNAME_CUSTOMERS, 32, 0));

        accounts.put("translator1", csvManager.getCsvValue(SHEETNAME_TRANSLATORS, 25, 0));

        accounts.put("translator26", csvManager.getCsvValue(SHEETNAME_TRANSLATORS, 26, 0));
        accounts.put("translator29", csvManager.getCsvValue(SHEETNAME_TRANSLATORS, 29, 0));

        accounts.put("translatorDeleted1", csvManager.getCsvValue(SHEETNAME_DELETED_TRANSLATORS, 1, 0));

        accounts.put("translatorStandard1", csvManager.getCsvValue(SHEETNAME_PRECREATED_TRANSLATORS, 1, 0));
        accounts.put("translatorStandard2", csvManager.getCsvValue(SHEETNAME_PRECREATED_TRANSLATORS, 2, 0));
        accounts.put("translatorStandard3", csvManager.getCsvValue(SHEETNAME_PRECREATED_TRANSLATORS, 3, 0));
        accounts.put("translatorStandard4", csvManager.getCsvValue(SHEETNAME_PRECREATED_TRANSLATORS, 4, 0));
        accounts.put("translatorStandard5", csvManager.getCsvValue(SHEETNAME_PRECREATED_TRANSLATORS, 5, 0));

        accounts.put("translatorPro1", csvManager.getCsvValue(SHEETNAME_PRECREATED_TRANSLATORS, 6, 0));
        accounts.put("translatorPro2", csvManager.getCsvValue(SHEETNAME_PRECREATED_TRANSLATORS, 7, 0));
        accounts.put("translatorPro3", csvManager.getCsvValue(SHEETNAME_PRECREATED_TRANSLATORS, 8, 0));
        accounts.put("translatorPro4", csvManager.getCsvValue(SHEETNAME_PRECREATED_TRANSLATORS, 9, 0));
        accounts.put("translatorPro5", csvManager.getCsvValue(SHEETNAME_PRECREATED_TRANSLATORS, 10, 0));

        accounts.put("testAdmin", csvManager.getCsvValue(SHEETNAME_OTHERACCOUNTS, 5, 0));
        accounts.put("testEmployee", csvManager.getCsvValue(SHEETNAME_OTHERACCOUNTS, 8, 0));
        accounts.put("testContractor", csvManager.getCsvValue(SHEETNAME_OTHERACCOUNTS, 7, 0));

        accounts.put("defaultPassword", DEFAULT_PASSWORD);
        accounts.put("alternatePassword", ALTERNATE_PASSWORD);

        return accounts;
    }

    public String getGmailEmail() {
        return accountsFields().get("gmailEmail");
    }
    public String getGmailPassword() {
        return accountsFields().get("gmailPassword");
    }
    public String getFbEmail(int number) {
        return accountsFields().get("fbEmail" + number);
    }
    public String getFbPassword(int number) {
        return accountsFields().get("fbPassword" + number);
    }
    public String getCustomer(int number) {
        return accountsFields().get("customer" + number);
    }
    public String getTranslator(int number) {
        return accountsFields().get("translator" + number);
    }
    public String getTranslatorDeleted(int number) {
        return accountsFields().get("translatorDeleted" + number);
    }
    public String getTranslatorStandard(int number) {
        return accountsFields().get("translatorStandard" + number);
    }
    public String getTranslatorPro(int number) {
        return accountsFields().get("translatorPro" + number);
    }
    public String getTestAdmin() {
        return accountsFields().get("testAdmin");
    }
    public String getTestEmployee() {
        return accountsFields().get("testEmployee");
    }
    public String getTestContractor() {
        return accountsFields().get("testContractor");
    }
    public String getPayPalEmail() {
        return accountsFields().get("paypalEmail");
    }
    public String getPayPalPassword() {
        return accountsFields().get("paypalPassword");
    }
    public String getDefaultPassword() {
        return accountsFields().get("defaultPassword");
    }
    public String getAlternatePassword() {
        return accountsFields().get("alternatePassword");
    }

    /**
     * @return Input csvManager
     */
    private HashMap<String, String> inputDataFields() {
        inputData.put("fullName", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 1, 1));
        inputData.put("firstName", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 2, 1));
        inputData.put("lastName", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 3, 1));
        inputData.put("address1", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 4, 1));
        inputData.put("address2", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 5, 1));
        inputData.put("town", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 6, 1));
        inputData.put("state", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 7, 1));
        inputData.put("zipCode", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 8, 1));

        inputData.put("customerComment", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 9, 1));
        inputData.put("translatorComment", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 10, 1));
        inputData.put("customerComment1", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 11, 1));
        inputData.put("customerComment2", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 12, 1));
        inputData.put("customerComment3", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 13, 1));
        inputData.put("customerComment4", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 14, 1));
        inputData.put("customerComment5", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 15, 1));
        inputData.put("translatorComment1", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 16, 1));
        inputData.put("translatorComment2", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 17, 1));
        inputData.put("translatorComment3", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 18, 1));
        inputData.put("translatorComment4", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 19, 1));
        inputData.put("translatorComment5", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 20, 1));
        inputData.put("translatorComment6", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 21, 1));
        inputData.put("commentWithHtmlAndBracket", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 26, 1));
        inputData.put("characterComment", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 27, 1));

        inputData.put("customerCorrection", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 22, 1));
        inputData.put("translatorFlag", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 23, 1));
        inputData.put("instruction", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 24, 1));
        inputData.put("checkerFeedback", csvManager.getCsvValue(SHEETNAME_INPUTDATA, 25, 1));

        inputData.put("creditCard", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 1, 1));
        inputData.put("creditCardVisa1", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 2, 1));
        inputData.put("creditCardVisa2", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 3, 1));
        inputData.put("debitCardVisa1", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 4, 1));
        inputData.put("creditCardMaster1", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 5, 1));
        inputData.put("debitCardMaster1", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 6, 1));
        inputData.put("prepaidCardMaster1", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 7, 1));
        inputData.put("expiryDate", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 8, 1));
        inputData.put("cvcNumber", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 9, 1));
        inputData.put("invalidCvcNumber", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 10, 1));
        inputData.put("incorrectCvcNumber", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 11, 1));
        inputData.put("creditCardZipCode", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 12, 1));
        inputData.put("creditCard1", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 13, 1));
        inputData.put("creditCard2", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 14, 1));
        inputData.put("creditCard3", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 15, 1));
        inputData.put("creditCard4", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 16, 1));
        inputData.put("creditCard5", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 17, 1));
        inputData.put("creditCard6", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 18, 1));
        inputData.put("creditCard7", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 19, 1));
        inputData.put("creditCard8", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 20, 1));
        inputData.put("creditCard9", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 21, 1));
        inputData.put("creditCard10", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 22, 1));
        inputData.put("creditCard11", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 23, 1));
        inputData.put("creditCard12", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 24, 1));
        inputData.put("creditCard13", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 25, 1));
        inputData.put("creditCard14", csvManager.getCsvValue(SHEETNAME_CREDITCARD, 26, 1));

        inputData.put("DE", "Deutsch");
        inputData.put("ES", "Español");
        inputData.put("ZH", "中文");
        inputData.put("JA", "日本語");
        inputData.put("EN", "English");

        inputData.put("AVAILABLE", "Available");
        inputData.put("SUBMITTED", "Submitted");
        inputData.put("NOT_SUBMITTED", "Not Submitted");
        inputData.put("REVISING", "Revising");
        inputData.put("ON_HOLD", "On Hold");

        return inputData;
    }

    public String getFullName() {
        return inputDataFields().get("fullName");
    }
    public String getFirstName() {
        return inputDataFields().get("firstName");
    }
    public String getLastName() {
        return inputDataFields().get("lastName");
    }
    public String getAddress1() {
        return inputDataFields().get("address1");
    }
    public String getAddress2() {
        return inputDataFields().get("address2");
    }
    public String getTown() {
        return inputDataFields().get("town");
    }
    public String getState() {
        return inputDataFields().get("state");
    }
    public String getZipCode() {
        return inputDataFields().get("zipCode");
    }
    public String getCreditCard() {
        return inputDataFields().get("creditCard");
    }
    public String getCreditCardVisa(int number) {
        return inputDataFields().get("creditCardVisa" + number);
    }
    public String getDebitCardVisa1() {
        return inputDataFields().get("debitCardVisa1");
    }
    public String getCreditCardMaster1() {
        return inputDataFields().get("creditCardMaster1");
    }
    public String getDebitCardMaster1() {
        return inputDataFields().get("debitCardMaster1");
    }
    public String getPrepaidCardMaster1() {
        return inputDataFields().get("prepaidCardMaster1");
    }
    public String getExpiryDate() {
        return inputDataFields().get("expiryDate");
    }
    public String getCvcNumber() {
        return inputDataFields().get("cvcNumber");
    }
    public String getInvalidCvcNumber() {
        return inputDataFields().get("invalidCvcNumber");
    }
    public String getIncorrectCvcNumber() {
        return inputDataFields().get("incorrectCvcNumber");
    }
    public String getCreditCardZipCode() {
        return inputDataFields().get("creditCardZipCode");
    }
    public String getCreditCard(int number) {
        return inputDataFields().get("creditCard" + number);
    }
    public String getCustomerComment() {
        return inputDataFields().get("customerComment");
    }
    public String getCustomerCommentNo(int number) {
        return inputDataFields().get("customerComment" + number);
    }
    public String getTranslatorComment() {
        return inputDataFields().get("translatorComment");
    }
    public String getTranslatorCommentNo(int number) {
        return inputDataFields().get("translatorComment" + number);
    }
    public String getTranslatorFlagComment() { return inputDataFields().get("translatorFlag"); }
    public String getCustomerCorrection() { return inputDataFields().get("customerCorrection"); }
    public String getCustomerInstruction() { return inputDataFields().get("instruction"); }
    public String getCheckerFeedback() { return inputDataFields().get("checkerFeedback"); }
    public String getLangDE() {
        return inputDataFields().get("DE");
    }
    public String getLangES() {
        return inputDataFields().get("ES");
    }
    public String getLangZH() {
        return inputDataFields().get("ZH");
    }
    public String getLangJA() {
        return inputDataFields().get("JA");
    }
    public String getLangEN() {
        return inputDataFields().get("EN");
    }
    public String getAvailableJobStatus() {
        return inputDataFields().get("AVAILABLE");
    }
    public String getSubmittedJobStatus() {
        return inputDataFields().get("SUBMITTED");
    }
    public String getNotSubmittedJobStatus() {
        return inputDataFields().get("NOT_SUBMITTED");
    }
    public String getRevisingJobStatus() {
        return inputDataFields().get("REVISING");
    }
    public String getOnHoldJobStatus() {
        return inputDataFields().get("ON_HOLD");
    }
    public String getCommentWithHtmlAndBracket(boolean isTranslator) {
        if (isTranslator) {
            return "from translator : " + inputDataFields().get("commentWithHtmlAndBracket");
        }
        return "from customer : " + inputDataFields().get("commentWithHtmlAndBracket");
    }
    public String getCharacterComment() {
        return inputDataFields().get("characterComment");
    }

    /**
     * @return Top up amounts
     */
    private HashMap<String, String> topUpAmts() {
        // USD amounts
        topUpAmt.put("amountUSD5", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 1, 0));
        topUpAmt.put("amountUSD10", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 2, 0));
        topUpAmt.put("amountUSD25", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 3, 0));
        topUpAmt.put("amountUSD50", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 4, 0));
        topUpAmt.put("amountUSD100", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 5, 0));
        topUpAmt.put("amountUSD250", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 6, 0));
        topUpAmt.put("amountUSD500", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 7, 0));
        topUpAmt.put("amountUSD1000", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 8, 0));
        topUpAmt.put("amountUSD2000", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 9, 0));
        topUpAmt.put("amountUSD3500", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 10, 0));
        topUpAmt.put("amountUSD4000", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 11, 0));
        topUpAmt.put("amountUSD8000", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 12, 0));

        // EUR amounts
        topUpAmt.put("amountEUR5", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 1, 1));
        topUpAmt.put("amountEUR10", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 2, 1));
        topUpAmt.put("amountEUR25", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 3, 1));
        topUpAmt.put("amountEUR50", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 4, 1));
        topUpAmt.put("amountEUR100", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 5, 1));
        topUpAmt.put("amountEUR250", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 6, 1));
        topUpAmt.put("amountEUR500", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 7, 1));
        topUpAmt.put("amountEUR1000", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 8, 1));
        topUpAmt.put("amountEUR2000", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 9, 1));
        topUpAmt.put("amountEUR4000", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 10, 1));
        topUpAmt.put("amountEUR8000", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 11, 1));

        // JPY amounts
        topUpAmt.put("amountJPY500", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 1, 2));
        topUpAmt.put("amountJPY1000", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 2, 2));
        topUpAmt.put("amountJPY2500", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 3, 2));
        topUpAmt.put("amountJPY5000", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 4, 2));
        topUpAmt.put("amountJPY10000", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 5, 2));
        topUpAmt.put("amountJPY25000", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 6, 2));
        topUpAmt.put("amountJPY50000", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 7, 2));
        topUpAmt.put("amountJPY100000", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 8, 2));
        topUpAmt.put("amountJPY200000", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 9, 2));
        topUpAmt.put("amountJPY400000", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 10, 2));
        topUpAmt.put("amountJPY800000", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 11, 2));

        // GBP amounts
        topUpAmt.put("amountGBP5", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 1, 3));
        topUpAmt.put("amountGBP10", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 2, 3));
        topUpAmt.put("amountGBP25", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 3, 3));
        topUpAmt.put("amountGBP50", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 4, 3));
        topUpAmt.put("amountGBP100", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 5, 3));
        topUpAmt.put("amountGBP250", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 6, 3));
        topUpAmt.put("amountGBP500", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 7, 3));
        topUpAmt.put("amountGBP1000", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 8, 3));
        topUpAmt.put("amountGBP2000", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 9, 3));
        topUpAmt.put("amountGBP4000", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 10, 3));
        topUpAmt.put("amountGBP8000", csvManager.getCsvValue(SHEETNAME_TOPUPAMTS, 11, 3));

        return topUpAmt;
    }

    public String getTopUpAmountUSD(int amount) {
        return topUpAmts().get("amountUSD" + amount);
    }
    public String getTopUpAmountEUR(int amount) {
        return topUpAmts().get("amountEUR" + amount);
    }
    public String getTopUpAmountJPY(int amount) {
        return topUpAmts().get("amountJPY" + amount + "00");
    }
    public String getTopUpAmountGBP(int amount) {
        return topUpAmts().get("amountGBP" + amount);
    }

    /**
     * @return Translation csvManager
     */
    private HashMap<String, String> translationsData() throws IOException {
        // Item to translate collections.
        translationData.put("itemToTranslate1", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 1, 0));
        translationData.put("itemToTranslate2", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 2, 0));
        translationData.put("itemToTranslate3", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 3, 0));
        translationData.put("itemToTranslate4", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 4, 0));
        translationData.put("itemToTranslate5", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 5, 0));
        translationData.put("itemToTranslate6", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 6, 0));
        translationData.put("itemToTranslate7", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 7, 0));
        translationData.put("itemToTranslate8", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 8, 0));
        translationData.put("itemToTranslate9", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 9, 0));
        translationData.put("itemToTranslate10", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 10, 0));
        translationData.put("itemToTranslate11", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 11, 0));
        translationData.put("itemToTranslate12", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 12, 0));
        translationData.put("itemToTranslate13", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 13, 0));
        translationData.put("itemToTranslate14", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 14, 0));
        translationData.put("itemToTranslate15", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 15, 0));
        translationData.put("itemToTranslate16", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 16, 0));
        translationData.put("itemToTranslate17", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 17, 0));
        translationData.put("itemToTranslate18", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 18, 0));
        translationData.put("itemToTranslate19", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 19, 0));
        translationData.put("itemToTranslate20", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 20, 0));
        translationData.put("itemToTranslate21", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 21, 0));
        translationData.put("itemToTranslate22", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 22, 0));
        translationData.put("itemToTranslate23", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 23, 0));
        translationData.put("itemToTranslate24", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 24, 0));
        translationData.put("itemToTranslate25", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 25, 0));
        translationData.put("itemToTranslate26", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 26, 0));
        translationData.put("itemToTranslate27", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 27, 0));
        translationData.put("itemToTranslate28", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 28, 0));
        translationData.put("itemToTranslate29", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 29, 0));
        translationData.put("itemToTranslate30", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 30, 0));
        translationData.put("itemToTranslate31", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 31, 0));
        translationData.put("itemToTranslate32", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 32, 0));
        translationData.put("itemToTranslate33", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 33, 0));
        translationData.put("itemToTranslate34", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 34, 0));
        translationData.put("itemToTranslate35", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 35, 0));
        translationData.put("itemToTranslate36", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 36, 0));
        translationData.put("itemToTranslate37", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 37, 0));
        translationData.put("itemToTranslate38", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 38, 0));
        translationData.put("itemToTranslate39", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 39, 0));
        translationData.put("itemToTranslate40", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 40, 0));
        translationData.put("itemToTranslate41", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 41, 0));
        translationData.put("itemToTranslate42", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 42, 0));
        translationData.put("itemToTranslate43", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 43, 0));
        translationData.put("itemToTranslate44", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 44, 0));
        translationData.put("itemToTranslate45", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 45, 0));
        translationData.put("itemToTranslate46", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 46, 0));
        translationData.put("itemToTranslate47", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 47, 0));
        translationData.put("itemToTranslate48", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 48, 0));
        translationData.put("itemToTranslate49", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 49, 0));
        translationData.put("itemToTranslate50", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 50, 0));
        translationData.put("itemToTranslate51", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 51, 0));
        translationData.put("itemToTranslate52", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 52, 0));
        translationData.put("itemToTranslate53", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 53, 0));
        translationData.put("itemToTranslate54", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 54, 0));
        translationData.put("itemToTranslate55", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 55, 0));
        translationData.put("itemToTranslate56", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 56, 0));
        translationData.put("itemToTranslate57", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 57, 0));
        translationData.put("itemToTranslate58", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 58, 0));
        translationData.put("itemToTranslate59", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 59, 0));
        translationData.put("itemToTranslate60", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 60, 0));
        translationData.put("itemToTranslate61", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 61, 0));
        translationData.put("itemToTranslate62", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 62, 0));
        translationData.put("itemToTranslate63", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 63, 0));
        translationData.put("itemToTranslate64", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 64, 0));
        translationData.put("itemToTranslate65", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 65, 0));
        translationData.put("itemToTranslate66", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 66, 0));
        translationData.put("itemToTranslate67", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 67, 0));
        translationData.put("itemToTranslate68", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 68, 0));
        translationData.put("itemToTranslate69", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 69, 0));
        translationData.put("itemToTranslate70", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 70, 0));
        translationData.put("itemToTranslate71", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 71, 0));
        translationData.put("itemToTranslate72", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 72, 0));
        translationData.put("itemToTranslate73", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 73, 0));
        translationData.put("itemToTranslate74", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 74, 0));

        translationData.put("file1", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 1, 4));
        translationData.put("file9", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 9, 4));
        translationData.put("file13", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 13, 4));
        translationData.put("file57", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 57, 4));
        translationData.put("file58", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 58, 4));
        translationData.put("file72", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 72, 4));
        translationData.put("file73", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 73, 4));
        translationData.put("file78", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 78, 4));
        translationData.put("file79", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 79, 4));
        translationData.put("file80", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 80, 4));
        translationData.put("file81", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 81, 4));
        translationData.put("file82", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 82, 4));
        translationData.put("file83", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 83, 4));
        translationData.put("file84", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 84, 4));
        translationData.put("file85", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 85, 4));
        translationData.put("file86", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 86, 4));
        translationData.put("file87", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 87, 4));
        translationData.put("file88", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 88, 4));
        translationData.put("file89", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 89, 4));
        translationData.put("file90", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 90, 4));
        translationData.put("file91", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 91, 4));
        translationData.put("file92", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 92, 4));
        translationData.put("file93", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 93, 4));
        translationData.put("file94", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 94, 4));
        translationData.put("file95", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 95, 4));
        translationData.put("file96", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 96, 4));

        // Unit count per respective item to translate. (value is just parallel to it's row)
        translationData.put("unitCountSource1", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 1, 1));
        translationData.put("unitCountSource2", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 2, 1));
        translationData.put("unitCountSource3", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 3, 1));
        translationData.put("unitCountSource4", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 4, 1));
        translationData.put("unitCountSource5", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 5, 1));
        translationData.put("unitCountSource6", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 6, 1));
        translationData.put("unitCountSource7", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 7, 1));
        translationData.put("unitCountSource8", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 8, 1));
        translationData.put("unitCountSource9", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 9, 1));
        translationData.put("unitCountSource10", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 10, 1));
        translationData.put("unitCountSource11", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 11, 1));
        translationData.put("unitCountSource12", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 12, 1));
        translationData.put("unitCountSource13", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 13, 1));
        translationData.put("unitCountSource14", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 14, 1));
        translationData.put("unitCountSource15", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 15, 1));
        translationData.put("unitCountSource16", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 16, 1));
        translationData.put("unitCountSource17", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 17, 1));
        translationData.put("unitCountSource18", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 18, 1));
        translationData.put("unitCountSource19", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 19, 1));
        translationData.put("unitCountSource20", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 20, 1));
        translationData.put("unitCountSource21", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 21, 1));
        translationData.put("unitCountSource22", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 22, 1));
        translationData.put("unitCountSource23", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 23, 1));
        translationData.put("unitCountSource24", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 24, 1));
        translationData.put("unitCountSource25", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 25, 1));
        translationData.put("unitCountSource26", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 26, 1));
        translationData.put("unitCountSource27", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 27, 1));
        translationData.put("unitCountSource28", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 28, 1));
        translationData.put("unitCountSource29", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 29, 1));
        translationData.put("unitCountSource30", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 30, 1));
        translationData.put("unitCountSource31", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 31, 1));
        translationData.put("unitCountSource32", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 32, 1));
        translationData.put("unitCountSource33", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 33, 1));
        translationData.put("unitCountSource34", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 34, 1));
        translationData.put("unitCountSource35", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 35, 1));
        translationData.put("unitCountSource36", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 36, 1));
        translationData.put("unitCountSource37", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 37, 1));
        translationData.put("unitCountSource38", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 38, 1));
        translationData.put("unitCountSource39", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 39, 1));
        translationData.put("unitCountSource40", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 40, 1));
        translationData.put("unitCountSource41", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 41, 1));
        translationData.put("unitCountSource42", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 42, 1));
        translationData.put("unitCountSource43", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 43, 1));
        translationData.put("unitCountSource44", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 44, 1));
        translationData.put("unitCountSource45", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 45, 1));
        translationData.put("unitCountSource46", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 46, 1));
        translationData.put("unitCountSource47", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 47, 1));
        translationData.put("unitCountSource48", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 48, 1));
        translationData.put("unitCountSource49", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 49, 1));
        translationData.put("unitCountSource50", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 50, 1));
        translationData.put("unitCountSource51", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 51, 1));
        translationData.put("unitCountSource52", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 52, 1));
        translationData.put("unitCountSource53", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 53, 1));
        translationData.put("unitCountSource54", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 54, 1));
        translationData.put("unitCountSource55", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 55, 1));
        translationData.put("unitCountSource56", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 56, 1));
        translationData.put("unitCountSource57", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 57, 1));
        translationData.put("unitCountSource58", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 58, 1));
        translationData.put("unitCountSource59", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 59, 1));
        translationData.put("unitCountSource60", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 60, 1));
        translationData.put("unitCountSource61", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 61, 1));
        translationData.put("unitCountSource62", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 62, 1));
        translationData.put("unitCountSource63", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 63, 1));
        translationData.put("unitCountSource64", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 64, 1));
        translationData.put("unitCountSource65", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 65, 1));
        translationData.put("unitCountSource66", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 66, 1));
        translationData.put("unitCountSource67", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 67, 1));
        translationData.put("unitCountSource68", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 68, 1));
        translationData.put("unitCountSource69", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 69, 1));
        translationData.put("unitCountSource70", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 70, 1));
        translationData.put("unitCountSource71", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 71, 1));
        translationData.put("unitCountSource72", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 72, 1));
        translationData.put("unitCountSource73", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 73, 1));
        translationData.put("unitCountSource74", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 74, 1));

        // Translated item collections.
        translationData.put("translatedItem1", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 1, 2));
        translationData.put("translatedItem2", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 2, 2));
        translationData.put("translatedItem3", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 3, 2));
        translationData.put("translatedItem4", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 4, 2));
        translationData.put("translatedItem5", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 5, 2));
        translationData.put("translatedItem6", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 6, 2));
        translationData.put("translatedItem7", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 7, 2));
        translationData.put("translatedItem8", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 8, 2));
        translationData.put("translatedItem9", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 9, 2));
        translationData.put("translatedItem10", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 10, 2));
        translationData.put("translatedItem11", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 11, 2));
        translationData.put("translatedItem12", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 12, 2));
        translationData.put("translatedItem13", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 13, 2));
        translationData.put("translatedItem14", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 14, 2));
        translationData.put("translatedItem15", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 15, 2));
        translationData.put("translatedItem16", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 16, 2));
        translationData.put("translatedItem17", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 17, 2));
        translationData.put("translatedItem18", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 18, 2));
        translationData.put("translatedItem19", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 19, 2));
        translationData.put("translatedItem20", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 20, 2));
        translationData.put("translatedItem21", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 21, 2));
        translationData.put("translatedItem22", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 22, 2));
        translationData.put("translatedItem23", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 23, 2));
        translationData.put("translatedItem24", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 24, 2));
        translationData.put("translatedItem25", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 25, 2));
        translationData.put("translatedItem26", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 26, 2));
        translationData.put("translatedItem27", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 27, 2));
        translationData.put("translatedItem28", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 28, 2));
        translationData.put("translatedItem29", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 29, 2));
        translationData.put("translatedItem30", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 30, 2));
        translationData.put("translatedItem31", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 31, 2));
        translationData.put("translatedItem32", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 32, 2));
        translationData.put("translatedItem33", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 33, 2));
        translationData.put("translatedItem34", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 34, 2));
        translationData.put("translatedItem35", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 35, 2));
        translationData.put("translatedItem36", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 36, 2));
        translationData.put("translatedItem37", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 37, 2));
        translationData.put("translatedItem38", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 38, 2));
        translationData.put("translatedItem39", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 39, 2));
        translationData.put("translatedItem40", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 40, 2));
        translationData.put("translatedItem41", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 41, 2));
        translationData.put("translatedItem42", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 42, 2));
        translationData.put("translatedItem43", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 43, 2));
        translationData.put("translatedItem44", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 44, 2));
        translationData.put("translatedItem45", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 45, 2));
        translationData.put("translatedItem46", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 46, 2));
        translationData.put("translatedItem47", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 47, 2));
        translationData.put("translatedItem48", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 48, 2));
        translationData.put("translatedItem49", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 49, 2));
        translationData.put("translatedItem50", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 50, 2));
        translationData.put("translatedItem51", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 51, 2));
        translationData.put("translatedItem52", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 52, 2));
        translationData.put("translatedItem53", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 53, 2));
        translationData.put("translatedItem54", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 54, 2));
        translationData.put("translatedItem55", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 55, 2));
        translationData.put("translatedItem56", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 56, 2));
        translationData.put("translatedItem57", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 57, 2));
        translationData.put("translatedItem58", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 58, 2));
        translationData.put("translatedItem59", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 59, 2));
        translationData.put("translatedItem60", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 60, 2));
        translationData.put("translatedItem61", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 61, 2));
        translationData.put("translatedItem62", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 62, 2));
        translationData.put("translatedItem63", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 63, 2));
        translationData.put("translatedItem64", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 64, 2));
        translationData.put("translatedItem65", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 65, 2));
        translationData.put("translatedItem66", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 66, 2));
        translationData.put("translatedItem67", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 67, 2));
        translationData.put("translatedItem68", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 68, 2));
        translationData.put("translatedItem69", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 69, 2));
        translationData.put("translatedItem70", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 70, 2));
        translationData.put("translatedItem71", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 71, 2));
        translationData.put("translatedItem72", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 72, 2));
        translationData.put("translatedItem73", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 73, 2));
        translationData.put("translatedItem74", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 74, 2));

        // Unit count per respective translated item. (value is just parallel to it's row)
        translationData.put("unitCountTarget1", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 1, 3));
        translationData.put("unitCountTarget2", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 2, 3));
        translationData.put("unitCountTarget3", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 3, 3));
        translationData.put("unitCountTarget4", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 4, 3));
        translationData.put("unitCountTarget5", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 5, 3));
        translationData.put("unitCountTarget6", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 6, 3));
        translationData.put("unitCountTarget7", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 7, 3));
        translationData.put("unitCountTarget8", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 8, 3));
        translationData.put("unitCountTarget9", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 9, 3));
        translationData.put("unitCountTarget10", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 10, 3));
        translationData.put("unitCountTarget11", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 11, 3));
        translationData.put("unitCountTarget12", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 12, 3));
        translationData.put("unitCountTarget13", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 13, 3));
        translationData.put("unitCountTarget14", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 14, 3));
        translationData.put("unitCountTarget15", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 15, 3));
        translationData.put("unitCountTarget16", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 16, 3));
        translationData.put("unitCountTarget17", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 17, 3));
        translationData.put("unitCountTarget18", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 18, 3));
        translationData.put("unitCountTarget19", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 19, 3));
        translationData.put("unitCountTarget20", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 20, 3));
        translationData.put("unitCountTarget21", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 21, 3));
        translationData.put("unitCountTarget22", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 22, 3));
        translationData.put("unitCountTarget23", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 23, 3));
        translationData.put("unitCountTarget24", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 24, 3));
        translationData.put("unitCountTarget25", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 25, 3));
        translationData.put("unitCountTarget26", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 26, 3));
        translationData.put("unitCountTarget27", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 27, 3));
        translationData.put("unitCountTarget28", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 28, 3));
        translationData.put("unitCountTarget29", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 29, 3));
        translationData.put("unitCountTarget30", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 30, 3));
        translationData.put("unitCountTarget31", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 31, 3));
        translationData.put("unitCountTarget32", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 32, 3));
        translationData.put("unitCountTarget33", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 33, 3));
        translationData.put("unitCountTarget34", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 34, 3));
        translationData.put("unitCountTarget35", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 35, 3));
        translationData.put("unitCountTarget36", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 36, 3));
        translationData.put("unitCountTarget37", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 37, 3));
        translationData.put("unitCountTarget38", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 38, 3));
        translationData.put("unitCountTarget39", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 39, 3));
        translationData.put("unitCountTarget40", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 40, 3));
        translationData.put("unitCountTarget41", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 41, 3));
        translationData.put("unitCountTarget42", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 42, 3));
        translationData.put("unitCountTarget43", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 43, 3));
        translationData.put("unitCountTarget44", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 44, 3));
        translationData.put("unitCountTarget45", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 45, 3));
        translationData.put("unitCountTarget46", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 46, 3));
        translationData.put("unitCountTarget47", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 47, 3));
        translationData.put("unitCountTarget48", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 48, 3));
        translationData.put("unitCountTarget49", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 49, 3));
        translationData.put("unitCountTarget50", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 50, 3));
        translationData.put("unitCountTarget51", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 51, 3));
        translationData.put("unitCountTarget52", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 52, 3));
        translationData.put("unitCountTarget53", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 53, 3));
        translationData.put("unitCountTarget54", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 54, 3));
        translationData.put("unitCountTarget55", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 55, 3));
        translationData.put("unitCountTarget56", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 56, 3));
        translationData.put("unitCountTarget57", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 57, 3));
        translationData.put("unitCountTarget58", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 58, 3));
        translationData.put("unitCountTarget59", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 59, 3));
        translationData.put("unitCountTarget60", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 60, 3));
        translationData.put("unitCountTarget61", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 61, 3));
        translationData.put("unitCountTarget62", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 62, 3));
        translationData.put("unitCountTarget63", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 63, 3));
        translationData.put("unitCountTarget64", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 64, 3));
        translationData.put("unitCountTarget65", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 65, 3));
        translationData.put("unitCountTarget66", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 66, 3));
        translationData.put("unitCountTarget67", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 67, 3));
        translationData.put("unitCountTarget68", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 68, 3));
        translationData.put("unitCountTarget69", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 69, 3));
        translationData.put("unitCountTarget70", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 70, 3));
        translationData.put("unitCountTarget71", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 71, 3));
        translationData.put("unitCountTarget72", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 72, 3));
        translationData.put("unitCountTarget73", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 71, 3));
        translationData.put("unitCountTarget74", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 72, 3));

        translationData.put("file9", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 9, 4));
        translationData.put("file13", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 13, 4));
        translationData.put("file58", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 58, 4));
        translationData.put("file72", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 72, 4));
        translationData.put("file73", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 73, 4));
        translationData.put("file78", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 78, 4));
        translationData.put("file79", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 79, 4));
        translationData.put("file80", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 80, 4));
        translationData.put("file81", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 81, 4));
        translationData.put("file82", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 82, 4));
        translationData.put("file83", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 83, 4));

        translationData.put("unitCountFileSource9", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 9, 5));
        translationData.put("unitCountFileSource13", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 13, 5));
        translationData.put("unitCountFileSource58", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 58, 5));
        translationData.put("unitCountFileSource94", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 94, 5));
        translationData.put("unitCountFileSource90", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 90, 5));

        // Glossary
        translationData.put("enToJa", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 21, 4));
        translationData.put("enToJaBracketed", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 74, 4));
        translationData.put("enToTlBracketed", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 75, 4));
        translationData.put("jaToEnBracketed", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 76, 4));
        translationData.put("jaToZhBracketed", csvManager.getTranslationData(SHEETNAME_TRANSLATIONDATA, 77, 4));

        return translationData;
    }

    public String getItemToTranslate(int number) throws IOException {
        return translationsData().get("itemToTranslate" + number);
    }
    public String getFile(int number) throws IOException {
        return FILE_PATH + translationsData().get("file" + number);
    }
    public String getFileName(int number) throws IOException {
        return translationsData().get("file" + number);
    }
    public String getUnitCountSource(int number) throws IOException {
        return translationsData().get("unitCountSource" + number);
    }
    public String getTranslatedItem(int number) throws IOException {
        return translationsData().get("translatedItem" + number);
    }
    public String getUnitCountTarget(int number) throws IOException {
        return translationsData().get("unitCountTarget" + number);
    }
    public String getUnitCountFileSource(int number) throws IOException {
        return translationsData().get("unitCountFileSource" + number);
    }
    public String getEnToJaGlossary() throws IOException {
        return translationsData().get("enToJa");
    }
    public String getEnToJaBracketedGlossary() throws IOException {
        return translationsData().get("enToJaBracketed");
    }
    public String getEnToTlBracketedGlossary() throws IOException {
        return translationsData().get("enToTlBracketed");
    }
    public String getJaToEnBracketedGlossary() throws IOException {
        return translationsData().get("jaToEnBracketed");
    }
    public String getJaToZhBracketedGlossary() throws IOException {
        return translationsData().get("jaToZhBracketed");
    }

    /**
     * @return Translation excerpts
     */
    private HashMap<String, String> excerpts() {
        excerpt.put("excerptEnglish1", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 1, 0));
        excerpt.put("excerptEnglish2", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 2, 0));
        excerpt.put("excerptEnglish3", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 3, 0));
        excerpt.put("excerptEnglish4", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 4, 0));
        excerpt.put("excerptEnglish5", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 5, 0));
        excerpt.put("excerptEnglish6", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 6, 0));
        excerpt.put("excerptEnglish7", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 7, 0));
        excerpt.put("excerptEnglish8", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 8, 0));
        excerpt.put("excerptEnglish9", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 9, 0));
        excerpt.put("excerptEnglish10", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 10, 0));
        excerpt.put("excerptEnglish11", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 11, 0));
        excerpt.put("excerptEnglish12", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 12, 0));
        excerpt.put("excerptEnglish13", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 13, 0));
        excerpt.put("excerptEnglish14", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 14, 0));
        excerpt.put("excerptEnglish15", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 15, 0));
        excerpt.put("excerptEnglish16", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 16, 0));
        excerpt.put("excerptEnglish17", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 17, 0));
        excerpt.put("excerptEnglish18", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 18, 0));
        excerpt.put("excerptEnglish19", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 19, 0));
        excerpt.put("excerptEnglish20", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 20, 0));
        excerpt.put("excerptEnglish21", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 21, 0));
        excerpt.put("excerptEnglish22", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 22, 0));
        excerpt.put("excerptEnglish23", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 23, 0));
        excerpt.put("excerptEnglish24", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 24, 0));
        excerpt.put("excerptEnglish25", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 25, 0));
        excerpt.put("excerptEnglish26", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 26, 0));
        excerpt.put("excerptEnglish27", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 27, 0));
        excerpt.put("excerptEnglish28", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 28, 0));
        excerpt.put("excerptEnglish29", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 29, 0));
        excerpt.put("excerptEnglish30", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 30, 0));
        excerpt.put("excerptEnglish31", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 31, 0));
        excerpt.put("excerptEnglish32", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 32, 0));
        excerpt.put("excerptEnglish33", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 33, 0));
        excerpt.put("excerptEnglish34", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 34, 0));
        excerpt.put("excerptEnglish35", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 35, 0));

        excerpt.put("excerptNonEnglish1", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 1, 1));
        excerpt.put("excerptNonEnglish2", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 2, 1));
        excerpt.put("excerptNonEnglish3", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 3, 1));
        excerpt.put("excerptNonEnglish4", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 4, 1));
        excerpt.put("excerptNonEnglish5", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 5, 1));
        excerpt.put("excerptNonEnglish6", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 6, 1));
        excerpt.put("excerptNonEnglish7", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 7, 1));
        excerpt.put("excerptNonEnglish8", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 8, 1));
        excerpt.put("excerptNonEnglish9", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 9, 1));
        excerpt.put("excerptNonEnglish10", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 10, 1));
        excerpt.put("excerptNonEnglish11", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 11, 1));
        excerpt.put("excerptNonEnglish12", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 12, 1));
        excerpt.put("excerptNonEnglish13", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 13, 1));
        excerpt.put("excerptNonEnglish14", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 14, 1));
        excerpt.put("excerptNonEnglish15", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 15, 1));
        excerpt.put("excerptNonEnglish16", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 16, 1));
        excerpt.put("excerptNonEnglish17", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 17, 1));
        excerpt.put("excerptNonEnglish18", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 18, 1));
        excerpt.put("excerptNonEnglish19", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 19, 1));
        excerpt.put("excerptNonEnglish20", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 20, 1));
        excerpt.put("excerptNonEnglish21", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 21, 1));
        excerpt.put("excerptNonEnglish22", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 22, 1));
        excerpt.put("excerptNonEnglish23", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 23, 1));
        excerpt.put("excerptNonEnglish24", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 24, 1));
        excerpt.put("excerptNonEnglish25", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 25, 1));
        excerpt.put("excerptNonEnglish26", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 26, 1));
        excerpt.put("excerptNonEnglish27", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 27, 1));
        excerpt.put("excerptNonEnglish31", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 31, 1));
        excerpt.put("excerptNonEnglish32", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 32, 1));
        excerpt.put("excerptNonEnglish33", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 33, 1));

        excerpt.put("excerptFile1", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 1, 2));
        excerpt.put("excerptFile2", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 2, 2));
        excerpt.put("excerptFile3", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 3, 2));
        excerpt.put("excerptFile4", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 4, 2));
        excerpt.put("excerptFile5", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 5, 2));
        excerpt.put("excerptFile6", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 6, 2));
        excerpt.put("excerptFile7", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 7, 2));
        excerpt.put("excerptFile8", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 8, 2));
        excerpt.put("excerptFile9", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 9, 2));
        excerpt.put("excerptFile10", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 10, 2));
        excerpt.put("excerptFile11", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 11, 2));
        excerpt.put("excerptFile12", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 12, 2));
        excerpt.put("excerptFile13", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 13, 2));
        excerpt.put("excerptFile14", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 14, 2));
        excerpt.put("excerptFile15", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 15, 2));
        excerpt.put("excerptFile16", csvManager.getCsvValue(SHEETNAME_EXCERPTS, 16, 2));
        return excerpt;
    }

    public String getExcerptEnglish(int number) {
        return excerpts().get("excerptEnglish" + number);
    }
    public String getExcerptNonEnglish(int number) {
        return excerpts().get("excerptNonEnglish" + number);
    }
    public String getExcerptFile(int number) {
        return excerpts().get("excerptFile" + number);
    }

    /**
     * @return Source and target language to process.
     */
    private HashMap<String, String> languagePairs() {
        language.put("EN", csvManager.getCsvValue(SHEETNAME_LANGUAGEPAIRS, 1, 0));
        language.put("JA", csvManager.getCsvValue(SHEETNAME_LANGUAGEPAIRS, 1, 1));

        return language;
    }

    public String source(String language) {
        return languagePairs().get(language);
    }
    public String target(String language) {
        return languagePairs().get(language);
    }

    /**
     * @return Translate from {for class CustomerOrderLanguagePageFactory}
     */
    private HashMap<String, String> orderLanguageFrom() {
        languageFrom.put("german", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 1, 0));
        languageFrom.put("english", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 2, 0));
        languageFrom.put("spanishSpain", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 3, 0));
        languageFrom.put("spanishLatinAmerica", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 4, 0));
        languageFrom.put("korean", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 5, 0));
        languageFrom.put("french", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 6, 0));
        languageFrom.put("frenchCanada", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 7, 0));
        languageFrom.put("italian", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 8, 0));
        languageFrom.put("japanese", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 9, 0));
        languageFrom.put("dutch", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 10, 0));
        languageFrom.put("portugueseEurope", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 11, 0));
        languageFrom.put("portugueseBrazil", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 12, 0));
        languageFrom.put("chineseTraditional", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 13, 0));
        languageFrom.put("russian", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 14, 0));
        languageFrom.put("swedish", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 15, 0));
        languageFrom.put("danish", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 16, 0));
        languageFrom.put("norwegian", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 17, 0));
        languageFrom.put("polish", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 18, 0));
        languageFrom.put("chineseSimplified", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 19, 0));

        return languageFrom;
    }

    public String getGermanFrom() {
        return orderLanguageFrom().get("german");
    }
    public String getEnglishFrom() {
        return orderLanguageFrom().get("english");
    }
    public String getSpanishSpainFrom() {
        return orderLanguageFrom().get("spanishSpain");
    }
    public String getSpanishLatinAmericaFrom() {
        return orderLanguageFrom().get("spanishLatinAmerica");
    }
    public String getKoreanFrom() {
        return orderLanguageFrom().get("korean");
    }
    public String getFrenchFrom() {
        return orderLanguageFrom().get("french");
    }
    public String getFrenchCanadaFrom() {
        return orderLanguageFrom().get("frenchCanada");
    }
    public String getItalianFrom() {
        return orderLanguageFrom().get("italian");
    }
    public String getJapaneseFrom() {
        return orderLanguageFrom().get("japanese");
    }
    public String getDutchFrom() {
        return orderLanguageFrom().get("dutch");
    }
    public String getPortugueseEuropeFrom() {
        return orderLanguageFrom().get("portugueseEurope");
    }
    public String getPortugueseBrazilFrom() {
        return orderLanguageFrom().get("portugueseBrazil");
    }
    public String getChineseTraditionalFrom() {
        return orderLanguageFrom().get("chineseTraditional");
    }
    public String getRussianFrom() {
        return orderLanguageFrom().get("russian");
    }
    public String getSwedishFrom() {
        return orderLanguageFrom().get("swedish");
    }
    public String getDanishFrom() {
        return orderLanguageFrom().get("danish");
    }
    public String getNorwegianFrom() {
        return orderLanguageFrom().get("norwegian");
    }
    public String getPolishFrom() {
        return orderLanguageFrom().get("polish");
    }
    public String getChineseSimplifiedFrom() {
        return orderLanguageFrom().get("chineseSimplified");
    }

    /**
     * @return Translate to {for class CustomerOrderLanguagePageFactory}
     */
    private HashMap<String, String> orderLanguageTo() {
        languageTo.put("arabic", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 1, 1));
        languageTo.put("bulgarian", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 2, 1));
        languageTo.put("chineseSimplified", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 3, 1));
        languageTo.put("chineseTraditional", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 4, 1));
        languageTo.put("czech", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 5, 1));
        languageTo.put("danish", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 6, 1));
        languageTo.put("dutch", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 7, 1));
        languageTo.put("englishBritish", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 8, 1));
        languageTo.put("finnish", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 9, 1));
        languageTo.put("french", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 10, 1));
        languageTo.put("frenchCanada", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 11, 1));
        languageTo.put("german", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 12, 1));
        languageTo.put("greek", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 13, 1));
        languageTo.put("hebrew", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 14, 1));
        languageTo.put("hungarian", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 15, 1));
        languageTo.put("indonesian", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 16, 1));
        languageTo.put("italian", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 17, 1));
        languageTo.put("japanese", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 18, 1));
        languageTo.put("korean", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 19, 1));
        languageTo.put("malay", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 20, 1));
        languageTo.put("norwegian", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 21, 1));
        languageTo.put("polish", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 22, 1));
        languageTo.put("portugueseBrazil", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 23, 1));
        languageTo.put("portugueseEurope", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 24, 1));
        languageTo.put("romanian", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 25, 1));
        languageTo.put("russian", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 26, 1));
        languageTo.put("serbian", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 27, 1));
        languageTo.put("slovak", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 28, 1));
        languageTo.put("spanishLatinAmerica", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 29, 1));
        languageTo.put("spanishSpain", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 30, 1));
        languageTo.put("swedish", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 31, 1));
        languageTo.put("tagalog", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 32, 1));
        languageTo.put("thai", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 33, 1));
        languageTo.put("turkish", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 34, 1));
        languageTo.put("ukrainian", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 35, 1));
        languageTo.put("vietnamese", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 36, 1));
        languageTo.put("english", csvManager.getCsvValue(SHEETNAME_ORDERLANGUAGES, 37, 1));

        return languageTo;
    }

    public String getArabicTo() {
        return orderLanguageTo().get("arabic");
    }
    public String getBulgarianTo() {
        return orderLanguageTo().get("bulgarian");
    }
    public String getChineseSimplifiedTo() {
        return orderLanguageTo().get("chineseSimplified");
    }
    public String getChineseTraditionalTo() {
        return orderLanguageTo().get("chineseTraditional");
    }
    public String getCzechTo() {
        return orderLanguageTo().get("czech");
    }
    public String getDanishTo() {
        return orderLanguageTo().get("danish");
    }
    public String getDutchTo() {
        return orderLanguageTo().get("dutch");
    }
    public String getEnglishBritishTo() {
        return orderLanguageTo().get("englishBritish");
    }
    public String getFinnishTo() {
        return orderLanguageTo().get("finnish");
    }
    public String getFrenchTo() {
        return orderLanguageTo().get("french");
    }
    public String getFrenchCanadaTo() {
        return orderLanguageTo().get("frenchCanada");
    }
    public String getGermanTo() {
        return orderLanguageTo().get("german");
    }
    public String getGreekTo() {
        return orderLanguageTo().get("greek");
    }
    public String getHebrewTo() {
        return orderLanguageTo().get("hebrew");
    }
    public String getHungarianTo() {
        return orderLanguageTo().get("hungarian");
    }
    public String getIndonesianTo() {
        return orderLanguageTo().get("indonesian");
    }
    public String getItalianTo() {
        return orderLanguageTo().get("italian");
    }
    public String getJapaneseTo() {
        return orderLanguageTo().get("japanese");
    }
    public String getKoreanTo() {
        return orderLanguageTo().get("korean");
    }
    public String getMalayTo() {
        return orderLanguageTo().get("malay");
    }
    public String getNorwegianTo() {
        return orderLanguageTo().get("norwegian");
    }
    public String getPolishTo() {
        return orderLanguageTo().get("polish");
    }
    public String getPortugueseBrazilTo() {
        return orderLanguageTo().get("portugueseBrazil");
    }
    public String getPortugueseEuropeTo() {
        return orderLanguageTo().get("portugueseEurope");
    }
    public String getRomanianTo() {
        return orderLanguageTo().get("romanian");
    }
    public String getRussianTo() {
        return orderLanguageTo().get("russian");
    }
    public String getSerbianTo() {
        return orderLanguageTo().get("serbian");
    }
    public String getSlovakTo() {
        return orderLanguageTo().get("slovak");
    }
    public String getSpanishLatinAmericaTo() {
        return orderLanguageTo().get("spanishLatinAmerica");
    }
    public String getSpanishSpainTo() {
        return orderLanguageTo().get("spanishSpain");
    }
    public String getSwedishTo() {
        return orderLanguageTo().get("swedish");
    }
    public String getTagalogTo() {
        return orderLanguageTo().get("tagalog");
    }
    public String getThaiTo() {
        return orderLanguageTo().get("thai");
    }
    public String getTurkishTo() {
        return orderLanguageTo().get("turkish");
    }
    public String getUkrainianTo() {
        return orderLanguageTo().get("ukrainian");
    }
    public String getVietnameseTo() {
        return orderLanguageTo().get("vietnamese");
    }
    public String getEnglishTo() {
        return orderLanguageTo().get("english");
    }

    /**
     * @return Error messages
     */
    private HashMap<String, String> errorMessages() {
        errorMsg.put("wrongURL", csvManager.getCsvValue(SHEETNAME_ERRORS, 1, 0));
        errorMsg.put("elementNotFound", csvManager.getCsvValue(SHEETNAME_ERRORS, 2, 0));
        errorMsg.put("textNotEqual", csvManager.getCsvValue(SHEETNAME_ERRORS, 3, 0));
        errorMsg.put("elementIsEnabled", csvManager.getCsvValue(SHEETNAME_ERRORS, 4, 0));
        errorMsg.put("elementIsNotEnabled", csvManager.getCsvValue(SHEETNAME_ERRORS, 5, 0));
        errorMsg.put("elementIsDisplayed", csvManager.getCsvValue(SHEETNAME_ERRORS, 6, 0));
        errorMsg.put("elementIsNotDisplayed", csvManager.getCsvValue(SHEETNAME_ERRORS, 7, 0));
        errorMsg.put("amountIsNotRight", csvManager.getCsvValue(SHEETNAME_ERRORS, 8, 0));
        errorMsg.put("updateAddress", csvManager.getCsvValue(SHEETNAME_ERRORS, 9, 0));
        errorMsg.put("updateDisplayName", csvManager.getCsvValue(SHEETNAME_ERRORS, 10, 0));
        errorMsg.put("updatePasswordBlankFields", csvManager.getCsvValue(SHEETNAME_ERRORS, 11, 0));
        errorMsg.put("updatePasswordIncorrect", csvManager.getCsvValue(SHEETNAME_ERRORS, 12, 0));
        errorMsg.put("updatePasswordNotMatch", csvManager.getCsvValue(SHEETNAME_ERRORS, 13, 0));
        errorMsg.put("updatePasswordInsufficientLength", csvManager.getCsvValue(SHEETNAME_ERRORS, 14, 0));
        errorMsg.put("updateEmailBlankFields", csvManager.getCsvValue(SHEETNAME_ERRORS, 15, 0));
        errorMsg.put("updateEmailNotMatch", csvManager.getCsvValue(SHEETNAME_ERRORS, 16, 0));
        errorMsg.put("updateEmailFailed", csvManager.getCsvValue(SHEETNAME_ERRORS, 17, 0));
        errorMsg.put("topUpFailed", csvManager.getCsvValue(SHEETNAME_ERRORS, 19, 0));
        errorMsg.put("failedSelectedAmount", csvManager.getCsvValue(SHEETNAME_ERRORS, 20, 0));

        return errorMsg;
    }

    public String getWrongUrlErrMsg() {
        return errorMessages().get("wrongURL");
    }
    public String getElementNotFoundErrMsg() {
        return errorMessages().get("elementNotFound");
    }
    public String getTextNotEqualErrMsg() {
        return errorMessages().get("textNotEqual");
    }
    public String getElementIsEnabledErrMsg() {
        return errorMessages().get("elementIsEnabled");
    }
    public String getElementIsNotEnabledErrMsg() {
        return errorMessages().get("elementIsNotEnabled");
    }
    public String getElementIsDisplayedErrMsg() {
        return errorMessages().get("elementIsDisplayed");
    }
    public String getElementIsNotDisplayedErrMsg() {
        return errorMessages().get("elementIsNotDisplayed");
    }
    public String getAmountIsNotRightErrMsg() {
        return errorMessages().get("amountIsNotRight");
    }
    public String getUpdateAddressErrMsg() {
        return errorMessages().get("updateAddress");
    }
    public String getUpdateDisplayNameErrMsg() {
        return errorMessages().get("updateDisplayName");
    }
    public String getUpdatePasswordBlankFieldsErrMsg() {
        return errorMessages().get("updatePasswordBlankFields");
    }
    public String getUpdatePasswordIncorrectErrMsg() {
        return errorMessages().get("updatePasswordIncorrect");
    }
    public String getUpdatePasswordNotMatchErrMsg() {
        return errorMessages().get("updatePasswordNotMatch");
    }
    public String getUpdatePasswordInsufficientLengthErrMsg() {
        return errorMessages().get("updatePasswordInsufficientLength");
    }
    public String getUpdateEmailBlankFieldsErrMsg() {
        return errorMessages().get("updateEmailBlankFields");
    }
    public String getUpdateEmailNotMatchErrMsg() {
        return errorMessages().get("updateEmailNotMatch");
    }
    public String getUpdateEmailFailedErrMsg() {
        return errorMessages().get("updateEmailFailed");
    }
    public String getTopUpFailedErrMsg() {
        return errorMessages().get("topUpFailed");
    }
    public String getFailedSelectedAmount() {
        return errorMessages().get("failedSelectedAmount");
    }

    /**
     * @return Customer Accounts to be Generated
     */
    public HashMap<String, String> customerAccounts() {

        for(int i = 1; i <= csvManager.getLastRowIndex(SHEETNAME_GENERATECUSTOMER); i++)
            customerAccounts.put("customerToGenerate" + "" + i, csvManager.getCsvValue(SHEETNAME_GENERATECUSTOMER, i, 0));
        return customerAccounts;
    }

    public String getCustomerToGenerate(int number) {
        return customerAccounts().get("customerToGenerate" + "" + number);
    }

    /**
     * @return Customer Accounts to be Generated
     */
    public HashMap<String, String> customerCurrency() {
        for(int i = 1; i <= csvManager.getLastRowIndex(SHEETNAME_GENERATECUSTOMERCURRENCY); i++) {
            customerCurrency.put("customerToGenerateCurrency" + "" + i, csvManager.getCsvValue(SHEETNAME_GENERATECUSTOMERCURRENCY, i, 0));
            customerCurrency.put("customerCurrency" + "" + i, csvManager.getCsvValue(SHEETNAME_GENERATECUSTOMERCURRENCY, i, 1));
        }
        return customerCurrency;
    }

    public String getCustomerToGenerateCurrency(int number) {
        return customerCurrency().get("customerToGenerateCurrency" + "" + number);
    }

    public String getCustomerCurrency(int number) {
        return customerCurrency().get("customerCurrency" + "" + number);
    }


    /**
     * @return Customer Accounts with Top Up to be Generated
     */
    public HashMap<String, String> customerAccountsWithTopUp() {
        for(int i = 1; i <= csvManager.getLastRowIndex(SHEETNAME_GENERATECUSTOMERWITHTOPUP); i++) {
            customerAccountsWithTopUp.put("customerToGenerateWithTopUp" + "" + i, csvManager.getCsvValue(SHEETNAME_GENERATECUSTOMERWITHTOPUP, i, 0));
            customerAccountsWithTopUp.put("currency" + "" + i, csvManager.getCsvValue(SHEETNAME_GENERATECUSTOMERWITHTOPUP, i, 1));
            customerAccountsWithTopUp.put("amount" + "" + i, csvManager.getCsvValue(SHEETNAME_GENERATECUSTOMERWITHTOPUP, i, 2));
            customerAccountsWithTopUp.put("isDone" + "" + i, csvManager.getCsvValue(SHEETNAME_GENERATECUSTOMERWITHTOPUP, i, 3));
        }
        return  customerAccountsWithTopUp;
    }

    public String getCustomerToGenerateWithTopUp(int number) {
        return customerAccountsWithTopUp().get("customerToGenerateWithTopUp" + "" + number);
    }

    public String getCurrency(int number) {
        return customerAccountsWithTopUp().get("currency" + "" + number);
    }

    public String getAmount(int number) {
        return customerAccountsWithTopUp().get("amount" + "" + number);
    }

    public String getIsDone(int number) {
        return customerAccountsWithTopUp().get("isDone" + "" + number);
    }

    /**
     * @return Translator Accounts to be Generated
     */
    public HashMap<String, String> translatorAccounts() {
        for(int i = 1; i <= csvManager.getLastRowIndex(SHEETNAME_GENERATETRANSLATOR); i++) {
            translatorAccounts.put("translatorToGenerate" + "" + i, csvManager.getCsvValue(SHEETNAME_GENERATETRANSLATOR, i, 0));
            translatorAccounts.put("isOnboarded" + "" + i, csvManager.getCsvValue(SHEETNAME_GENERATETRANSLATOR, i, 1));
        }
        return translatorAccounts;
    }

    public String getTranslatorToGenerate(int number) {
        return translatorAccounts().get("translatorToGenerate" + "" + number);
    }

    public String getIsNewOnboarded(int number) {
        return translatorAccounts().get("isOnboarded" + "" + number);
    }

    /**
     * @return Translator Accounts ID
     */
    public HashMap<String, String> translatorID() {
        for(int i = 1; i <= csvManager.getLastRowIndex(SHEETNAME_TRANSLATORID); i++) {
            translatorID.put("translatorID" + "" + i, csvManager.getCsvValue(SHEETNAME_TRANSLATORID, i, 0));
            translatorID.put("translatorEmail" + "" + i, csvManager.getCsvValue(SHEETNAME_TRANSLATORID, i, 1));
            translatorID.put("isRetrieved" + "" + i, csvManager.getCsvValue(SHEETNAME_TRANSLATORID, i, 2));
            translatorID.put(csvManager.getCsvValue(SHEETNAME_TRANSLATORID, i, 1), csvManager.getCsvValue(SHEETNAME_TRANSLATORID, i, 0));
        }
        return translatorID;
    }

    public int getTranslatorIDRows(){
        return  csvManager.getLastRowIndex(SHEETNAME_TRANSLATORID);
    }
    public String getTranslatorID(String email) {
        return translatorID().get(email);
    }
    public String getTranslatorIDEmail(int number) {
        return translatorID().get("translatorEmail" + "" + number);
    }
    public String getIsRetrieved(int number) {
        return translatorID().get("isRetrieved" + "" + number);
    }

    /**
     * @return Translator Qualifications List
     */
    public HashMap<String, String> translatorQualifications() {
        for(int i = 1; i <= csvManager.getLastRowIndex(SHEETNAME_QUALIFICATIONS); i++) {
            translatorQualifications.put("translatorEmail" + "" + i, csvManager.getCsvValue(SHEETNAME_QUALIFICATIONS, i, 0));
            translatorQualifications.put("sourceLanguage" + "" + i, csvManager.getCsvValue(SHEETNAME_QUALIFICATIONS, i, 1));
            translatorQualifications.put("targetLanguage" + "" + i, csvManager.getCsvValue(SHEETNAME_QUALIFICATIONS, i, 2));
            translatorQualifications.put("rank" + "" + i, csvManager.getCsvValue(SHEETNAME_QUALIFICATIONS, i, 3));
            translatorQualifications.put("isAdded" + "" + i, csvManager.getCsvValue(SHEETNAME_QUALIFICATIONS, i, 4));
        }
        return translatorQualifications;
    }

    public String getTranslatorEmail(int number) {
        return translatorQualifications().get("translatorEmail" + "" + number);
    }

    public String getSourceLanguage(int number) {
        return translatorQualifications().get("sourceLanguage" + "" + number);
    }

    public String getTargetLanguage(int number) {
        return translatorQualifications().get("targetLanguage" + "" + number);
    }

    public String getIsAdded(int number) {
        return translatorQualifications().get("isAdded" + "" + number);
    }

    public String getRank(int number) {
        return translatorQualifications().get("rank" + "" + number);
    }

    /**
     * @return Translator Qualifications List
     */
    public HashMap<String, String> preCreatedTranslators() {
        for(int i = 1; i <= csvManager.getLastRowIndex(SHEETNAME_PRECREATED_TRANSLATORS); i++) {
            preCreatedTranslators.put("preCreatedTranslator" + "" + i, csvManager.getCsvValue(SHEETNAME_PRECREATED_TRANSLATORS, i, 0));
            preCreatedTranslators.put("isOnboarded" + "" + i, csvManager.getCsvValue(SHEETNAME_PRECREATED_TRANSLATORS, i, 1));
        }
        return preCreatedTranslators;
    }

    public String getPreCreatedTranslator(int number) {
        return preCreatedTranslators.get("preCreatedTranslator"+ "" + number);
    }

    public String getIsOnboarded(int number){
        return preCreatedTranslators.get("isOnboarded"+ "" + number);
    }
}
