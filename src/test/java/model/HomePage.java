package model;

import model.base.BaseSideMenuPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class HomePage extends BaseSideMenuPage<HomePage, HomePageSideMenuFrame> {

    @FindBy(tagName = "h1")
    private List<WebElement> mainHeaderH1;

    @FindBy(xpath = "//td[@class='jenkins-table__cell--tight']")
    private List<WebElement> listBuildButtons;

    @FindBy(xpath = "//ul[@id='breadcrumbs']/li[@class='children']")
    private WebElement dropDownMenuViewsOnBreadcrumbs;

    @FindBy(xpath = "//ul[@id='breadcrumbs']/li[@class='item']")
    private List<WebElement> listItemNamesOnBreadcrumbs;

    @FindBy(css = "div .tab a")
    private List<WebElement> listViewNamesOnTabBar;

    @FindBy(id = "search-box")
    private WebElement inputSearchBox;

    @FindBy(xpath = "//a[@href='/toggleCollapse?paneId=buildQueue']")
    private WebElement btnBuildQueueToggle;

    @FindBy(xpath = "//a[@href='/toggleCollapse?paneId=executors']")
    private WebElement btnBuildExecutorToggle;

    @FindBy(xpath = "//div[@id='buildQueue']//table")
    private List<WebElement> listElementsBuildsInQueue;

    @FindBy(xpath = "//div[@id='executors']//table")
    private List<WebElement> listElementsBuildExecutorStatus;

    @FindBy(id = "systemmessage")
    private WebElement systemMessage;

    @FindBy(css = "tr td a.model-link")
    private List<WebElement> listAllProjectNames;

    @FindBy(xpath = "//a[@rel='noopener noreferrer']")
    private WebElement linkToJenkinsIO;

    @FindBy(xpath = "//a[@href='/legend']")
    private WebElement linkIconLegend;

    @FindBy(id = "menuSelector")
    private WebElement menuSelectorProject;

    private final static String PROJECT_LINK_XPATH = "//a[text()='%s']";
    private final static String PROJECT_ICON_XPATH = "parent::td/parent::tr//img";
    private final static String PROJECT_LINK_ID_XPATH = "//tr[@id='job_%s']";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public HomePageSideMenuFrame getSideMenu() {
        return new HomePageSideMenuFrame(getDriver());
    }

    private WebElement getProjectLinkByName(String name) {
        return getDriver().findElement(By.xpath(String.format(PROJECT_LINK_XPATH, name)));
    }

    public String getProjectIconAttributeClass(String name) {
        return getProjectLinkByName(name).findElement(By.xpath(PROJECT_ICON_XPATH)).getAttribute("class");
    }

    public int getSizeOfProjectLinkByName(String name) {
        return getDriver().findElements(By.xpath(String.format(PROJECT_LINK_ID_XPATH, name))).size();
    }

    public List<WebElement> getListProjectLinkByName(String name) {
        return getDriver().findElements(By.xpath(String.format(PROJECT_LINK_XPATH, name)));
    }

    public OrganizationFolderProjectPage clickOrganizationFolderName(String name) {
        getProjectLinkByName(name).click();

        return new OrganizationFolderProjectPage(getDriver());
    }

    public List<String> getProjectsOnDashboardList() {
        return listAllProjectNames.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public boolean isProjectPresentAfterDelete(String projectName) {
        boolean result = false;

        List<WebElement> actual = mainHeaderH1;

        if (actual.size() == 0) {
            for (String webElement : getProjectsOnDashboardList()) {
                if (webElement.contains(projectName)) {
                    result = false;
                    break;
                } else {
                    result = true;
                }
            }
        } else {
            for (WebElement element : mainHeaderH1) {
                if (element.getText().contains("Welcome to Jenkins!")) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    public boolean isProjectNamePresent(String projectName) {
        return getProjectsOnDashboardList().contains(projectName);
    }

    //?????????????? ??????????????????, ?????????????? ????????!
    public FolderProjectPage clickFolderName(String name) {
        getProjectLinkByName(name).click();

        return new FolderProjectPage(getDriver());
    }

    public PipelineProjectPage clickPipelineName(String name) {
        getProjectLinkByName(name).click();

        return new PipelineProjectPage(getDriver());
    }

    public FreestyleProjectPage clickFreestyleName(String name) {
        getProjectLinkByName(name).click();

        return new FreestyleProjectPage(getDriver());
    }

    public HomePage buildSelectPipeline(String pipelineName, boolean isDoubleClick) {
        for (WebElement el : listBuildButtons) {
            if (el.getText().contains(pipelineName) && isDoubleClick) {
                getActions().moveToElement(el)
                        .doubleClick()
                        .perform();
            } else if (el.getText().contains(pipelineName) && !isDoubleClick) {
                el.click();
            }
        }

        return this;
    }

    public HomePage clickRefreshPage() {
        getDriver().navigate().refresh();

        return this;
    }

    public MyViewPage selectNameOfViewOnBreadcrumbs(String name) {
        dropDownMenuViewsOnBreadcrumbs.click();
        getDriver().findElement(By.xpath(String.format("//li/a[contains(@href, '%s')]", name))).click();

        return new MyViewPage(getDriver());
    }

    public MyViewPage clickNameOfFirstViewOnTabBar() {
        listViewNamesOnTabBar.get(0).click();

        return new MyViewPage(getDriver());
    }

    public MyViewPage clickNameOfViewOnTabBar(String name) {
        getDriver().findElement(By.xpath(String.format("//div/a[contains(text(), '%s')]", name))).click();

        return new MyViewPage(getDriver());
    }

    public List<String> getListItemNamesOnBreadcrumbs() {
        return listItemNamesOnBreadcrumbs.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public PipelineProjectPage navigateToPreviousCreatedPipeline(String projectName) {
        List<WebElement> createdJobFromListJobs = getListProjectLinkByName(projectName);
        getDriver().navigate().to(createdJobFromListJobs.get(0).getAttribute("href"));

        return new PipelineProjectPage(getDriver());
    }

    public SearchPage searchText(String text) {
        inputSearchBox.sendKeys(text, Keys.ENTER);

        return new SearchPage(getDriver());
    }

    public HomePage clickBuildQueueToggleButton() {
        btnBuildQueueToggle.click();

        return this;
    }

    public HomePage clickBuildExecutorToggleButton() {
        btnBuildExecutorToggle.click();

        return this;
    }

    public String getBuildQueueToggleAttrTitle() {
        return btnBuildQueueToggle.getAttribute("title");
    }

    public String getBuildExecutorToggleAttrTitle() {
        return btnBuildExecutorToggle.getAttribute("title");
    }

    public int getSizeOfListElementsBuildsInQueue() {
        return listElementsBuildsInQueue.size();
    }

    public int getSizeOfListElementsBuildExecutorStatus() {
        return listElementsBuildExecutorStatus.size();
    }

    public boolean isItemPresent(String name) {
        boolean isPresent = false;

        List<WebElement> projectsOnDashboard = listAllProjectNames;
        for (WebElement jobs : projectsOnDashboard) {
            if (jobs.getText().contains(name)) {
                isPresent = true;
            }
        }

        return isPresent;
    }

    public MultiConfigurationProjectPage clickMultiConfigurationProjectName(String name) {
        getProjectLinkByName(name).click();

        return new MultiConfigurationProjectPage(getDriver());
    }

    public MultibranchPipelineProjectPage clickMultibranchPipelineName(String name) {
        getProjectLinkByName(name).click();

        return new MultibranchPipelineProjectPage(getDriver());
    }

    public String getSystemMessageText() {
        return getWait5().until(ExpectedConditions.visibilityOf(systemMessage)).getText();
    }

    public String getJenkinsIOPageTitle() {
        String oldTab = getDriver().getWindowHandle();
        linkToJenkinsIO.click();
        ArrayList<String> newTab = new ArrayList<>(getDriver().getWindowHandles());
        newTab.remove(oldTab);
        String title = getDriver().switchTo().window(newTab.get(0)).getTitle();

        return title;
    }

    public boolean isVisibleIconLegend() {
        return linkIconLegend.isDisplayed();
    }

    public boolean isEnabledIconLegend() {
        return linkIconLegend.isEnabled();
    }

    public LegendPage clickLinkIconLegend() {
        linkIconLegend.click();

        return new LegendPage(getDriver());
    }

    public HomePageSelectorMenuFrame clickProjectDropDownMenu(String name) {
        for (WebElement s : listAllProjectNames) {
            if (s.getText().contains(name)) {
                getActions().moveToElement(s).build().perform();
            }
        }
        menuSelectorProject.click();

        return new HomePageSelectorMenuFrame(getDriver());
    }

    public SearchPage cleanAndSearchText(String text) {
        inputSearchBox.clear();
        inputSearchBox.sendKeys(text + Keys.ENTER);

        return new SearchPage(getDriver());
    }

    public boolean isTitleDashboardJenkins() {
        return getDriver().getTitle().contains("Dashboard [Jenkins]");
    }

    public Page404Page switchToPage404() {
        getDriver().navigate().back();

        return new Page404Page(getDriver());
    }

}
