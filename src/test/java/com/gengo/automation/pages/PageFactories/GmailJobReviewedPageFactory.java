package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for Gmail inbox job reviewed page.
 * Contains PageFactory initialization.
 */
public class GmailJobReviewedPageFactory {

    protected WebDriver driver;

    public GmailJobReviewedPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//a[contains(text(), 'Check feedback')]")
    WebElement checkFeedback;

    /**
     * Getters
     */
    public WebElement getCheckFeedback() {
        return checkFeedback;
    }

}