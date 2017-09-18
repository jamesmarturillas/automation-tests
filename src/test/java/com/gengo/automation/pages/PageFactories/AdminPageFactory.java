package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for Admin page.
 * Contains PageFactory initialization.
 */
public class AdminPageFactory {

    protected WebDriver driver;

    public AdminPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//h1[@class='pull-left']")
    WebElement pageTitle;

    @FindBy(xpath = "//ul[@class='nav navbar-nav']/li[contains(., 'Activity')]")
    WebElement activityTab;

    @FindBy(xpath = "//ul[@class='nav navbar-nav']/li[contains(., 'Projects')]")
    WebElement projectsTab;

    @FindBy(xpath = "//ul[@class='nav navbar-nav']/li[contains(., 'Languages')]")
    WebElement languagesTab;

    @FindBy(xpath = "//ul[@class='nav navbar-nav']/li[contains(., 'Finance')]")
    WebElement financeTab;

    @FindBy(xpath = "//ul[@class='nav navbar-nav']/li[contains(., 'Manage')]")
    WebElement manageTab;

    /** Activity tab */
    By byOrders = By.xpath("//ul[@class='dropdown-menu']/li/a[contains(., 'Orders')]");
    By byCollections = By.xpath("//ul[@class='dropdown-menu']/li/a[contains(., 'Collections')]");
    By byJobs = By.xpath("//ul[@class='dropdown-menu']/li/a[contains(., 'Jobs')]");
    By byComments = By.xpath("//ul[@class='dropdown-menu']/li/a[contains(., 'Comments')]");

    /** Projects tab */
    By byProjects = By.xpath("//ul[@class='dropdown-menu']/li/a[contains(., 'Projects')]");
    By byFiles = By.xpath("//ul[@class='dropdown-menu']/li/a[contains(., 'Files')]");

    /** Languages tab */
    By byLanguages = By.xpath("//ul[@class='dropdown-menu']/li/a[contains(., 'Languages')]");
    By byLanguagePairs = By.xpath("//ul[@class='dropdown-menu']/li/a[contains(., 'Language Pairs')]");
    By byQualifications = By.xpath("//ul[@class='dropdown-menu']/li/a[contains(., 'Qualifications')]");
    By byChecks = By.xpath("//ul[@class='dropdown-menu']/li/a[contains(., 'Checks')]");

    /** Finance tab */
    By byTransactions = By.xpath("//ul[@class='dropdown-menu']/li/a[contains(., 'Transactions')]");
    By byPurchases = By.xpath("//ul[@class='dropdown-menu']/li/a[contains(., 'Purchases')]");
    By byPayouts = By.xpath("//ul[@class='dropdown-menu']/li/a[contains(., 'Payouts')]");

    /** Manage tab */
    By byAccounts = By.xpath("//ul[@class='dropdown-menu']/li/a[contains(., 'Accounts')]");
    By byUsers = By.xpath("//ul[@class='dropdown-menu']/li/a[contains(., 'Users')]");
    By byNews = By.xpath("//ul[@class='dropdown-menu']/li/a[contains(., 'News')]");


    @FindBy(xpath = "//input[@class='form-control form-search input-sm']")
    WebElement txtBoxSearch;

    @FindBy(id = "pHideToolBarButton")
    WebElement rightPanelHide;

    @FindBy(name = "job_id")
    WebElement txtBoxJobId;

    @FindBy(xpath = "//table[@id='table-items']/tbody")
    WebElement resultTable;

    /** Result table 'By' locators */
    By byResultId = By.xpath("//tr[@class='item-link']/td/a[contains(@href, '/collections/')]");

    @FindBy(xpath = "//button[@class='btn btn-default dropdown-toggle']")
    WebElement actionBtn;

    /** 'Collection' locators */
    @FindBy(xpath = "//button[@class='btn btn-default dropdown-toggle']/parent::div/ul/li/a[contains(text(), 'Comment')]")
    WebElement collectionActionBtnCommentLink;

    @FindBy(xpath = "//button[@class='btn btn-default dropdown-toggle']/parent::div/ul/li/a[contains(text(), 'Unhold')]")
    WebElement collectionActionBtnUnholdLink;

