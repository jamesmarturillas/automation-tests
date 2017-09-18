package com.gengo.automation.pages;

import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.AdminNewQualificationsPageFactory;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class AdminNewQualificationsPage extends AdminNewQualificationsPageFactory {

    public AdminNewQualificationsPage(WebDriver driver) {
        super(driver);
    }
    private Wait wait = new Wait(driver);

    public boolean isAlertVisible() {
        try{
            return getAlertMsg().isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }
}
