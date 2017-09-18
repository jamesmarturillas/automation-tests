package com.gengo.automation.pages;

import com.gengo.automation.helpers.Switcher;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.GmailJobReviewedPageFactory;
import org.openqa.selenium.WebDriver;

/**
 * @class Object repository for Gmail inbox job reviewed page.
 * Contains element operations.
 */
public class GmailJobReviewedPage extends GmailJobReviewedPageFactory {

    public GmailJobReviewedPage(WebDriver driver) {
        super(driver);
    }

    private Switcher switcher = new Switcher(driver);
    private Wait wait = new Wait(driver);

    public void clickCheckFeedback() {
        String checkFeedback = getCheckFeedback().getAttribute("href");
        driver.get(checkFeedback);
        switcher.switchToAlertAndAccept();
        wait.impWait(10);
    }
}
