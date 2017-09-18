package com.gengo.automation.pages;

import com.gengo.automation.fields.Variables;
import com.gengo.automation.helpers.Switcher;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.PayPalPageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

/**
 * @class Object repository for sample template of a PageObject class.
 * Contains element operations.
 */
public class PayPalPage extends PayPalPageFactory {

    public PayPalPage(WebDriver driver) throws IOException {
        super(driver);
    }
    protected Wait wait = new Wait(driver);
    private Variables var = new Variables();
    private Switcher switcher = new Switcher(driver);
    private CustomerOrderCompletePage customerOrderCompletePage = new CustomerOrderCompletePage(driver);

    public void paypalPayment() {
        wait.impWait(30, getPaypalPageTxtCriteria());
        final String currentURL = driver.getCurrentUrl();
        if (currentURL.contains("/cgi-bin")) {
            this.pay(false);
        }
        else if (currentURL.contains("webapps/hermes")) {
            this.pay(true);
        }
    }

    private boolean isLoadingPresent(boolean isNewUi) {
        if (isNewUi) {
            return driver.findElements(By.xpath("//div[@id='spinner']")).size() > 0;
        }
        return driver.findElements(By.xpath("//div[@id='progressMeter']/img")).size() > 0;
    }

    public void pay(boolean isNewUi) {
        if (isNewUi) {
            switcher.switchToFrame(NEW_UI_FRAME_NAME);
            getNewUiEmailTxtBox().clear();
            getNewUiEmailTxtBox().sendKeys(var.getPayPalEmail());
            getNewUiPasswordTxtBox().sendKeys(var.getPayPalPassword());
            getNewUiPasswordTxtBox().submit();
            wait.impWait(30);
            switcher.switchToDefaultContent();
            while (isLoadingPresent(isNewUi)) {
                wait.impWait(20, getNewUiPayNowBtn());
            }
            wait.untilElementIsClickable(getNewUiPayNowBtn());
            getNewUiPayNowBtn().click();
        }
        else {
            getPayWithPayPal().click();
            while (isLoadingPresent(!isNewUi)) {
                wait.impWait(20, getOldUiEmailTxtBox());
            }
            getOldUiEmailTxtBox().clear();
            getOldUiEmailTxtBox().sendKeys(var.getPayPalEmail());
            getOldUiPasswordTxtBox().sendKeys(var.getPayPalPassword());
            getOldUiPasswordTxtBox().submit();
            wait.impWait(30, getOldUiPayNowBtn());
            getOldUiPayNowBtn().click();
        }
    }
}
