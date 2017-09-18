package com.gengo.automation.pages;

import com.gengo.automation.fields.Variables;
import com.gengo.automation.helpers.Switcher;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.TopUpPageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;

import static org.testng.Assert.*;

/**
 * @class Object repository for top up page.
 * Contains element operations.
 */
public class TopUpPage extends TopUpPageFactory {

    public TopUpPage(WebDriver driver) throws IOException {
        super(driver);
    }
    private Variables var = new Variables();
    private Switcher switcher = new Switcher(driver);
    private Wait wait = new Wait(driver);
    private PayPalPage payPalPage = new PayPalPage(driver);

    public void selectAmount(String amount) {
        getAmountDropdown().click();
        getAmountOption().findElement(By.xpath("//li[@role='presentation']/a[contains(text(), '" + amount + "')]")).click();
        assertTrue(getPayBtn().getText().contains(amount),
                var.getTextNotEqualErrMsg());
    }

    public WebElement chooseStripe() {
        return getCreditRadioBtn();
    }

    public WebElement choosePayPal() {
        return getPaypalRadioBtn();
    }

    public void selectPaymentMode(WebElement element) {
        element.click();
    }

    public void clickPay(boolean isPayPal) {
        getPayBtn().click();
        if (!isPayPal) {
            wait.impWait(10);
            switcher.switchToFrame("stripe_checkout_app");
            wait.impWait(30, getStripePaymentTxtCriteria());
        }
    }

    public void processPayment(String creditCard) {
        this.payWithStripe(creditCard);
    }

    public void processPayment() {
        wait.impWait(10);
        this.payWithPayPal();
    }

    private void payWithStripe(String creditCard) {
        if (getCreditCardTxtBox().getAttribute("value").isEmpty()) {
            getCreditCardTxtBox().sendKeys(creditCard);
        }
        else {
            getCreditCardTxtBox().clear();
            getCreditCardTxtBox().sendKeys(creditCard);
        }

        if (getCardDateExpiryTxtBox().getAttribute("value").isEmpty()) {
            getCardDateExpiryTxtBox().sendKeys(var.getExpiryDate());
        }
        else {
            getCardDateExpiryTxtBox().clear();
            getCardDateExpiryTxtBox().sendKeys(var.getExpiryDate());
        }

        if (getCvcTxtBox().getAttribute("value").isEmpty()) {
            getCvcTxtBox().sendKeys(var.getCvcNumber());
        }
        else {
            getCvcTxtBox().clear();
            getCvcTxtBox().sendKeys(var.getCvcNumber());
        }

        if (getZipCodeTxtBox().getAttribute("value").isEmpty()) {
            getZipCodeTxtBox().sendKeys(var.getCreditCardZipCode());
        }
        else {
            getZipCodeTxtBox().clear();
            getZipCodeTxtBox().sendKeys(var.getCreditCardZipCode());
        }

        getStripeSubmitBtn().click();
    }

    private void payWithPayPal() {
        payPalPage.paypalPayment();
    }
}
