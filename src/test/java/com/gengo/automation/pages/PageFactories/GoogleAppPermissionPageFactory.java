package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Sample template for PageFactory class.
 * Contains PageFactory initialization.
 */
public class GoogleAppPermissionPageFactory {

    protected WebDriver driver;
    private final String GMAIL_PERMISSION_PAGE = "https://myaccount.google.com/permissions";

    public GoogleAppPermissionPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//h1[contains(text(), 'Apps connected to your account')]")
    WebElement permissionPageTxtCriteria;

    @FindBy(xpath = "//div[text()='Gengo']")
    WebElement gengoAppBody;

    @FindBy(xpath = "//h2[text()='Gengo']/parent::div/parent::div/div[2]/div/content/span[text()='Remove']")
    WebElement gengoRemoveBtn;

    @FindBy(xpath = "//*[@id='xb']/div[3]/div/div[2]/div[3]/div[2]/content/span")
    WebElement okBtn;

    @FindBy(xpath = "//*[contains(text(), 'Gengo can no longer access your account')]")
    WebElement appNoLongerExistsMsg;
    /**
     * Getters
     */
    public WebElement getPermissionPageTxtCriteria() {
        return permissionPageTxtCriteria;
    }

    public WebElement getGengoAppBody() {
        return gengoAppBody;
    }

    public WebElement getGengoRemoveBtn() {
        return gengoRemoveBtn;
    }

    public WebElement getOkBtn() {
        return okBtn;
    }

    public WebElement getAppNoLongerExistsMsg() {
        return appNoLongerExistsMsg;
    }
}
