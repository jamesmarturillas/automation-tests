package com.gengo.automation.pages;

import com.gengo.automation.pages.PageFactories.AdminJobEditPageFactory;
import org.openqa.selenium.WebDriver;

public class AdminJobEditPage extends AdminJobEditPageFactory{
    public AdminJobEditPage(WebDriver driver) {
        super(driver);
    }

    public void editTitle(String newTitle) {
        getTitleText().clear();
        getTitleText().sendKeys(newTitle);
    }
}
