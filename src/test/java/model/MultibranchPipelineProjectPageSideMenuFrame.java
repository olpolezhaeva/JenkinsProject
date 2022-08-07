package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.BaseModel;

public class MultibranchPipelineProjectPageSideMenuFrame extends BaseModel<MultibranchPipelineProjectPageSideMenuFrame> {

    @FindBy(linkText = "Rename")
    private WebElement menuRename;

    @FindBy(css = ".icon-edit-delete")
    private WebElement menuDelete;

    @FindBy(id = "yui-gen1-button")
    private WebElement yesButton;

    public MultibranchPipelineProjectPageSideMenuFrame(WebDriver driver) {
        super(driver);
    }

    public RenamePage<MultibranchPipelineProjectPage, MultibranchPipelineProjectPageSideMenuFrame> clickMenuRenameAndGoToRenamePage() {
        menuRename.click();

        return new RenamePage<>(getDriver(), new MultibranchPipelineProjectPage(getDriver()));
    }

    public MultibranchPipelineProjectPageSideMenuFrame clickMenuDelete() {
        menuDelete.click();

        return this;
    }

    public HomePage confirmDeleteAndGoHomePage() {
        yesButton.click();

        return new HomePage(getDriver());
    }
}
