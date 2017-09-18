package com.gengo.automation.config;

import io.github.bonigarcia.wdm.*;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @class Parser of browser required.
 * @return browser to invoke using the WebDriverManager dependency
 * @dependency_repository https://github.com/bonigarcia/webdrivermanager
 *
 * This is the most magical thing in this automation framework.
 * It doesn't require you to download the required browser drivers
 * since the WebDriverManager is the one who handles the downloading once it is
 * not found in your machine. Otherwise, it wont't download.
 *
 * NOTE : In Linux machine, only Firefox and Chrome are the supported browsers.
 */
public class BrowserFactory {

    public static WebDriver driver = null;
    public static final String FIREFOX = "firefox";
    public static final String CHROME = "chrome";
    public static final String IE = "ie";
    public static final String EDGE = "edge";
    public static final String PHANTOMJS = "phantomjs";
    public static final String HTMLUNIT = "htmlunit";
    public static final String SELENIUMGRID = "seleniumgrid";

    private static final String LIVE_JENKINS = "http://jenkins.gengo.com:4444/wd/hub";

    public static WebDriver startBrowser(String browser) throws MalformedURLException {
        switch (browser) {
            case CHROME:
                ChromeDriverManager.getInstance().setup();
                ChromeOptions options = new ChromeOptions();
                Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("profile.default_content_settings.popups", 0);
                prefs.put("credentials_enable_service", false);
                prefs.put("password_manager_enabled", false);
                options.addArguments("disable-extensions");
                options.setExperimentalOption("prefs", prefs);
                options.addArguments("chrome.switches", "--disable-extensions");
                options.addArguments("--test-type");
                options.addArguments("disable-infobars");
                options.addArguments("start-maximized");
                DesiredCapabilities cap = DesiredCapabilities.chrome();
                cap.setCapability(ChromeOptions.CAPABILITY, options);
                cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
                return driver = new ChromeDriver(cap);
            case FIREFOX:
                FirefoxDriverManager.getInstance().setup();
                return driver = new FirefoxDriver();
            case IE:
                InternetExplorerDriverManager.getInstance().setup();
                return driver = new InternetExplorerDriver();
            case EDGE:
                EdgeDriverManager.getInstance().setup();
                return driver = new EdgeDriver();
            case PHANTOMJS:
                PhantomJsDriverManager.getInstance().useTaobaoMirror().setup();
                return driver = new PhantomJSDriver();
            case HTMLUNIT:
                return driver = new HtmlUnitDriver(true);
            case SELENIUMGRID:
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                return driver = new RemoteWebDriver(new URL(LIVE_JENKINS), capabilities);
            default:
                throw new IllegalArgumentException("Invalid browser name : " + browser);
        }
    }
}
