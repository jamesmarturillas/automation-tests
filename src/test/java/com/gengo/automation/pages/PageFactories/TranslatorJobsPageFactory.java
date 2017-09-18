package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Object repository for translator tests page.
 * Contains PageFactory initialization.
 */
public class TranslatorJobsPageFactory {

    protected WebDriver driver;

    public TranslatorJobsPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//a[@class='btn btn-default next-page-pagination']")
    WebElement nextButton;

    @FindBy(xpath = "//a[@class='btn btn-default prev-page-pagination']")
    WebElement previousButton;

    @FindBy(xpath = "//a[contains(.,'Incomplete')]")
    WebElement incompleteTab;

    @FindBy(xpath = "//h1[contains(.,' Incomplete jobs')]")
    WebElement incompleteHeader;

    @FindBy(xpath = "//a[contains(.,'Reviewable')]")
    WebElement reviewableTab;

    @FindBy(xpath = "//h1[contains(.,' Reviewable jobs')]")
    WebElement reviewableHeader;

    @FindBy(xpath = "//a[contains(.,'Held')]")
    WebElement heldTab;

    @FindBy(xpath = "//h1[contains(.,' Held jobs')]")
    WebElement heldHeader;

    @FindBy(xpath = "//input[@id='iagree']")
    WebElement agreeTermsCheck;

    @FindBy(xpath = "//a[@class='ui_btn primary_btn'][contains(.,'Continue')]")
    WebElement agreeTermsContinueBtn;

    @FindBy(id = "errorModalLabel")
    WebElement modalCantPickAnotherJob;

    /**
     * Getters
     */
    public WebElement getNextButton(){
        return nextButton;
    }

    public WebElement getPreviousButton(){
        return previousButton;
    }

    public WebElement getIncompleteTab() { return incompleteTab; }

    public WebElement getIncompleteHeader() {
        return incompleteHeader;
    }

    public WebElement getReviewableTab() { return reviewableTab; }

    public WebElement getReviewableHeader() {
        return reviewableHeader;
    }

    public WebElement getHeldTab() {
        return heldTab;
    }

    public WebElement getHeldHeader() {
        return heldHeader;
    }

    public WebElement getAgreeTermsCheck() {
        return agreeTermsCheck;
    }

    public WebElement getAgreeTermsContinueBtn() {
        return agreeTermsContinueBtn;
    }

    public WebElement getModalCantPickAnotherJob() {
        return modalCantPickAnotherJob;
    }
}
