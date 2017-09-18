package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for customer order optional (checkout) page.
 * Contains PageFactory initialization.
 */
public class CustomerCheckoutPageFactory {

    protected WebDriver driver;
    public final String CUSTOMEROPTIONAL_URL = "https://staging.gengo.com/order/optional";

    public CustomerCheckoutPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    /**
     * Element initialization
     */
    @FindBy(id = "purpose_select_btn")
    WebElement choosePurposeDropDown;

    @FindBy(xpath = "//li[@class='purpose_line dropdown-submenu']/a[contains(text(), 'Everyday use')]")
    WebElement purposeEverydayUse;

    @FindBy(xpath = "//li[@class='purpose_line dropdown-submenu']/a[contains(text(), 'Online content')]")
    WebElement purposeOnlineContent;

    @FindBy(xpath = "//li[@class='purpose_line dropdown-submenu']/a[contains(text(), 'Professional use')]")
    WebElement purposeProfessionalUse;

    @FindBy(xpath = "//li[@class='purpose_line dropdown-submenu']/a[contains(text(), 'App/Web localization')]")
    WebElement purposeAppWebLocalization;

    @FindBy(xpath = "//li[@class='purpose_line dropdown-submenu']/a[contains(text(), 'Media/Publishing')]")
    WebElement purposeMediaPublishing;

    @FindBy(xpath = "//li[@class='purpose_line dropdown-submenu']/a[contains(text(), 'Specialized translation')]")
    WebElement purposeSpecializedTranslation;

    @FindBy(xpath = "//input[@value='standard']")
    WebElement tierStandard;

    @FindBy(xpath = "//input[@value='pro']")
    WebElement tierPro;

    @FindBy(xpath = "//a[@id='download_quotes_btn']")
    WebElement viewFullQuoteBtn;

    @FindBy(xpath = "//input[@value='stripe']")
    WebElement stripeRadio;

    @FindBy(xpath = "//input[@value='paypal']")
    WebElement paypalRadio;

    @FindBy(xpath = "//div[@class='box advanced_option_expand_section']")
    WebElement advanceOption;

    @FindBy(xpath = "//div[@class='box advanced_option_expand_section_detail']")
    WebElement advanceOptionOpen;

    @FindBy(xpath = "//input[@id='group_order_chk']")
    WebElement useOneTranslatorChkBox;

    @FindBy(id = "preferred_translator_chk")
    WebElement preferredTranslatorChkBox;

    @FindBy(xpath = "//textarea[@id='instructions']")
    WebElement intructionsTxtArea;

    @FindBy(id = "change_complete_btn")
    WebElement payAndConfirmBtn;

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
    WebElement submitBtn;

    @FindBy(id = "glossary_select_btn")
    WebElement chooseGlossary;

    @FindBy(xpath = "//button[@id='tone']")
    WebElement toneDropDown;

    @FindBy(xpath = "//ul[@id='tone-dropdown']/li[@data-value='informal']")
    WebElement toneInformal;

    @FindBy(xpath = "//ul[@id='tone-dropdown']/li[@data-value='friendly']")
    WebElement toneFriendly;

    @FindBy(xpath = "//ul[@id='tone-dropdown']/li[@data-value='business']")
    WebElement toneBusiness;

    @FindBy(xpath = "//ul[@id='tone-dropdown']/li[@data-value='formal']")
    WebElement toneFormal;

    @FindBy(xpath = "//ul[@id='tone-dropdown']/li[@data-value='other']")
    WebElement toneOther;

    @FindBy(xpath = "//div[@class='Button-content']/span/span")
    WebElement stripePrice;

    @FindBy(xpath = "//td[contains(.,'Order total')]/following-sibling::td[@class='quote-value']")
    WebElement orderTotalPrice;

    @FindBy(xpath = "//td[@class='total']")
    WebElement fullQuoteTotalPrice;

    @FindBy(xpath = "//td[@class='quote-value'][contains(.,'/ word') or contains(.,'/ character')]")
    WebElement unitPriceText;

    @FindBy(xpath = "(//p[@class='credits'][contains(.,'word')or contains(.,'character')])[1]")
    WebElement standardUnitPriceText;

    @FindBy(xpath = "(//p[@class='credits'][contains(.,'word')or contains(.,'character')])[2]")
    WebElement businessUnitPriceText;

    /**
     * Getters
     */
    public WebElement getChoosePurposeDropDown() {
        return choosePurposeDropDown;
    }

    public WebElement getPurposeEverydayUse() {
        return purposeEverydayUse;
    }

    public WebElement getPurposeOnlineContent() {
        return purposeOnlineContent;
    }

    public WebElement getPurposeProfessionalUse() {
        return purposeProfessionalUse;
    }

    public WebElement getPurposeAppWebLocalization() {
        return purposeAppWebLocalization;
    }

    public WebElement getPurposeMediaPublishing() {
        return purposeMediaPublishing;
    }

    public WebElement getPurposeSpecializedTranslation() {
        return purposeSpecializedTranslation;
    }

    public WebElement getTierStandard() {
        return tierStandard;
    }

    public WebElement getTierPro() {
        return tierPro;
    }

    public WebElement getViewFullQuoteBtn() {
        return viewFullQuoteBtn;
    }

    public WebElement getStripeRadio() {
        return stripeRadio;
    }

    public WebElement getPaypalRadio() {
        return paypalRadio;
    }

    public WebElement getAdvanceOption() {
        return advanceOption;
    }

    public WebElement getAdvanceOptionOpen() {
        return advanceOptionOpen;
    }

    public WebElement getUseOneTranslatorChkBox() {
        return useOneTranslatorChkBox;
    }

    public WebElement getPreferredTranslatorChkBox() {
        return preferredTranslatorChkBox;
    }

    public WebElement getIntructionsTxtArea() {
        return intructionsTxtArea;
    }

    public WebElement getPayAndConfirmBtn() {
        return payAndConfirmBtn;
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

    public WebElement getSubmitBtn() {
        return submitBtn;
    }

    public WebElement getChooseGlossary() {
        return chooseGlossary;
    }

    public WebElement getToneDropDown() {
        return toneDropDown;
    }

    public WebElement getToneInformal() {
        return toneInformal;
    }

    public WebElement getToneFriendly() {
        return toneFriendly;
    }

    public WebElement getToneBusiness() {
        return toneBusiness;
    }

    public WebElement getToneFormal() {
        return toneFormal;
    }

    public WebElement getToneOther() {
        return toneOther;
    }

    public WebElement getStripePrice() {
        return  stripePrice;
    }

    public WebElement getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public WebElement getFullQuoteTotalPrice() {
        return fullQuoteTotalPrice;
    }

    public WebElement getUnitPriceText() {
        return unitPriceText;
    }

    public WebElement getStandardUnitPriceText() {
        return standardUnitPriceText;
    }

    public WebElement getBusinessUnitPriceText() {
        return businessUnitPriceText;
    }
}
