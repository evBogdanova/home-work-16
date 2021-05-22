import com.codeborne.selenide.WebDriverRunner;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class AddProductsOnCartTest extends TestBase{
    private static final String COOKIE = "NOPCOMMERCE.AUTH=FE85E9AF5F56E1E18E3056A37350AD6834DE57AECA6BB438936DB22858CE9E89AF20E9641980955849E8242683E3216E6CC99413265E5BB6DB6FE5E3A33DD23D48E9DF5EA71274AA6587D39AF66F0A4C849177D7A4A2744817BC0545D8134EDDE8539977DF05EA3D02A86F50AC2349E183CFA5F9AE021E33156684E96D785F7ED653F7D7FD5F3D4C959B15255B88C836AE49865A70CFC5BE3C07B173AD8C6B52; Nop.customer=d93ab111-fc86-4911-b732-647656a4871d";

    @Test
    void addProductOnCartNewUser() {
        addProductToCart("")
                .body("success", is(true))
                .body("updatetopcartsectionhtml", is("(1)"));
    }

    @Test
    void addProductOnCartExistingUserTest() {
        String productSize = addProductToCart(COOKIE)
                .extract()
                .path("updatetopcartsectionhtml");
        Integer actualProductSize = convert(productSize);
        addProductToCart(COOKIE)
                .body("success", is(true))
                .body("updatetopcartsectionhtml", is(convert(actualProductSize)));

        open("http://demowebshop.tricentis.com/141-inch-laptop");
        getWebDriver().manage().addCookie(new Cookie("NOPCOMMERCE.AUTH", getCookie()));
        $("#add-to-cart-button-31").click();
        $("#topcartlink a[href='/cart']").shouldHave(text(productSize));
    }

}
