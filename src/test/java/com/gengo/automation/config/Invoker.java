package com.gengo.automation.config;

import com.gengo.automation.global.AccountGeneration;
import com.gengo.automation.helpers.CSVManager;
import com.gengo.automation.helpers.DataManager;
import com.gengo.automation.helpers.PageActions;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.GlobalPage;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.awt.*;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * @class Base class to invoke browser for each test case.
 */
@Listeners(ScreenshotListener.class)
public class Invoker {

    protected static WebDriver driver;
    protected static ResourceBundle conf = ResourceBundle.getBundle("config");
    protected static DataManager data;
    protected static CSVManager csv;
    protected static Wait wait;
    protected static String baseURL = conf.getString("url");
    protected static PageActions page;
    protected static GlobalPage global;
    protected static AccountGeneration accountGeneration;

    /**
     * This method is in-charge of setting up the accounts needed for testing
     * */
//    @BeforeSuite
    public void suiteSetUp() throws IOException, AWTException {
        driver = BrowserFactory.startBrowser(conf.getString("browser").toLowerCase());
        data = new DataManager();
        csv = new CSVManager();
        wait = new Wait(getDriver());
        page = new PageActions(getDriver());
        global = new GlobalPage(getDriver());
        wait.impWait(10);
        page.maximizeWindow();
        accountGeneration = new AccountGeneration(getDriver());
        accountGeneration.generateUsers();
        if (getDriver() != null) {
            getDriver().quit();
        }
    }

    @BeforeClass
    public void setUp() throws IOException {
        driver = BrowserFactory.startBrowser(conf.getString("browser").toLowerCase());

        data = new DataManager();
        csv = new CSVManager();
        wait = new Wait(getDriver());
        page = new PageActions(getDriver());
        global = new GlobalPage(getDriver());

        wait.impWait(10);

        page.maximizeWindow();

        page.launchUrl(baseURL);

        Reporter.log("[Case run started]", true);
    }

    /**
     * NOTE : If the @AfterClass method is commented out in the commit,
     * there is a high chance that it was just committed partially for
     * debugging purposes (for coder's side) and ease of review (for reviewer's side).
     * Otherwise, it's the final PR.
     */
//    @AfterClass
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            Reporter.log("[Case run ended]", true);
            Reporter.log("[Browser closed]", true);
        }
    }

    protected WebDriver getDriver() {
        return driver;
    }
}
