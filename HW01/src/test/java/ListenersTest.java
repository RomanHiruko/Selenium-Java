import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ListenersTest implements WebDriverListener {
    private Logger logger = LogManager.getLogger(ListenersTest.class);
    private static int number = 1;

    @Override
    public void afterClick(WebElement element) {
        logger.info("Нажата кнопка");
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        try {
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\ListenersScreen_" + number + ".png"));
            number += 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Сделан скриншот после нахождения элемента");
    }

    @Override
    public void afterGetText(WebElement element, String result) {
        logger.info("Получен текст");
    }
}
