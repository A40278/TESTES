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
        prep.setInt(1, 20);
        prep.setString(2, std.getName());
        prep.executeUpdate();
        con.close();
    }
    @Test
    public void test_delete() throws SQLException{
        SQLServerDataSource dataSource = new SQLServerDataSource();
        dataSource.setServerName(System.getenv("LS_DB_SRV"));
        dataSource.setUser(System.getenv("LS_DB_USER"));
        dataSource.setPassword(System.getenv("LS_DB_PW"));
        dataSource.setDatabaseName(System.getenv("LS_DB_NAME"));

        Connection con = dataSource.getConnection();
        String str = "DELETE FROM STUDENTS WHERE name = ?";
        PreparedStatement preparedStatement = con.prepareStatement(str);
        //preparedStatement.setString(new Student("Eu",21,414141,"M").getName());
    }

    @Test
    public void test_select() throws SQLException{
        SQLServerDataSource dataSource = new SQLServerDataSource();
        dataSource.setServerName(System.getenv("LS_DB_SRV"));
        dataSource.setUser(System.getenv("LS_DB_USER"));
        dataSource.setPassword(System.getenv("LS_DB_PW"));
        dataSource.setDatabaseName(System.getenv("LS_DB_NAME"));

        Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM STUDENTS");
        while(rs.next()){
            System.out.println("Student Number " +rs.getInt(1) + " Name " +rs.getString(2)
            + " Age " + rs.getInt(3) + " Gender " + rs.getString(4));
        }
        stmt.close();
        con.close();
    }
}
