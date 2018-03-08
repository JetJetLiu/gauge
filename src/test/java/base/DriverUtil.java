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
驱动的封装
 */
public class DriverUtil {
    /**
     * 成员变量，被封装的 webdriver
     */
    private WebDriver baseDriver = null;

    /**
     * 构造方法
     * 实例化 RanzhiDriver的时候，
     * RanzhiDriver bd = new RanzhiDriver()
     * 执行本方法，产生一个对象，bd
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

    // 成员方法
    /**
     * 私有方法： 根据外面传递的 定位符，分析定位符的类型，然后定位到元素并返回该元素
     *
     * @param selector
     * @return
     */
    public WebElement locatElement(String selector) {
        // 如果定位符中 有 分隔符，那么就从分隔符处分成两段
        // 第一段是By
        // 第二段是真正的定位符
        // 如果没有分隔符，就默认用 id 定位
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
     * 根据具体的 by 和 value，进行元素定位，并返回该元素
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
     * 定位元素可以有如下这种写法 switch case
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
     * 定位 一组 元素
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
     * 根据具体的 by 和 value，进行元素定位，并返回该元素
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
     * 定位到指定的元素，并且返回 text
     *
     * @param selector
     * @return
     */
    public String getText(String selector) {
        return this.locatElement(selector).getText();
    }

    /**
     * 获取一组元素的文本
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
     * 所有元素中，查找特定元素，并且 单击
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
     * 所有元素中，查找特定元素，并且 双击
     * 没有 分页 显示
     */
    public void searchAndDoubleClickNoPages(String selector,String data){
        List<WebElement> we = locatElements(selector);
        String texts = null;
        for (int i = 0; i < we.size(); i++) {
            texts = we.get(i).getText();
            System.out.println("["+texts+"]");
            //字符串用equals,不能用 ==
            if(data.equals(we.get(i).getText())){
                doubleClickByWebElement(we.get(i));
                break;
            }
        }
    }


    /**
     * 所有元素中，查找特定元素，并且 双击
     * 有 分页 显示
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
     * 清除所有cookies,导航到指定的url,并且最大化窗口
     *
     * @param url
     */
    public void navigateAndMaximize(String url) {
        this.baseDriver.manage().deleteAllCookies();
        this.baseDriver.get(url);
        this.baseDriver.manage().window().maximize();
    }


    /**
     * 刷新浏览器
     */
    public void navigateRefresh() {
        this.baseDriver.navigate().refresh();
    }

    /**
     * 页面前进
     */
    public void navigateForward() {
        this.baseDriver.navigate().forward();
    }

    /**
     * 页面后退
     */
    public void navigateBack() {
        this.baseDriver.navigate().back();
    }

    /**
     * 点击指定的 selector
     * 约定 selector 是css selector
     *
     * @param selector
     */
    public void click(String selector) {
        this.locatElement(selector).click();
    }

    /**
     * 单选框、复选框勾选状态
     */
    public boolean isSelected(String selector){
        WebElement we = this.locatElement(selector);
        if (we != null) {
            return we.isSelected();
        }
        return false;
    }

    /**
     * 右击指定的 selector
     *
     * @param selector
     */
    public void contextClick(String selector) {
        Actions action = new Actions(this.baseDriver);
        action.contextClick(this.locatElement(selector)).perform();
    }

    /**
     * 双击指定的 selector
     *
     * @param selector
     */
    public void doubleClickBySelector(String selector) {
        Actions action = new Actions(this.baseDriver);
        action.doubleClick(this.locatElement(selector)).perform();
    }

    /**
     * 双击指定的 selector 的 WebElement
     *
     * @param selector
     */
    public void doubleClickByWebElement(WebElement we) {
        Actions action = new Actions(this.baseDriver);
        action.doubleClick(we).perform();
    }

    /**
     * 悬停 到指定的 selector
     *
     * @param selector
     */
    public void moveToElement(String selector) {
        Actions action = new Actions(this.baseDriver);
        action.moveToElement(this.locatElement(selector)).perform();
    }


    /**
     * 鼠标拖拽
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
     * 获取当前页面的标题
     */
    public String getTitle(){
        return this.baseDriver.getTitle();
    }

    /**
     * 定位到指定过的元素，并且把 text 填写进去
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
     * <input ... type="file"> 类型，定位方式为：上传文件 按钮
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
     * 连续逐个点击一组元素
     */
    public void clickGroupWebElemets(String selector) throws InterruptedException{
        List<WebElement> we = locatElements(selector);
        for(int i=0;i<we.size();i++){
            we.get(i).click();
            sleep(1000);
        }
    }

    /**
     * < 非 input > 类型的 文件上传</>
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
     * src下新建log4j.properties配置文件，此处调用
     */
    public Logger log(){
        // 适用于非静态方法
        String className = this.getClass().getName();
//        适用于静态方法
//        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String log4jPropertiesPath = "src/log4j.properties";
        File filePath = new File(log4jPropertiesPath);
        PropertyConfigurator.configure(filePath.getAbsolutePath());
        Logger log  =  Logger.getLogger(className+".class");
        return log;
    }


    /**
     * 全选 输入框内容
     */
    public void typeCtrlC(String selector) {
        this.locatElement(selector).sendKeys(Keys.CONTROL, "a");
    }

    /**
     * 剪切 输入框内容
     */
    public void typeCtrlX(String selector) {
        this.locatElement(selector).sendKeys(Keys.CONTROL, "x");
    }

    /**
     * 粘贴 输入框内容
     */
    public void typeCtrlV(String selector) {
        this.locatElement(selector).sendKeys(Keys.CONTROL, "v");
    }

    /**
     * 通过“回车”代替提交按钮功能
     */
    public void typeEnter(String selector) {
        this.locatElement(selector).sendKeys(Keys.ENTER);
    }

    /**
     * 输入框中输入“空格”
     */
    public void typeSpace(String selector) {
        this.locatElement(selector).sendKeys(Keys.SPACE);
    }

    /**
     * 用css selector 定位到 frame 并切换进去
     *
     * @param selector
     */
    public void switchToFrame(String selector) {
        WebElement we = this.locatElement(selector);
        this.baseDriver.switchTo().frame(we);
    }

    /**
     * 切换到默认的frame
     */
    public void switchToDefault() {
        this.baseDriver.switchTo().defaultContent();
    }

    /**
     * 切换到新窗口
     */
    public void clickToSwitchToNewWindow(String selector) {
        // 获取当前页面句柄
        String handle = this.baseDriver.getWindowHandle();
        click(selector);
        Set<String> handles = this.baseDriver.getWindowHandles();
        Iterator iterator = handles.iterator();
        // 获取所有页面的句柄，并循环判断不是当前的句柄
        while (iterator.hasNext()){
            String h = (String) iterator.next();
            if(h!=handle){
                this.baseDriver.switchTo().window(h);
            }
        }
    }

    /**
     * 定位到指定的 select，并选择 index
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
     * 定位到指定的 select，并选择 VisibleText
     *
     */
    public void selectByVisibleText(String selector, String visibleText) {
        WebElement we = this.locatElement(selector);
        Select s = new Select(we);
        s.selectByVisibleText(visibleText);
    }

    /**
     * 定位到指定的 select，并选择 value
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
     * 退出浏览器
     */
    public void quit() {
        this.baseDriver.quit();
    }

    /**
     * 获取当前页面URL
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
     * 文本框、复选框和单选框的可编辑状态，如果可以编辑，则返回true，否则返回false
     */
    public boolean isEnable(String selector) {
        WebElement we = this.locatElement(selector);
        if (we != null) {
            return we.isEnabled();
        }
        return false;
    }

    /**
     * 获取 CSV 数据
     * 相对路径会报错“文件找不到”，只能用绝对路径！
     * String csvPath = "D:\\IdeaProjects\\ranzhi\\src\\main\\java\\data\\sql.csv";
     * 若非要用相对路径,则可以: File(path)从项目的src根目录开始
     */
    public String getCsvData(String csvPath){
        String Line = null;
        StringBuilder csvData = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvPath));//换成你的文件名
            //csvData = reader.readLine(); //第一行信息，为标题信息，不用;如果需要，注释掉
            while((Line=reader.readLine())!=null){
                csvData.append(Line+"\n");
            }reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }return csvData.toString();
    }

    /**
     * 读取 Mysql 数据库表数据
     */
    // 如果有一个方法你觉得不合适，想要删除，但是别人已经引用了很多次，删除了会对他人的工作产生影响
    // 加入@Deprecated注解即可,告诉调用者，该方法可以调用，但不是最佳实践。
    @Deprecated
    public String getMysqlData(String sql,String db) throws SQLException {
        // 先导入mysql-connection-java.jar 驱动包
//        以前老版本driver = "com.mysql.jdbc.Driver";新版本改为了"com.mysql.cj.jdbc.Driver";
//        但是url不能String url="jdbc:mysql://localhost:3306/test，还需要加上后缀
        String driver = "com.mysql.cj.jdbc.Driver";
        String url="jdbc:mysql://localhost:3306/" + db + "?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        //字符串中需要拼接变量，该变量用单引号括起来，然后加两个双引号再加两个加号，中间就是变量
        String usr = "root";
        String pwd = "";
        String mysqlData = null;

        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, usr, pwd);
            if ( conn != null || !conn.isClosed()) {
                System.out.println("数据库连接成功！");
            }
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            //            返回rs的总列数
            ResultSetMetaData rsmd = rs.getMetaData() ;
            int columnCount = rsmd.getColumnCount();
            System.out.println("总列数：" + columnCount);

            //            返回rs的总行数
            rs.last();
            System.out.println("总行数：" + rs.getRow());

