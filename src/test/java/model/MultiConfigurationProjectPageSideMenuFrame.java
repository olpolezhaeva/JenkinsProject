package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.BaseModel;

public class MultiConfigurationProjectPageSideMenuFrame extends BaseModel<MultiConfigurationProjectPageSideMenuFrame> {

    @FindBy(linkText = "Rename")
    private WebElement menuRename;

    public MultiConfigurationProjectPageSideMenuFrame(WebDriver driver) {
        super(driver);
    }

    public RenamePage<MultiConfigurationProjectPage, MultiConfigurationProjectPageSideMenuFrame> clickMenuRenameAndGoToRenamePage() {
        menuRename.click();

        return new RenamePage<>(getDriver(), new MultiConfigurationProjectPage(getDriver()));
    }
}