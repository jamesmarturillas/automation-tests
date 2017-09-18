package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for Admin-Orders Details page.
 */
public class AdminOrderDetailsPageFactory {
    protected WebDriver driver;

    public AdminOrderDetailsPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
