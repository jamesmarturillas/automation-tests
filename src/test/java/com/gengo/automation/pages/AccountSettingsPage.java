package com.gengo.automation.pages;

import com.gengo.automation.fields.Variables;
import com.gengo.automation.helpers.JSExecutor;
import com.gengo.automation.helpers.Switcher;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.AccountSettingsPageFactory;
import com.gengo.automation.pages.PageFactories.TopUpPageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;

import static org.testng.Assert.*;

/**
 * @class Object repository for Account settings page.
 * Contains element operations.
 */
public class AccountSettingsPage extends AccountSettingsPageFactory {

    public AccountSettingsPage(WebDriver driver) throws IOException {
        super(driver);
    }
    private Wait wait = new Wait(driver);
    private JSExecutor js = new JSExecutor(driver);
    private Variables var = new Variables();
    private Switcher switcher = new Switcher(driver);
    private TopUpPageFactory topUpPageFactory = new TopUpPageFactory(driver);

    public void clickCloseAccountLink() {
        js.scrollIntoElement(getCloseAcctLink());
        getCloseAcctLink().click();
        wait.impWait(10, getCloseModalIcon());
    }

    public void closeAccountPermanently() {
        getChkBoxConfirmClose().click();
        wait.impWait(5);
        getCloseAcctPermanentlyBtn().click();
        wait.impWait(10);
    }

    public void clickCloseModalIcon() {
        getCloseModalIcon().click();
        wait.impWait(10);
        wait.untilElementNotVisible(By.xpath("//a[@class='gengo-modal-close']"));
    }

    public void clickModalCancelBtn() {
        getModalCancelBtn().click();
        wait.untilElementNotVisible(By.xpath("//a[@class='gengo-modal-close']"));
    }

    public void clickChangeAddresBtn() {
        getAddressDetailsBtn().click();
        wait.impWait(10, getAddressUpdateBtn());
    }

    public void fillOutAddressForm() {
        this.clearAllAddressFieldsForm();
        getTxtBoxFullName().sendKeys(var.getFullName());
        getTxtBoxAddressLine1().sendKeys(var.getAddress1());
        getTxtBoxAddressLine2().sendKeys(var.getAddress2());
        getTxtBoxCity().sendKeys(var.getTown());
        getTxtBoxRegion().sendKeys(var.getState());
        getTxtBoxZipCode().sendKeys(var.getZipCode());
    }

    public void clickUpdateAddressDetailsBtn() {
        getAddressUpdateBtn().click();
        wait.impWait(30);
    }

    public void clearAllAddressFieldsForm() {
        getTxtBoxFullName().clear();
        getTxtBoxAddressLine1().clear();
        getTxtBoxAddressLine2().clear();
        getTxtBoxCity().clear();
        getTxtBoxRegion().clear();
        getTxtBoxZipCode().clear();
    }

    public void clickChangeTimeZoneBtn() {
        getYourTimeZoneBtn().click();
        wait.impWait(10, getTimeZoneUpdateBtn());
    }

    public void selectTimeZone(String timezone) {
        this.clickChangeTimeZoneBtn();
        Select timeZones = new Select(getTimeZoneDropDown());
        timeZones.selectByValue(timezone);
        assertTrue(getTimeZoneDropDown().getText().contains(timezone),
                var.getTextNotEqualErrMsg());
        getTimeZoneUpdateBtn().click();
    }

    public void clickChangeDisplayNameBtn() {
        getDisplayNameBtn().click();
        wait.impWait(10, getDisplayNameUpdateBtn());
    }

    public void clickUpdateDisplayNameBtn() {
        getDisplayNameUpdateBtn().click();
        wait.impWait(10);
    }

    public void clearDisplayNameTxtBox() {
        getDisplayNameTxtBox().clear();
    }

    public void changeDisplayName(String newDisplayName) {
        this.clickChangeTimeZoneBtn();
        this.clearDisplayNameTxtBox();
        getDisplayNameTxtBox().sendKeys(newDisplayName);
        this.clickUpdateDisplayNameBtn();
    }

