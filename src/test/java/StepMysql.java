import com.thoughtworks.gauge.ContinueOnFailure;
import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.Step;

import java.sql.*;

import static org.junit.Assert.assertTrue;

public class StepMysql {
    private Connection connection;
    private PreparedStatement statement;

    @Step("open connection before crud")
    public void openConnection() throws SQLException {
        try {
//            Mysql 5版本
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/test?useSSL=false";
//            Mysql 6版本，采用如下驱动
//            String driverClassName=com.mysql.cj.jdbc.Driver
//            String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
            String username = "root";
            String password = "";

            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关链接,释放资源
            connection.close();
        }
    }


//    Gauge默认在步骤中的第一个失败时中断执行。所以如果场景中的第一个步骤失败了，随后的所有步骤都将跳过执行。
//    加上@ContinueOnFailure，执行场景中的所有步骤，而不管前面的步骤是否失败。
//    目前仅支持Java
    @ContinueOnFailure
    @Step("insert new record named <Gauge>,sex is <Male>,age is <25>")
    public void insert(String name, String sex, Integer age) throws SQLException {
        // If there is an error here, Gauge will still execute next steps
        try {
            statement = connection.prepareStatement("insert into user(username,sex,age) values (?,?,?)");
            statement.setString(1, name);
            statement.setString(2, sex);
            statement.setInt(3, age);

            int result = statement.executeUpdate();

            assertTrue(result > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }statement.close();
    }


    @Step("query all records")
    public void query() throws SQLException {
        try {
            statement = connection.prepareStatement("SELECT * FROM user");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                StringBuffer userInfo = new StringBuffer();
                userInfo.append("ID:" + resultSet.getString("id"));
                userInfo.append("\t\tUsername:" + resultSet.getString("username"));
                userInfo.append("\t\tSex:" + resultSet.getString("sex"));
                userInfo.append("\t\tAge:" + resultSet.getString("age"));
                userInfo.append("\t\tSchool:" + resultSet.getString("school"));
                userInfo.append("\t\tMajor:" + resultSet.getString("major"));
                userInfo.append("\t\tAddress:" + resultSet.getString("address"));
                Gauge.writeMessage(userInfo.toString());
            }

            resultSet.last();
            int rowCount = resultSet.getRow();
            resultSet.close();

            assertTrue(rowCount > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            statement.close();
        }
    }

    @Step("update record sex to <Female> which named <Gauge>")
    public void update(String sex, String name) throws SQLException {
        try {
            statement = connection.prepareStatement("UPDATE user SET sex=? WHERE username=?");
            statement.setString(1, sex);
            statement.setString(2, name);
            int resultNum = statement.executeUpdate();
            assertTrue(resultNum > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            statement.close();
        }
    }

    @Step("delete the record which named <Guage>")
    public void delete(String name) throws SQLException {
        try {
            statement = connection.prepareStatement("DELETE FROM user WHERE username=?");
            statement.setString(1, name);
            int resultNum = statement.executeUpdate();
            assertTrue(resultNum > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            statement.close();
        }
    }

}
