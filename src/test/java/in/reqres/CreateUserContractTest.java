package in.reqres;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.User;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CreateUserContractTest extends Base{

    @Test
    public void createUserContract() {
        logger = extent.startTest("User contract", "check user creation contract");

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
                .body(matchesJsonSchemaInClasspath("schema.json"))
                .extract().response();
        jsonAsString = response.asString();
        Assert.assertTrue(true);

        String img = logger.addScreenCapture("img-path");
        logger.log(LogStatus.PASS, "user contract " + jsonAsString);
        logger.log(LogStatus.INFO, "Image", "Image: " + img);
    }
}