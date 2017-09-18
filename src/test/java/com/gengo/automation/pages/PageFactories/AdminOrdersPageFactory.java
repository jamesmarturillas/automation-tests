package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for Admin-Orders page.
 */
public class AdminOrdersPageFactory {

    protected WebDriver driver;

    public AdminOrdersPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /** Order' filters locators */
    @FindBy(xpath = "//label[contains(.,'Status')]/following-sibling::select")
    WebElement filterStatus;

    @FindBy(xpath = "//button[@id='btn-filter']")
    WebElement filterBtn;

    @FindBy(xpath = "//*[@id='table-items']/tbody/tr[1]/td[2]/a")
    WebElement firstOrderEntryID;

    @FindBy(xpath = "//*[@id='table-items']/tbody/tr[1]/td[3]")
    WebElement firstOrderEntryDateCreated;

    @FindBy(xpath = "//*[@id='table-items']/tbody/tr[1]/td[5]")
    WebElement firstOrderEntryStatus;

    @FindBy(xpath = "//*[@id='table-items']/tbody/tr[1]/td[6]")
    WebElement firstOrderEntryJobsCount;

    @FindBy(xpath = "//*[@id='table-items']/tbody/tr[1]/td[7]")
    WebElement firstOrderEntryCollectionsCount;

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

    @FindBy(xpath = "//p[contains(.,'All available jobs have been cancelled.')]")
    WebElement cancelConfirmationAlert;


    @FindBy(xpath = "//div[@class='alert alert-dismissable alert-danger'][contains(.,'Failed to cancel orders')]")
    WebElement cancelFailAlert;

    @FindBy(xpath = "//h2[contains(.,'Cancel Order')]")
    WebElement cancelModalHeader;

    @FindBy(xpath = "//div[@class='modal-body'][contains(.,'Are you sure you want to cancel this order? There is no undo.')]")
    WebElement cancelModalText;

    @FindBy(xpath = "//div[@class='alert alert-dismissable alert-danger'][contains(.,'jobs can be cancelled')]")
    WebElement canCancelAlert;

    /**
     * Getters
     */

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

    public WebElement getCancelFailAlert() {
        return cancelFailAlert;
    }

    public WebElement getFilterStatus() {
        return filterStatus;
    }

    public WebElement getFilterBtn() {
        return filterBtn;
    }

    public WebElement getFirstOrderEntryID() {
        return firstOrderEntryID;
    }

    public WebElement getFirstOrderEntryDateCreated() {
        return firstOrderEntryDateCreated;
    }

    public WebElement getFirstOrderEntryStatus() {
        return firstOrderEntryStatus;
    }

    public WebElement getFirstOrderEntryJobsCount() {
        return firstOrderEntryJobsCount;
    }

    public WebElement getFirstOrderEntryCollectionsCount() {
        return firstOrderEntryCollectionsCount;
    }
}
