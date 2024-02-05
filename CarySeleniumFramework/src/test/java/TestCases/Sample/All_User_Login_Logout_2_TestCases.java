package TestCases.Sample;

import Base.BaseTest;
import Utilities.ExcelUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

public class All_User_Login_Logout_2_TestCases extends BaseTest {

    @DataProvider(name = "testdata")
    public Object[][] testData(Method method) throws IOException {
        String sheetName=""; // No need to initialize

        // Determine the Excel sheet name based on the test method's name
        if (method.getName().equals("test4_FOP2_000_006_008_009_010_011")) {
            sheetName = "FOP2_000_006";
        } else if (method.getName().equals("FOP2_000_004")) {
            sheetName = "FOP2_000_005";
        }// Add more conditions for other test methods as needed

        return ExcelUtils.readExcel("C://Users//Prince Pineda//IdeaProjects//RequestorFramework//src//main//java//Resources//testdata.xlsx", sheetName);
    }



    @Test(dataProvider = "testdata",priority = 1)
    @Story("Story: LOGIN")
    @Description("Test Description: Log In Log Out - User Redirection - Requestor,Recruitment")
    @Epic("test4 FOP2-000 - 006,008,009,011")
    @Feature("Login")
    public void test4_FOP2_000_006_008_009_010_011(String Username, String Password) throws IOException, InterruptedException{



    }



}
