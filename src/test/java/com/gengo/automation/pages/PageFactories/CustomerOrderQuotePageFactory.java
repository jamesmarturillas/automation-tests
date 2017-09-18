package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for customer order quote page.
 * Contains PageFactory initialization.
 */
public class CustomerOrderQuotePageFactory {

    protected WebDriver driver;
    public final String CUSTOMERORDERQUOTE_URL = "https://staging.gengo.com/order/mitsumori";

    public CustomerOrderQuotePageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//textarea[@name='quote-address-to']")
    WebElement addressTxtArea;

    @FindBy(id = "submit-address")
    WebElement addAddressBtn;

    @FindBy(xpath = "//div[@class='vcard to gd address']")
    WebElement addressEmbedded;

    @FindBy(id = "download-button")
    WebElement downloadBtn;

    @FindBy(id = "print")
    WebElement printBtn;

    @FindBy(xpath = "(//ul/li)[2]")
    WebElement unitPriceText;

    /**
     * Getters
     */
    public WebElement getAddressTxtArea() {
        return addressTxtArea;
    }

    public WebElement getAddAddressBtn() {
        return addAddressBtn;
    }

    public WebElement getAddressEmbedded() {
        return addressEmbedded;
    }

    public WebElement getDownloadBtn() {
        return downloadBtn;
    }

    public WebElement getPrintBtn() {
        return printBtn;
    }

    public WebElement getUnitPriceText() {
        return unitPriceText;
    }
}
