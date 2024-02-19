package PageObjectsDataPulseAdmin;

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

public class DataPulseUserAdminLogInPage extends AbstractComponent {

    WebDriver driver;
    public DataPulseUserAdminLogInPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "")
    WebElement LoginWithYourStratpointEmailButton;
    @FindBy(xpath = "")
    WebElement LoginModalTitleText;
    @FindBy(xpath = "")
    WebElement UsernameTextBox;
    @FindBy(xpath = "")
    WebElement PasswordTextBox;
    @FindBy(xpath = "")
    WebElement NextButtonModal;


    //*************************************************************************************

    @Step("Step: Click The (Login With Your Stratpoint Email) Button In Data Pulse Login Page")
    @Severity(SeverityLevel.MINOR)
    public void LoginWithYourStratpointEmailBtn() throws InterruptedException {
        try {
            Allure.step("Click the (Login With Your Stratpoint Email) Button.");
            Boolean isLoginWithYourStratpointEmailButton = LoginWithYourStratpointEmailButton.isDisplayed();
            Assert.assertTrue(isLoginWithYourStratpointEmailButton,"Button Doesn't Exist");
            Assert.assertTrue(LoginWithYourStratpointEmailButton.isEnabled(),
                    "(Login With Your Stratpoint Email) Button Is Disabled.");
            LoginWithYourStratpointEmailButton.click();
            waitforWebelementtoappear(LoginModalTitleText);
            String ActualText = LoginModalTitleText.getText();
            Assert.assertEquals(ActualText,"Login Modal Title","Mismatch in comparison");
            AbstractComponent.screenshot(driver);
            Allure.addAttachment("(Login With Your Stratpoint Email) Button Is Selected, Check For Drop Down Values",
                    "Actual Values: " + isLoginWithYourStratpointEmailButton);
        } catch (AssertionError e) {
            AbstractComponent.screenshot(driver);
            System.out.println("Assertion failed: " + e.getMessage());
            Allure.addAttachment("Assertion Failure Details", e.getMessage());
            throw e;

        }
    }

    //*************************************************************************************

    @Step("Step: Inject The Username and Password In Modal Login Page")
    @Severity(SeverityLevel.MINOR)
    public void InjectUsernameAndPassword(String JSONUsername, String JSONPassword) throws InterruptedException {
         try{
             Allure.step("Inject Username And Password in the Modal shown after the clicked of the (LoginWithYourStratpointEmail)");
             waitforWebelementtoappear(UsernameTextBox);
             Assert.assertTrue(UsernameTextBox.isEnabled(),
                     "Username Text box Is Disabled.");
             UsernameTextBox.sendKeys(JSONUsername);
             String ActualUsername = UsernameTextBox.getAttribute("value");
             String ExpectedUsername = JSONUsername;
             Assert.assertEquals(ActualUsername,ExpectedUsername,"Mismatch Comparison");

             waitforWebelementtoappear(PasswordTextBox);
             Assert.assertTrue(PasswordTextBox.isEnabled(),
                     "Password Text box Is Disabled.");
             PasswordTextBox.sendKeys(JSONPassword);
             String ActualPassword = PasswordTextBox.getAttribute("value");
             String ExpectedPassword = JSONPassword;
             Assert.assertEquals(ActualPassword,ExpectedPassword,"Mismatch Comparison");

             Allure.addAttachment("Validation Passed In Injecting Username And Password, Check For Drop Down Values",
                     "Actual Values: " + ActualUsername+", "+ExpectedUsername);

             Thread.sleep(1000);
             NextButtonModal.click();

         } catch (AssertionError e){
            AbstractComponent.screenshot(driver);
            System.out.println("Assertion failed: " + e.getMessage());
            Allure.addAttachment("Assertion Failure Details", e.getMessage());
            throw e;
        }
    }
}
