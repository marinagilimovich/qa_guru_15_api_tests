import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class RegressInTests {
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void singleResourceSuccessfulTest() {
        given()
                .when()
                .get("/api/unknown/2")
                .then()
                .statusCode(200)
                .body("id", is(2))
                .body("name", is("fuchsia rose"));
    }

    @Test
    void singleResourceFailedTest() {
        given()
                .when()
                .get("/api/unknown/23")
                .then()
                .statusCode(404);
    }

    @Test
    void registrationSuccessfulTest() {
        given()
                .contentType(JSON)
                .body("{ \"email\": \"eve.holt@reqres.in\", " +
                        "\"password\": \"pistol\" }")
                .when()
                .post("/api/register")
                .then()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void loginSuccessfulTest() {
        given()
                .contentType(JSON)
                .body("{ \"email\": \"eve.holt@reqres.in\", " +
                        "\"password\": \"cityslicka\" }")
                .when()
                .post("/api/login")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void updateUserSuccessfulTest() {
        given()
                .contentType(JSON)
                .body("{ \"name\": \"morpheus\", " +
                        "\"job\": \"zion resident\" }")
                .when()
                .put("/api/users/2")
                .then()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }
}
