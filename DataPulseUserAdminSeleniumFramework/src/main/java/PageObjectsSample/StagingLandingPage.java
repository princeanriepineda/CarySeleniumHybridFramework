package PageObjectsSample;

import AbstractComponent.AbstractComponent;
import Utilities.PasswordEncryptionDecryptionUtil;
import io.qameta.allure.Allure;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestListener;
import org.testng.annotations.Listeners;

@Listeners({ITestListener.class})

public class StagingLandingPage extends AbstractComponent {

    WebDriver driver;

    PasswordEncryptionDecryptionUtil pswrdEncDnc = new PasswordEncryptionDecryptionUtil();

    public StagingLandingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    //*****************************************************************************
    //ELEMENTS HERE****************************************************************
    //*****************************************************************************
    @FindBy(xpath = "//img[@alt='logo']")
    WebElement F1LogoImg;

    @FindBy(xpath = "//div[text()='Resource Management Tool']")
    WebElement RsrMngmntTooltxt;

    @FindBy(xpath = "//div[text()='Welcome Stratizens!']")
    WebElement WlcmStrtzntxt;

    @FindBy(xpath = "//span[@class='nsm7Bb-HzV7m-LgbsSe-BPrWId'][text()='Mag-sign in sa Google']")
    WebElement GglLgnBtn;

    @FindBy(xpath = "//html/body/iframe[1]")
    WebElement elementInsideIframe;

    @FindBy(xpath = "//input[@type='email' and @class='whsOnd zHQkBf']")
    WebElement usernametxtbx;

    @FindBy(xpath = "(//span[@jsname='V67aGc' and @class='VfPpkd-vQzf8d'])[2]")
    WebElement usrnamenxtbtn;

    @FindBy(xpath = "//input[@type='password' and @class='whsOnd zHQkBf']")
    WebElement pswrdtxtbx;

    @FindBy(xpath = "(//span[@jsname='V67aGc'])[2]")
    WebElement pswrdnxtbtn;

    @FindBy(xpath = "(//div[@style]/div)[6]")
    WebElement ErrMsg1;

    @FindBy(xpath = "(//div[@style]/div)[7]")
    WebElement ErrMsg2;

    @FindBy(xpath = "(//div[@style]/div)[8]")
    WebElement ErrMsg3;

    @FindBy(xpath = "(//div[@style]/div)[9]")
    WebElement ErrMsg4;

    @FindBy(xpath = "//div[@class='nsm7Bb-HzV7m-LgbsSe-MJoBVe']/parent::div")
    WebElement LogInAgainBtn;

    @FindBy(xpath = "(//div[@class='fFW7wc-ibnC6b-ssJRIf'])[2]")
    WebElement UseAnotherAcctBtn;



    //******************************************************************************
    //functions here****************************************************************
    //******************************************************************************

    public void switchToIframe() {
        driver.switchTo().frame(elementInsideIframe);
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public void switchwindow() {
        String Title = driver.getTitle();
        String parentHandle = driver.getWindowHandle();
        // Switch to the new window
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(parentHandle)) {
                driver.switchTo().window(windowHandle);
                return;
            }
        }
        //  If the desired window title was not found, switch back to the parent window
        driver.switchTo().window(Title);
    }





