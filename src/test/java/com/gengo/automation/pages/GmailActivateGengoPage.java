package com.gengo.automation.pages;

import com.gengo.automation.helpers.Switcher;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.GmailActivateGengoPageFactory;
import org.openqa.selenium.WebDriver;

/**
 * @class Object repository for Gmail inbox activate gengo account page.
 * Contains element operations.
 */
public class GmailActivateGengoPage extends GmailActivateGengoPageFactory{

    public GmailActivateGengoPage(WebDriver driver) {
        super(driver);
    }

    private Switcher switcher = new Switcher(driver);
    private Wait wait = new Wait(driver);

    public void clickActivateAccount() {
        String activateAccount = getActivateAccountBtn().getAttribute("href");
        driver.get(activateAccount);
        switcher.switchToAlertAndAccept();
        wait.impWait(10);
    }
}