    @FindBy(xpath = "//button[@class='btn btn-default dropdown-toggle']/parent::div/ul/li/a[contains(text(), 'Make Preferred')]")
    WebElement collectionActionBtnMakePreferredLink;

    @FindBy(xpath = "//button[@class='btn btn-default dropdown-toggle']/parent::div/ul/li/a[contains(text(), 'Assign Translator')]")
    WebElement collectionActionBtnAssignTranslatorLink;

    @FindBy(xpath = "//button[@class='btn btn-default dropdown-toggle']/parent::div/ul/li/a[contains(text(), 'Hold')]")
    WebElement collectionActionBtnHoldLink;

    @FindBy(xpath = "//button[@class='btn btn-default dropdown-toggle']/parent::div/ul/li/a[contains(text(), 'Edit Reward')]")
    WebElement collectionActionBtnEditRewardLink;

    @FindBy(xpath = "//div[@id='modal-confirmation']/div/div/div[@class='modal-footer']/button[@class='btn btn-default'][contains(text(), 'No')]")
    WebElement answerNo;

    @FindBy(xpath = "//div[@id='modal-confirmation']/div/div/div[@class='modal-footer']/button[@class='btn confirm-action  btn-danger'][contains(text(), 'Yes')]")
    WebElement answerYes;

    @FindBy(xpath = "//button[@class='close']")
    WebElement closeBtn;

    @FindBy(xpath = "//h2[@class='modal-title'][contains(.,'Assign Translator')]")
    WebElement assignTranslatorModalHeader;

    @FindBy(xpath = "//div[@class='modal-body'][contains(.,'Are you sure you want to assign a translator to this job? All other available jobs in the same collection will also be assigned. There is no undo.')]")
    WebElement assignTranslatorModalText;

    @FindBy(xpath = "//h2[@class='modal-title'][contains(.,'Assign a translator')]")
    WebElement addTranslatorModalHeader;

    @FindBy(xpath = "//label[contains(.,'User ID, Email or Name: ')]")
    WebElement addTranslatorModalText;

    @FindBy(xpath = "//input[@id='newTranslator']")
    WebElement addTranslatorInput;

    @FindBy(xpath = "//button[@id='assignNewTranslatorBtn']")
    WebElement addTranslatorUpdateBtn;

    @FindBy(xpath = "//button[@class='btn btn-default'][contains(.,'Cancel')]")
    WebElement addTranslatorCancelBtn;

    /** User account 'Action' button options */
    @FindBy(xpath = "//button[@class='btn btn-default dropdown-toggle']/parent::div/ul/li/a[contains(text(), 'Hijack User')]")
    WebElement userActionBtnHijackUserLink;

    @FindBy(xpath = "//button[@class='btn btn-default dropdown-toggle']/parent::div/ul/li/a[contains(text(), 'Add Preferred Translators')]")
    WebElement userActionBtnAddPreferredTranslatorsLink;

    @FindBy(xpath = "//button[@class='btn btn-default dropdown-toggle']/parent::div/ul/li/a[contains(text(), 'Bulk Add Preferred Translators')]")
    WebElement userActionBtnBulkAddPreferredTranslatorsLink;

    @FindBy(xpath = "//button[@class='btn btn-default dropdown-toggle']/parent::div/ul/li/a[contains(text(), 'Credit or Reward Adjustment')]")
    WebElement userActionBtnCreditOrRewardAdjustmentLink;

    @FindBy(xpath = "//button[@class='btn btn-default dropdown-toggle']/parent::div/ul/li/a[contains(text(), 'Associate Account')]")
    WebElement userActionBtnAssociateAccountLink;

    @FindBy(xpath = "//button[@class='btn btn-default dropdown-toggle']/parent::div/ul/li/a[contains(text(), 'Suspend User')]")
    WebElement userActionBtnSuspendUserLink;

    @FindBy(xpath = "//button[@class='btn btn-default dropdown-toggle']/parent::div/ul/li/a[contains(text(), 'Comment')]")
    WebElement userActionBtnCommentLink;

