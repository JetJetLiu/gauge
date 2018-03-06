import driver.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DriverUtil {

    public static WebElement findByWrapper(String mode, String pattern) {
        switch (mode) {
            case "xpath":
                return Driver.webDriver.findElement(By.xpath(pattern));

            // Other cases
        }

        // Default Option
        return Driver.webDriver.findElement(By.id(pattern));
    }
}
