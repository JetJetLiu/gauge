package base;

import base.DriverUtil;
import driver.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.thoughtworks.gauge.Step;
import driver.DriverFactory;

public class MainPage {

//    /**
//     * 调用基类的构造方法
//     *
//     * @param baseDriver driver
//     */
//    public MainPage(CommonDriver baseDriver) {
//        super(baseDriver);
//    }

//    public static void main(String[] args) throws InterruptedException {
//        CommonDriver baseDiver = new CommonDriver("");
////        baseDriver.navigateAndMaximize("http://www.baidu.com");

//        MainPage mainPage = new MainPage(baseDriver);
//        mainPage.OpenBaidu();
//    }

    @Step("visit baidu website")
    public void visitBaidu(){
//        //不每次都使用Driver打开浏览器，手工操作
////        打开浏览器
//        WebDriver webDriver = DriverFactory.getDriver();
//        String app_url = System.getenv("BD_URL");
//        webDriver.get(app_url);
////        Demo中多了"/",没有这个，也是正常运行的
////        webDriver.get(app_url + "/");
////        关闭浏览器
//        webDriver.close();


        // 使用Driver里的@BeforeSuite、@AfterSuite等方法，每次执行spec都会自动打开浏览器
        try{
            String app_url = System.getenv("BD_URL");
            Driver.webDriver.get(app_url + "/");
            Driver.webDriver.findElement(By.id("kw")).sendKeys("hello");
            Driver.webDriver.findElement(By.id("su")).click();
        }catch (Exception e){
            e.printStackTrace();

        }

        // 使用DriverUtil封装方法里的浏览器打开 百度
//        DriverUtil baseDriver = new DriverUtil("");
//        baseDriver.navigateAndMaximize("http://www.baidu.com");
    }
}