package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for Gmail inbox welcome to gengo account page.
 * Contains PageFactory initialization.
 */
public class GmailWelcomeToGengoPageFactory {

    protected WebDriver driver;

    public GmailWelcomeToGengoPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//a[contains(text(), 'Order Translation')]")
    WebElement orderTranslationBtn;

    @FindBy(xpath = "//a[contains(text(), 'Take Test')]")
    WebElement takeTestBtn;

    /**
     * Getters
     */
    public WebElement getOrderTranslationBtn() {
        return orderTranslationBtn;
    }

    public WebElement getTakeTestBtn() {
        return takeTestBtn;
    }
}
