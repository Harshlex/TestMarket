import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;


public class MainTest {
    public String baseUL = "https://yandex.ru/";
    public WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(baseUL);

    }

    @Test
    public void testFNS() throws IOException {

        //проверка наличия логотипа Яндекса
        WebElement logo = driver.findElement(By.xpath("//div [@aria-label='Яндекс']"));
        Assert.assertNotNull(logo, "Отсутствует логотип яндекса на главной странице");

        //проверка наличия раздела "Маркет"
        WebElement market = driver.findElement(By.xpath("//a [@data-id='market']"));
        Assert.assertNotNull(market, "Отсутствует раздел Маркет");

        //переход на Маркет
        String marketLink = market.getAttribute("href");
        driver.get(marketLink);


        //проверка наличия заголовка Маркет
        WebElement logoMarket = driver.findElement(By.xpath("//a [@id = 'logoPartMarket']"));
        Assert.assertNotNull(logoMarket, "Отсутствует заголовок Маркет");


        //поиск на Маркете
        WebElement search = driver.findElement(By.xpath("//input [@placeholder = 'Искать товары']"));
        search.sendKeys("ноутбук xiaomi redmibook");
        WebElement button = driver.findElement(By.xpath("//button [@type = 'submit']"));
        button.click();

        //выбрать - Сначала мой регион
        WebElement checkbox = driver.findElement(By.xpath("//div [@title='Сначала мой регион']"));
        checkbox.click();

        //проверка галочки - Сначала мой регион
        //Assert.assertTrue(checkbox.isSelected(), "Должна быть выбрана галочка \"Сначала мой регион\"");

    }

    @AfterTest
    /*public void endSession() {
        driver.quit();
    }*/

    public void takeScreenshot()
    {
        //делаем скриншот страницы
        Screenshot screenshot = new
                AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
        try{
            ImageIO.write(screenshot.getImage(),"PNG", new File("screen.png"));
        } catch (IOException e){
            e.printStackTrace();
        }

        driver.quit();
    }
}
