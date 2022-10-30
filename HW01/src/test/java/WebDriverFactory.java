import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {
    private static Logger logger = LogManager.getLogger(WebDriverFactory.class);


    public static WebDriver getDriver(String browser, String loadStrategy) {

        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--incognito");
                options.setPageLoadStrategy(PageLoadStrategy.valueOf(loadStrategy));
                //options.setImplicitWaitTimeout(Duration.ofSeconds(5));
                logger.info("������� ��� �������� Google Chrome");
                return new ChromeDriver(options);
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options1 = new FirefoxOptions();
                options1.addArguments("--start-maximized");
                options1.addArguments("--incognito");
                options1.setPageLoadStrategy(PageLoadStrategy.valueOf(loadStrategy));
                //options1.setImplicitWaitTimeout(Duration.ofSeconds(5));
                logger.info("������� ��� Mozilla Firefox");
                return new FirefoxDriver(options1);
            default:
                throw new RuntimeException("������� ������������ �������� ��������");
        }
    }
}
