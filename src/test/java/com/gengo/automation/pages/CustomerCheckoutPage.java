package com.gengo.automation.pages;

import com.gengo.automation.fields.Variables;
import com.gengo.automation.helpers.JSExecutor;
import com.gengo.automation.helpers.PageActions;
import com.gengo.automation.helpers.Switcher;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.CustomerCheckoutPageFactory;
import org.openqa.selenium.*;

import java.io.IOException;

/**
 * @class Object repository for customer order optional (checkout) page.
 * Contains element operations.
 */
public class CustomerCheckoutPage extends CustomerCheckoutPageFactory {

    public CustomerCheckoutPage(WebDriver driver) throws IOException {
        super(driver);
    }
    private PayPalPage payPalPage = new PayPalPage(driver);
    private CustomerOrderCompletePage customerOrderCompletePage = new CustomerOrderCompletePage(driver);
    private Variables var = new Variables();
    private Switcher switcher = new Switcher(driver);
    private Wait wait = new Wait(driver);
    private JSExecutor js = new JSExecutor(driver);
    private PageActions page = new PageActions(driver);
    private String stripePrice;

    public void clickViewFullQuote() {
        driver.navigate().refresh();
        getViewFullQuoteBtn().click();
    }

    /**
     * @param isPayPal
     *  Clicks the PayPal radio button if true, otherwise will do the stripe payment.
     * @param withCreditCard
     *  for Stripe Payment, {true} will input credit card value in the stripe modal credit card textbox, otherwise will not.
     * @param withCreditCardExpiry
     *  for Stripe Payment, {true} will input credit card expiry in the stripe modal credit card expiry textbox, otherwise will not.
     * @param withCVC
     *  for Stripe Payment, {true} will input credit card cvc value in the stripe modal credit card cvc textbox, otherwise will not.
     * @param withZipCode
     *  for Stripe Payment, {true} will input user zip code in the stripe modal credit card zip code textbox, otherwise will not.
     */
    public void clickPayNowAndConfirm(boolean isPayPal, boolean withCreditCard, boolean withCreditCardExpiry, boolean withCVC, boolean withZipCode) {
        if (isPayPal) getPaypalRadio().click();
        this.makePaymentButtonEnabled();
        getPayAndConfirmBtn().click();
        if (!isPayPal && !withCreditCard && !withCreditCardExpiry && !withCVC && !withZipCode) {
            wait.untilElementVisible(customerOrderCompletePage.getBackToDashBoardBtn());
        }

        if (isPayPal) {
            payPalPage.paypalPayment();
            try{
                this.payWithCredits();
            }
            catch(NoSuchElementException e){}

        }
        else if (!isPayPal && (withCreditCard || withCreditCardExpiry || withCVC || withZipCode)) {
            try {
                this.stripePayment(withCreditCard, withCreditCardExpiry, withCVC, withZipCode);
            }
            catch (WebDriverException e) {
                page.refresh();
                wait.impWait(30, getPayAndConfirmBtn());
                getPayAndConfirmBtn().click();
                this.stripePayment(withCreditCard, withCreditCardExpiry, withCVC, withZipCode);
            }
        }
    }

    private void stripePayment(boolean withCreditCard, boolean withCreditCardExpiry, boolean withCVC, boolean withZipCode) {
        wait.impWait(30);
        switcher.switchToFrame("stripe_checkout_app");
        wait.impWait(30, getStripePaymentTxtCriteria());
        if (withCreditCard) getCreditCardTxtBox().sendKeys(var.getCreditCard());
        if (withCreditCardExpiry) getCardDateExpiryTxtBox().sendKeys(var.getExpiryDate());
        if (withCVC) getCvcTxtBox().sendKeys(var.getCvcNumber());
        if (withZipCode) getZipCodeTxtBox().sendKeys(var.getCreditCardZipCode());
        this.setStripePrice(getStripePrice().getText());
        getSubmitBtn().click();
        switcher.switchToDefaultContent();
        if (withCreditCard && withCreditCardExpiry && withCVC && withZipCode) {
            wait.impWait(30, customerOrderCompletePage.getBackToDashBoardBtn());
        }
        else {
            wait.impWait(30);
        }
    }

    /**
     * @return Brings the state of the "RED" payment button if disabled or not.
     */
    private boolean isPaymentButtonDisabled() {
        return getPayAndConfirmBtn().getAttribute("class").contains("disabled");
    }

