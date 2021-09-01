package in.reqres;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class CreateUserTest extends Base {

    @Test
    public void createUser() {
        logger = extent.startTest("Post create user", "Create user random");

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
                .body(containsString("createdAt"))
                .extract().response();
        jsonAsString = response.asString();
        Assert.assertTrue(true);

        String img = logger.addScreenCapture("img-path");
        logger.log(LogStatus.PASS, "user created successfully" + jsonAsString);
        logger.log(LogStatus.INFO, "Image", "Image: " + img);
    }
}