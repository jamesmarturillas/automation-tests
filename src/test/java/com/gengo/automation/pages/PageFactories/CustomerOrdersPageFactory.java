package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Object repository for customer orders page.
 * Contains PageFactory initialization.
 */
public class CustomerOrdersPageFactory {

    protected WebDriver driver;

    public CustomerOrdersPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//button[@class='btn btn-primary']")
    WebElement orderTranslationBtn;

    @FindBy(name = "search_input")
    WebElement searchTextBox;

    @FindBy(id = "//ul[@class='dropdown-menu']/li[contains(.,'By job #')]")
    WebElement searchByJobNo;

    @FindBy(id = "//ul[@class='dropdown-menu']/li[contains(.,'By term')]")
    WebElement searchByTerm;

    @FindBy(xpath = "//h1[contains(.,'Reviewable')]")
    WebElement reviewableHeader;

    @FindBy(xpath = "//a[contains(.,'Reviewable')]")
    WebElement reviewableOption;

    @FindBy(xpath = "//li[@class='active']/a[contains(.,'Reviewable')]")
    WebElement reviewableOptionActive;

    @FindBy(xpath = "//h1[contains(.,'Held')]")
    WebElement heldHeader;

    @FindBy(xpath = "//a[contains(.,'Held')]")
    WebElement heldOption;

    @FindBy(xpath = "//li[@class='active']/a[contains(.,'Held')]")
    WebElement heldOptionActive;

    @FindBy(xpath = "//h1[contains(.,'Pending')]")
    WebElement pendingHeader;

    @FindBy(xpath = "//a[contains(.,'Pending')]")
    WebElement pendingOption;

    @FindBy(xpath = "//li[@class='active']/a[contains(.,'Pending')]")
    WebElement pendingOptionActive;

    @FindBy(xpath = "//h1[contains(.,'Revising')]")
    WebElement revisingHeader;

    @FindBy(xpath = "//a[contains(.,'Revising')]")
    WebElement revisingOption;

    @FindBy(xpath = "//li[@class='active']/a[contains(.,'Revising')]")
    WebElement revisingOptionActive;

    @FindBy(xpath = "//h1[contains(.,'Approved')]")
    WebElement approvedHeader;

    @FindBy(xpath = "//a[contains(.,'Approved')]")
    WebElement approvedOption;

    @FindBy(xpath = "//li[@class='active']/a[contains(.,'Approved')]")
    WebElement approvedOptionActive;

    @FindBy(xpath = "//a[contains(.,'All')]")
    WebElement allOption;

    @FindBy(xpath = "//div[@class='basic-info']")
    WebElement collectionInfoHolder;

    @FindBy(xpath = "//span[@class='reserved-toggle-img']")
    WebElement preferredJobIcon;

    @FindBy(xpath = "//div[@class='reserved-box-content']")
    WebElement preferredJobIconMsg;

    /**
     * Getters
     */
    public WebElement getOrderTranslationBtn() {
        return orderTranslationBtn;
    }

    public WebElement getSearchTextBox() {
        return searchTextBox;
    }

    public WebElement getSearchByJobNo() {
        return searchByJobNo;
    }

    public WebElement getSearchByTerm() {
        return searchByTerm;
    }

    public WebElement getReviewableHeader() {
        return reviewableHeader;
    }

    public WebElement getReviewableOption() {
        return reviewableOption;
    }

    public WebElement getReviewableOptionActive() {
        return reviewableOptionActive;
    }

    public WebElement getHeldHeader() {
        return heldHeader;
    }

    public WebElement getHeldOption() {
        return heldOption;
    }

    public WebElement getHeldOptionActive() {
        return heldOptionActive;
    }

    public WebElement getPendingHeader() {
        return pendingHeader;
    }

    public WebElement getPendingOption() {
        return pendingOption;
    }

    public WebElement getPendingOptionActive() {
        return pendingOptionActive;
    }

    public WebElement getRevisingHeader() {
        return revisingHeader;
    }

    public WebElement getRevisingOption() {
        return revisingOption;
    }

    public WebElement getRevisingOptionActive() {
        return revisingOptionActive;
    }

    public WebElement getApprovedHeader() {
        return approvedHeader;
    }

    public WebElement getApprovedOption() {
        return approvedOption;
    }

    public WebElement getApprovedOptionActive() {
        return approvedOptionActive;
    }

    public WebElement getAllOption() {
        return allOption;
    }

    public WebElement getCollectionInfoHolder() {
        return collectionInfoHolder;
    }

    public WebElement getPreferredJobIcon() {
        return preferredJobIcon;
    }

    public WebElement getPreferredJobIconMsg() {
        return preferredJobIconMsg;
    }
}
