import base.DriverUtil;
import com.thoughtworks.gauge.Step;

import java.sql.SQLException;

public class MysqlWrapper {
    @Step("Connect & Query: execute the SQL <select * from user;> of the database named <test>")
    public void getMysqlData(String sql, String db) throws SQLException {
        DriverUtil baseDriver = new DriverUtil();
        baseDriver.getMysqlData(sql, db);
    }
}
