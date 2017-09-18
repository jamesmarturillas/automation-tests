package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for Gmail user comment page.
 * Contains PageFactory initialization.
 */
public class GmailUserCommentPageFactory {

    protected WebDriver driver;

    public GmailUserCommentPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//a[contains(text(), 'Reply')]")
    WebElement replyBtn;

    /**
     * Getters
     */
    public WebElement getReplyBtn() {
        return replyBtn;
    }
}
