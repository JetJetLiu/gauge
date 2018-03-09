import driver.Driver;
import com.thoughtworks.gauge.Step;

import java.sql.SQLException;

public class MysqlWrapper {
    Driver driver = new Driver();

    @Step("Connect & Query: execute the SQL <select * from user;> of the database named <test>")
    public void getMysqlData(String sql, String db) throws SQLException {
        driver.getMysqlData(sql, db);
    }

    @Step("visit baidu website")
    public void implementation1() {
        String app_url = System.getenv("BD_URL");
        Driver.webDriver.get(app_url + "/");
        driver.type("i,kw", "Hi,Jacky!");
        driver.click("i,su");
        driver.takesScreenshot();
    }
}
