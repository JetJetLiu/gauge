package base;

import com.thoughtworks.gauge.Gauge;
import driver.Driver;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/*
Copyright for Jet 2017-11-12
�����ķ�װ
 */
public class DriverUtil {
    /**
     * ��Ա����������װ�� webdriver
     */
    private WebDriver baseDriver = null;

    /**
     * ���췽��
     * ʵ���� RanzhiDriver��ʱ��
     * RanzhiDriver bd = new RanzhiDriver()
     * ִ�б�����������һ������bd
     */
//    public DriverUtil(String browser) {
//        if (browser == "Firefox") {
//            try {
//                this.baseDriver = new FirefoxDriver();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            try {
//                this.baseDriver = new ChromeDriver();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public WebDriver getBaseDriver() {
        return this.baseDriver;
    }

    // ��Ա����
    /**
     * ˽�з����� �������洫�ݵ� ��λ����������λ�������ͣ�Ȼ��λ��Ԫ�ز����ظ�Ԫ��
     *
     * @param selector
     * @return
     */
    public WebElement locatElement(String selector) {
        // �����λ���� �� �ָ�������ô�ʹӷָ������ֳ�����
        // ��һ����By
        // �ڶ����������Ķ�λ��
        // ���û�зָ�������Ĭ���� id ��λ
        WebElement we;

        if (!selector.contains(",")) {
            we = this.baseDriver.findElement(By.id(selector));
        } else {
            String by = selector.split(",")[0].trim();
            String value = selector.split(",")[1].trim();
            we = findElementByChar(by, value);
        }
        return we;
    }

    /**
     * ���ݾ���� by �� value������Ԫ�ض�λ�������ظ�Ԫ��
     *
     * @param by
     * @param value
     * @return
     */
    public WebElement findElementByChar(String by, String value) {
        WebElement we = null;
        String lowerCaseBy = by.toLowerCase();

        if (lowerCaseBy.equals("id") || lowerCaseBy.equals("i")) {
            we = this.baseDriver.findElement(By.id(value));
        } else if (lowerCaseBy.equals("css") || lowerCaseBy.equals("c")) {
            we = this.baseDriver.findElement(By.cssSelector(value));
        } else if (lowerCaseBy.equals("xpath") || lowerCaseBy.equals("x")) {
            we = this.baseDriver.findElement(By.xpath(value));
        } else if (lowerCaseBy.equals("linktext") || lowerCaseBy.equals("lt")) {
            we = this.baseDriver.findElement(By.linkText(value));
        } else if (lowerCaseBy.equals("name") || lowerCaseBy.equals("n")) {
            we = this.baseDriver.findElement(By.name(value));
        } else if (lowerCaseBy.equals("classname") || lowerCaseBy.equals("cn")) {
            we = this.baseDriver.findElement(By.className(value));
        } else if (lowerCaseBy.equals("tagname") || lowerCaseBy.equals("tn")) {
            we = this.baseDriver.findElement(By.tagName(value));
        } else if (lowerCaseBy.equals("partiallinktext") || lowerCaseBy.equals("pl")) {
            we = this.baseDriver.findElement(By.partialLinkText(value));
        }
        return we;
    }

    /**
     * ��λԪ�ؿ�������������д�� switch case
     */
    public WebElement findByWrapper(String mode, String pattern) {
        WebElement we = null;
        switch (mode) {
            case "xpath":
            case "x":
                we = this.baseDriver.findElement(By.xpath(pattern));
                break;
            case "css":
            case "c":
                we = this.baseDriver.findElement(By.cssSelector(pattern));
                break;
            case "name":
            case "n":
                we = this.baseDriver.findElement(By.name(pattern));
                break;
            case "linktext":
            case "lt":
                we = this.baseDriver.findElement(By.linkText(pattern));
                break;
            case "partiallinktext":
            case "pl":
                we = this.baseDriver.findElement(By.partialLinkText(pattern));
                break;
            case "classname":
            case "cn":
                we = this.baseDriver.findElement(By.className(pattern));
                break;
            case "tagname":
            case "tn":
                we = this.baseDriver.findElement(By.tagName(pattern));
                break;
            case "id":
            case "i":
                we = this.baseDriver.findElement(By.id(pattern));
                break;
        }
        // Default Option
        we = this.baseDriver.findElement(By.id(pattern));
        return we;
    }


    /**
     * ��λ һ�� Ԫ��
     *
     * @param selector
     * @return
     */
    public List<WebElement> locatElements(String selector) {
        List<WebElement> we;

        if (!selector.contains(",")) {
            we = this.baseDriver.findElements(By.id(selector));
        } else {
            String by = selector.split(",")[0].trim();
            String value = selector.split(",")[1].trim();
            we = findElementsByChar(by, value);
        }
        return we;
    }

    /**
     * ���ݾ���� by �� value������Ԫ�ض�λ�������ظ�Ԫ��
     *
     * @param by
     * @param value
     * @return
     */
    public List<WebElement> findElementsByChar(String by, String value) {
        List<WebElement> we = null;
        String lowerCaseBy = by.toLowerCase();

        if (lowerCaseBy.equals("id") || lowerCaseBy.equals("i")) {
            we = this.baseDriver.findElements(By.id(value));
        } else if (lowerCaseBy.equals("css") || lowerCaseBy.equals("s")) {
            we = this.baseDriver.findElements(By.cssSelector(value));
        } else if (lowerCaseBy.equals("xpath") || lowerCaseBy.equals("x")) {
            we = this.baseDriver.findElements(By.xpath(value));
        } else if (lowerCaseBy.equals("linktext") || lowerCaseBy.equals("l")) {
            we = this.baseDriver.findElements(By.linkText(value));
        } else if (lowerCaseBy.equals("name") || lowerCaseBy.equals("n")) {
            we = this.baseDriver.findElements(By.name(value));
        } else if (lowerCaseBy.equals("classname") || lowerCaseBy.equals("c")) {
            we = this.baseDriver.findElements(By.className(value));
        } else if (lowerCaseBy.equals("tagname") || lowerCaseBy.equals("t")) {
            we = this.baseDriver.findElements(By.tagName(value));
        } else if (lowerCaseBy.equals("partiallinktext") || lowerCaseBy.equals("p")) {
            we = this.baseDriver.findElements(By.partialLinkText(value));
        }
        return we;
    }

    /**
     * ��λ��ָ����Ԫ�أ����ҷ��� text
     *
     * @param selector
     * @return
     */
    public String getText(String selector) {
        return this.locatElement(selector).getText();
    }

    /**
     * ��ȡһ��Ԫ�ص��ı�
     */
    public String getTexts(String selector) {
        List<WebElement> we = this.locatElements(selector);
        String texts = null;
        for (int i = 0; i < we.size(); i++) {
            //System.out.println(we.get(i).getText());
            texts = we.get(i).getText();
        }return texts;

    }

    /**
     * ����Ԫ���У������ض�Ԫ�أ����� ����
     */
    public void searchAndClick(String selector,String data){
        List<WebElement> we = locatElements(selector);
        String texts = null;
        for (int i = 0; i < we.size(); i++) {
            texts = we.get(i).getText();
            System.out.println(texts);
            if(data==we.get(i).getText()){
                we.get(i).click();
                break;
            }
        }
    }


    /**
     * ����Ԫ���У������ض�Ԫ�أ����� ˫��
     * û�� ��ҳ ��ʾ
     */
    public void searchAndDoubleClickNoPages(String selector,String data){
        List<WebElement> we = locatElements(selector);
        String texts = null;
        for (int i = 0; i < we.size(); i++) {
            texts = we.get(i).getText();
            System.out.println("["+texts+"]");
            //�ַ�����equals,������ ==
            if(data.equals(we.get(i).getText())){
                doubleClickByWebElement(we.get(i));
                break;
            }
        }
    }


    /**
     * ����Ԫ���У������ض�Ԫ�أ����� ˫��
     * �� ��ҳ ��ʾ
     */
    public void searchAndDoubleClickForPages(String selectorForward,String selector,String data) throws InterruptedException{
        searchAndDoubleClickNoPages(selector, data);
        while(locatElement(selectorForward).isEnabled()) {
            click(selectorForward);
            sleep(2000);
            searchAndDoubleClickNoPages(selector, data);
            sleep(2000);
            break;
        }
    }





    /**
     * �������cookies,������ָ����url,������󻯴���
     *
     * @param url
     */
    public void navigateAndMaximize(String url) {
        this.baseDriver.manage().deleteAllCookies();
        this.baseDriver.get(url);
        this.baseDriver.manage().window().maximize();
    }


    /**
     * ˢ�������
     */
    public void navigateRefresh() {
        this.baseDriver.navigate().refresh();
    }

    /**
     * ҳ��ǰ��
     */
    public void navigateForward() {
        this.baseDriver.navigate().forward();
    }

    /**
     * ҳ�����
     */
    public void navigateBack() {
        this.baseDriver.navigate().back();
    }

    /**
     * ���ָ���� selector
     * Լ�� selector ��css selector
     *
     * @param selector
     */
    public void click(String selector) {
        this.locatElement(selector).click();
    }

    /**
     * ��ѡ�򡢸�ѡ��ѡ״̬
     */
    public boolean isSelected(String selector){
        WebElement we = this.locatElement(selector);
        if (we != null) {
            return we.isSelected();
        }
        return false;
    }

    /**
     * �һ�ָ���� selector
     *
     * @param selector
     */
    public void contextClick(String selector) {
        Actions action = new Actions(this.baseDriver);
        action.contextClick(this.locatElement(selector)).perform();
    }

    /**
     * ˫��ָ���� selector
     *
     * @param selector
     */
    public void doubleClickBySelector(String selector) {
        Actions action = new Actions(this.baseDriver);
        action.doubleClick(this.locatElement(selector)).perform();
    }

    /**
     * ˫��ָ���� selector �� WebElement
     *
     * @param selector
     */
    public void doubleClickByWebElement(WebElement we) {
        Actions action = new Actions(this.baseDriver);
        action.doubleClick(we).perform();
    }

    /**
     * ��ͣ ��ָ���� selector
     *
     * @param selector
     */
    public void moveToElement(String selector) {
        Actions action = new Actions(this.baseDriver);
        action.moveToElement(this.locatElement(selector)).perform();
    }


    /**
     * �����ק
     */
    public void dragAndDrop(String selectorBegin, String selectorEnd) {
        Actions action = new Actions(this.baseDriver);
        action.dragAndDrop(this.locatElement(selectorBegin), this.locatElement(selectorEnd)).perform();
    }

    /**
     * get specific attribute of element located by selector
     *
     * @param selector  selector should be passed by an example with "i,xxx"
     * @param attribute attribute to get
     * @return String
     */
    public String getAttribute(String selector, String attribute) {
        WebElement we = this.locatElement(selector);
        if (we != null) {
            return we.getAttribute(attribute);
        }
        return null;
    }

    /**
     * ��ȡ��ǰҳ��ı���
     */
    public String getTitle(){
        return this.baseDriver.getTitle();
    }

    /**
     * ��λ��ָ������Ԫ�أ����Ұ� text ��д��ȥ
     *
     * @param selector
     * @param text
     */
    public void type(String selector, String text) {
        WebElement we = this.locatElement(selector);
        we.clear();
        we.sendKeys(text);
    }

    /**
     * <input ... type="file"> ���ͣ���λ��ʽΪ���ϴ��ļ� ��ť
     * @param selector
     * @param localPath
     */
    public void uploadFileTypeInput(String selector, String localFilePath) {
        WebElement we = this.locatElement(selector);
        we.clear();
        File filePath = new File(localFilePath);
        we.sendKeys(filePath.getAbsolutePath());
    }

    /**
     * ����������һ��Ԫ��
     */
    public void clickGroupWebElemets(String selector) throws InterruptedException{
        List<WebElement> we = locatElements(selector);
        for(int i=0;i<we.size();i++){
            we.get(i).click();
            sleep(1000);
        }
    }

    /**
     * < �� input > ���͵� �ļ��ϴ�</>
     */
    public void uploadFileTypeNotInput(String selector,String exePath){
        click(selector);
        try {
            final Runtime exe = Runtime.getRuntime();
            Process process = null;
            File filePath = new File(exePath);
            process = exe.exec(filePath.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error to run the exe");
            e.printStackTrace();
        }
    }

    /**
     * Log4j
     * src���½�log4j.properties�����ļ����˴�����
     */
    public Logger log(){
        // �����ڷǾ�̬����
        String className = this.getClass().getName();
//        �����ھ�̬����
//        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String log4jPropertiesPath = "src/log4j.properties";
        File filePath = new File(log4jPropertiesPath);
        PropertyConfigurator.configure(filePath.getAbsolutePath());
        Logger log  =  Logger.getLogger(className+".class");
        return log;
    }


    /**
     * ȫѡ ���������
     */
    public void typeCtrlC(String selector) {
        this.locatElement(selector).sendKeys(Keys.CONTROL, "a");
    }

    /**
     * ���� ���������
     */
    public void typeCtrlX(String selector) {
        this.locatElement(selector).sendKeys(Keys.CONTROL, "x");
    }

    /**
     * ճ�� ���������
     */
    public void typeCtrlV(String selector) {
        this.locatElement(selector).sendKeys(Keys.CONTROL, "v");
    }

    /**
     * ͨ�����س��������ύ��ť����
     */
    public void typeEnter(String selector) {
        this.locatElement(selector).sendKeys(Keys.ENTER);
    }

    /**
     * ����������롰�ո�
     */
    public void typeSpace(String selector) {
        this.locatElement(selector).sendKeys(Keys.SPACE);
    }

    /**
     * ��css selector ��λ�� frame ���л���ȥ
     *
     * @param selector
     */
    public void switchToFrame(String selector) {
        WebElement we = this.locatElement(selector);
        this.baseDriver.switchTo().frame(we);
    }

    /**
     * �л���Ĭ�ϵ�frame
     */
    public void switchToDefault() {
        this.baseDriver.switchTo().defaultContent();
    }

    /**
     * �л����´���
     */
    public void clickToSwitchToNewWindow(String selector) {
        // ��ȡ��ǰҳ����
        String handle = this.baseDriver.getWindowHandle();
        click(selector);
        Set<String> handles = this.baseDriver.getWindowHandles();
        Iterator iterator = handles.iterator();
        // ��ȡ����ҳ��ľ������ѭ���жϲ��ǵ�ǰ�ľ��
        while (iterator.hasNext()){
            String h = (String) iterator.next();
            if(h!=handle){
                this.baseDriver.switchTo().window(h);
            }
        }
    }

    /**
     * ��λ��ָ���� select����ѡ�� index
     *
     * @param selector
     * @param index
     */
    public void selectByIndex(String selector, int index) {
        WebElement we = this.locatElement(selector);
        Select s = new Select(we);
        s.selectByIndex(index);
    }

    /**
     * ��λ��ָ���� select����ѡ�� VisibleText
     *
     */
    public void selectByVisibleText(String selector, String visibleText) {
        WebElement we = this.locatElement(selector);
        Select s = new Select(we);
        s.selectByVisibleText(visibleText);
    }

    /**
     * ��λ��ָ���� select����ѡ�� value
     *
     * @param selector
     * @param value
     */
    public void selectByValue(String selector, String value) {
        WebElement we = this.locatElement(selector);
        Select s = new Select(we);
        s.selectByValue(value);
    }

    /**
     * �˳������
     */
    public void quit() {
        this.baseDriver.quit();
    }

    /**
     * ��ȡ��ǰҳ��URL
     */
    public String getCurrentUrl() {
        return this.baseDriver.getCurrentUrl();
    }

    /**
     * accept the alert
     */
    public void acceptAlert() {
        if (this.baseDriver != null) {
            this.baseDriver.switchTo().alert().accept();
        }
    }

    /**
     * dismiss alert
     */
    public void dismissAlert() {
        if (this.baseDriver != null) {
            this.baseDriver.switchTo().alert().dismiss();
        }
    }

    /**
     * implicitly wait for seconds
     *
     * @param seconds selector should be passed by an example with "i,xxx"
     */
    public void implicitlyWait(int seconds) {
        if (this.baseDriver != null) {
            this.baseDriver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
        }
    }

    /**
     * sleep for seconds
     */
    public void sleep(int milliSeconds) throws InterruptedException{
        if(this.baseDriver != null){
            Thread.sleep(milliSeconds);
        }
    }

    /**
     * get whether display or not of element located by selector
     *
     * @param selector selector should be passed by an example with "i,xxx"
     * @return boolean
     */
    public boolean getDisplay(String selector) {
        WebElement we = this.locatElement(selector);
        if (we != null) {
            return we.isDisplayed();
        }
        return false;
    }

    /**
     * �ı��򡢸�ѡ��͵�ѡ��Ŀɱ༭״̬��������Ա༭���򷵻�true�����򷵻�false
     */
    public boolean isEnable(String selector) {
        WebElement we = this.locatElement(selector);
        if (we != null) {
            return we.isEnabled();
        }
        return false;
    }

    /**
     * ��ȡ CSV ����
     * ���·���ᱨ���ļ��Ҳ�������ֻ���þ���·����
     * String csvPath = "D:\\IdeaProjects\\ranzhi\\src\\main\\java\\data\\sql.csv";
     * ����Ҫ�����·��,�����: File(path)����Ŀ��src��Ŀ¼��ʼ
     */
    public String getCsvData(String csvPath){
        String Line = null;
        StringBuilder csvData = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvPath));//��������ļ���
            //csvData = reader.readLine(); //��һ����Ϣ��Ϊ������Ϣ������;�����Ҫ��ע�͵�
            while((Line=reader.readLine())!=null){
                csvData.append(Line+"\n");
            }reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }return csvData.toString();
    }

    /**
     * ��ȡ Mysql ���ݿ������
     */
    // �����һ����������ò����ʣ���Ҫɾ�������Ǳ����Ѿ������˺ܶ�Σ�ɾ���˻�����˵Ĺ�������Ӱ��
    // ����@Deprecatedע�⼴��,���ߵ����ߣ��÷������Ե��ã����������ʵ����
    @Deprecated
    public String getMysqlData(String sql,String db) throws SQLException {
        // �ȵ���mysql-connection-java.jar ������
//        ��ǰ�ϰ汾driver = "com.mysql.jdbc.Driver";�°汾��Ϊ��"com.mysql.cj.jdbc.Driver";
//        ����url����String url="jdbc:mysql://localhost:3306/test������Ҫ���Ϻ�׺
        String driver = "com.mysql.cj.jdbc.Driver";
        String url="jdbc:mysql://localhost:3306/" + db + "?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        //�ַ�������Ҫƴ�ӱ������ñ����õ�������������Ȼ�������˫�����ټ������Ӻţ��м���Ǳ���
        String usr = "root";
        String pwd = "";
        String mysqlData = null;

        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, usr, pwd);
            if ( conn != null || !conn.isClosed()) {
                System.out.println("���ݿ����ӳɹ���");
            }
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            //            ����rs��������
            ResultSetMetaData rsmd = rs.getMetaData() ;
            int columnCount = rsmd.getColumnCount();
            System.out.println("��������" + columnCount);

            //            ����rs��������
            rs.last();
            System.out.println("��������" + rs.getRow());

