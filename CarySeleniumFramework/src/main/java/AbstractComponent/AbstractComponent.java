package AbstractComponent;

import io.qameta.allure.Attachment;
import org.jboss.aerogear.security.otp.Totp;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AbstractComponent {

    WebDriver driver;
    public AbstractComponent(WebDriver driver) {
        this.driver = driver;
    }

    public void waitforelementtoappear(By FindBy){
        WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        Wait.until(ExpectedConditions.visibilityOfElementLocated(FindBy));
    }
    public void waitforWebelementtoappear(WebElement FindBy){
        WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        Wait.until(ExpectedConditions.visibilityOf(FindBy));
    }
    public void waitforWebelementtoSelect(WebElement FindBy){
        WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        Wait.until(ExpectedConditions.elementSelectionStateToBe(FindBy,true));
    }

    public static String getTwoFactorCode(){
        Totp totp = new Totp("f6ahjewlhz4bckutkewqebp7hrxfkqb5");
        String twoFactorCode = totp.now();
        return twoFactorCode;
    }

    @Attachment(type = "image/png")
    public static byte[] screenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

}
