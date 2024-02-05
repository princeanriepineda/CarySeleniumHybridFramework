package PageObjectsTricentis;

import AbstractComponent.AbstractComponent;
import io.qameta.allure.Allure;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class TricentisEnterVehicleDataPage extends AbstractComponent {

    WebDriver driver;
    public TricentisEnterVehicleDataPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "nextenterinsurantdata")
    WebElement EnterVehicleDataNextBtn;
    @FindBy(xpath = "//label[@class='main'][text()='First Name']")
    WebElement FirstNameTxtInEnterInsurantData;

    //*************************************************************************************

    @Step("Step: Click The Next Button In Tricentis Enter Vehicle Data Page")
    @Severity(SeverityLevel.MINOR)
    public void ClickEnterVehicleDataNextBtn() throws InterruptedException {
        Allure.step("Click Enter Vehicle Data Next Button.");
        try{
            Assert.assertTrue(EnterVehicleDataNextBtn.isEnabled(), "Enter Vehicle Data Next Button Is Disabled.");
            EnterVehicleDataNextBtn.click();
            waitforWebelementtoappear(FirstNameTxtInEnterInsurantData);
            String ActualFirstNameText = FirstNameTxtInEnterInsurantData.getText();
            Assert.assertEquals(ActualFirstNameText,"First Name","Failed To Click The Next Button Since The First Name Text Doesn't Validated");
            Allure.addAttachment("Enter Vehicle Data Next Button Is Selected, Check For Drop Down Values", "Actual Values: " +ActualFirstNameText );
            AbstractComponent.screenshot(driver);
        }catch (AssertionError e){
            AbstractComponent.screenshot(driver);
            System.out.println("Assertion failed: " + e.getMessage());
            Allure.addAttachment("Assertion Failure Details", e.getMessage());
            throw e;
        }
    }
}