//=================================================================================================

        @Step("Step 1")
        @Severity(SeverityLevel.MINOR)
        public void Goto() throws InterruptedException {
            // driver.wait(5000);
            driver.get("https://f1-staging.stratpoint.dev/");
        }

        @Step("Step 2")
        @Severity(SeverityLevel.MINOR)
        public void Log_In_UI_Verification() throws InterruptedException {
            // Verify that the F1LogoImg is displayed
            boolean isF1LogoImgDisplayed = F1LogoImg.isDisplayed();
            Allure.addAttachment("Expected: F1LogoImg is displayed", "Actual: " + isF1LogoImgDisplayed);
            Assert.assertTrue(isF1LogoImgDisplayed, "Image is Not Displayed");

            // Verify that RsrMngmntTooltxt is not empty
            String rsrMngmntText = RsrMngmntTooltxt.getText();
            Allure.addAttachment("Expected: RsrMngmntTooltxt is not empty", "Actual: " + rsrMngmntText);
            Assert.assertFalse(rsrMngmntText.isEmpty(), "Txt is Incorrect or Empty");

            // Verify that WlcmStrtzntxt is not empty
            String wlcmStrtzntxt = WlcmStrtzntxt.getText();
            Allure.addAttachment("Expected: WlcmStrtzntxt is not empty", "Actual: " + wlcmStrtzntxt);
            Assert.assertFalse(wlcmStrtzntxt.isEmpty(), "Txt is Incorrect or Empty");

            // Verify that GglLgnBtn is displayed
            boolean isGglLgnBtnDisplayed = GglLgnBtn.isDisplayed();
            Allure.addAttachment("Expected: GglLgnBtn is displayed", "Actual: " + isGglLgnBtnDisplayed);
            Assert.assertTrue(isGglLgnBtnDisplayed, "Btn is Not Displayed");


        }

        @Step("Step 3")
        @Severity(SeverityLevel.NORMAL)
        public void Log_In_Stratpoint_Email_Registered(String Username,String Password) throws InterruptedException {
            // Click GglLgnBtn inside the iframe
            Allure.step("Click GglLgnBtn inside the iframe");
            driver.navigate().refresh();
            GglLgnBtn.click();

            // Switch to the iframe
            Allure.step("Switch to pop up window Google Sign-In.");
            switchwindow();

            // Perform actions, e.g., enter username and password
            Allure.step("Enter username and click Next");
            usernametxtbx.sendKeys(Username);
            usrnamenxtbtn.click();

            Allure.step("Enter password and click Next");
            waitforWebelementtoappear(pswrdtxtbx);
            pswrdtxtbx.sendKeys(Password);
            waitforWebelementtoappear(pswrdnxtbtn);
            pswrdnxtbtn.click();

            //Two Factor Authenticator
            Allure.step("Two Factor Authenticator code");
            driver.findElement(By.id("totpPin")).sendKeys(getTwoFactorCode());
            driver.findElement(By.id("totpNext")).click();

            //Switch Back to Main Window
            Allure.step("Switch Back To Main Window");
            switchwindow();


        }

        @Step("Step 4")
        @Severity(SeverityLevel.NORMAL)
        public void Log_In_Stratpoint_Email_Not_Registered_Verification() throws InterruptedException {


            String ActualerrMsg1 = ErrMsg1.getText();
            String ExpectederrMsg1 = "You don't have access to Resource Management Tool.";
            Assert.assertEquals(ActualerrMsg1,ExpectederrMsg1, "Incorrect Error Message.");
            Allure.addAttachment("Actual Error Message 1", ActualerrMsg1);

            String ActualerrMsg2 = ErrMsg2.getText();
            String ExpectederrMsg2 = "To request access, contact Kaye Bautista";
            Assert.assertEquals(ActualerrMsg2,ExpectederrMsg2, "Incorrect Error Message.");
            Allure.addAttachment("Actual Error Message 2", ActualerrMsg2);


            String ActualerrMsg3 = ErrMsg3.getText();
            String ExpectederrMsg3 = "(kbautista@stratpoint.com) or Alisandra Yda Jaoguico";
            Assert.assertEquals(ActualerrMsg3,ExpectederrMsg3, "Incorrect Error Message.");
            Allure.addAttachment("Actual Error Message 3", ActualerrMsg3);

            String ActualerrMsg4 = ErrMsg4.getText();
            String ExpectederrMsg4 = "(ajaoguico@stratpoint.com).";
            Assert.assertEquals(ActualerrMsg4,ExpectederrMsg4, "Incorrect Error Message.");
            Allure.addAttachment("Actual Error Message 4", ActualerrMsg4);

        }



    }

