package base;

public class StepOpenBaidu {
    // ����ı��������м̳е��࣬������ʹ��
    CommonDriver baseDriver;

    /**
     * ҳ��Ԫ�ض�λ��
     */
    String URL = "http://www.baidu.com";


    /**
     * Ĭ�Ϲ��췽��
     */
    public StepOpenBaidu(){
    }

    /**
     * ���췽��
     */
    public StepOpenBaidu(CommonDriver driver){
        this.baseDriver = driver;
    }

    /**
     * �򿪰ٶ�
     */
    public void OpenBaidu() throws InterruptedException {
        baseDriver.navigateAndMaximize(URL);
        baseDriver.sleep(3000);
        baseDriver.quit();
    }

}
