package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Sample template for PageFactory class.
 * Contains PageFactory initialization.
 */
public class CustomerOrderCompletePageFactory {

    protected WebDriver driver;
    public final String ORDERCOMPLETE_URL = "https://staging.gengo.com/order/complete/";

    public CustomerOrderCompletePageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//div[@id='complete_section']")
    WebElement completePromptBody;

    @FindBy(id = "to_back_dashboard_btn")
    WebElement backToDashBoardBtn;

    @FindBy(id = "to_adding_contents_btn")
    WebElement createAnotherOrderBtn;

    @FindBy(id = "add-payment-method-link")
    WebElement accountSettingsLink;

    @FindBy(xpath = "//p[contains(.,'Your order number is')]/b")
    WebElement orderNumber;

    @FindBy(xpath = "//th[@class='quote-value']")
    WebElement orderTotalPrice;

    @FindBy(xpath = "//tr[contains(., 'Quality level:')]/td[@class='quote-value']")
    WebElement orderPricePerUnit;

    @FindBy(xpath = "//tr[contains(.,'item')]/td[@class='quote-value']/span[@class='count-text']")
    WebElement unitCount;

    /**
     * Getters
     */
    public WebElement getCompletePromptBody() {
        return completePromptBody;
    }

    public WebElement getBackToDashBoardBtn() {
        return backToDashBoardBtn;
    }

    public WebElement getCreateAnotherOrderBtn() {
        return createAnotherOrderBtn;
    }

    public WebElement getAccountSettingsLink() {
        return accountSettingsLink;
    }

    public WebElement getOrderNumber(){
        return orderNumber;
    }

    public WebElement getUnitCount() {
        return unitCount;
    }

    public WebElement getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public WebElement getOrderPricePerUnit() {
        return orderPricePerUnit;
    }
}
