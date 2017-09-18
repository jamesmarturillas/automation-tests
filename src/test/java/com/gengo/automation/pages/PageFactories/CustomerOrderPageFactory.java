package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for customer order (not list of jobs) page.
 * Contains PageFactory initialization.
 */
public class CustomerOrderPageFactory {

    protected WebDriver driver;

    public CustomerOrderPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//a[contains(@class, 'orange-link show-comments')]")
    WebElement addReadCommentsLink;

    @FindBy(xpath = "//a[contains(text(), 'Hide comments')]")
    WebElement hideCommentsLink;

    @FindBy(xpath = "//ol[contains(@class, 'comments-list')]")
    WebElement commentBlock;

    @FindBy(xpath = "//div[@class='textarea_wrap']/textarea[@name='body']")
    WebElement addCommentTxtArea;

    @FindBy(xpath = "//a[contains(@class, 'ui_btn module_btn add-comment')]")
    WebElement submitCommentBtn;

    /**
     * Getters
     */
    public WebElement getAddReadCommentsLink() {
        return addReadCommentsLink;
    }

    public WebElement getHideCommentsLink() {
        return hideCommentsLink;
    }

    public WebElement getCommentBlock() {
        return commentBlock;
    }

    public WebElement getAddCommentTxtArea() {
        return addCommentTxtArea;
    }

    public WebElement getSubmitCommentBtn() {
        return submitCommentBtn;
    }
}