//            rs.first(); �����ȡ���ǵ�һ�еļ�¼��
//            rs.last(); �����ȡ�������һ�еļ�¼��
//            ��Ҫ�ý�������Ͱ�ָ�����Ƶ���ʼ����λ��beforeFirst()
            rs.beforeFirst();
//            ѭ����ȡ���������
            while (rs.next()){
                for(int i=1;i<columnCount+1;i++){
                    mysqlData = rs.getString(i) + "," ;
                    System.out.print(mysqlData);
                }
                System.out.println();
            }

            st.close();
            rs.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("��������ʧ�ܣ�");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL���ִ��ʧ�ܣ�");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mysqlData;
    }

    /**
     * ���Ͳ��Ա��浽ָ������
     */

    public void sendEmail() throws Exception {
        // �����˵� ���� �� ���루�滻Ϊ�Լ�����������룩
        String myEmailAccount = "13807083044@163.com";
        String myEmailPassword = "Welcome123";

        // ����������� SMTP ��������ַ, ����׼ȷ, ��ͬ�ʼ���������ַ��ͬ, һ���ʽΪ: smtp.xxx.com
        // ����163����� SMTP ��������ַΪ: smtp.163.com
        String myEmailSMTPHost = "smtp.163.com";

        // �ռ������䣨�滻Ϊ�Լ�֪������Ч���䣩
        String receiveMailAccount = "406834061@qq.com";

        // 1. ������������, ���������ʼ��������Ĳ�������
        Properties props = new Properties(); // ��������
        props.setProperty("mail.transport.protocol", "smtp");  // ʹ�õ�Э�飨JavaMail�淶Ҫ��
        props.setProperty("mail.smtp.host", myEmailSMTPHost);  // �����˵������ SMTP ��������ַ
        props.setProperty("mail.smtp.auth", "true");  // ��Ҫ������֤

        // PS: ĳЩ���������Ҫ�� SMTP ������Ҫʹ�� SSL ��ȫ��֤ (Ϊ����߰�ȫ��, ����֧��SSL����, Ҳ�����Լ�����),
        //     ����޷������ʼ�������, ��ϸ�鿴����̨��ӡ�� log, ����������� ������ʧ��, Ҫ�� SSL ��ȫ���ӡ� �ȴ���,
        //     ������ /* ... */ ֮���ע�ʹ���, ���� SSL ��ȫ���ӡ�
        /*
        // SMTP �������Ķ˿� (�� SSL ���ӵĶ˿�һ��Ĭ��Ϊ 25, ���Բ����, ��������� SSL ����,
        //                  ��Ҫ��Ϊ��Ӧ����� SMTP �������Ķ˿�, ����ɲ鿴��Ӧ�������İ���,
        //                  QQ�����SMTP(SLL)�˿�Ϊ465��587, ������������ȥ�鿴)
        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        */

        // 2. �������ô����Ự����, ���ں��ʼ�����������
        Session session = Session.getInstance(props);
        session.setDebug(true);  // ����Ϊdebugģʽ, ���Բ鿴��ϸ�ķ��� log

        // 3. ����һ���ʼ�
        MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount);

        // Ҳ���Ա��ֵ����ز鿴
        // message.writeTo(file_out_put_stream);

        // 4. ���� Session ��ȡ�ʼ��������
        Transport transport = session.getTransport();

        // 5. ʹ�� �����˺� �� ���� �����ʼ�������, ������֤����������� message �еķ���������һ��, ���򱨴�
        //
        //    PS_01: �ɰܵ��жϹؼ��ڴ�һ��, ������ӷ�����ʧ��, �����ڿ���̨�����Ӧʧ��ԭ��� log,
        //           ��ϸ�鿴ʧ��ԭ��, ��Щ����������᷵�ش������鿴�������͵�����, ���ݸ����Ĵ���
        //           ���͵���Ӧ�ʼ��������İ�����վ�ϲ鿴����ʧ��ԭ��
        //
        //    PS_02: ����ʧ�ܵ�ԭ��ͨ��Ϊ���¼���, ��ϸ������:
        //           (1) ����û�п��� SMTP ����;
        //           (2) �����������, ����ĳЩ���俪���˶�������;
        //           (3) ���������Ҫ�����Ҫʹ�� SSL ��ȫ����;
        //           (4) �������Ƶ��������ԭ��, ���ʼ��������ܾ�����;
        //           (5) ������ϼ��㶼ȷ������, ���ʼ���������վ���Ұ�����
        //
        //    PS_03: ��ϸ��log, ���濴log, ����log, ����ԭ����log��˵����
        transport.connect(myEmailAccount, myEmailPassword);

        // 6. �����ʼ�, �������е��ռ���ַ, message.getAllRecipients() ��ȡ�������ڴ����ʼ�����ʱ��ӵ������ռ���, ������, ������
        transport.sendMessage(message, message.getAllRecipients());

        // 7. �ر�����
        transport.close();
    }
    /**
     * ����һ�⸴���ʼ����ı�+ͼƬ+������
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail) throws Exception {
        // 1. �����ʼ�����
        MimeMessage message = new MimeMessage(session);
        // 2. From: ������
        message.setFrom(new InternetAddress(sendMail, "Jet_163", "UTF-8"));
        // 3. To: �ռ��ˣ��������Ӷ���ռ��ˡ����͡����ͣ�
        message.addRecipient(RecipientType.TO, new InternetAddress(receiveMail, "Jet_QQ", "UTF-8"));
        // 4. Subject: �ʼ�����
        message.setSubject("Hello, CDTest !", "UTF-8");
        /*
         * �������ʼ����ݵĴ���:
         */

        // 5. ����ͼƬ���ڵ㡱
        MimeBodyPart image = new MimeBodyPart();
        // ��ȡ�����ļ�
        DataHandler dh = new DataHandler(new FileDataSource("src\\main\\java\\data\\1.jpg"));
        image.setDataHandler(dh);  // ��ͼƬ������ӵ����ڵ㡱
        image.setContentID("123"); // Ϊ���ڵ㡱����һ��Ψһ��ţ����ı����ڵ㡱�����ø�ID��

        // 6. �����ı����ڵ㡱
        MimeBodyPart text = new MimeBodyPart();
        //    �������ͼƬ�ķ�ʽ�ǽ�����ͼƬ�������ʼ�������, ʵ����Ҳ������ http ���ӵ���ʽ�������ͼƬ
        text.setContent("Beautiful girl ?<br/><img src='cid:123'/>", "text/html;charset=UTF-8");

        // 7. ���ı�+ͼƬ������ �ı� �� ͼƬ ���ڵ㡱�Ĺ�ϵ���� �ı� �� ͼƬ ���ڵ㡱�ϳ�һ����ϡ��ڵ㡱��
        MimeMultipart mm_text_image = new MimeMultipart();
        mm_text_image.addBodyPart(text);
        mm_text_image.addBodyPart(image);
        mm_text_image.setSubType("related"); // ������ϵ

        // 8. �� �ı�+ͼƬ �Ļ�ϡ��ڵ㡱��װ��һ����ͨ���ڵ㡱
        //    ������ӵ��ʼ��� Content ���ɶ�� BodyPart ��ɵ� Multipart, ����������Ҫ���� BodyPart,
        //    ����� mm_text_image ���� BodyPart, ����Ҫ�� mm_text_image ��װ��һ�� BodyPart
        MimeBodyPart text_image = new MimeBodyPart();
        text_image.setContent(mm_text_image);

        // 9. �����������ڵ㡱
        MimeBodyPart attachment = new MimeBodyPart();
        DataHandler dh2 = new DataHandler(new FileDataSource("test-output\\ReporterNG-Jacky.html"));
        attachment.setDataHandler(dh2);  // ������������ӵ����ڵ㡱
        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));  // ���ø������ļ�������Ҫ���룩

        // 10. ���ã��ı�+ͼƬ���� ���� �Ĺ�ϵ���ϳ�һ����Ļ�ϡ��ڵ㡱 / Multipart ��
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text_image);
        mm.addBodyPart(attachment);  // ����ж�����������Դ������������
        mm.setSubType("mixed");      // ��Ϲ�ϵ

        // 11. ���������ʼ��Ĺ�ϵ�������յĻ�ϡ��ڵ㡱��Ϊ�ʼ���������ӵ��ʼ�����
        message.setContent(mm);

        // 12. ���÷���ʱ��
        message.setSentDate(new Date());

        // 13. �����������������
        message.saveChanges();
        return message;
    }

    /**
     * ����ʧ�ܣ������������,�������ж�
     * ֻ�Ա�����ֵ VS ��ʵֵ
     */
    public void assertVerifyEqualsNoMsg(Object actual, Object expected){
        List<Error> errors = new ArrayList<Error>();
        try{
            Assert.assertEquals(actual, expected);
        }catch(Error e){
            errors.add(e);
            System.out.println(errors);
            takesScreenshot();
        }
    }

    /**
     * �Ա�����ֵ VS ��ʵֵ�����Ҷ���ʧ��ʱ����ʾ������ʾָ����Ϣ
     * @param actual
     * @param expected
     * @param message
     */
    public void assertVerifyEqualsMsgNoLog(Object actual, Object expected, String message){
        List<Error> errors = new ArrayList<Error>();
        try{
            Assert.assertEquals(actual, expected, message);
        }catch(Error e){
            errors.add(e);
            System.out.println(errors);
            takesScreenshot();
        }
    }

    /**
     * �Ա� ����ֵ VS ��ʵֵ�����Ҷ���ʧ�ܺ󣬷��ر�����Ϣ�����Ҵ�ӡ������־��logs/error.log
     * @param actual
     * @param expected
     * @param message
     * @param className
     */
    public void assertVerifyEqualsMsgLogError(Object actual, Object expected, String message){
        List<Error> errors = new ArrayList<Error>();
        try{
            Assert.assertEquals(actual, expected, message);
        }catch(Error e){
            errors.add(e);
            log().error(errors);
            takesScreenshot();
        }
    }

    /**
     *��ͼ����
     */
