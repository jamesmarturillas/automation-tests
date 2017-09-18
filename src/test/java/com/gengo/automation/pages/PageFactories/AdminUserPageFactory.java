package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for Admin user page.
 * Contains PageFactory initialization.
 */
public class AdminUserPageFactory {

    protected WebDriver driver;

    public AdminUserPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//h1[@class='pull-left']")
    WebElement userID;

    @FindBy(xpath = "//a[@class='unlink']")
    WebElement unlink;

    @FindBy(id = "detail-email")
    WebElement editableEmail;

    @FindBy(xpath = "//input[@class='form-control input-sm']")
    WebElement editEmailTxtBox;

    @FindBy(xpath = "//button[@class='btn btn-primary btn-sm editable-submit']")
    WebElement submitEditedBtn;

    @FindBy(xpath = "//p[contains(text(), 'User attribute updated.')]")
    WebElement successEditMsg;

    @FindBy(xpath = "//td[contains(.,'Rewards')]/following-sibling::td")
    WebElement rewardsAmt;

    /**
     * Getters
     */
    public WebElement getUserID() {
        return userID;
    }

    public WebElement getUnlink() {
        return unlink;
    }

    public WebElement getEditableEmail() {
        return editableEmail;
    }

    public WebElement getEditEmailTxtBox() {
        return editEmailTxtBox;
    }

    public WebElement getSubmitEditedBtn() {
        return submitEditedBtn;
    }

    public WebElement getSuccessEditMsg() {
        return successEditMsg;
    }

    public WebElement getRewardsAmt() {return rewardsAmt;}
}

