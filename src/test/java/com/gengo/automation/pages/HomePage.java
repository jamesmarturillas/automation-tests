package com.gengo.automation.pages;

import com.gengo.automation.fields.Constants;
import com.gengo.automation.helpers.ElementState;
import com.gengo.automation.helpers.JSExecutor;
import com.gengo.automation.helpers.PageActions;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.HomePageFactory;
import org.openqa.selenium.*;
import org.testng.Reporter;

import java.util.ArrayList;
import java.util.List;

/**
 * @class Object repository for homepage.
 * Contains element operations.
 */
public class HomePage extends HomePageFactory {

    public HomePage(WebDriver driver) {
        super(driver);
    }
    private Wait wait = new Wait(driver);
    private JSExecutor js = new JSExecutor(driver);
    private PageActions page = new PageActions(driver);

    public void clickSignInButton() {
        if (check.isElementPresentBySource(driver, STR_SIGNIN)) {
            getSignIn().click();
            wait.impWait(10);
        }
    }

    public Boolean checkHomePage(){
        Boolean state = true;
        try{
            getSignIn().isDisplayed();
            getBecomeATranslatorBtn().isDisplayed();
            getContactSalesLink().isDisplayed();
            getGengoLogo().isDisplayed();
            getSupportLink().isDisplayed();
            getHeaderText().isDisplayed();
            getSubHeaderText().isDisplayed();
            getNavigationMenu().isDisplayed();
            getOverviewSection().isDisplayed();
            getOrderOptionsSection().isDisplayed();
            getHowItWorksSection().isDisplayed();
            getGengoForBusinessSection().isDisplayed();
            getPricingSection().isDisplayed();
            getFooterSection().isDisplayed();
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public void clickSupportLink(String supportLocale, boolean isFooter) {
        WebElement element;
        wait.impWait(30);
        if (!isFooter) {
            // Index number of support in the header is 2.
            element = getHeaderLinksParent().get(2);
            if (element.getText().contains(supportLocale)) {
                element.click();
            }
            else {
                Reporter.log("Failed to click the '" + supportLocale + "' link in the header page.");
            }
        }
        else {
            element = getFooterLastColumnParent().get(2);
            js.scrollIntoElement(element);
            if (element.getText().equalsIgnoreCase(supportLocale)) {
                element.click();
            }
            else {
                Reporter.log("Failed to click the '" + supportLocale + "' link in the header page.");
            }
        }
        wait.impWait(30);
    }

    public void changeLocale(String locale) {
        try {
            wait.impWait(30);
            switch (locale) {
                case Constants.HP_EN_LOCALE :
                    getLocaleDropDown().click();
                    this.loopThroughDropDownAndClick(getLocaleLinks(), Constants.HP_EN_LOCALE);
                    wait.impWait(30);
                    break;
                case Constants.HP_ZH_LOCALE :
                    getLocaleDropDown().click();
                    this.loopThroughDropDownAndClick(getLocaleLinks(), Constants.HP_ZH_LOCALE);
                    wait.impWait(30);
                    break;
                case Constants.HP_JA_LOCALE :
                    getLocaleDropDown().click();
                    this.loopThroughDropDownAndClick(getLocaleLinks(), Constants.HP_JA_LOCALE);
                    wait.impWait(30);
                    break;
                case Constants.HP_ES_LOCALE :
                    getLocaleDropDown().click();
                    this.loopThroughDropDownAndClick(getLocaleLinks(), Constants.HP_ES_LOCALE);
                    wait.impWait(30);
                    break;
            }
        }
        catch (StaleElementReferenceException e) {
            page.refresh();
            wait.untilElementVisible(getLocaleIcon());
        }
    }

    private void loopThroughDropDownAndClick(List<WebElement> elements, String targetLocale) {
        for (WebElement element : elements) {
            if (element.getText().contains(targetLocale)) {
                element.click();
            }
        }
    }
}