//            rs.first(); 后面读取的是第一行的记录；
//            rs.last(); 后面读取的是最后一行的记录；
//            还要用结果集，就把指针再移到初始化的位置beforeFirst()
            rs.beforeFirst();
//            循环读取结果集数据
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
            System.out.println("驱动加载失败！");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL语句执行失败！");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mysqlData;
    }

    /**
     * 发送测试报告到指定邮箱
     */

    public void sendEmail() throws Exception {
        // 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
        String myEmailAccount = "13807083044@163.com";
        String myEmailPassword = "Welcome123";

        // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般格式为: smtp.xxx.com
        // 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
        String myEmailSMTPHost = "smtp.163.com";

        // 收件人邮箱（替换为自己知道的有效邮箱）
        String receiveMailAccount = "406834061@qq.com";

        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties(); // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");  // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);  // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");  // 需要请求认证

        // PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
        //     如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
        //     打开下面 /* ... */ 之间的注释代码, 开启 SSL 安全连接。
        /*
        // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
        //                  需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
        //                  QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        */

        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        session.setDebug(true);  // 设置为debug模式, 可以查看详细的发送 log

        // 3. 创建一封邮件
        MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount);

        // 也可以保持到本地查看
        // message.writeTo(file_out_put_stream);

        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();

        // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
        //
        //    PS_01: 成败的判断关键在此一句, 如果连接服务器失败, 都会在控制台输出相应失败原因的 log,
        //           仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接, 根据给出的错误
        //           类型到对应邮件服务器的帮助网站上查看具体失败原因。
        //
        //    PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
        //           (1) 邮箱没有开启 SMTP 服务;
        //           (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
        //           (3) 邮箱服务器要求必须要使用 SSL 安全连接;
        //           (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
        //           (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
        //
        //    PS_03: 仔细看log, 认真看log, 看懂log, 错误原因都在log已说明。
        transport.connect(myEmailAccount, myEmailPassword);

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());

        // 7. 关闭连接
        transport.close();
    }
    /**
     * 创建一封复杂邮件（文本+图片+附件）
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail) throws Exception {
        // 1. 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, "Jet_163", "UTF-8"));
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.addRecipient(RecipientType.TO, new InternetAddress(receiveMail, "Jet_QQ", "UTF-8"));
        // 4. Subject: 邮件主题
        message.setSubject("Hello, CDTest !", "UTF-8");
        /*
         * 下面是邮件内容的创建:
         */

        // 5. 创建图片“节点”
        MimeBodyPart image = new MimeBodyPart();
        // 读取本地文件
        DataHandler dh = new DataHandler(new FileDataSource("src\\main\\java\\data\\1.jpg"));
        image.setDataHandler(dh);  // 将图片数据添加到“节点”
        image.setContentID("123"); // 为“节点”设置一个唯一编号（在文本“节点”将引用该ID）

        // 6. 创建文本“节点”
        MimeBodyPart text = new MimeBodyPart();
        //    这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
        text.setContent("Beautiful girl ?<br/><img src='cid:123'/>", "text/html;charset=UTF-8");

        // 7. （文本+图片）设置 文本 和 图片 “节点”的关系（将 文本 和 图片 “节点”合成一个混合“节点”）
        MimeMultipart mm_text_image = new MimeMultipart();
        mm_text_image.addBodyPart(text);
        mm_text_image.addBodyPart(image);
        mm_text_image.setSubType("related"); // 关联关系

        // 8. 将 文本+图片 的混合“节点”封装成一个普通“节点”
        //    最终添加到邮件的 Content 是由多个 BodyPart 组成的 Multipart, 所以我们需要的是 BodyPart,
        //    上面的 mm_text_image 并非 BodyPart, 所有要把 mm_text_image 封装成一个 BodyPart
        MimeBodyPart text_image = new MimeBodyPart();
        text_image.setContent(mm_text_image);

        // 9. 创建附件“节点”
        MimeBodyPart attachment = new MimeBodyPart();
        DataHandler dh2 = new DataHandler(new FileDataSource("test-output\\ReporterNG-Jacky.html"));
        attachment.setDataHandler(dh2);  // 将附件数据添加到“节点”
        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));  // 设置附件的文件名（需要编码）

        // 10. 设置（文本+图片）和 附件 的关系（合成一个大的混合“节点” / Multipart ）
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text_image);
        mm.addBodyPart(attachment);  // 如果有多个附件，可以创建多个多次添加
        mm.setSubType("mixed");      // 混合关系

        // 11. 设置整个邮件的关系（将最终的混合“节点”作为邮件的内容添加到邮件对象）
        message.setContent(mm);

        // 12. 设置发件时间
        message.setSentDate(new Date());

        // 13. 保存上面的所有设置
        message.saveChanges();
        return message;
    }

    /**
     * 断言失败，程序继续运行,而不会中断
     * 只对比期望值 VS 真实值
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
     * 对比期望值 VS 真实值，并且断言失败时，显示报错提示指定信息
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
     * 对比 期望值 VS 真实值，并且断言失败后，返回报错信息，并且打印错误日志到logs/error.log
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
     *截图功能
     */
