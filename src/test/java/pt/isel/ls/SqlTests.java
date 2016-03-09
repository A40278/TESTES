package pt.isel.ls;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import java.sql.*;
/**
 * Created by EduW on 09/03/2016.
 */
public class SqlTests {

    @Test
    public void testInsert() throws SQLException{

        SQLServerDataSource dataSource = new SQLServerDataSource();
        dataSource.setServerName(System.getenv("LS_DB_SRV"));
        dataSource.setUser(System.getenv("LS_DB_USER"));
        dataSource.setPassword(System.getenv("LS_DB_PW"));
        dataSource.setDatabaseName(System.getenv("LS_DB_NAME"));
        Connection con = dataSource.getConnection();
        String str = "INSERT INTO Students VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = con.prepareStatement(str);
        Student std = new Student("Eu2",55,(int)(Math.random()*100),"M");
        preparedStatement.setInt(1,std.getNumber());
        preparedStatement.setString(2, std.getName());
        preparedStatement.setInt(3,std.getAge());
        preparedStatement.setString(4,std.getGender());
        preparedStatement.executeUpdate();
        con.close();
    }
}
