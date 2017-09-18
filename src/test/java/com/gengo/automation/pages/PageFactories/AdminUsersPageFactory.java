package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for Admin Users page.
 * Contains PageFactory initialization.
 */
public class AdminUsersPageFactory {
    protected WebDriver driver;

    public AdminUsersPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//select[@name='role']")
    WebElement roleDropdown;

    @FindBy(xpath = "//button[contains(.,'Filter')]")
    WebElement filterBtn;

    @FindBy(xpath = "//a/i[@class='icon-next']")
    WebElement nextBtn;

    @FindBy(xpath = "//a/i[@class='icon-previous']")
    WebElement prevBtn;

    @FindBy(xpath = "(//td[7][contains(.,'0.0')]/preceding-sibling::td[contains(.,'Translator')]/preceding-sibling::td/a)[1]")
    WebElement firstTranslatorZeroRewards;

    @FindBy(xpath = "(//td[7][not(contains(.,'0.0'))]/preceding-sibling::td[contains(.,'Translator')]/preceding-sibling::td/a)[1]")
    WebElement firstTranslatorWithRewards;

    /**
     * Getters
     */
    public WebElement getRoleDropdown() {
        return roleDropdown;
    }

    public WebElement getFilterBtn() {
        return filterBtn;
    }

    public WebElement getNextBtn() {
        return nextBtn;
    }

    public WebElement getPrevBtn() {
        return prevBtn;
    }

    public WebElement getFirstTranslatorZeroRewards() {
        return firstTranslatorZeroRewards;
    }

    public WebElement getFirstTranslatorWithRewards() {
        return firstTranslatorWithRewards;
    }
}
