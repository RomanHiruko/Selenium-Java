import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class ThirdCaseTest {

    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(ThirdCaseTest.class);
    private static int number = 1;

    // Чтение передаваемого параметра browser (-Dbrowser)
    String env = System.getProperty("browser", "chrome");

    //Чтение передаваемого параметра loadStrategy (-DloadStrategy)
    String loadStrategy = System.getProperty("loadStrategy", "normal");

    @BeforeEach
    public void setUp() {
        logger.info("Env = " + env);
        logger.info("Load Strategy = " + loadStrategy);
        driver = WebDriverFactory.getDriver(env.toLowerCase(), loadStrategy.toUpperCase());
        logger.info("Драйвер стартовал!");
    }

    @AfterEach
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }

    @Test
    public void dnsTest() {
        //Регистрация слушателя событий
        ListenersTest listener = new ListenersTest();
        WebDriver eventFiringWebDriver = new EventFiringDecorator<>(listener).decorate(driver);
        //Открыть сайт DNS
        eventFiringWebDriver.get("https://www.dns-shop.ru/");
        WebDriverWait wait = new WebDriverWait(eventFiringWebDriver, Duration.ofSeconds(100));

        //Сделать скриншот всей страницы после загрузки страницы
        try {
            Actions actionsScreen = new Actions(driver);
            actionsScreen
                    .sendKeys(Keys.END)
                    .sendKeys(Keys.HOME)
                    .perform();
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\FullPage_" + number + ".png"));
            number += 1;
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Навести курсор на ссылку Компьютеры и периферия
        By linkPcAndPeripheralXpath = By.xpath("//a[text()='Компьютеры и периферия']");
        WebElement linkPcAndPeripheral = driver.findElement(linkPcAndPeripheralXpath);
        Actions actions = new Actions(driver);
        actions
                .moveToElement(linkPcAndPeripheral)
                .perform();

        //Сделать скриншот всей страницы после открытия меню
        By submenu = By.xpath("//a[text()='Ноутбуки и аксессуары']");
        wait.until(ExpectedConditions.elementToBeClickable(submenu));
        try {
            Actions actionsScreen = new Actions(driver);
            actionsScreen
                    .sendKeys(Keys.END)
                    .sendKeys(Keys.HOME)
                    .perform();
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\FullPage_" + number + ".png"));
            number += 1;
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Перейти по ссылке Ноутбуки
        Actions actions1 = new Actions(driver);
        actions1
                .moveToElement(linkPcAndPeripheral)
                .perform();
        By linkNotebooksXpath = By.xpath("//a[text()='Ноутбуки']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(linkNotebooksXpath));
        WebElement linkNotebooks = eventFiringWebDriver.findElement(linkNotebooksXpath);
        linkNotebooks.click();

        //Сделать скриншот всей страницы после загрузки страницы
        try {
            Actions actionsScreen = new Actions(driver);
            actionsScreen
                    .sendKeys(Keys.END)
                    .sendKeys(Keys.HOME)
                    .perform();
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\FullPage_" + number + ".png"));
            number += 1;
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Скрыть блок страницы
        JavascriptExecutor js = (JavascriptExecutor) eventFiringWebDriver;
        js.executeScript("return document.getElementsByClassName('header-bottom slide')[0].remove();");

        //Сделать скриншот всей страницы после скрытия блока
        try {
            Actions actionsScreen = new Actions(driver);
            actionsScreen
                    .sendKeys(Keys.END)
                    .sendKeys(Keys.HOME)
                    .perform();
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\FullPage_" + number + ".png"));
            number += 1;
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Выбрать в фильтре Производитель значение ASUS
        WebElement checkboxAsus = eventFiringWebDriver.findElement(By.xpath("//span[text()='ASUS  ']/.." +
                "//span[@class='ui-checkbox__box ui-checkbox__box_list']"));
        wait.until(ExpectedConditions.elementToBeClickable(checkboxAsus));
        checkboxAsus.click();

        //Выбрать в фильтре Объем оперативной памяти значение 32 ГБ
        WebElement dropdownRam = eventFiringWebDriver.findElement(By.xpath("//span[text()='Объем оперативной памяти (ГБ)']"));
        dropdownRam.click();
        WebElement checkbox32Gb = eventFiringWebDriver.findElement(By.xpath("//span[text()='32 ГБ  ']/..//span[@class='ui-checkbox__box ui-checkbox__box_list']"));
        checkbox32Gb.click();

        //Нажать кнопку Применить
        WebElement buttonAccept = eventFiringWebDriver.findElement(By.xpath("//button[text()='Применить']"));
        buttonAccept.click();

        //Сделать скриншот всей страницы после применения фильтров
        try {
            Actions actionsScreen = new Actions(driver);
            actionsScreen
                    .sendKeys(Keys.END)
                    .sendKeys(Keys.HOME)
                    .perform();
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\FullPage_" + number + ".png"));
            number += 1;
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Применить сортировку Сначала дорогие
        WebElement dropdownSorting = eventFiringWebDriver.findElement(By.xpath("//span[text()='Сначала недорогие']"));
        dropdownSorting.click();
        WebElement radioBtnExpensive = eventFiringWebDriver.findElement(By.xpath("//input[@type='radio'][@value=2]/../span"));
        radioBtnExpensive.click();

        //Сделать скриншот всей страницы после применения сортировки
        WebElement catalog = eventFiringWebDriver.findElement(By.xpath("//div[@class='products-list__content']"));
        wait.until(ExpectedConditions.domPropertyToBe(catalog, "style", "[]"));
        try {
            Actions actionsScreen = new Actions(driver);
            actionsScreen
                    .sendKeys(Keys.END)
                    .sendKeys(Keys.HOME)
                    .perform();
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\FullPage_" + number + ".png"));
            number += 1;
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Перейти на страницу первого продукта в списке
        //Страница открывается в новом окне
        //Страница открывается в максимизированном режиме (не fullscreen)
        WebElement linkFirstGood = eventFiringWebDriver.findElement(By.xpath("//a[@class='catalog-product__name ui-link ui-link_black'][1]"));
        wait.until(ExpectedConditions.elementToBeClickable(linkFirstGood));
        String urlFirstGood = linkFirstGood.getAttribute("href");
        String oldWindow = eventFiringWebDriver.getWindowHandle();
        eventFiringWebDriver.switchTo().newWindow(WindowType.WINDOW);
        eventFiringWebDriver.get(urlFirstGood);
        eventFiringWebDriver.manage().window().maximize();
        String newWindow = eventFiringWebDriver.getWindowHandle();

        //Сделать скриншот всей страницы после загрузки страницы
        try {
            Actions actionsScreen = new Actions(driver);
            actionsScreen
                    .sendKeys(Keys.END)
                    .sendKeys(Keys.HOME)
                    .perform();
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\FullPage_" + number + ".png"));
            number += 1;
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Проверить, что заголовок страницы соответствует ожидаемому
        //Название соответствует названию в списке на предыдущей странице
        WebElement titleNotebooks = eventFiringWebDriver.findElement(By.xpath("//h1[@class='product-card-top__title']"));
        String titleNewWindow = titleNotebooks.getAttribute("outerText");
        eventFiringWebDriver.switchTo().window(oldWindow);
        String titleOldWindow = linkFirstGood.getAttribute("outerText");
        Assertions.assertTrue(titleOldWindow.contains(titleNewWindow), "Название не соответствует предыдущей странице");

        //Проверить, что в блоке Характеристики заголовок содержит ASUS
        //Проверить, что в блоке Характеристики значение Объем оперативной памяти равно 32 ГБ
        eventFiringWebDriver.switchTo().window(newWindow);
        By buttonXpath = By.xpath("//button[text()='Развернуть все']");
        WebElement button = eventFiringWebDriver.findElement(buttonXpath);
        Actions actions2 = new Actions(driver);
        actions2
                .moveToElement(button)
                .perform();
        button.click();
        WebElement textTitle = eventFiringWebDriver.findElement(By.xpath("//div[@class='product-card-description__title']"));
        WebElement textRam = eventFiringWebDriver.findElement(By.xpath(" //div[text()=' Объем оперативной памяти ']/.." +
                "//div[not(text()=' Объем оперативной памяти ')]"));
        Assertions.assertTrue(textTitle.getText().contains("ASUS"), "В блоке Характеристики заголовок не содержит ASUS");
        Assertions.assertEquals("32 ГБ", textRam.getText(), "В блоке Характеристики значение Объем оперативной памяти не равно 32 ГБ");
    }
}