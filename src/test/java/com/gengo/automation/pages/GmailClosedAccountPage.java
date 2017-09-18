package com.gengo.automation.pages;

import com.gengo.automation.pages.PageFactories.GmailClosedAccountPageFactory;
import org.openqa.selenium.WebDriver;

/**
 * @class Object repository for Gmail inbox close gengo page.
 * Contains element operations.
 */
public class GmailClosedAccountPage extends GmailClosedAccountPageFactory{

    public GmailClosedAccountPage(WebDriver driver) {
        super(driver);
    }
}