    @FindBy(xpath = "//button[@class='btn btn-default dropdown-toggle']/parent::div/ul/li/a[contains(text(), 'Edit Custom Prices')]")
    WebElement userActionBtnEditCustomPricesLink;

    @FindBy(xpath = "//button[@class='btn btn-default dropdown-toggle']/parent::div/ul/li/a[contains(text(), 'Export Spend')]")
    WebElement userActionBtnExportSpendLink;

    @FindBy(xpath = "//button[@class='btn btn-default dropdown-toggle']/parent::div/ul/li/a[contains(text(), 'Export TM Report')]")
    WebElement userActionBtnExportTmReportLink;

    /** Qualifications */
    @FindBy(xpath = "//a[@class='btn btn-primary pull-right']")
    WebElement createNewQualificationsBtn;

    @FindBy(name = "translator_id")
    WebElement txtTranslatorId;

    @FindBy(name = "lc_src")
    WebElement selectQualificationsSource;

    @FindBy(name = "lc_tgt")
    WebElement selectQualificationsTarget;

    @FindBy(xpath = "//select[@name='rank']")
    WebElement selectQualificationsRank;

    @FindBy(xpath = "//h2[@class='modal-title' and contains(text(), 'Add Preferred Translators')]")
    WebElement preferredTranslatorModalTxt;

    @FindBy(name = "language_pair")
    WebElement selectLanguagePair;

    @FindBy(xpath = "//textarea[@name='translators']")
    WebElement txtAreaAddPreferredTranslators;

    @FindBy(id = "confirm-add-preferred")
    WebElement btnAddPreferredUpdate;

    // Filter or Clear inputs
    @FindBy(xpath = "//button[@id='btn-filter']")
    WebElement filterCheckButton;

    @FindBy(xpath = "//button[@id='btn-clear']")
    WebElement clearFilterCheckButton;

    /** Success messages */
    @FindBy(xpath = "//p[contains(text(), 'Collection unheld successfully')]")
    WebElement unheldSuccessMsg;

    @FindBy(xpath = "//p[contains(text(), 'Preferred translators set.')]")
    WebElement preferredTranslatorSetMsg;

    /**
     * Getters
     */
    public WebElement getPageTitle() {
        return pageTitle;
    }

    public WebElement getActivityTab() {
        return activityTab;
    }

    public WebElement getProjectsTab() {
        return projectsTab;
    }

    public WebElement getLanguagesTab() {
        return languagesTab;
    }

    public WebElement getFinanceTab() {
        return financeTab;
    }

    public WebElement getManageTab() {
        return manageTab;
    }

    public By getByOrders() {
        return byOrders;
    }

    public By getByCollections() {
        return byCollections;
    }

    public By getByJobs() {
        return byJobs;
    }

    public By getByComments() {
        return byComments;
    }

    public By getByProjects() {
        return byProjects;
    }

    public By getByFiles() {
        return byFiles;
    }

    public By getByLanguages() {
        return byLanguages;
    }

    public By getByLanguagePairs() {
        return byLanguagePairs;
    }

    public By getByQualifications() {
        return byQualifications;
    }

    public By getByChecks() {
        return byChecks;
    }

    public By getByTransactions() {
        return byTransactions;
    }

    public By getByPurchases() {
        return byPurchases;
    }

    public By getByPayouts() {
        return byPayouts;
    }

    public By getByAccounts() {
        return byAccounts;
    }

    public By getByUsers() {
        return byUsers;
    }

    public By getByNews() {
        return byNews;
    }

    public WebElement getTxtBoxSearch() {
        return txtBoxSearch;
    }

    public WebElement getRightPanelHide() {
        return rightPanelHide;
    }

    public WebElement getTxtBoxJobId() {
        return txtBoxJobId;
    }

    public WebElement getResultTable() {
        return resultTable;
    }

    public By getByResultId() {
        return byResultId;
    }

    public WebElement getActionBtn() {
        return actionBtn;
    }

    public WebElement getCollectionActionBtnCommentLink() {
        return collectionActionBtnCommentLink;
    }

