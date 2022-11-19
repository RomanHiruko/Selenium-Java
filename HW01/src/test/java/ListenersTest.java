import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

import java.lang.reflect.Method;
import java.net.URL;

public class ListenersTest implements WebDriverListener {
    private Logger logger = LogManager.getLogger(ListenersTest.class);

    @Override
    public void afterClick(WebElement element) {
        logger.info("Нажата кнопка");
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        logger.info("Найден элемент");
    }

    @Override
    public void afterGetText(WebElement element, String result) {
        logger.info("Получен текст");
    }

    @Override
    public void afterAnyWindowCall(WebDriver.Window window, Method method, Object[] args, Object result) {
        logger.info("Выполнен переход");
    }

    @Override
    public void afterIsSelected(WebElement element, boolean result) {
        logger.info("ВЫполнено наведение курсора");
    }
}
