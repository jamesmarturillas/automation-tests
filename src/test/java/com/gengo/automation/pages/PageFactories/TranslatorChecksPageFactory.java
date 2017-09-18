package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Object repository for translator checker page.
 * Contains PageFactory initialization.
 */
public class TranslatorChecksPageFactory {

    protected WebDriver driver;

    public TranslatorChecksPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//h1[contains(text(), 'Available checks - All')]")
    WebElement translatorChecksPageHeaderAll;

    @FindBy(xpath = "//h1[contains(text(), 'Available checks - Priority')]")
    WebElement translatorChecksPageHeaderPriority;

    @FindBy(xpath = "//a[contains(text(), 'All')]")
    WebElement allLeftPanel;

    @FindBy(xpath = "//a[contains(text(), 'Priority')]")
    WebElement priorityLeftPanel;

    /**
     * Getters
     */
    public WebElement getTranslatorChecksPageHeaderAll() {
        return translatorChecksPageHeaderAll;
    }

    public WebElement getTranslatorChecksPageHeaderPriority() {
        return translatorChecksPageHeaderPriority;
    }

    public WebElement getAllLeftPanel() {
        return allLeftPanel;
    }

    public WebElement getPriorityLeftPanel() {
        return priorityLeftPanel;
    }
}
