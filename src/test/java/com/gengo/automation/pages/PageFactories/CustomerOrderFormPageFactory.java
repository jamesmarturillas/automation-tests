package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for customer order form page.
 * Contains PageFactory initialization.
 */
public class CustomerOrderFormPageFactory {

    protected WebDriver driver;
    public final String ORDER_FORM_URL = "https://staging.gengo.com/order/";

    public CustomerOrderFormPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//textarea[@class='order_text_body row-fluid']")
    WebElement orderFormTextArea;

    @FindBy(xpath = "//span[contains(text(),'Upload Files')]/following-sibling::input")
    WebElement uploadFileBtn;

    @FindBy(xpath = "//button[@class='btn btn-link btn-small pull-right clear-all-items-link']")
    WebElement clearAllItems;

    @FindBy(id = "text_unit_count_section")
    WebElement unitCount;

    @FindBy(xpath = "//div[@id='input_text_file_section']")
    WebElement addMoreJobBtn;

    @FindBy(xpath = "//button[contains(@class, 'btn btn-primary order_text_add_btn pull-left')]")
    WebElement done;

    @FindBy(id = "choose_languages_btn")
    WebElement chooseLanguagesBtn;

    @FindBy(xpath = "//div[@id='input_text_file_section']/div/form/div/span[@class='btn fileinput-button']/input")
    WebElement addMoreFile;

    @FindBy(xpath = "//div[@id='input_text_file_section']/span")
    WebElement addMoreBtn;

    @FindBy(xpath = "//a[@id='user_info_dropdown']")
    WebElement navAccountInfo;

    /**
     * Getters
     */
    public WebElement getOrderFormTextArea() {
        return orderFormTextArea;
    }

    public WebElement getUploadFileBtn() {
        return uploadFileBtn;
    }

    public WebElement getClearAllItems() {
        return clearAllItems;
    }

    public WebElement getUnitCount() {
        return unitCount;
    }

    public WebElement getAddMoreJobBtn() {
        return addMoreJobBtn;
    }

    public WebElement getDone() {
        return done;
    }

    public WebElement getChooseLanguagesBtn() {
        return chooseLanguagesBtn;
    }

    public WebElement getAddMoreFile() {
        return addMoreFile;
    }

    public WebElement getAddMoreBtn() {
        return addMoreBtn;
    }

    public WebElement getNavAccountInfo() {
        return navAccountInfo;
    }
}
