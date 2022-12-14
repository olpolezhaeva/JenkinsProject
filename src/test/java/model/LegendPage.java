package model;

import model.base.BaseHeaderFooterPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import runner.TestUtils;

import java.util.List;

public final class LegendPage extends BaseHeaderFooterPage {

    public LegendPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getIconDescriptionsText() {
        return TestUtils.getTextFromList(getDriver(), By.xpath("//table[@id='legend-table']//tbody/tr/td"));
    }
}
