package com.gengo.automation.pages;

import com.gengo.automation.helpers.Switcher;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.GmailActivateGengoPageFactory;
import com.gengo.automation.pages.PageFactories.GmailChangeEmailPageFactory;
import org.openqa.selenium.WebDriver;

/**
 * @class Object repository for Gmail inbox change Gengo email account page.
 * Contains element operations.
 */
public class GmailChangeEmailPage extends GmailChangeEmailPageFactory {

    public GmailChangeEmailPage(WebDriver driver) {
        super(driver);
    }

    private Switcher switcher = new Switcher(driver);
    private Wait wait = new Wait(driver);

    public void clickChangeEmailBtn() {
        String changeEmailURL = getChangeEmailAddressBtn().getAttribute("href");
        driver.get(changeEmailURL);
        switcher.switchToAlertAndAccept();
        wait.impWait(10);
    }
}
