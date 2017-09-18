package com.gengo.automation.tests.CoreTest;

import com.gengo.automation.fields.Constants;
import com.gengo.automation.global.AutomationBase;
import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * @case Check all links in the Contact Support page with every locale.
 * @reference https://gengo.testrail.com/index.php?/cases/view/40786
 */
public class C40786_ContactSupport extends AutomationBase {

    public C40786_ContactSupport() throws IOException {}

    // Expected header texts for some locales are the same as of July 21, 2017
    private final String EN_SUPPORT_HEADER = "Customer Support";
    private final String ZH_SUPPORT_HEADER = "Customer Support";
    private final String JA_SUPPORT_HEADER = "カスタマーサポート";
    private final String ES_SUPPORT_HEADER = "Customer Support";
    private String prevUrl = null;

    @AfterClass
    public void afterRun() throws IOException {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test(priority = 1)
    public void checkWithEnLocale() {
        // Assert that the redirected page is https://staging.gengo.com
        assertTrue(page.getCurrentUrl().equals
                (Constants.HOMEPAGE_URL), var.getWrongUrlErrMsg());

        // Click the SUPPORT link in the header and assert that the language of the redirected page is based on the specified locale.
        homePage.clickSupportLink(Constants.HP_EN_SUPPORT, false);
        assertTrue(supportPage.getPageHeader().getText()
                .equals(EN_SUPPORT_HEADER), var.getTextNotEqualErrMsg());

        // Go back.
        page.goBack();

        // Assert that the redirected page is https://staging.gengo.com
        assertTrue(page.getCurrentUrl().equals
                (Constants.HOMEPAGE_URL), var.getWrongUrlErrMsg());

        // Click the SUPPORT link in the footer and assert that the language of the redirected page is based on the specified locale.
        homePage.clickSupportLink(Constants.HP_EN_SUPPORT, true);
        assertTrue(supportPage.getPageHeader().getText()
                .equals(EN_SUPPORT_HEADER), var.getTextNotEqualErrMsg());

        // Check if all links are working in respective page part.
        supportPage.verifyLinks(Constants.SUPPORT_HEADER, Constants.HP_EN_LOCALE);
        supportPage.verifyLinks(Constants.SUPPORT_BODY, Constants.HP_EN_LOCALE);
        supportPage.verifyLinks(Constants.SUPPORT_FOOTER, Constants.HP_EN_LOCALE);
    }

    @Test(priority = 2)
    public void checkWithZhLocale() {
        page.launchUrl(Constants.HOMEPAGE_URL);
        // Assert that the redirected page is https://staging.gengo.com
        assertTrue(page.getCurrentUrl().equals
                (Constants.HOMEPAGE_URL), var.getWrongUrlErrMsg());

        // Change the locale.
        homePage.changeLocale(Constants.HP_ZH_LOCALE);

        prevUrl = page.getCurrentUrl();

        // Click the SUPPORT link in the header and assert that the language of the redirected page is based on the specified locale.
        homePage.clickSupportLink(Constants.HP_ZH_SUPPORT, false);
        assertTrue(supportPage.getPageHeader().getText()
                .equals(ZH_SUPPORT_HEADER), var.getTextNotEqualErrMsg());

        // Go back.
        page.goBack();

        // Assert that the redirected page is https://staging.gengo.com
        assertTrue(page.getCurrentUrl().equals
                (prevUrl), var.getWrongUrlErrMsg());

        // Click the SUPPORT link in the footer and assert that the language of the redirected page is based on the specified locale.
        homePage.clickSupportLink(Constants.HP_ZH_SUPPORT, true);
        assertTrue(supportPage.getPageHeader().getText()
                .equals(ZH_SUPPORT_HEADER), var.getTextNotEqualErrMsg());

        // Check if all links are working in respective page part.
        supportPage.verifyLinks(Constants.SUPPORT_HEADER, Constants.HP_ZH_LOCALE);
        supportPage.verifyLinks(Constants.SUPPORT_BODY, Constants.HP_ZH_LOCALE);
        supportPage.verifyLinks(Constants.SUPPORT_FOOTER, Constants.HP_ZH_LOCALE);
    }

    @Test(priority = 3)
    public void checkWithJaLocale() {
        page.launchUrl(Constants.HOMEPAGE_URL);
        // Assert that the redirected page is https://staging.gengo.com
        assertTrue(page.getCurrentUrl().equals
                (Constants.HOMEPAGE_URL), var.getWrongUrlErrMsg());

        // Change the locale.
        homePage.changeLocale(Constants.HP_JA_LOCALE);

        prevUrl = page.getCurrentUrl();

        // Click the SUPPORT link in the header and assert that the language of the redirected page is based on the specified locale.
        homePage.clickSupportLink(Constants.HP_JA_SUPPORT, false);
        assertTrue(supportPage.getPageHeader().getText()
                .equals(JA_SUPPORT_HEADER), var.getTextNotEqualErrMsg());

        // Go back.
        page.goBack();

        // Assert that the redirected page is https://staging.gengo.com
        assertTrue(page.getCurrentUrl().equals
                (prevUrl), var.getWrongUrlErrMsg());

        // Click the SUPPORT link in the footer and assert that the language of the redirected page is based on the specified locale.
        homePage.clickSupportLink(Constants.HP_JA_SUPPORT, true);
        assertTrue(supportPage.getPageHeader().getText()
                .equals(JA_SUPPORT_HEADER), var.getTextNotEqualErrMsg());

        // Check if all links are working in respective page part.
        supportPage.verifyLinks(Constants.SUPPORT_HEADER, Constants.HP_JA_LOCALE);
        supportPage.verifyLinks(Constants.SUPPORT_BODY, Constants.HP_JA_LOCALE);
        supportPage.verifyLinks(Constants.SUPPORT_FOOTER, Constants.HP_JA_LOCALE);
    }

    @Test(priority = 4)
    public void checkWithEsLocale() {
        // Assert that the redirected page is https://staging.gengo.com
        assertTrue(page.getCurrentUrl().equals
                (Constants.HOMEPAGE_URL), var.getWrongUrlErrMsg());

        // Change the locale.
        homePage.changeLocale(Constants.HP_ES_LOCALE);

        prevUrl = page.getCurrentUrl();

        // Click the SUPPORT link in the header and assert that the language of the redirected page is based on the specified locale.
        homePage.getHeaderLinksParent().get(2).click();
        assertTrue(supportPage.getPageHeader().getText()
                .equals(ES_SUPPORT_HEADER), var.getTextNotEqualErrMsg());

        // Go back.
        page.goBack();

        // Assert that the redirected page is https://staging.gengo.com
        assertTrue(page.getCurrentUrl().equals
                (prevUrl), var.getWrongUrlErrMsg());

        // Click the SUPPORT link in the footer and assert that the language of the redirected page is based on the specified locale.
        homePage.clickSupportLink(Constants.HP_ES_SUPPORT, true);
        assertTrue(supportPage.getPageHeader().getText()
                .equals(ES_SUPPORT_HEADER), var.getTextNotEqualErrMsg());

        // Check if all links are working in respective page part.
        supportPage.verifyLinks(Constants.SUPPORT_HEADER, Constants.HP_ES_LOCALE);
        supportPage.verifyLinks(Constants.SUPPORT_BODY, Constants.HP_ES_LOCALE);
        supportPage.verifyLinks(Constants.SUPPORT_FOOTER, Constants.HP_ES_LOCALE);
    }
}
