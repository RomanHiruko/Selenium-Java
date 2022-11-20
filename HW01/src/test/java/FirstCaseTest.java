import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class FirstCaseTest {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(FirstCaseTest.class);
    private static int number = 1;

    // Чтение передаваемого параметра browser (-Dbrowser)
    String env = System.getProperty("browser", "chrome");

    //Чтение передаваемого параметра loadStrategy (-DloadStrategy)
    String loadStrategy = System.getProperty("loadStrategy", "normal");

    public void makeScreenshot() {
        try {
            Actions actions = new Actions(driver);
            actions
                    .sendKeys(Keys.END)
                    .sendKeys(Keys.HOME)
                    .perform();
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\FirstCaseScreen_" + number + ".png"));
            number += 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        logger.info("load strategy = " + loadStrategy);
        driver = WebDriverFactory.getDriver(env.toLowerCase(), loadStrategy.toUpperCase());
        logger.info("Драйвер стартовал");
    }

    @AfterEach
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }

    @Test
    public void categoriesTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        Actions actions = new Actions(driver);

        //Открыть страницу DNS
        driver.get("https://www.dns-shop.ru/");
        makeScreenshot();

        //Вывести в логи:
        //заголовок страницы
        String title = driver.getTitle();

        //текущий URL
        logger.info("Title: " + title);
        String url = driver.getCurrentUrl();

        //размеры окна браузера
        logger.info("Url: " + url);
        Dimension size = driver.manage().window().getSize();
        logger.info("Window size: " + size);

        //Нажать кнопку Всё верно
        WebElement buttonEverythingIsRight = driver.findElement(By.xpath("//span[text()='Всё верно']"));
        actions
                .moveToElement(buttonEverythingIsRight)
                .click()
                .perform();
        makeScreenshot();

        //Перейти по ссылке Бытовая техника
        driver.navigate().refresh();
        By linkAppliancesXpath = By.xpath("//a[text()='Бытовая техника']");
        WebElement linkAppliances = driver.findElement(linkAppliancesXpath);
        actions
                .moveToElement(linkAppliances)
                .click()
                .perform();
        makeScreenshot();

        //Проверить, что отображается текст Бытовая кухня
        WebElement textAppliancesTitle = driver.findElement(By.xpath("//h1[text()='Бытовая техника']"));
        Assertions.assertTrue(textAppliancesTitle.isDisplayed(), "Заголовок не отображается!");

        //Перейти по ссылке Техника для кухни
        WebElement linkKitchenAppliances = driver.findElement(By.xpath("//span[text()='Техника для кухни']"));
        actions
                .moveToElement(linkKitchenAppliances)
                .click()
                .perform();

        //Проверить, что отображается текст Техника для кухни
        WebElement textKitchenAppliancesTitle = driver.findElement(By.xpath("//span[text()='Техника для кухни']"));
        Assertions.assertTrue(textKitchenAppliancesTitle.isDisplayed(), "Заголовок не отображается!");

        //Проверить, что отображается ссылка Собрать свою кухню
        WebElement linkMakeKitchen = driver.findElement(By.xpath("//a[text()='Собрать свою кухню']"));
        Assertions.assertTrue(linkMakeKitchen.isDisplayed(), "Ссылка не отображается");

        //Вывести в логи названия всех категорий
        List<WebElement> textAllCategories = driver.findElements(By.xpath("//div[@class='subcategory__item-container ']//span"));
        for (WebElement category : textAllCategories) {
            logger.info("Категория: " + category.getText());
        }

        //Проверить, что количество категорий больше 5
        Assertions.assertTrue(textAllCategories.size() > 5, "Количество категорий меньше или равно 5");
    }

    @Test
    public void cookingTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        Actions actions = new Actions(driver);

        //Открыть страницу DNS
        driver.get("https://www.dns-shop.ru/");
        makeScreenshot();

        //Нажать кнопку Всё верно
        WebElement buttonEverythingIsRight = driver.findElement(By.xpath("//span[text()='Всё верно']"));
        actions
                .moveToElement(buttonEverythingIsRight)
                .click()
                .perform();
        makeScreenshot();

        //Навести курсор на ссылку Бытовая техника
        driver.navigate().refresh();
        By linkAppliancesXpath = By.xpath("//a[text()='Бытовая техника']");
        wait.until(ExpectedConditions.elementToBeClickable(linkAppliancesXpath));
        WebElement linkAppliances = driver.findElement(linkAppliancesXpath);
        actions
                .moveToElement(linkAppliances)
                .perform();
        makeScreenshot();

        //Проверить, что отображаются ссылки:
        //Техника для кухни
        WebElement linkKitchenAppliances = driver.findElement(By.xpath("//a[text()='Техника для кухни']"));
        Assertions.assertTrue(linkKitchenAppliances.isDisplayed(), "Ссылка Техника для кухни не отображается");

        //Техника для дома
        WebElement linkHouseAppliances = driver.findElement(By.xpath("//a[text()='Техника для дома']"));
        Assertions.assertTrue(linkHouseAppliances.isDisplayed(), "Ссылка Техника для дома не отображается");

        //Красота и здоровье
        WebElement linkBeautyAndHealth = driver.findElement(By.xpath("//a[text()='Красота и здоровье']"));
        Assertions.assertTrue(linkBeautyAndHealth.isDisplayed(), "Ссылка Красота и здоровье не отображается");

        //Навести курсор на ссылку Приготовление пищи
        WebElement linkCookingFood = driver.findElement(By.xpath("//a[text()='Приготовление пищи']"));
        actions.moveToElement(linkCookingFood).perform();
        makeScreenshot();

        //Проверить, что количество ссылок в подменю Приготовление пищи больше 5
        List<WebElement> popupCookingFood = driver.findElements(By.xpath("//a[text()='Приготовление пищи']//a"));
        Assertions.assertTrue(popupCookingFood.size() > 5, "Количество ссылок в подменю Приготовление пищи меньше или равно 5");

        //Навести курсор на ссылку Плиты
        //Перейти по ссылке Плиты
        WebElement linkStoves = driver.findElement(By.xpath("//a[text()='Приготовление пищи']//a[text()='Плиты']"));
        actions.moveToElement(linkStoves).click().perform();
        makeScreenshot();

        //Перейти по ссылке Плиты электрические
        WebElement linkElectricStoves = driver.findElement(By.xpath("//span[text()='Плиты электрические']"));
        linkElectricStoves.click();
        makeScreenshot();

        //Проверить, что в тексте Плиты электрические [количество] товаров количество товаров больше 100
        WebElement textProductsCount = driver.findElement(By.xpath("//span[@class='products-count']"));
        String[] text = textProductsCount.getText().split(" ");
        int count = Integer.parseInt(text[0]);
        Assertions.assertTrue(count > 100, "в тексте Плиты электрические [количество] товаров количество товаров меньше или равно 100");
    }
}