import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;

public class FirstCaseTest {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(FirstCaseTest.class);

    // Чтение передаваемого параметра browser (-Dbrowser)
    String env = System.getProperty("browser", "chrome");

    //Чтение передаваемого параметра loadStrategy (-DloadStrategy)
    String loadStrategy = System.getProperty("loadStrategy", "normal");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        logger.info("load strategy = " + loadStrategy);
        driver = WebDriverFactory.getDriver(env.toLowerCase(), loadStrategy.toUpperCase());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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

        //Открыть страницу DNS
        driver.get("https://www.dns-shop.ru/");

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
        WebElement allRight = driver.findElement(By.xpath("//span[text()='Всё верно']"));
        allRight.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Перейти по ссылке Бытовая техника
        WebElement appliances = driver.findElement(By.xpath("//a[text()='Бытовая техника']"));
        appliances.click();

        //Проверить, что отображается текст Бытовая кухня
        WebElement appliancesTitle = driver.findElement(By.xpath("//h1[text()='Бытовая техника']"));
        Assertions.assertTrue(appliancesTitle.isDisplayed(), "Заголовок не отображается!");

        //Перейти по ссылке Техника для кухни
        WebElement kitchenAppliances = driver.findElement(By.xpath("//span[text()='Техника для кухни']"));
        kitchenAppliances.click();

        //Проверить, что отображается текст Техника для кухни
        WebElement kitchenAppliancesTitle = driver.findElement(By.xpath("//span[text()='Техника для кухни']"));
        Assertions.assertTrue(kitchenAppliancesTitle.isDisplayed(), "Заголовок не отображается!");

        //Проверить, что отображается ссылка Собрать свою кухню
        WebElement makeKitchen = driver.findElement(By.xpath("//a[text()='Собрать свою кухню']"));
        Assertions.assertTrue(makeKitchen.isDisplayed(), "Ссылка не отображается");

        //Вывести в логи названия всех категорий
        List<WebElement> allCategories = driver.findElements(By.xpath("//div[@class='subcategory__item-container ']//span"));
        for (WebElement category : allCategories) {
            logger.info("Категория: " + category.getText());
        }

        //Проверить, что количество категорий больше 5
        Assertions.assertTrue(allCategories.size() > 5, "Количество категорий меньше или равно 5");
    }

    @Test
    public void cookingTest() {

        //Открыть страницу DNS
        driver.get("https://www.dns-shop.ru/");

        //Нажать кнопку Всё верно
        WebElement allRight = driver.findElement(By.xpath("//span[text()='Всё верно']"));
        allRight.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Навести курсор на ссылку Бытовая техника
        WebElement appliances = driver.findElement(By.xpath("//a[text()='Бытовая техника']"));
        Actions actions = new Actions(driver);
        actions
                .moveToElement(appliances)
                .perform();

        //Проверить, что отображаются ссылки:
        //Техника для кухни
        WebElement kitchen = driver.findElement(By.xpath("//a[text()='Техника для кухни']"));
        Assertions.assertTrue(kitchen.isDisplayed(), "Ссылка Техника для кухни не отображается");

        //Техника для дома
        WebElement house = driver.findElement(By.xpath("//a[text()='Техника для дома']"));
        Assertions.assertTrue(house.isDisplayed(), "Ссылка Техника для дома не отображается");

        //Красота и здоровье
        WebElement health = driver.findElement(By.xpath("//a[text()='Красота и здоровье']"));
        Assertions.assertTrue(health.isDisplayed(), "Ссылка Красота и здоровье не отображается");

        //Навести курсор на ссылку Приготовление пищи
        WebElement cookingFood = driver.findElement(By.xpath("//a[text()='Приготовление пищи']"));
        actions
                .moveToElement(cookingFood)
                .perform();

        //Проверить, что количество ссылок в подменю Приготовление пищи больше 5
        List<WebElement> popupCookingFood = driver.findElements(By.xpath("//a[text()='Приготовление пищи']//a"));
        Assertions.assertTrue(popupCookingFood.size() > 5, "Количество ссылок в подменю Приготовление пищи меньше или равно 5");

        //Навести курсор на ссылку Плиты
        //Перейти по ссылке Плиты
        WebElement stoves = driver.findElement(By.xpath("//a[text()='Приготовление пищи']//a[text()='Плиты']"));
        actions
                .moveToElement(stoves)
                .click()
                .perform();

        //Перейти по ссылке Плиты электрические
        WebElement electricStoves = driver.findElement(By.xpath("//span[text()='Плиты электрические']"));
        electricStoves.click();

        //Проверить, что в тексте Плиты электрические [количество] товаров количество товаров больше 100
        WebElement productsCount = driver.findElement(By.xpath("//span[@class='products-count']"));
        String[] text = productsCount.getText().split(" ");
        int count = Integer.parseInt(text[0]);
        Assertions.assertTrue(count > 100, "в тексте Плиты электрические [количество] товаров количество товаров меньше или равно 100");
    }
}