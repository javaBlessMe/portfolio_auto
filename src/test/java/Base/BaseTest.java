package Base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import static com.codeborne.selenide.Selenide.*;
public abstract class BaseTest {
    protected static WebDriver driver;
    @RegisterExtension
    TestListener testListener = new TestListener();

    @BeforeEach
    void setUp(){
        Configuration.timeout=25000;
        Configuration.browserSize ="1366x768";
        SelenideLogger.addListener("AllureSelenide",new AllureSelenide());
        open("https://auto.ru/");
        driver=WebDriverRunner.getWebDriver();
    }

   @AfterEach
    void tearDown(){
       byte[] byteScreenShort=  ((TakesScreenshot) driver).
                getScreenshotAs(OutputType.BYTES); //Сделать скриншот перед закрытием. Скриншот будет делаться всегда,
       testListener.setFailScreenshot(byteScreenShort); //но добавляться в отчет только если тест упал
       clearBrowserCookies();
       clearBrowserLocalStorage();
       closeWebDriver();
    }
}
