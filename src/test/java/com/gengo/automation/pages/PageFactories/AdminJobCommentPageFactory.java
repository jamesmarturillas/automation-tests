package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by rgonzaga on 7/19/17.
 */
public class AdminJobCommentPageFactory {

    protected WebDriver driver;

    public AdminJobCommentPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element Initialization
     * */
    @FindBy(xpath = "//textarea[@name='body']")
    WebElement commentBodyTextArea;

    @FindBy(xpath = "//button[contains(.,'Create')]")
    WebElement createBtn;

    @FindBy(xpath = "//div[@class='alert alert-dismissable alert-danger'][contains(.,'body cannot be empty')]")
    WebElement emptyCommentAlert;

    @FindBy(xpath = "//div[@class='alert alert-dismissable alert-success'][contains(.,'was created.')]")
    WebElement addedCommentAlert;

    /**
     * Getters
     * */
    public WebElement getCommentBodyTextArea() {
        return commentBodyTextArea;
    }

    public WebElement getCreateBtn() {
        return createBtn;
    }

    public WebElement getEmptyCommentAlert() {
        return emptyCommentAlert;
    }

    public WebElement getAddedCommentAlert() {
        return addedCommentAlert;
    }
}
