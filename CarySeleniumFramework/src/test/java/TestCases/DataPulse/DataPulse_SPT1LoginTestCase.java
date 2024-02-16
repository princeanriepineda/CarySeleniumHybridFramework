package TestCases.DataPulse;

import Base.BaseTest;
import Base.Retry;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class DataPulse_SPT1LoginTestCase extends BaseTest {

    @Test(priority = 1,dataProvider = "getData",retryAnalyzer = Retry.class)
    @Story("Story: SPT-1[LOGIN], Test Case ID: SPT-1-001")
    @Description("Description: Log In - Stratpoint Email -Registered")
    @Epic("Epic: User Administrator")
    @Feature("Feature: Login")
    public void Test1EnterVehicleDataMultipleValues(HashMap<String,String> input) throws InterruptedException {
        System.out.println("Selenium Started!!!");

        //****************************************************************************
        DPUALoginP.LoginWithYourStratpointEmailBtn();
        DPUALoginP.InjectUsernameAndPassword(input.get("JSONUsername"),input.get("JSONPassword"));

    }

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = getJsonData(System.getProperty("user.dir") + "/src/main/java/Resources/DataPulseAdminLogin.json");
        // Convert List<HashMap> to Object[][]
        Object[][] dataArray = new Object[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            dataArray[i][0] = data.get(i);
        }
        return dataArray;
    }
}