    public void clickChangePasswordBtn() {
        js.scrollIntoElement(getChangePasswordBtn());
        getChangePasswordBtn().click();
        wait.impWait(10, getUpdatePasswordBtn());
    }

    public void clickUpdatePasswordBtn() {
        getUpdatePasswordBtn().click();
        wait.impWait(10);
    }

    public void fillCurrentPasswordField(String password) {
        getCurrentPasswordTxtBox().sendKeys(password);
    }

    public void fillNewPassword(String password) {
        getNewPasswordTxtBox().sendKeys(password);
    }

    public void fillNewPasswordAgainField(String password) {
        getNewPasswordAgainTxtBox().sendKeys(password);
    }

    public void fillPasswordsFields(String[] passwords) {
        this.fillCurrentPasswordField(passwords[0]);
        this.fillNewPassword(passwords[1]);
        this.fillNewPasswordAgainField(passwords[2]);
        this.clickUpdatePasswordBtn();
    }

    public void clickChangeEmailBtn() {
        getChangeEmailBtn().click();
        wait.impWait(10, getUpdateEmailBtn());
    }

    public void fillNewEmailField(String newEmail) {
        getNewEmailTxtBox().sendKeys(newEmail);
    }

    public void fillConfirmEmailField(String confirmEmail) {
       getConfirmEmailTxtBox().sendKeys(confirmEmail);
    }

    public void clickUpdateEmailBtn() {
        getUpdateEmailBtn().click();
        wait.impWait(10);
    }

    public void fillEmailsField(String[] emails) {
        this.fillNewEmailField(emails[0]);
        this.fillConfirmEmailField(emails[1]);
        this.clickUpdateEmailBtn();
    }

    public void clickChangePaymentPreferences() {
        getPaymentPreferencesBtn().click();
        wait.impWait(10, getUpdatePaymentMethodBtn());
    }

    public void chooseCreditCardPaymentPreference() {
        getStripeRadioBtn().click();
        wait.impWait(10);
        this.clickUpdatePaymentPreferenceBtn();
    }

    public void choosePayPalPaymentPreference() {
        getPayPalRadioBtn().click();
        wait.impWait(10);
    }

    public void clickUpdatePaymentPreferenceBtn() {
        getUpdatePaymentMethodBtn().click();
        wait.impWait(10);
    }

    /**
     * @param currency
     *  Can choose among the following string values :
     *  "USD", "EUR", "JPY", "GBP"
     *
     * Note :
     * The string literals above are callable from the
     * 'Constants' class without instantiating the class itself.
     */
    public void selectCurrency(String currency) {
        Select dropdown = new Select(getSelectCurrencyDropDown());
        dropdown.selectByValue(currency);
        wait.impWait(10);
        getUpdateCurrencyBtn().click();
        wait.impWait(30, getConfirmChangeCurrencyBtn());
        getConfirmChangeCurrencyBtn().click();
        wait.impWait(30, getChangeCurrencySuccessMsg());
    }

    public void clickAddCreditCardBtn() {
        getAddCreditCardBtn().click();
        wait.impWait(10, topUpPageFactory.getStripeModalIframe());
    }

    public void addCreditCard() {
        switcher.switchToFrame(topUpPageFactory.getStripeModalIframe());
        topUpPageFactory.getCreditCardTxtBox().sendKeys(var.getCreditCard());
        topUpPageFactory.getCardDateExpiryTxtBox().sendKeys(var.getExpiryDate());
        topUpPageFactory.getCvcTxtBox().sendKeys(var.getCvcNumber());
        topUpPageFactory.getZipCodeTxtBox().sendKeys(var.getCreditCardZipCode());
        topUpPageFactory.getStripeSubmitBtn().click();
        wait.impWait(30);
        switcher.switchToDefaultContent();
        wait.impWait(30, getUpdateCreditCardSuccessMsg());
    }

    public void removeCreditCard() {
        getRemoveCreditCardLink().click();
        wait.impWait(30, getRemoveCreditCardSuccessMsg());
    }
}
