package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for PayPal payment page.
 * Contains PageFactory initialization.
 */
public class PayPalPageFactory {

    protected WebDriver driver;
    public final String NEW_UI_FRAME_NAME = "injectedUl";

    public PayPalPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//*[contains(., 'Test Store')]")
    WebElement paypalPageTxtCriteria;

    @FindBy(name = "login_email")
    WebElement newUiEmailTxtBox;

    @FindBy(xpath = "//input[@name='login_password' and @class='hasHelp  validateEmpty   pin-password']")
    WebElement newUiPasswordTxtBox;

    @FindBy(xpath = "//div[@id='spinner']")
    WebElement newUiLoadingAnimation;

    @FindBy(xpath = "//input[@id='confirmButtonTop']")
    WebElement newUiPayNowBtn;

    @FindBy(xpath = "//input[@id='loadLogin']")
    WebElement payWithPayPal;

    @FindBy(xpath = "//div[@id='progressMeter']/img")
    WebElement oldUiLoadingAnimation;

    @FindBy(xpath = "//div[@id='panelMask'][@class='']/div/div/div[@id='progressMeter']")
    WebElement oldUiAfterPayLoadingAnimation;

    @FindBy(id = "login_email")
    WebElement oldUiEmailTxtBox;

    @FindBy(id = "login_password")
    WebElement oldUiPasswordTxtBox;

    @FindBy(xpath = "//input[@id='continue_abovefold']")
    WebElement oldUiPayNowBtn;

    /**
     * Getters
     */

    public WebElement getPaypalPageTxtCriteria() {
        return paypalPageTxtCriteria;
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ NEW UI ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public WebElement getNewUiEmailTxtBox() {
        return newUiEmailTxtBox;
    }

    public WebElement getNewUiPasswordTxtBox() {
        return newUiPasswordTxtBox;
    }

    public WebElement getNewUiLoadingAnimation() {
        return newUiLoadingAnimation;
    }

    public WebElement getNewUiPayNowBtn() {
        return newUiPayNowBtn;
    }
    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ OLD UI ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public WebElement getPayWithPayPal() {
        return payWithPayPal;
    }

    public WebElement getOldUiLoadingAnimation() {
        return oldUiLoadingAnimation;
    }

    public WebElement getOldUiAfterPayLoadingAnimation() {
        return oldUiAfterPayLoadingAnimation;
    }

    public WebElement getOldUiEmailTxtBox() {
        return oldUiEmailTxtBox;
    }

    public WebElement getOldUiPasswordTxtBox() {
        return oldUiPasswordTxtBox;
    }

    public WebElement getOldUiPayNowBtn() {
        return oldUiPayNowBtn;
    }

}
