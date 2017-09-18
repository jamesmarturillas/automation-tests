package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AdminJobEditPageFactory {
    protected WebDriver driver;

    public AdminJobEditPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }


    /**
     * Element Initialization
     * */
    @FindBy(xpath = "//input[@id='text_title']")
    WebElement titleText;

    @FindBy(xpath = "//button[contains(.,'Update')]")
    WebElement updateBtn;

    /**
     * Getters
     * */
    public WebElement getTitleText() {
        return titleText;
    }

    public WebElement getUpdateBtn() {
        return updateBtn;
    }
}
