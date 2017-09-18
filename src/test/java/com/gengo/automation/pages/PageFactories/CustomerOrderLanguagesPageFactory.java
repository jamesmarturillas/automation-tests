package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for customer order languages page.
 * Contains PageFactory initialization.
 */
public class CustomerOrderLanguagesPageFactory {

    protected WebDriver driver;
    public final String ORDERLANGUAGES_URL = "https://staging.gengo.com/order/languages";

    public CustomerOrderLanguagesPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(id = "candidate_source_language_btn")
    WebElement sourceLanguageDropDown;

    @FindBy(id = "candidate_language_btn")
    WebElement targetLanguageDropDown;

    @FindBy(xpath = "//button[@class='btn btn-link pull-right cancel_link']")
    WebElement cancelAddLanguageBtn;

    @FindBy(xpath = "//button[@class='btn btn-primary pull-right confirm_btn']")
    WebElement addLanguageBtn;

    @FindBy(id = "clear-all-language-link")
    WebElement clearAllLanguagesLink;

    @FindBy(id = "optional_btn")
    WebElement nextOptionsBtn;

    WebElement languageFrom(String language) {
        return driver.findElement(By.xpath("//li[@class='language_line']/a[contains(text(), '" + language + "')]"));
    }

    WebElement languageTo(String language) {
        return driver.findElement(By.xpath("//span[@class='language_name pull-left' and contains(text(), '" + language + "')]"));
    }

    /**
     * Getters
     */

    public WebElement getSourceLanguageDropDown() {
        return sourceLanguageDropDown;
    }

    public WebElement getTargetLanguageDropDown() {
        return targetLanguageDropDown;
    }

    public WebElement getCancelAddLanguageBtn() {
        return cancelAddLanguageBtn;
    }

    public WebElement getAddLanguageBtn() {
        return addLanguageBtn;
    }

    public WebElement getClearAllLanguagesLink() {
        return clearAllLanguagesLink;
    }

    public WebElement getNextOptionsBtn() {
        return nextOptionsBtn;
    }

    public WebElement getLanguageFrom(String language) {
        return languageFrom(language);
    }

    public WebElement getLanguageTo(String language) {
        return languageTo(language);
    }

}
