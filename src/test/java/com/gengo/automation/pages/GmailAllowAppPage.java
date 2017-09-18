package com.gengo.automation.pages;

import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.GmailAllowAppPageFactory;
import org.openqa.selenium.WebDriver;

/**
 * @class Object repository for Gmail allowing app access page.
 * Contains element operations.
 */
public class GmailAllowAppPage extends GmailAllowAppPageFactory {

    public GmailAllowAppPage(WebDriver driver) {
        super(driver);
    }
    private Wait wait = new Wait(driver);

    public void clickAllow() {
        wait.impWait(60, getAllowAccess());
        wait.untilElementIsClickable(getAllowAccess());
        getAllowAccess().click();
    }

    public void clickDeny() {
        wait.untilElementIsClickable(getDenyAccess());
        getDenyAccess().click();
    }
}
