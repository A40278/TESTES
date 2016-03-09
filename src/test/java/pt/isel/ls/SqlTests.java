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
    public void testInsert(){
        Student std = new Student("William",30,12345,"M");

    }

    @Test
    public void testUpdate() throws SQLException {
        SQLServerDataSource dataSource = new SQLServerDataSource();
        dataSource.setServerName(System.getenv("LS_DB_SRV"));
        dataSource.setUser(System.getenv("LS_DB_USER"));
        dataSource.setPassword(System.getenv("LS_DB_PW"));
        dataSource.setDatabaseName(System.getenv("LS_DB_NAME"));

        Connection con = dataSource.getConnection();

        String upd = "UPDATE Students SET idade=? WHERE nome=?";
        Student std = new Student("William",30,12345,"M");

        PreparedStatement prep = con.prepareStatement(upd);
        prep.setInt(1, std.getAge());
        prep.setString(2, std.getName());
        prep.executeUpdate();
        con.close();
    }

}
