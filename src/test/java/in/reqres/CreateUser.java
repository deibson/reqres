package in.reqres;

import com.github.javafaker.Faker;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pojo.User;

import java.io.File;
import java.util.Locale;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class CreateUser {

    public Faker faker = new Faker(new Locale("pt-BR"));

    public static ExtentTest logger;
    public static ExtentReports extent;

    public static Response response;
    public static String jsonAsString;

    @BeforeTest
    public static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = "https://reqres.in/";

        extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/Report.html", true);
        extent.loadConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));
    }

    @Test
    public void createUser() {
        logger = extent.startTest("PostCreateUser", "Create user random");

        User params = new User();
        params.setName(faker.name().firstName());
        params.setJob(faker.job().field());

        response = given()
                .contentType("application/json")
                .body(params.toString())
                .when()
                .post("/api/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED).and()
                .body("name", is(params.getName())).and()
                .body("job", is(params.getJob())).and()
                .body(containsString("id")).and()
                .body(containsString("createdAt")).and()
                .body(matchesJsonSchemaInClasspath("schema.json"))
                .extract().response();
        jsonAsString = response.asString();
        Assert.assertTrue(true);

        String img = logger.addScreenCapture("img-path");
        logger.log(LogStatus.PASS, "user created successfully" + jsonAsString);
        logger.log(LogStatus.INFO, "Image", "Image: " + img);
    }

    @AfterTest
    public void endReport() {
        extent.endTest(logger);
        extent.flush();
    }
}