    public WebElement getCollectionActionBtnUnholdLink() {
        return collectionActionBtnUnholdLink;
    }

    public WebElement getCollectionActionBtnMakePreferredLink() {
        return collectionActionBtnMakePreferredLink;
    }

    public WebElement getCollectionActionBtnAssignTranslatorLink() {
        return collectionActionBtnAssignTranslatorLink;
    }

    public WebElement getCollectionActionBtnHoldLink() {
        return collectionActionBtnHoldLink;
    }

    public WebElement getCollectionActionBtnEditRewardLink() {
        return collectionActionBtnEditRewardLink;
    }

    public WebElement getAnswerNo() {
        return answerNo;
    }

    public WebElement getAnswerYes() {
        return answerYes;
    }

    public WebElement getCloseBtn() {
        return closeBtn;
    }

    public WebElement getAssignTranslatorModalHeader() {
        return assignTranslatorModalHeader;
    }

    public WebElement getAssignTranslatorModalText() {
        return assignTranslatorModalText;
    }

    public WebElement getAddTranslatorModalHeader() {
        return addTranslatorModalHeader;
    }

    public WebElement getAddTranslatorModalText() {
        return addTranslatorModalText;
    }

    public WebElement getAddTranslatorInput() {
        return addTranslatorInput;
    }

    public WebElement getAddTranslatorUpdateBtn() {
        return addTranslatorUpdateBtn;
    }

    public WebElement getAddTranslatorCancelBtn() {
        return addTranslatorCancelBtn;
    }

    public WebElement getUserActionBtnHijackUserLink() {
        return userActionBtnHijackUserLink;
    }

    public WebElement getUserActionBtnAddPreferredTranslatorsLink() {
        return userActionBtnAddPreferredTranslatorsLink;
    }

    public WebElement getUserActionBtnBulkAddPreferredTranslatorsLink() {
        return userActionBtnBulkAddPreferredTranslatorsLink;
    }

    public WebElement getUserActionBtnCreditOrRewardAdjustmentLink() {
        return userActionBtnCreditOrRewardAdjustmentLink;
    }

    public WebElement getUserActionBtnAssociateAccountLink() {
        return userActionBtnAssociateAccountLink;
    }

    public WebElement getUserActionBtnSuspendUserLink() {
        return userActionBtnSuspendUserLink;
    }

    public WebElement getUserActionBtnCommentLink() {
        return userActionBtnCommentLink;
    }

    public WebElement getUserActionBtnEditCustomPricesLink() {
        return userActionBtnEditCustomPricesLink;
    }

    public WebElement getUserActionBtnExportSpendLink() {
        return userActionBtnExportSpendLink;
    }

    public WebElement getUserActionBtnExportTmReportLink() {
        return userActionBtnExportTmReportLink;
    }

    public WebElement getPreferredTranslatorModalTxt() {
        return preferredTranslatorModalTxt;
    }

    public WebElement getUnheldSuccessMsg() {
        return unheldSuccessMsg;
    }

    public WebElement getFilterCheckButton() {
        return filterCheckButton;
    }

    public WebElement getClearFilterCheckButton() {
        return clearFilterCheckButton;
    }

    public WebElement getCreateNewQualificationsBtn() {
        return createNewQualificationsBtn;
    }

    public WebElement getTxtTranslatorId() {
        return txtTranslatorId;
    }

    public WebElement getSelectQualificationsSource() {
        return selectQualificationsSource;
    }

    public WebElement getSelectQualificationsTarget() {
        return selectQualificationsTarget;
    }

    public WebElement getSelectQualificationsRank() {
        return selectQualificationsRank;
    }

    public WebElement getSelectLanguagePair() {
        return selectLanguagePair;
    }

    public WebElement getTxtAreaAddPreferredTranslators() {
        return txtAreaAddPreferredTranslators;
    }

    public WebElement getBtnAddPreferredUpdate() {
        return btnAddPreferredUpdate;
    }

    public WebElement getPreferredTranslatorSetMsg() {
        return preferredTranslatorSetMsg;
    }
}
