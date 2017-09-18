package com.gengo.automation.pages;

import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.GmailSignInEmailPageFactory;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

/**
 * @class Object repository for Gmail email inputting page.
 * Contains element operations.
 */
public class GmailSignInEmailPage extends GmailSignInEmailPageFactory {

    public GmailSignInEmailPage(WebDriver driver) {
        super(driver);
    }
    private Wait wait = new Wait(driver);
    private GmailSignInPasswordPage gmailSignInPasswordPage = new GmailSignInPasswordPage(driver);

    public void inputEmail(String email) {
        getTxtBoxEmail().sendKeys(email);
        getTxtBoxEmail().sendKeys(Keys.ENTER);
    }
}