    /**
     * @method makePaymentButtonEnabled() : Performs a continuous refresh while the button is not enabled.
     */
    private void makePaymentButtonEnabled() {
        wait.impWait(30);
        while (isPaymentButtonDisabled()) {
            page.refresh();
            wait.impWait(30); // impWait() method is stored inside to assure complete load of page before the justification.
        }
    }

    public void setStripePrice(String price) {
        this.stripePrice = price;
    }

    public String returnStripePrice() {
        return stripePrice;
    }

    public void clickAdvanceOptions() {
        boolean clickBtn = true;
        wait.impWait(5);
        while(clickBtn){
            try{
                js.scrollIntoElement(getAdvanceOption());
                getAdvanceOption().click();
                if(getAdvanceOptionOpen().isDisplayed()) {
                    clickBtn = false;
                    wait.untilElementVisible(getAdvanceOptionOpen());
                }
            }
            catch(NoSuchElementException | StaleElementReferenceException e){
                clickBtn = true;
            }
            wait.impWait(5);
        }
    }

    public void clickOneTranslator() {
        js.scrollIntoElement(getUseOneTranslatorChkBox());
        getUseOneTranslatorChkBox().click();
    }

    private boolean isAdvanceOptionDisplayed() {
        try {
            return getAdvanceOption().isDisplayed();
        }
        catch(Exception e) {
            return false;
        }
    }

    public void orderAsAGroup() {
        if (isAdvanceOptionDisplayed()) this.clickAdvanceOptions();
        wait.impWait(30, getUseOneTranslatorChkBox());
        this.clickOneTranslator();
        wait.impWait(10);
    }

    public void orderAsAGroupClick() {
        wait.impWait(30, getUseOneTranslatorChkBox());
        this.clickOneTranslator();
        wait.impWait(10);
    }

    public void businessTier(boolean isBusiness){
        if(isBusiness) {
            wait.untilElementVisible(getTierPro());
            while (!getTierPro().isSelected())
                getTierPro().click();
            wait.impWait(10);
        }
    }

    public void addGlossary(String glossary) {
        if (isAdvanceOptionDisplayed()) this.clickAdvanceOptions();
        wait.untilElementIsClickable(getChooseGlossary());
        getChooseGlossary().click();
        WebElement glossaryFile = driver.findElement(By.xpath("//li[@class='glossary_line']/a[contains(.,'" + glossary + "')]"));
        wait.untilElementIsClickable(glossaryFile);
        glossaryFile.click();
        WebElement updatedButton = driver.findElement(By.xpath(".//*[@id='glossary_select_btn'][contains(.,'" + glossary + "')]"));
        wait.untilElementVisible(updatedButton);
    }

    public void chooseTone(WebElement chosenTone) {
        js.scrollIntoElement(getToneDropDown());
        getToneDropDown().click();
        wait.impWait(10, chosenTone);
        chosenTone.click();
        wait.impWait(10);
    }

    public void choosePurpose(WebElement chosenPurpose) {
        js.scrollIntoElement(getToneDropDown());
        getChoosePurposeDropDown().click();
        wait.impWait(10, chosenPurpose);
        chosenPurpose.click();
        wait.impWait(10);
    }

    public boolean isGlossaryPresent(String glossaryName) {
        if (isAdvanceOptionDisplayed()) this.clickAdvanceOptions();
        wait.untilElementIsClickable(getChooseGlossary());
        getChooseGlossary().click();
        boolean state;
        try{
            WebElement glossaryFile = driver.findElement(By.xpath("//li[@class='glossary_line']/a[contains(.,'" + glossaryName + "')]"));
            state = glossaryFile.isDisplayed();
        }
        catch(NoSuchElementException e) {
            state = false;
        }
        return state;
    }

    public void addInstructions(String instruction) {
        js.scrollIntoElement(getIntructionsTxtArea());
        getIntructionsTxtArea().sendKeys(instruction);
    }

    public void payWithCredits(){
        getPayAndConfirmBtn().click();
        wait.untilElementVisible(customerOrderCompletePage.getBackToDashBoardBtn());
    }

    public boolean checkOrderAsOne() {
        boolean state = true;
        try {
            getUseOneTranslatorChkBox().isDisplayed();
        }
        catch(NoSuchElementException e) {
            state = false;
        }
        return state;
    }

    public void orderWithPreferredTranslator() {
        if (isAdvanceOptionDisplayed()) this.clickAdvanceOptions();
        wait.impWait(30, getPreferredTranslatorChkBox());
        getPreferredTranslatorChkBox().click();
        wait.impWait(10);
    }
}
