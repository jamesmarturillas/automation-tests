package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for Admin Job Details Page
 * Contains PageFactory initialization.
 */
public class AdminJobDetailsPageFactory {

    protected WebDriver driver;

    public AdminJobDetailsPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//button[contains(.,'Actions')]")
    WebElement actionsBtn;

    @FindBy(xpath = "//a[@id='action-comment']")
    WebElement commentBtn;

    @FindBy(xpath = "//a[@id='action-request-check']")
    WebElement requestCheckBtn;

    @FindBy(xpath = "//a[@id='action-edit']")
    WebElement editBtn;

    @FindBy(xpath = "//h1[contains(.,'Job #')]")
    WebElement jobIDNo;

    @FindBy(xpath = "//td[contains(.,'Status')]/following-sibling::td")
    WebElement jobStatus;

    @FindBy(xpath = "//td[contains(.,'Level')]/following-sibling::td")
    WebElement jobLevel;

    @FindBy(xpath = "//td[contains(.,'Date Created')]/following-sibling::td")
    WebElement jobDateCreated;

    @FindBy(xpath = "//div/h2[contains(.,'Source')]/parent::div/following-sibling::div/span")
    WebElement jobSource;

    @FindBy(xpath = "//div/h2[contains(.,'Target')]/parent::div/following-sibling::div/span")
    WebElement jobTarget;

    @FindBy(xpath = "//h2[contains(.,'Title')]/parent::div/parent::div/following-sibling::pre")
    WebElement jobTitle;

    @FindBy(xpath = ".//*[@id='notification']/div[contains(.,'was updated')]")
    WebElement successUpdateText;

    /**
     * Getters
     */
    public WebElement getActionsBtn() {
        return actionsBtn;
    }

    public WebElement getRequestCheckBtn() {
        return requestCheckBtn;
    }

    public WebElement getCommentBtn() {
        return commentBtn;
    }

    public WebElement getEditBtn() {
        return editBtn;
    }

    public WebElement getJobIDNo() {
        return jobIDNo;
    }

    public WebElement getJobStatus() {
        return jobStatus;
    }

    public WebElement getJobLevel() {
        return jobLevel;
    }

    public WebElement getJobDateCreated() {
        return jobDateCreated;
    }

    public WebElement getJobSource() {
        return jobSource;
    }

    public WebElement getJobTarget() {
        return jobTarget;
    }

    public WebElement getJobTitle() {
        return jobTitle;
    }

    public WebElement getSuccessUpdateText() {
        return successUpdateText;
    }
}
