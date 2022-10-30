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

    // ������ ������������� ��������� browser (-Dbrowser)
    String env = System.getProperty("browser", "chrome");

    //������ ������������� ��������� loadStrategy (-DloadStrategy)
    String loadStrategy = System.getProperty("loadStrategy", "normal");

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        logger.info("load strategy = " + loadStrategy);
        driver = WebDriverFactory.getDriver(env.toLowerCase(), loadStrategy.toUpperCase());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        logger.info("������� ���������");
    }

    @AfterEach
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("������� ����������!");
        }
    }

    @Test
    public void categoriesTest() {

        //������� �������� DNS
        driver.get("https://www.dns-shop.ru/");

        //������� � ����:
        //��������� ��������
        String title = driver.getTitle();

        //������� URL
        logger.info("Title: " + title);
        String url = driver.getCurrentUrl();

        //������� ���� ��������
        logger.info("Url: " + url);
        Dimension size = driver.manage().window().getSize();
        logger.info("Window size: " + size);

        //������ ������ �� �����
        WebElement allRight = driver.findElement(By.xpath("//span[text()='�� �����']"));
        allRight.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //������� �� ������ ������� �������
        WebElement appliances = driver.findElement(By.xpath("//a[text()='������� �������']"));
        appliances.click();

        //���������, ��� ������������ ����� ������� �����
        WebElement appliancesTitle = driver.findElement(By.xpath("//h1[text()='������� �������']"));
        Assertions.assertTrue(appliancesTitle.isDisplayed(), "��������� �� ������������!");

        //������� �� ������ ������� ��� �����
        WebElement kitchenAppliances = driver.findElement(By.xpath("//span[text()='������� ��� �����']"));
        kitchenAppliances.click();

        //���������, ��� ������������ ����� ������� ��� �����
        WebElement kitchenAppliancesTitle = driver.findElement(By.xpath("//span[text()='������� ��� �����']"));
        Assertions.assertTrue(kitchenAppliancesTitle.isDisplayed(), "��������� �� ������������!");

        //���������, ��� ������������ ������ ������� ���� �����
        WebElement makeKitchen = driver.findElement(By.xpath("//a[text()='������� ���� �����']"));
        Assertions.assertTrue(makeKitchen.isDisplayed(), "������ �� ������������");

        //������� � ���� �������� ���� ���������
        List<WebElement> allCategories = driver.findElements(By.xpath("//div[@class='subcategory__item-container ']//span"));
        for (WebElement category : allCategories) {
            logger.info("���������: " + category.getText());
        }

        //���������, ��� ���������� ��������� ������ 5
        Assertions.assertTrue(allCategories.size() > 5, "���������� ��������� ������ ��� ����� 5");
    }

    @Test
    public void cookingTest() {

        //������� �������� DNS
        driver.get("https://www.dns-shop.ru/");

        //������ ������ �� �����
        WebElement allRight = driver.findElement(By.xpath("//span[text()='�� �����']"));
        allRight.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //������� ������ �� ������ ������� �������
        WebElement appliances = driver.findElement(By.xpath("//a[text()='������� �������']"));
        Actions actions = new Actions(driver);
        actions
                .moveToElement(appliances)
                .perform();

        //���������, ��� ������������ ������:
        //������� ��� �����
        WebElement kitchen = driver.findElement(By.xpath("//a[text()='������� ��� �����']"));
        Assertions.assertTrue(kitchen.isDisplayed(), "������ ������� ��� ����� �� ������������");

        //������� ��� ����
        WebElement house = driver.findElement(By.xpath("//a[text()='������� ��� ����']"));
        Assertions.assertTrue(house.isDisplayed(), "������ ������� ��� ���� �� ������������");

        //������� � ��������
        WebElement health = driver.findElement(By.xpath("//a[text()='������� � ��������']"));
        Assertions.assertTrue(health.isDisplayed(), "������ ������� � �������� �� ������������");

        //������� ������ �� ������ ������������� ����
        WebElement cookingFood = driver.findElement(By.xpath("//a[text()='������������� ����']"));
        actions
                .moveToElement(cookingFood)
                .perform();

        //���������, ��� ���������� ������ � ������� ������������� ���� ������ 5
        List<WebElement> popupCookingFood = driver.findElements(By.xpath("//a[text()='������������� ����']//a"));
        Assertions.assertTrue(popupCookingFood.size() > 5, "���������� ������ � ������� ������������� ���� ������ ��� ����� 5");

        //������� ������ �� ������ �����
        //������� �� ������ �����
        WebElement stoves = driver.findElement(By.xpath("//a[text()='������������� ����']//a[text()='�����']"));
        actions
                .moveToElement(stoves)
                .click()
                .perform();

        //������� �� ������ ����� �������������
        WebElement electricStoves = driver.findElement(By.xpath("//span[text()='����� �������������']"));
        electricStoves.click();

        //���������, ��� � ������ ����� ������������� [����������] ������� ���������� ������� ������ 100
        WebElement productsCount = driver.findElement(By.xpath("//span[@class='products-count']"));
        String[] text = productsCount.getText().split(" ");
        int count = Integer.parseInt(text[0]);
        Assertions.assertTrue(count > 100, "� ������ ����� ������������� [����������] ������� ���������� ������� ������ ��� ����� 100");
    }
}