//              * ��ȡ��ǰ·��
//              * String currentPath = System.getProperty("user.dir");
//              *
    public void takesScreenshot() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String time = formatter.format(date);
        String imgName = String.format("ScreenShot [ %s ].png", time);
        String filePath = "src\\test\\java\\screenshot";
        if (!(new File(filePath).isDirectory())) {  // �ж��Ƿ���ڸ�Ŀ¼
            new File(filePath).mkdir();  // ������������½�һ��Ŀ¼
        }
        File dir = new File(filePath);
        if (!dir.exists())
            dir.mkdirs();
        String screenPath = dir.getAbsolutePath() + "\\" + imgName;
        tScreenshot(screenPath);
    }
    public void tScreenshot(String screenPath) {
        try {
            //ָ����OutputType.FILE��Ϊ�������ݸ�getScreenshotAs()�������京���ǽ���ȡ����Ļ���ļ���ʽ���ء�
            File scrFile = ((TakesScreenshot) this.baseDriver)
                    .getScreenshotAs(OutputType.FILE); // �ؼ����룬ִ����Ļ��ͼ��Ĭ�ϻ�ѽ�ͼ���浽tempĿ¼
            FileUtils.copyFile(scrFile, new File(screenPath));  //����FileUtils�������copyFile()��������getScreenshotAs()���ص��ļ�����
//            System.out.println("�ô�����Բ鿴��ͼ��"+screenPath );
            Gauge.writeMessage(screenPath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }


}
