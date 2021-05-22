import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.filters;
import static io.restassured.RestAssured.given;

public class TestBase {

    protected static Integer convert(String productSize) {
        return Integer.parseInt(productSize.substring(1, productSize.length() - 1));
    }

    protected String convert(Integer productSize) {
        return "(" + (productSize + 1) + ")";
    }

    protected ValidatableResponse addProductToCart(String cookie) {
        return given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body("addtocart_31.EnteredQuantity=1")
                .cookie(cookie)
                .when()
                .post("/addproducttocart/details/31/1")
                .then()
                .statusCode(200);
    }

    protected String getCookie() {
       return given()
                        .log().uri()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .formParam("Email", "test@testovi4.test")
                        .formParam("Password", "123456")
                        .when()
                        .post("/login")
                        .then()
                        .statusCode(302)
                        .log().body()
                        .extract()
                        .cookie("NOPCOMMERCE.AUTH");
    }

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://demowebshop.tricentis.com";
    }
}
