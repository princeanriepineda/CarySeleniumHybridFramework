package TestCases.Tricentis;

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


public class TricentisRegistration_PositiveFlowTestCase extends BaseTest {

    @Test(priority = 1,dataProvider = "getData")
    @Story("Story: Tricentis Registration Positive Flow")
    @Description("Description: Validation of Input Form Elements In Enter Vehicle Data: Select Drop Down Lists and Input TextBoxes")
    @Epic("Epic: User Management")
    @Feature("Feature: Select DropDowns And Input TextBoxes In Enter Vehicle Data Page")
    public void Test1EnterVehicleDataMultipleValues(HashMap<String,String> input) throws InterruptedException {
        System.out.println("Selenium Started!!!");

        //****************************************************************************,retryAnalyzer = Retry.class
        TricentisHomeP.ClickVehicleHeaderBtn(input.get("Vehicle"));
        TricentisEnterVehicleDP.ClickEnterVehicleDataNextBtn();
        TricentisEnterInsurantDP.FillUpEnterInsurantData(input.get("Gender"));

    }

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = getJsonData(System.getProperty("user.dir") + "/src/main/java/Resources/Tricentis.json");
        // Convert List<HashMap> to Object[][]
        Object[][] dataArray = new Object[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            dataArray[i][0] = data.get(i);
        }
        return dataArray;
    }
}
