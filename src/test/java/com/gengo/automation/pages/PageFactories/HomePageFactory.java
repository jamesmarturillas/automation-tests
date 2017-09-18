package com.gengo.automation.pages.PageFactories;

import com.gengo.automation.helpers.CheckElementPresence;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * @class Object repository for homepage.
 * Contains PageFactory initialization.
 */
public class HomePageFactory {

    protected WebDriver driver;
    protected final String STR_SIGNIN = "SIGN IN";
    protected CheckElementPresence check = new CheckElementPresence(driver);

    public HomePageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//ul[@data-toggle='lang-dropdown']/li")
    WebElement localeDropDown;

    @FindBys({@FindBy(xpath = "//ul[@class='dropdown-menu']/li/a")})
    List<WebElement> localeLinks;

    @FindBy(xpath = "//i[@class='icon-global icon-white']")
    WebElement localeIcon;

    @FindBy(xpath = "//a[@class='btn-sign-in' and @href='https://staging.gengo.com/auth/form/login/']")
    WebElement signIn;

    @FindBy(className = "logo")
    WebElement gengoLogo;

    @FindBy(xpath = "//a[@class='btn-sign-in' and contains(@href, '/?from=topnav')]")
    WebElement contactSalesLink;

    @FindBys({@FindBy(xpath = "//div[@class='float-right']/a")})
    List<WebElement> headerLinksParent;

    @FindBys({@FindBy(xpath = "//ul[contains(@id, 'menu-company')]/li/a")})
    List<WebElement> footerLastColumnParent;

    @FindBy(xpath = "//div[@class='float-right']/a[contains(.,'Support')]")
    WebElement supportLink;

    @FindBy(xpath = "//div[@class='float-right']/a[contains(.,'Become a translator')]")
    WebElement becomeATranslatorBtn;

    @FindBy(xpath = "//h1[@class='negative'][contains(.,'Expand your world')]")
    WebElement headerText;

    @FindBy(xpath = "//p[@class='negative p-lg'][contains(.,' with our people-powered translations')]")
    WebElement subHeaderText;

    @FindBy(xpath = "//*[@id='menu-general-sub-menu']")
    WebElement navigationMenu;

    @FindBy(id = "overview")
    WebElement overviewSection;

    @FindBy(id = "Order-options")
    WebElement orderOptionsSection;

    @FindBy(id = "howitworks")
    WebElement howItWorksSection;

    @FindBy(id = "uses")
    WebElement gengoForBusinessSection;

    @FindBy(id = "pricing")
    WebElement pricingSection;

    @FindBy(tagName = "footer")
    WebElement footerSection;

    /**
     * Getters
     */
    public WebElement getLocaleDropDown() {
        return localeDropDown;
    }

    public List<WebElement> getLocaleLinks() {
        return localeLinks;
    }

    public WebElement getLocaleIcon() {
        return localeIcon;
    }

    public WebElement getSignIn() {
        return signIn;
    }

    public WebElement getGengoLogo(){
        return gengoLogo;
    }

    public WebElement getContactSalesLink(){
        return  contactSalesLink;
    }

    public List<WebElement> getHeaderLinksParent() {
        return headerLinksParent;
    }

    public List<WebElement> getFooterLastColumnParent() {
        return footerLastColumnParent;
    }

    public WebElement getSupportLink(){
        return supportLink;
    }

    public WebElement getBecomeATranslatorBtn(){
        return becomeATranslatorBtn;
    }

    public WebElement getHeaderText(){
        return headerText;
    }

    public WebElement getSubHeaderText(){
        return subHeaderText;
    }

    public WebElement getNavigationMenu(){
        return navigationMenu;
    }

    public WebElement getOverviewSection(){
        return overviewSection;
    }

    public WebElement getOrderOptionsSection(){
        return orderOptionsSection;
    }

    public WebElement getHowItWorksSection(){
        return howItWorksSection;
    }

    public WebElement getGengoForBusinessSection(){
        return gengoForBusinessSection;
    }

    public WebElement getPricingSection(){
        return pricingSection;
    }

    public WebElement getFooterSection(){
        return footerSection;
    }
}
