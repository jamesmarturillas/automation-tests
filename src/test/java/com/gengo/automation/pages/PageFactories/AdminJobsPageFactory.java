package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for Admin-Jobs page.
 */
public class AdminJobsPageFactory {

    protected WebDriver driver;

    public AdminJobsPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /** Jobs' filters locators */
    @FindBy(xpath = "//input[@name='customer_id']")
    WebElement filterJobsCustomerID;

    @FindBy(xpath = "//input[@name='translator_id']")
    WebElement filterJobsTranslatorID;

    @FindBy(xpath = "//input[@name='order_id']")
    WebElement filterJobsOrderID;

    @FindBy(xpath = "//input[@id='filter-form']/div[2]/div[4]/div")
    WebElement filterJobsCollectionID;

    @FindBy(xpath = "//label[contains(.,'Status')]/following-sibling::select")
    WebElement filterStatus;

    @FindBy(xpath = "//button[@id='btn-filter']")
    WebElement filterBtn;

    @FindBy(xpath = "//*[@id='table-items']/tbody/tr[1]/td[2]/a")
    WebElement firstJobEntryID;

    @FindBy(xpath = "//*[@id='table-items']/tbody/tr[1]/td[3]")
    WebElement firstJobEntryDateCreated;

    @FindBy(xpath = "//*[@id='table-items']/tbody/tr[1]/td[6]")
    WebElement firstJobEntrySource;

    @FindBy(xpath = "//*[@id='table-items']/tbody/tr[1]/td[7]")
    WebElement firstJobEntryTarget;

    @FindBy(xpath = "//*[@id='table-items']/tbody/tr[1]/td[8]")
    WebElement firstJobEntryLevel;

    @FindBy(xpath = "//*[@id='table-items']/tbody/tr[1]/td[9]")
    WebElement firstJobEntryStatus;

    /** 'Job details' locators */
    @FindBy(xpath = "//a[@id='action-force-approve']")
    WebElement jobActionBtnForceApprove;

    @FindBy(xpath = "//a[@id='action-request-check']")
    WebElement jobActionBtnRequestCheck;

    @FindBy(xpath = "//a[@id='action-request-corrections']")
    WebElement jobActionBtnRequestCorrections;

    @FindBy(xpath = "//a[@id='action-comment']")
    WebElement jobActionBtnComment;

    @FindBy(xpath = "//a[@id='action-edit']")
    WebElement jobActionBtnEdit;

    @FindBy(xpath = ".//*[@id='content']/div/div[2]/div[2]/div[2]/div/h1")
    WebElement jobDetailsJobID;

    @FindBy(xpath = "//i[@class='icon-next']/parent::a")
    WebElement nextSetTable;

    /** Actions Dropdown */
    @FindBy(xpath="//button[@class='btn btn-default dropdown-toggle'][contains(.,'Actions')]")
    WebElement actionsBtn;

    @FindBy(xpath = "//a[@id='action-cancel']")
    WebElement cancelLink;

    /** Modals */
    @FindBy(xpath = "//button[contains(.,'Yes')]")
    WebElement yesBtn;

    @FindBy(xpath = "//button[contains(.,'No')]")
    WebElement noBtn;

    @FindBy(xpath = "//div[@class='modal-body'][contains(.,'Are you sure you want to cancel')]/following-sibling::div/button[contains(.,'No')]")
    WebElement cancelNoBtn;

    @FindBy(xpath = "//div[@class='modal-body'][contains(.,'Are you sure you want to cancel')]/following-sibling::div/button[contains(.,'Yes')]")
    WebElement cancelYesBtn;

    @FindBy(xpath = "//p[contains(.,'The selected jobs were cancelled.')]")
    WebElement cancelConfirmationAlert;

    @FindBy(xpath = "//h2[contains(.,'Cancel Jobs')]")
    WebElement cancelModalHeader;

    @FindBy(xpath = "//div[@class='modal-body'][contains(.,'Are you sure you want to cancel these jobs? All other jobs in the same collection will also be canceled. There is no undo.')]")
    WebElement cancelModalText;

    @FindBy(xpath = "//div[@class='alert alert-dismissable alert-danger'][contains(.,'jobs can be cancelled')]")
    WebElement canCancelAlert;

    /**
     * Getters
     */
    public WebElement getJobDetailsJobID() {
        return jobDetailsJobID;
    }

    public WebElement getFilterJobsCustomerID() {
        return filterJobsCustomerID;
    }

    public WebElement getFilterJobsTranslatorID() {
        return filterJobsTranslatorID;
    }

    public WebElement getFilterJobsOrderID() {
        return filterJobsOrderID;
    }

    public WebElement getFilterJobsCollectionID() {
        return filterJobsCollectionID;
    }

    public WebElement getJobActionBtnForceApprove(){
        return jobActionBtnForceApprove;
    }

    public WebElement getJobActionBtnRequestCheck() {
        return jobActionBtnRequestCheck;
    }

    public WebElement getJobActionBtnRequestCorrections() {
        return jobActionBtnRequestCorrections;
    }

    public WebElement getJobActionBtnComment() {
        return jobActionBtnComment;
    }

    public WebElement getJobActionBtnEdit() {
        return jobActionBtnEdit;
    }

    public WebElement getNextSetTable() {
        return nextSetTable;
    }

    public WebElement getActionsBtn() {
        return actionsBtn;
    }

    public WebElement getCancelLink() {
        return cancelLink;
    }

    public WebElement getYesBtn() {
        return yesBtn;
    }

    public WebElement getNoBtn() {
        return noBtn;
    }

    public WebElement getCancelYesBtn() {
        return cancelYesBtn;
    }

    public WebElement getCancelNoBtn() {
        return cancelNoBtn;
    }

    public WebElement getCancelConfirmationAlert() {
        return cancelConfirmationAlert;
    }

    public WebElement getCancelModalHeader() {
        return cancelModalHeader;
    }

    public WebElement getCancelModalText() {
        return cancelModalText;
    }

    public WebElement getCanCancelAlert() {
        return canCancelAlert;
    }

    public WebElement getFilterStatus() {
        return filterStatus;
    }

    public WebElement getFilterBtn() {
        return filterBtn;
    }

    public WebElement getFirstJobEntryID() {
        return firstJobEntryID;
    }

    public WebElement getFirstJobEntryDateCreated() {
        return firstJobEntryDateCreated;
    }

    public WebElement getFirstJobEntrySource() {
        return firstJobEntrySource;
    }

    public WebElement getFirstJobEntryTarget() {
        return firstJobEntryTarget;
    }

    public WebElement getFirstJobEntryLevel() {
        return firstJobEntryLevel;
    }

    public WebElement getFirstJobEntryStatus() {
        return firstJobEntryStatus;
    }
}
