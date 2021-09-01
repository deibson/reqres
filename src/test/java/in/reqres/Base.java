package in.reqres;

import com.github.javafaker.Faker;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.util.Locale;

public class Base {

    public static ExtentTest logger;
    public static ExtentReports extent;
    public Faker faker = new Faker(new Locale("pt-BR"));
    public static String jsonAsString;
    public static Response response;

    @BeforeTest
    public static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = "https://reqres.in/";

        extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/Report.html", true);
        extent.loadConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));
    }

    @AfterTest
    public void endReport() {
        extent.endTest(logger);
        extent.flush();
    }
}