//              * 获取当前路径
//              * String currentPath = System.getProperty("user.dir");
//              *
    public void takesScreenshot() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String time = formatter.format(date);
        String imgName = String.format("ScreenShot [ %s ].png", time);
        String filePath = "src\\test\\java\\screenshot";
        if (!(new File(filePath).isDirectory())) {  // 判断是否存在该目录
            new File(filePath).mkdir();  // 如果不存在则新建一个目录
        }
        File dir = new File(filePath);
        if (!dir.exists())
            dir.mkdirs();
        String screenPath = dir.getAbsolutePath() + "\\" + imgName;
        tScreenshot(screenPath);
    }
    public void tScreenshot(String screenPath) {
        try {
            //指定了OutputType.FILE做为参数传递给getScreenshotAs()方法，其含义是将截取的屏幕以文件形式返回。
            File scrFile = ((TakesScreenshot) this.baseDriver)
                    .getScreenshotAs(OutputType.FILE); // 关键代码，执行屏幕截图，默认会把截图保存到temp目录
            FileUtils.copyFile(scrFile, new File(screenPath));  //利用FileUtils工具类的copyFile()方法保存getScreenshotAs()返回的文件对象。
//            System.out.println("该错误可以查看截图："+screenPath );
            Gauge.writeMessage(screenPath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }


}
