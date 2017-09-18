package com.gengo.automation.pages;

import com.gengo.automation.helpers.ElementState;
import com.gengo.automation.helpers.Switcher;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.CustomerOrderFormPageFactory;
import org.openqa.selenium.*;

import static org.testng.Assert.*;

/**
 * @class Object repository for customer order form page.
 * Contains element operations.
 */
public class CustomerOrderFormPage extends CustomerOrderFormPageFactory {

    public CustomerOrderFormPage(WebDriver driver) {
        super(driver);
    }
    private Switcher switcher = new Switcher(driver);
    private Wait wait = new Wait(driver);
    private ElementState elementState = new ElementState(driver);
    private static final String FILE_PARTIAL_PATH = "src//test//resources//files//";

    public void clickClearAllItems() {
        if (this.isClearAllItemsPresent()) {
            getClearAllItems().click();
            switcher.switchToAlertAndAccept();
            wait.impWait(10);
            driver.navigate().refresh();
            wait.impWait(10);
        }
    }

    /**
     * @param item
     *  Accepts single string argument for item to translate data
     * @param unitCount
     *  Accepts single string argument for unit count per item to translate extracted from the DataSource.xls through HashMap<> Object
     * @param isFile
     *  Accepts argument of boolean value. If {true}, will perform upload of file. Otherwise, word job will be typed.
     */
    public void inputItemToTranslate(String item, String unitCount, boolean isFile) {
        this.clickClearAllItems();
        getOrderFormTextArea().sendKeys(item);
        wait.impWait(60, getUnitCount());
        assertTrue(getUnitCount().getText().contains(unitCount));
        wait.impWait(20, getChooseLanguagesBtn());
        getChooseLanguagesBtn().click();
    }

    /**
     * @param item
     *  Accepts array argument for item to translate data
     * @param unitCount
     *  Accepts array argument for unit count per item to translate extracted from the DataSource.xls through HashMap<> Object
     * @param amountOfJob
     *  Accepts argument base on the length of the parameter 'item' to exactly extract the absolute value of the amount of job.
     * @param isFile
     *  Accepts argument of boolean value. If {true}, will perform upload of file. Otherwise, word job will be typed.
     */
    public void inputItemToTranslate(String[] item, String[] unitCount, int amountOfJob, boolean isFile) {
        this.clickClearAllItems();
        if (!isFile) {
            for (int amt = 0; amt < amountOfJob; amt++) {
                String src = driver.getPageSource();
                if (item[amt].contains(FILE_PARTIAL_PATH) && src.contains("Upload Files")) {
                    getUploadFileBtn().sendKeys(item[amt]);
                    while (elementState.isElementPresentInSource("uploading...", true)) {
                        wait.impWait(30);
                    }
                }
                else {
                    getOrderFormTextArea().sendKeys(item[amt]);
                }
                if (amt == 0) {
                    wait.impWait(30, getAddMoreJobBtn());
                }
                if (amt > 0) {
                    wait.untilElementIsClickable(getDone());
                    getDone().click();
                    driver.navigate().refresh();
                    wait.impWait(10, getAddMoreJobBtn());
                    // Native element interaction is
                    // implemented here due to xpath traversion
                    // >> looping through the DOM using 'amt' variable
                    //    to test if the unit count is correct as expected.
                    assertTrue(driver.findElement(By.xpath("//tr[@class='order_line job_state_success'][" + amt + "]/td[3]/span[2]")).getText().contains(unitCount[amt - 1]));
                }
                if (amountOfJob - amt != 1) {
                    getAddMoreJobBtn().click();
                }
            }
        }
        else {
            String src = driver.getPageSource();
            if (src.contains("Upload Files")) {
                for (int amt = 0; amt < amountOfJob; amt++) {
                    try {
                        getUploadFileBtn().sendKeys(item[amt]);
                        if (amt > 0) {
                            assertTrue(driver.findElement(By.xpath("//tr[@class='order_line job_state_success'][" + amt + "]/td[3]/span[2]")).getText().contains(unitCount[amt - 1]));
                        }
                        wait.untilElementVisible(driver.findElement(By.xpath("//div[@class='progress progress-striped active']")));
                        this.waitForFileToUpload();
                    }
                    catch(StaleElementReferenceException e) {
                        driver.navigate().refresh();
                        continue;
                    }
                }
            }
        }
        wait.untilElementNotVisible(By.xpath("//button[@class='btn btn-primary btn-large pull-right disabled']"));
        getChooseLanguagesBtn().click();
    }

    public void uploadValidFile(String[] filename, boolean[] exceedsLimit) {
        String src = driver.getPageSource();
        if (src.contains("Upload Files")) {
            for (int count = 0; count < filename.length; count++) {
                getUploadFileBtn().sendKeys(filename[count]);
                if(!exceedsLimit[count]) {
                    wait.untilElementVisible(driver.findElement(By.xpath("//div[@class='progress progress-striped active']")));
                    this.waitForFileToUpload();
                }
                else {
                    wait.untilElementVisible(driver.findElement(By.xpath("//div[@class='alert in fade alert-error'][contains(.,'Sorry, this file exceeds the maximum size of 100.0MB.')]")));
                }
            }
        }
    }

    public void addOneFile(String filename) {
        wait.untilElementVisible(getAddMoreFile());
        getAddMoreFile().sendKeys(filename);
        wait.untilElementVisible(driver.findElement(By.xpath("//div[@class='progress progress-striped active']")));
        this.waitForFileToUpload();
    }

    public void addMoreText(String[] texts) {
        for(String text : texts) {
            wait.untilElementIsClickable(getAddMoreBtn());
            getAddMoreBtn().click();
            wait.untilElementVisible(driver.findElement(By.xpath("//div[@id='input_text_file_section_on_textarea']/textarea")));
            driver.findElement(By.xpath("//div[@id='input_text_file_section_on_textarea']/textarea")).sendKeys(text);
        }
    }

    public void addSingleText(String text, String count) {
        this.clickClearAllItems();
        getOrderFormTextArea().sendKeys(text);
        wait.untilElementVisible(driver.findElement(By.xpath("//*[contains(.,'" + count +"')]")));
        wait.impWait(30, getAddMoreJobBtn());
    }

    private boolean isClearAllItemsPresent() {
        return driver.findElements(By.xpath("//button[@class='btn btn-link btn-small pull-right clear-all-items-link']")).size() > 0;
    }

    private void waitForFileToUpload() {
        boolean toClick = true;
        while(toClick) {
            try {
                wait.untilElementNotVisible(By.xpath("//div[@class='progress progress-striped active']"));
                if(driver.findElement(By.xpath("//div[@class='progress progress-striped active']")).isDisplayed())
                    toClick = true;
                else
                    toClick = false;
            }
            catch(TimeoutException e) {
                toClick = true;
            }
            catch(NoSuchElementException e2) {
                toClick = false;
            }
        }
    }

    public double getCurrentCredits() {
        return Double.parseDouble(getNavAccountInfo().getText().substring(getNavAccountInfo().getText().lastIndexOf(" ")+1).replaceAll("[^0-9.,]+",""));
    }
}
