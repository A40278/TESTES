package pt.isel.ls;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.sql.*;
/**
 * Created by EduW on 09/03/2016.
 */
public class SqlTests {
    /**
     *
     * @return Connection to be used in the calling method.
     * @throws SQLException Dealing with SQLServerDataSource might throw an exception.
     */

    private static SQLServerDataSource dataSource = null;
    static{
        dataSource = new SQLServerDataSource();
        dataSource.setServerName(System.getenv("LS_DB_SRV"));
        dataSource.setUser(System.getenv("LS_DB_USER"));
        dataSource.setPassword(System.getenv("LS_DB_PW"));
        dataSource.setDatabaseName(System.getenv("LS_DB_NAME"));
    }

    /**
     *
     * @param std Student object (exact representation of the DB table) to check
     *            in the DB.
     * @return If the Student exists returns true, false otherwise.
     * @throws SQLException Dealing with Connections and PreparedStatements might throw an exception.
     */
    private boolean exists(Student std) throws SQLException {
        Connection con = dataSource.getConnection();
        PreparedStatement pstmt = con.prepareStatement("SELECT nome FROM STUDENTS WHERE nAluno = ?");
        PreparedStatement delStmt = con.prepareStatement("DELETE FROM Students WHERE nAluno = ?");
        pstmt.setInt(1, std.getNumber());
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
            delStmt.setInt(1,std.getNumber());
            delStmt.executeUpdate();
            con.close();
            return true;
        }
        con.close();
        return false;
    }
    @Test
    public void testInsert() throws SQLException{

        Connection con = dataSource.getConnection();
        String str = "INSERT INTO Students VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = con.prepareStatement(str);
        Student std = new Student("TestingName",55,(int)(Math.random()*100),"M");
        preparedStatement.setInt(1,std.getNumber());
        preparedStatement.setString(2, std.getName());
        preparedStatement.setInt(3,std.getAge());
        preparedStatement.setString(4,std.getGender());
        preparedStatement.executeUpdate();
        con.close();
        assertTrue(exists(std));
    }

    @Test
    public void testUpdate() throws SQLException {

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

        Connection con = dataSource.getConnection();
        String str = "DELETE FROM STUDENTS WHERE nome = ?";
        PreparedStatement preparedStatement = con.prepareStatement(str);

        Student toDelete = new Student("Dummy",32,6868,"M");
        //insert student to delete right after
        insert(toDelete);
        preparedStatement.setString(1,toDelete.getName());
        preparedStatement.executeUpdate();
        assertFalse(exists(toDelete));
    }

    /**
     * This method permanently inserts a student into the DB.
     * @param in Student to be inserted into the DB.
     * @throws SQLException Connection or PreparedStatement objects might throw an SQLException.
     */
    private void insert(Student in) throws SQLException{
        Connection con = dataSource.getConnection();
        String str = "INSERT INTO Students VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = con.prepareStatement(str);
        preparedStatement.setInt(1, in.getNumber());
        preparedStatement.setString(2, in.getName());
        preparedStatement.setInt(3, in.getAge());
        preparedStatement.setString(4, in.getGender());
        preparedStatement.executeUpdate();
        con.close();
    }

    @Test
    public void testSelect() throws SQLException{


        Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM STUDENTS ");
        while(rs.next()){
            System.out.println("Student Number: " +rs.getInt(1) + " Name: " +rs.getString(2)
            + " Age: " + rs.getInt(3) + " Gender: " + rs.getString(4));
        }
        System.out.println("---------------------------------------------------------");
        rs = stmt.executeQuery("SELECT MAX(nAluno) FROM STUDENTS");
        while(rs.next()){
            System.out.println("Biggest Number is = "+rs.getInt(1));
        }
        System.out.println("---------------------------------------------------------");
        rs = stmt.executeQuery("SELECT MIN(nAluno) FROM STUDENTS");
        while(rs.next()){
            System.out.println("Smallest Number is = "+rs.getInt(1));
        }
        System.out.println("---------------------------------------------------------");
        rs = stmt.executeQuery("SELECT AVG(idade) FROM STUDENTS");
        while(rs.next()){
            System.out.println("Average Age is = "+rs.getInt(1));
        }
        System.out.println("---------------------------------------------------------");
        rs = stmt.executeQuery("SELECT COUNT(nAluno) FROM STUDENTS WHERE idade>(SELECT AVG(idade) FROM STUDENTS)");
        while(rs.next()){
            System.out.println("The number of studens older than average is = "+rs.getInt(1));
        }
        System.out.println();
        stmt.close();
        con.close();
    }
}
