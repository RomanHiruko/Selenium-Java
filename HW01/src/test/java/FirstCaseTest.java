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
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
        //Регистрация слушателя событий
        ListenersTest listener = new ListenersTest();
        WebDriver eventFiringWebDriver = new EventFiringDecorator<>(listener).decorate(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));

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
        WebElement buttonEverythingIsRight = eventFiringWebDriver.findElement(By.xpath("//span[text()='Всё верно']"));
        buttonEverythingIsRight.click();

        //Перейти по ссылке Бытовая техника
        driver.navigate().refresh();
        By linkAppliancesXpath = By.xpath("//a[text()='Бытовая техника']");
        WebElement linkAppliances = eventFiringWebDriver.findElement(linkAppliancesXpath);
        linkAppliances.click();

        //Проверить, что отображается текст Бытовая кухня
        WebElement textAppliancesTitle = eventFiringWebDriver.findElement(By.xpath("//h1[text()='Бытовая техника']"));
        Assertions.assertTrue(textAppliancesTitle.isDisplayed(), "Заголовок не отображается!");

        //Перейти по ссылке Техника для кухни
        WebElement linkKitchenAppliances = eventFiringWebDriver.findElement(By.xpath("//span[text()='Техника для кухни']"));
        linkKitchenAppliances.click();

        //Проверить, что отображается текст Техника для кухни
        WebElement textKitchenAppliancesTitle = eventFiringWebDriver.findElement(By.xpath("//span[text()='Техника для кухни']"));
        Assertions.assertTrue(textKitchenAppliancesTitle.isDisplayed(), "Заголовок не отображается!");

        //Проверить, что отображается ссылка Собрать свою кухню
        WebElement linkMakeKitchen = eventFiringWebDriver.findElement(By.xpath("//a[text()='Собрать свою кухню']"));
        Assertions.assertTrue(linkMakeKitchen.isDisplayed(), "Ссылка не отображается");

        //Вывести в логи названия всех категорий
        List<WebElement> textAllCategories = eventFiringWebDriver.findElements(By.xpath("//div[@class='subcategory__item-container ']//span"));
        for (WebElement category : textAllCategories) {
            logger.info("Категория: " + category.getText());
        }

        //Проверить, что количество категорий больше 5
        Assertions.assertTrue(textAllCategories.size() > 5, "Количество категорий меньше или равно 5");
    }

    @Test
    public void cookingTest() {
        //Регистрация слушателя событий
        ListenersTest listener = new ListenersTest();
        WebDriver eventFiringWebDriver = new EventFiringDecorator<>(listener).decorate(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));

        //Открыть страницу DNS
        driver.get("https://www.dns-shop.ru/");

        //Нажать кнопку Всё верно
        WebElement buttonEverythingIsRight = eventFiringWebDriver.findElement(By.xpath("//span[text()='Всё верно']"));
        buttonEverythingIsRight.click();

        //Навести курсор на ссылку Бытовая техника
        driver.navigate().refresh();
        By linkAppliancesXpath = By.xpath("//a[text()='Бытовая техника']");
        wait.until(ExpectedConditions.elementToBeClickable(linkAppliancesXpath));
        WebElement linkAppliances = eventFiringWebDriver.findElement(linkAppliancesXpath);
        Actions actions = new Actions(eventFiringWebDriver);
        actions
                .moveToElement(linkAppliances)
                .perform();

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
        WebElement linkCookingFood = eventFiringWebDriver.findElement(By.xpath("//a[text()='Приготовление пищи']"));
        actions.moveToElement(linkCookingFood).perform();

        //Проверить, что количество ссылок в подменю Приготовление пищи больше 5
        List<WebElement> popupCookingFood = driver.findElements(By.xpath("//a[text()='Приготовление пищи']//a"));
        Assertions.assertTrue(popupCookingFood.size() > 5, "Количество ссылок в подменю Приготовление пищи меньше или равно 5");

        //Навести курсор на ссылку Плиты
        //Перейти по ссылке Плиты
        WebElement linkStoves = eventFiringWebDriver.findElement(By.xpath("//a[text()='Приготовление пищи']//a[text()='Плиты']"));
        actions.moveToElement(linkStoves).click().perform();

        //Перейти по ссылке Плиты электрические
        WebElement linkElectricStoves = eventFiringWebDriver.findElement(By.xpath("//span[text()='Плиты электрические']"));
        linkElectricStoves.click();

        //Проверить, что в тексте Плиты электрические [количество] товаров количество товаров больше 100
        WebElement textProductsCount = driver.findElement(By.xpath("//span[@class='products-count']"));
        String[] text = textProductsCount.getText().split(" ");
        int count = Integer.parseInt(text[0]);
        Assertions.assertTrue(count > 100, "в тексте Плиты электрические [количество] товаров количество товаров меньше или равно 100");
    }
}