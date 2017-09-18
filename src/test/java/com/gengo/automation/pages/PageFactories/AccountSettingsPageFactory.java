package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Object repository for account page.
 * Contains PageFactory initialization.
 */
public class AccountSettingsPageFactory {

    protected WebDriver driver;
    public final String ACCOUNT_SETTINGS_URL = "staging.gengo.com/account/profile/";

    public AccountSettingsPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//h1[contains(text(), 'Account settings')]")
    WebElement accountSettingsTextBtn;

    @FindBy(id = "change_payment_link")
    WebElement paymentPreferencesBtn;

    @FindBy(xpath = "//input[@name='payment_method' and @value='stripe']")
    WebElement stripeRadioBtn;

    @FindBy(xpath = "//input[@name='payment_method' and @value='paypal']")
    WebElement payPalRadioBtn;

    @FindBy(id = "update-payment-btn")
    WebElement updatePaymentMethodBtn;

    @FindBy(xpath = "//select[@name='currency']")
    WebElement selectCurrencyDropDown;

    @FindBy(id = "btn-update-currency")
    WebElement updateCurrencyBtn;

    @FindBy(id = "confirm-change-currency")
    WebElement confirmChangeCurrencyBtn;

    @FindBy(id = "add-credit-card-btn")
    WebElement addCreditCardBtn;

    @FindBy(xpath = "//div[contains(@class, 'flashdata') and contains(., 'Default payment method changed successfully.')]")
    WebElement updatePaymentMethodSuccessMsg;

    @FindBy(xpath = "//div[contains(@class, 'flashdata') and contains(., 'Credit card added successfully.')]")
    WebElement updateCreditCardSuccessMsg;

    @FindBy(xpath = "//a[@class='remove-credit-card']")
    WebElement removeCreditCardLink;

    @FindBy(xpath = "//div[contains(@class, 'flashdata') and contains(., 'Credit card removed successfully.')]")
    WebElement removeCreditCardSuccessMsg;

    @FindBy(xpath = "//div[contains(@class, 'flashdata') or contains(., 'Currency changed successfully.') or contains(., 'Se ha actualizado la moneda de uso.')]")
    WebElement changeCurrencySuccessMsg;

    @FindBy(id = "change_address_link")
    WebElement addressDetailsBtn;

    @FindBy(name = "full_name")
    WebElement txtBoxFullName;

    @FindBy(name = "address_line_1")
    WebElement txtBoxAddressLine1;

    @FindBy(name = "address_line_2")
    WebElement txtBoxAddressLine2;

    @FindBy(name = "city")
    WebElement txtBoxCity;

    @FindBy(name = "region")
    WebElement txtBoxRegion;

    @FindBy(name = "zip_code")
    WebElement txtBoxZipCode;

    @FindBy(xpath = "//select[@name='country']")
    WebElement selectCountryDropDown;

    @FindBy(xpath = "//div[@id='change_address']/div/form/fieldset/button[@class='ui_btn small_btn primary_btn']")
    WebElement addressUpdateBtn;

    @FindBy(xpath = "//div[contains(@class, 'flashdata') and contains(., 'Address changed successfully.')]")
    WebElement addressUpdateSuccessMsg;

    @FindBy(id = "change_timezone_link")
    WebElement yourTimeZoneBtn;

    @FindBy(name = "timezones")
    WebElement timeZoneDropDown;

    @FindBy(xpath = "//div[@id='change_timezone']/div/form/fieldset/button[@class='ui_btn small_btn primary_btn']")
    WebElement timeZoneUpdateBtn;

    @FindBy(xpath = "//div[contains(@class, 'flashdata') and contains(., 'Timezone changed successfully.')]")
    WebElement timeZoneUpdateSuccessMsg;

    @FindBy(id = "change_display_name_link")
    WebElement displayNameBtn;

    @FindBy(name = "display_name")
    WebElement displayNameTxtBox;

    @FindBy(xpath = "//div[@id='change_display_name']/form/fieldset/button[@class='ui_btn small_btn primary_btn']")
    WebElement displayNameUpdateBtn;

    @FindBy(xpath = "//div[contains(@class, 'flashdata') and contains(., 'Display name changed successfully.')]")
    WebElement displayNameSuccessMsg;

    @FindBy(id = "change_email_link")
    WebElement changeEmailBtn;

    @FindBy(name = "new_email")
    WebElement newEmailTxtBox;

    @FindBy(name = "new_email_again")
    WebElement confirmEmailTxtBox;

    @FindBy(xpath = "//div[@id='change_email']/form/fieldset/button[@class='ui_btn small_btn primary_btn']")
    WebElement updateEmailBtn;

    @FindBy(xpath = "//div[contains(@class, 'flashdata') and contains(., 'You will receive an email to confirm your updated email address after you submit this form.')]")
    WebElement updateEmailRequestSuccessMsg;

    @FindBy(xpath = "//div[contains(@class, 'flashdata') and contains(., 'Email address changed successfully.')]")
    WebElement updateEmailSuccessMsg;

    @FindBy(id = "change_password_link")
    WebElement changePasswordBtn;

    @FindBy(name = "current_password")
    WebElement currentPasswordTxtBox;

    @FindBy(name = "new_password")
    WebElement newPasswordTxtBox;

    @FindBy(name = "new_password_again")
    WebElement newPasswordAgainTxtBox;

    @FindBy(xpath = "//div[@id='change_password']/form/fieldset/button[@class='ui_btn small_btn primary_btn']")
    WebElement updatePasswordBtn;

    @FindBy(xpath = "//div[contains(@class, 'flashdata') and contains(., 'Password changed successfully.')]")
    WebElement updatePasswordSuccessMsg;

