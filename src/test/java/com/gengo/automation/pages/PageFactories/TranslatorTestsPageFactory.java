package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Object repository for translator tests page.
 * Contains PageFactory initialization.
 */
public class TranslatorTestsPageFactory {

    protected WebDriver driver;
    
    public TranslatorTestsPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//h1[contains(text(), 'Tests')]")
    WebElement translatorTestsPageText;

    /**
     * Getters
     */
    public WebElement getTranslatorTestsPageText() {
        return translatorTestsPageText;
    }
}
