package com.gengo.automation.pages;

import com.gengo.automation.fields.Variables;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.CustomerOrderQuotePageFactory;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

/**
 * @class Object repository for customer order quote page.
 * Contains element operations.
 */
public class CustomerOrderQuotePage extends CustomerOrderQuotePageFactory {

    public CustomerOrderQuotePage(WebDriver driver) throws IOException {
        super(driver);
    }
    private Variables var = new Variables();
    private Wait wait = new Wait(driver);

    public void typeAdress() {
        wait.impWait(20, getAddressTxtArea());
        getAddressTxtArea().sendKeys(var.getAddress1());
        getAddAddressBtn().click();
        wait.impWait(10, getAddressEmbedded());
    }

    public void downloadQuote() {
        getDownloadBtn().click();
        wait.impWait(20);
        driver.close();
    }
}
