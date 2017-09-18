package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory class for Glossary page.
 * Contains PageFactory initialization.
 */
public class GlossaryPageFactory {

    protected WebDriver driver;
    public final String GLOSSARY_PAGE_URL = "https://staging.gengo.com/c/glossaries/";
    public GlossaryPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//a[@class='button_medium upload-csv']")
    WebElement sendGlossaryBtn;

    @FindBy(xpath = "//a[@class='button_medium download-glossary-template']")
    WebElement helpCreateGlossaryBtn;

    @FindBy(linkText = "Become a translator")
    WebElement becomeATranslatorLink;

    @FindBy(xpath = "//a[@class='ui_btn primary_btn upload-csv']")
    WebElement uploadGlossaryBtn;

    @FindBy(xpath = "//a[@class='ui_btn primary_btn download-glossary-template']")
    WebElement downloadGlossaryTemplateBtn;

    @FindBy(xpath = "//a[contains(.,'Choose Glossary File')]/input[@name='file']")
    WebElement chooseGlossaryFileBtn;

    @FindBy(xpath = "//a[contains(@href, 'glossaries/download/') and contains(text(), 'Download')]")
    WebElement downloadGlossaryTxt;

    @FindBy(xpath = "//div[@class='flashdata red-background']/h2[contains(.,'Error')]")
    WebElement errorMessage;

    @FindBy(xpath = "//div[@class='flashdata red-background']/h2[contains(.,'Error')]/following-sibling::p[contains(.,'Glossary entries are not allowed to contain triple brackets')]")
    WebElement bracketedTermErrorMsg;

    /**
     * Getters
     */
    public WebElement getSendGlossaryBtn() {
        return sendGlossaryBtn;
    }

    public WebElement getHelpCreateGlossaryBtn() {
        return helpCreateGlossaryBtn;
    }

    public WebElement getBecomeATranslatorLink() {
        return becomeATranslatorLink;
    }

    public WebElement getUploadGlossaryBtn() {
        return uploadGlossaryBtn;
    }

    public WebElement getDownloadGlossaryTemplateBtn() {
        return downloadGlossaryTemplateBtn;
    }

    public WebElement getChooseGlossaryFileBtn() {
        return chooseGlossaryFileBtn;
    }

    public WebElement getDownloadGlossaryTxt() {
        return downloadGlossaryTxt;
    }

    public WebElement getErrorMessage() {
        return errorMessage;
    }

    public WebElement getBracketedTermErrorMsg() {
        return bracketedTermErrorMsg;
    }
}
