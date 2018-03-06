package base;

public class StepOpenBaidu {
    // 基类的变量，所有继承的类，都可以使用
    CommonDriver baseDriver;

    /**
     * 页面元素定位符
     */
    String URL = "http://www.baidu.com";


    /**
     * 默认构造方法
     */
    public StepOpenBaidu(){
    }

    /**
     * 构造方法
     */
    public StepOpenBaidu(CommonDriver driver){
        this.baseDriver = driver;
    }

    /**
     * 打开百度
     */
    public void OpenBaidu() throws InterruptedException {
        baseDriver.navigateAndMaximize(URL);
        baseDriver.sleep(3000);
        baseDriver.quit();
    }

}
