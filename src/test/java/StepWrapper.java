import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.Step;
import driver.Driver;
import org.openqa.selenium.WebElement;

public class StepWrapper {
    @Step("Go to Gauge Get Started Page1")
    public void goToGaugeGetStartedPage1() throws InterruptedException {
        WebElement getStartedButton = DriverUtil.findByWrapper("xpath", "//a[@class='link-get-started']");
        getStartedButton.click();
        Gauge.writeMessage("Page title is %s", Driver.webDriver.getTitle());
    }
}
