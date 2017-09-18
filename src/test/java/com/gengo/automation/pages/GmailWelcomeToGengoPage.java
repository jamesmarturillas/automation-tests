package com.gengo.automation.pages;

import com.gengo.automation.helpers.Switcher;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.GmailWelcomeToGengoPageFactory;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

/**
 * @class Object repository for Gmail inbox order translation page.
 * Contains element operations.
 */
public class GmailWelcomeToGengoPage extends GmailWelcomeToGengoPageFactory {

    public GmailWelcomeToGengoPage(WebDriver driver) throws IOException {
        super(driver);
    }
    private Wait wait = new Wait(driver);

    private CustomerOrderFormPage customerOrderFormPage = new CustomerOrderFormPage(driver);
    private TranslatorOnboardPage translatorOnboardPage = new TranslatorOnboardPage(driver);
    private Switcher switcher = new Switcher(driver);

    public void clickOrderTranslation() {
        String orderTranslation = getOrderTranslationBtn().getAttribute("href");
        driver.get(orderTranslation);
        switcher.switchToAlertAndAccept();
        wait.impWait(10, customerOrderFormPage.getOrderFormTextArea());
    }

    public void clickTakeTest() {
        String takeTest = getTakeTestBtn().getAttribute("href");
        driver.get(takeTest);
        switcher.switchToAlertAndAccept();
        wait.impWait(10,translatorOnboardPage.getTranslatorOnboardText());
    }
}