    @FindBy(id = "close-account")
    WebElement closeAcctLink;

    @FindBy(id = "account-remove-check")
    WebElement chkBoxConfirmClose;

    @FindBy(id = "account-remove-confirm")
    WebElement closeAcctPermanentlyBtn;
    @FindBy(xpath = "//a[@class='gengo-modal-close']")
    WebElement closeModalIcon;

    @FindBy(xpath = "//h2[contains(text(), 'Are you sure you want to close the account?')]")
    WebElement accountRemoveBody;

    @FindBy(id = "account-remove-back")
    WebElement modalCancelBtn;

    @FindBy(xpath = "//div[@class='flashdata red-background']")
    WebElement errorMessage;

    /**
     * Getters
     */
    public WebElement getAccountSettingsTextBtn() {
        return accountSettingsTextBtn;
    }

    public WebElement getPaymentPreferencesBtn() {
        return paymentPreferencesBtn;
    }

    public WebElement getStripeRadioBtn() {
        return stripeRadioBtn;
    }

    public WebElement getPayPalRadioBtn() {
        return payPalRadioBtn;
    }

    public WebElement getUpdatePaymentMethodBtn() {
        return updatePaymentMethodBtn;
    }

    public WebElement getSelectCurrencyDropDown() {
        return selectCurrencyDropDown;
    }

    public WebElement getUpdateCurrencyBtn() {
        return updateCurrencyBtn;
    }

    public WebElement getConfirmChangeCurrencyBtn() {
        return confirmChangeCurrencyBtn;
    }

    public WebElement getAddCreditCardBtn() {
        return addCreditCardBtn;
    }

    public WebElement getUpdatePaymentMethodSuccessMsg() {
        return updatePaymentMethodSuccessMsg;
    }

    public WebElement getUpdateCreditCardSuccessMsg() {
        return updateCreditCardSuccessMsg;
    }

    public WebElement getRemoveCreditCardLink() {
        return removeCreditCardLink;
    }

    public WebElement getRemoveCreditCardSuccessMsg() {
        return removeCreditCardSuccessMsg;
    }

    public WebElement getChangeCurrencySuccessMsg() {
        return changeCurrencySuccessMsg;
    }

    public WebElement getAddressDetailsBtn() {
        return addressDetailsBtn;
    }

    public WebElement getTxtBoxFullName() {
        return txtBoxFullName;
    }

    public WebElement getTxtBoxAddressLine1() {
        return txtBoxAddressLine1;
    }

    public WebElement getTxtBoxAddressLine2() {
        return txtBoxAddressLine2;
    }

    public WebElement getTxtBoxCity() {
        return txtBoxCity;
    }

    public WebElement getTxtBoxRegion() {
        return txtBoxRegion;
    }

    public WebElement getTxtBoxZipCode() {
        return txtBoxZipCode;
    }

    public WebElement getSelectCountryDropDown() {
        return selectCountryDropDown;
    }

    public WebElement getAddressUpdateBtn() {
        return addressUpdateBtn;
    }

    public WebElement getAddressUpdateSuccessMsg() {
        return addressUpdateSuccessMsg;
    }

    public WebElement getYourTimeZoneBtn() {
        return yourTimeZoneBtn;
    }

    public WebElement getTimeZoneDropDown() {
        return timeZoneDropDown;
    }

    public WebElement getTimeZoneUpdateBtn() {
        return timeZoneUpdateBtn;
    }

    public WebElement getTimeZoneUpdateSuccessMsg() {
        return timeZoneUpdateSuccessMsg;
    }

    public WebElement getDisplayNameBtn() {
        return displayNameBtn;
    }

    public WebElement getDisplayNameTxtBox() {
        return displayNameTxtBox;
    }

    public WebElement getDisplayNameUpdateBtn() {
        return displayNameUpdateBtn;
    }

    public WebElement getDisplayNameSuccessMsg() {
        return displayNameSuccessMsg;
    }

    public WebElement getChangeEmailBtn() {
        return changeEmailBtn;
    }

    public WebElement getNewEmailTxtBox() {
        return newEmailTxtBox;
    }

    public WebElement getConfirmEmailTxtBox() {
        return confirmEmailTxtBox;
    }

    public WebElement getUpdateEmailBtn() {
        return updateEmailBtn;
    }

    public WebElement getUpdateEmailRequestSuccessMsg() {
        return updateEmailRequestSuccessMsg;
    }

    public WebElement getUpdateEmailSuccessMsg() {
        return updateEmailSuccessMsg;
    }

    public WebElement getChangePasswordBtn() {
        return changePasswordBtn;
    }

    public WebElement getCurrentPasswordTxtBox() {
        return currentPasswordTxtBox;
    }

    public WebElement getNewPasswordTxtBox() {
        return newPasswordTxtBox;
    }

    public WebElement getNewPasswordAgainTxtBox() {
        return newPasswordAgainTxtBox;
    }

    public WebElement getUpdatePasswordBtn() {
        return updatePasswordBtn;
    }

    public WebElement getUpdatePasswordSuccessMsg() {
        return updatePasswordSuccessMsg;
    }

    public WebElement getCloseAcctLink() {
        return closeAcctLink;
    }

    public WebElement getChkBoxConfirmClose() {
        return chkBoxConfirmClose;
    }

    public WebElement getCloseAcctPermanentlyBtn() {
        return closeAcctPermanentlyBtn;
    }

    public WebElement getCloseModalIcon() {
        return closeModalIcon;
    }

    public WebElement getAccountRemoveBody() {
        return accountRemoveBody;
    }

    public WebElement getModalCancelBtn() {
        return modalCancelBtn;
    }

    public WebElement getErrorMessage() {
        return errorMessage;
    }
}
