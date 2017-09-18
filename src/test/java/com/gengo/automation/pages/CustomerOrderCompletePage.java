package com.gengo.automation.pages;

import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.CustomerOrderCompletePageFactory;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;

/**
 * @class Object repository for sample template of a PageObject class.
 * Contains element operations.
 */
public class CustomerOrderCompletePage extends CustomerOrderCompletePageFactory {

    public CustomerOrderCompletePage(WebDriver driver) {
        super(driver);
    }
    private Wait wait = new Wait(driver);

    public void clickGoToDashboard() {
        wait.impWait(10);
        getBackToDashBoardBtn().click();
        wait.impWait(10);
    }

    public String orderNumber() {
        return getOrderNumber().getText();
    }

    public BigDecimal totalPrice() {
        String price = getOrderTotalPrice().getText().substring(1);
        return truncateDecimal(Double.parseDouble(price), 2);
    }

    public String pricePerUnit() {
        return getOrderPricePerUnit().getText().replaceAll("[A-z /]","").substring(1);
    }

    public String computedTotalPrice(String unitCount) {
        double intPricePerUnit = Double.parseDouble(this.pricePerUnit());
        Double computed = intPricePerUnit * Integer.parseInt(unitCount);

        return String.format("%.2f", computed);
    }

    public String computedTotalPrice(String unitCount, String recordedPricePerUnit) {
        double intPricePerUnit = Double.parseDouble(recordedPricePerUnit);
        Double computed = intPricePerUnit * Integer.parseInt(unitCount);

        return String.format("%.2f", computed);
    }

    public boolean isDeductedRight(String unitCount) {
        return this.totalPrice().toString().contains(this.computedTotalPrice(unitCount));
    }

    public boolean isDeductedRight(String unitCount, String recordedPricePerUnit) {
        return this.totalPrice().toString().contains(this.computedTotalPrice(unitCount, recordedPricePerUnit));
    }

    public String extractedUnitCount() {
        return getUnitCount().getText().replaceAll("[A-z /]", "");
    }

    private BigDecimal truncateDecimal(Double x, int numberOfDecimals)
    {
        if (x > 0) {
            return new BigDecimal(String.valueOf(x)).setScale(numberOfDecimals, BigDecimal.ROUND_FLOOR);
        }
        return new BigDecimal(String.valueOf(x)).setScale(numberOfDecimals, BigDecimal.ROUND_CEILING);
    }
}
