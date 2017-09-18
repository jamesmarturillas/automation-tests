package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * @class Page factory for login page.
 * Contains PageFactory initialization.
 */
public class SupportPageFactory {

    protected WebDriver driver;

    public SupportPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//h1[@class='page-header']")
    WebElement pageHeader;

    @FindBys({@FindBy(xpath = "//div[@class='header-inner']/*/a")})
    List<WebElement> headerLinks;

    @FindBys({@FindBy(xpath = "//div[@class='section-tree']/section/*/*")})
    List<WebElement> bodyLinks;

    @FindBys({@FindBy(xpath = "//footer/*/*/*")})
    List<WebElement> footerLinks;

    /**
     * Getters
     */
    public WebElement getPageHeader() {
        return pageHeader;
    }

    public List<WebElement> getHeaderLinks() {
        return headerLinks;
    }

    public List<WebElement> getBodyLinks() {
        return bodyLinks;
    }

    public List<WebElement> getFooterLinks() {
        return footerLinks;
    }
}
