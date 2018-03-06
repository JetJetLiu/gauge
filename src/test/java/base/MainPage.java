package base;

import com.thoughtworks.gauge.Step;

public class MainPage extends StepOpenBaidu {

//    /**
//     * 调用基类的构造方法
//     *
//     * @param baseDriver driver
//     */
//    public MainPage(CommonDriver baseDriver) {
//        super(baseDriver);
//    }

//    public static void main(String[] args) throws InterruptedException {
//        CommonDriver baseDriver = new CommonDriver("");
////        baseDriver.navigateAndMaximize("http://www.baidu.com");

//        MainPage mainPage = new MainPage(baseDriver);
//        mainPage.OpenBaidu();
//    }

    @Step("visit baidu website")
    public void visitBaidu(){
        CommonDriver baseDriver = new CommonDriver("");
        baseDriver.navigateAndMaximize("http://www.baidu.com");
    }
}