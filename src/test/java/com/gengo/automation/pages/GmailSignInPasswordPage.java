package com.gengo.automation.pages;

import com.gengo.automation.pages.PageFactories.GmailSignInPasswordPageFactory;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

/**
 * @class Object repository for Gmail password inputting page.
 * Contains element operations.
 */
public class GmailSignInPasswordPage extends GmailSignInPasswordPageFactory {

    public GmailSignInPasswordPage(WebDriver driver) {
        super(driver);
    }

    public void inputPasswordAndSubmit(String password) {
        getTxtBoxGmailPassword().sendKeys(password);
        getTxtBoxGmailPassword().sendKeys(Keys.ENTER);
    }
}
