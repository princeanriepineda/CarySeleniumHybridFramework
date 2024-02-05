package PageObjectsTricentis;

import AbstractComponent.AbstractComponent;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.qameta.allure.Allure;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class TricentisEnterInsurantVehicleDataPage extends AbstractComponent {
    WebDriver driver;
    public TricentisEnterInsurantVehicleDataPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id='gendermale']/following::span[@class='ideal-radio'][1]")
    WebElement MaleGenderRadioBtn;
    @FindBy(xpath = "//input[@id='gendermale']")
    WebElement MaleGenderRadioBtnChecker;
    @FindBy(xpath = "//input[@id='genderfemale']/following::span[@class='ideal-radio'][1]")
    WebElement FemaleGenderRadioBtn;
    @FindBy(xpath = "//input[@id='genderfemale']")
    WebElement FeMaleGenderRadioBtnChecker;

    //*************************************************************************************

    @Step("Step: Validation Fill Up Scenario: Enter Insurant Data Page")
    @Severity(SeverityLevel.MINOR)
    public void FillUpEnterInsurantData(String Gender) throws InterruptedException {
        Allure.step("Fill Up Necessary Fields In Enter Insurant Data Page.");
        try{
            if(Gender.equalsIgnoreCase("Female")){
                Assert.assertTrue(FemaleGenderRadioBtn.isEnabled(),"Radio Button Is Not Enabled!");
                FemaleGenderRadioBtn.click();
                String ActualGenderBtn = FeMaleGenderRadioBtnChecker.getAttribute("checked");
                Assert.assertEquals(ActualGenderBtn,"true","Radio Button Is Not Selected!");
                Allure.addAttachment("Validation Passed Check For Actual Values.","Actual Values: "+ActualGenderBtn);
                AbstractComponent.screenshot(driver);
            }else{
                Assert.assertTrue(MaleGenderRadioBtn.isEnabled(),"Radio Button Is Not Enabled!");
                MaleGenderRadioBtn.click();
                String ActualGenderBtn = MaleGenderRadioBtnChecker.getAttribute("checked");
                Assert.assertEquals(ActualGenderBtn,"true","Radio Button Is Not Selected!");
                Allure.addAttachment("Validation Passed Check For Actual Values.","Actual Values: "+ActualGenderBtn);
                AbstractComponent.screenshot(driver);
            }
        }catch (AssertionError e){
            AbstractComponent.screenshot(driver);
            System.out.println("Assertion failed: " + e.getMessage());
            Allure.addAttachment("Assertion Failure Details", e.getMessage());
            throw e;
        }
    }
}
