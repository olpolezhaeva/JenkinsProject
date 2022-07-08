package model;

import model.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class ProjectPage extends BasePage {

    private static final By DISPLAY_NAME = By.cssSelector(".display-name");

    @FindBy(css = "h1.page-headline")
    private WebElement projectName;

    @FindBy(css = "h1")
    private WebElement folderName;

    @FindBy(linkText = "Dashboard")
    private WebElement dashboardButton;

    @FindBy(linkText = "Build Now")
    private WebElement buildButton;

    @FindBy(xpath = "//td[@class='build-row-cell']//a[@class='tip model-link inside build-link display-name']")
    private List<WebElement> buildList;

    @FindBy(className = "build-row-cell")
    private List<WebElement> buildsRowList;

    public ProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getProjectName() {
        return projectName.getText().substring("Project ".length());
    }

    public String getFolderName() {
        return folderName.getText();
    }

    public HomePage clickDashboardButton() {
        dashboardButton.click();
        return new HomePage(getDriver());
    }

    public boolean isProjectStatus(String value) {
        return getWait20().until(ExpectedConditions.attributeToBe(
                By.cssSelector(".tobsTable-body .job"), "class", value));
    }

    public ProjectPage clickBuildButton() {
        buildButton.click();

        return this;
    }

    public String getBuildNumber() {
        WebElement displayName = getWait20().until(ExpectedConditions.presenceOfElementLocated(DISPLAY_NAME));

        return displayName.getText().substring("#".length());
    }

    public boolean buildNumberIsDisplayed() {
        return getWait20().until(ExpectedConditions.presenceOfElementLocated(DISPLAY_NAME)).isDisplayed();
    }

    public ProjectPage waitForBuildNumber(int number) {
        getWait20().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//td[@class='build-row-cell']//a[@class='tip model-link inside build-link display-name' and .='#" + number + "']")));

        return this;
    }

    public List<WebElement> getVisibleBuildList() {
        return buildList;
    }

    public List<WebElement> getBuildsRowList() {
        return buildsRowList;
    }

    public ProjectPage clickMultipleTimesBuildButton(int number) throws InterruptedException {
        for (int i = 0; i < number; ++i) {
            clickBuildButton();
            Thread.sleep(200);
        }

        return this;
    }

    public ProjectPage refreshPage() {
        getDriver().navigate().refresh();

        return this;
    }

    public List<Integer> getNumbersBuildsList() {
        List<WebElement> buildList = getVisibleBuildList();
        List<Integer> buildNumberList = new ArrayList<>();

        for (WebElement element : buildList) {
            buildNumberList.add(Integer.parseInt(element.getText().replace("#", "")));
        }

        return buildNumberList;
    }
}
