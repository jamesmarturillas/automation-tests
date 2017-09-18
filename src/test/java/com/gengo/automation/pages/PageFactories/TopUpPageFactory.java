package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for top up page.
 * Contains PageFactory initialization.
 */
public class TopUpPageFactory {

    protected WebDriver driver;
    public final String TOPUP_URL = "staging.gengo.com/account/top_up/";

    public TopUpPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//div[@id='credits-you-pay-box']/div/button[@class='btn btn-default dropdown-toggle']")
    WebElement amountDropdown;

    /** Options can be seen at DataSource.xls -> TopUpAmounts sheet. */
    @FindBy(xpath = "//div[@id='credits-you-pay-box']/div/ul[@class='dropdown-menu']")
    WebElement amountOption;

    @FindBy(xpath = "//input[@name='paymentMethod' and @value='credit']")
    WebElement creditRadioBtn;

    @FindBy(xpath = "//input[@name='paymentMethod' and @value='paypal']")
    WebElement paypalRadioBtn;

    @FindBy(id = "pay-btn")
    WebElement payBtn;

    @FindBy(xpath = "//iframe[@name='stripe_checkout_app']")
    WebElement stripeModalIframe;

    @FindBy(xpath = "//h1[@class='Header-companyName u-textTruncate']")
    WebElement stripePaymentTxtCriteria;

    @FindBy(xpath = "//input[@placeholder='Card number']")
    WebElement creditCardTxtBox;

    @FindBy(xpath = "//input[@placeholder='MM / YY']")
    WebElement cardDateExpiryTxtBox;

    @FindBy(xpath = "//input[@placeholder='CVC']")
    WebElement cvcTxtBox;

    @FindBy(xpath = "//input[@placeholder='ZIP Code']")
    WebElement zipCodeTxtBox;

    @FindBy(xpath = "//button[@class='Button-animationWrapper-child--primary Button']")
    WebElement stripeSubmitBtn;

    @FindBy(xpath = "//div[contains(@class, 'flashdata')]")
    WebElement topUpPageErrorMsg;

    @FindBy(xpath = "//div[contains(@class, 'flashdata') and contains(., 'Your credit balance has been updated.')]")
    WebElement topUpSuccessMsg;

    @FindBy(xpath = "//div[@class='Popover-content' and contains(text(), 'This card was declined.')]")
    WebElement topUpDeclinedCardErrorMsg;

    @FindBy(xpath = "//div[@class='Popover-content' and contains(text(), 'An error occurred while processing your card. Try again in a little bit.')]")
    WebElement topUpProcessingCardError;

    @FindBy(xpath = "//div[@class='Fieldset-childRight u-size1of2 Textbox Textbox--iconLeft can-setfocus is-invalid Fieldset-child--focused']")
    WebElement errorBoxRight;

    @FindBy(xpath = "//div[@class='Fieldset-childLeft u-size1of2 Textbox Textbox--iconLeft can-setfocus is-invalid Fieldset-child--focused']")
    WebElement errorBoxLeft;

    @FindBy(xpath = "//span[@class='Header-navClose']")
    WebElement closeStripeModal;

    @FindBy(xpath = "//div[@class='credit']/b")
    WebElement creditsBalance;

    @FindBy(xpath = "//div[@class='input-group-addon currency']")
    WebElement topUpCurrency;

    /**
     * Getters
     */
    public WebElement getAmountDropdown() {
        return amountDropdown;
    }

    public WebElement getAmountOption() {
        return amountOption;
    }

    public WebElement getCreditRadioBtn() {
        return creditRadioBtn;
    }

    public WebElement getPaypalRadioBtn() {
        return paypalRadioBtn;
    }

    public WebElement getPayBtn() {
        return payBtn;
    }

    public WebElement getStripeModalIframe() {
        return stripeModalIframe;
    }

    public WebElement getStripePaymentTxtCriteria() {
        return stripePaymentTxtCriteria;
    }

    public WebElement getCreditCardTxtBox() {
        return creditCardTxtBox;
    }

    public WebElement getCardDateExpiryTxtBox() {
        return cardDateExpiryTxtBox;
    }

    public WebElement getCvcTxtBox() {
        return cvcTxtBox;
    }

    public WebElement getZipCodeTxtBox() {
        return zipCodeTxtBox;
    }

    public WebElement getStripeSubmitBtn() {
        return stripeSubmitBtn;
    }

    public WebElement getTopUpPageErrorMsg() {
        return topUpPageErrorMsg;
    }

    public WebElement getTopUpSuccessMsg() {
        return topUpSuccessMsg;
    }

    public WebElement getTopUpDeclinedCardErrorMsg() {
        return topUpDeclinedCardErrorMsg;
    }

    public WebElement getTopUpProcessingCardError() {
        return topUpProcessingCardError;
    }

    public WebElement getErrorBoxRight() {
        return errorBoxRight;
    }

    public WebElement getErrorBoxLeft() {
        return errorBoxLeft;
    }

    public WebElement getCloseStripeModal() {
        return closeStripeModal;
    }

    public WebElement getCreditsBalance() {
        return creditsBalance;
    }

    public WebElement getTopUpCurrency() {
        return topUpCurrency;
    }
}
