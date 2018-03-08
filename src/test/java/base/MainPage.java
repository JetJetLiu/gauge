package base;

import base.DriverUtil;
import driver.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.thoughtworks.gauge.Step;
import driver.DriverFactory;

public class MainPage {

//    /**
//     * ���û���Ĺ��췽��
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
//        //��ÿ�ζ�ʹ��Driver����������ֹ�����
////        �������
//        WebDriver webDriver = DriverFactory.getDriver();
//        String app_url = System.getenv("BD_URL");
//        webDriver.get(app_url);
////        Demo�ж���"/",û�������Ҳ���������е�
////        webDriver.get(app_url + "/");
////        �ر������
//        webDriver.close();


        // ʹ��Driver���@BeforeSuite��@AfterSuite�ȷ�����ÿ��ִ��spec�����Զ��������
        try{
            String app_url = System.getenv("BD_URL");
            Driver.webDriver.get(app_url + "/");
            Driver.webDriver.findElement(By.id("kw")).sendKeys("hello");
            Driver.webDriver.findElement(By.id("su")).click();
        }catch (Exception e){
            e.printStackTrace();

        }

        // ʹ��DriverUtil��װ�������������� �ٶ�
//        DriverUtil baseDriver = new DriverUtil("");
//        baseDriver.navigateAndMaximize("http://www.baidu.com");
    }
}