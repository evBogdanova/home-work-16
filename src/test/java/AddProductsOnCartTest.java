import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.hamcrest.Matchers.is;

public class AddProductsOnCartTest extends TestBase {
    private static final String COOKIE = "Nop.customer=df2370ac-e83f-4530-ae7d-48dc795d31ee; NOPCOMMERCE.AUTH=E41B9C2A5A7D5154F5B5224233ABBAAD8421B448816C8502693104489DF052F171C474D936FD49C1A08388B566CE393C1FD307FAD9C30B0DAD3F418C586D4E46B5A213E063A2C9F873E553BCF4A18EC4D520F23FDCA7A4580D2AE733F474C77C29F58B3F4466CC517144690DA1AFB046F61DE5444635A1F94E9208EDC2E32652C74448BE654BA63079AED00088770DFB11A6554F658F744800FD8C3C0A86DB4F;";

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
        getWebDriver().manage().addCookie(new Cookie("Nop.customer", getCookie()));
        $("#add-to-cart-button-31").click();
        productSize = convert((actualProductSize + 1));
        $("#topcartlink a[href='/cart']").shouldHave(text(productSize));
    }

}
