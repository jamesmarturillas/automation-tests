package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for Review page.
 */
public class ReviewPageFactory {

    protected WebDriver driver;

    public ReviewPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /** 'Review' locators */
    @FindBy(xpath = "//div[@id='footer-area']/label[contains(.,'Feedback to the translator:')]")
    WebElement feedbackArea;

    @FindBy(xpath = "//p[@id='feedback_comment']")
    WebElement feedbackContent;

    @FindBy(xpath = "//div[@id='content']/p[@class='content']")
    WebElement targetContent;

    /**
     * Getters
     */
    public WebElement getFeedbackArea() {
        return feedbackArea;
    }

    public WebElement getFeedbackContent() {
        return feedbackContent;
    }

    public WebElement getTargetContent() {
        return targetContent;
    }
}
