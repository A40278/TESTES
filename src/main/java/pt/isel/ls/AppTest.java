package pt.isel.ls;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
import java.util.Scanner;

/**
     Nome das variáveis:
     LS_DB_NAME
     LS_DB_PW
     LS_DB_USER
     LS_DB_SRV
 */
public class AppTest {

    public static void main(String[] arg) {

        SQLServerDataSource dataSource = new SQLServerDataSource();
        dataSource.setServerName(System.getenv("LS_DB_SRV"));
        dataSource.setUser(System.getenv("LS_DB_USER"));
        dataSource.setPassword(System.getenv("LS_DB_PW"));
        dataSource.setDatabaseName(System.getenv("LS_DB_NAME"));
        try {
            Connection con = dataSource.getConnection();
            System.out.println("Done");
        } catch (SQLServerException e) {
            e.printStackTrace();
        }
        //testInsert();
        //testSelect();
        //upDateAge("Lebron James",31);
        //testDelete("Edu Jorge");

    }
    public static String getUrl(){
        return "jdbc:sqlserver://localhost:1433;user=walter;password=lebronjames;databaseName=LSTest";
    }
    public static void upDateAge(String name, int age){
        try {
            Connection con = DriverManager.getConnection(getUrl());
            con.setAutoCommit(false);
            String str = "UPDATE Students SET idade = ? WHERE nome = ?";
            PreparedStatement preparedStatement = con.prepareStatement(str);
            preparedStatement.setInt(1,age);
            preparedStatement.setString(2,name);
            preparedStatement.executeUpdate();
            con.commit();
            preparedStatement.close();
            con.close();
            System.out.println("Done updating!!");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    public static boolean testInsert(Student student){
        try {
            Connection con = DriverManager.getConnection(getUrl());
            String str = "INSERT INTO STUDENTS VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(str);

            preparedStatement.setInt(1,student.getNumber());
            preparedStatement.setString(2,student.getName());
            preparedStatement.setInt(3,student.getAge());
            preparedStatement.setString(4, student.getGender());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public static void testSelect(){
        System.out.println("Testing select >> ");
        try {
            Connection con = DriverManager.getConnection(getUrl());
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM STUDENTS");
            printResults(rs);
            stmt.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Done!");
    }
    public static void testDelete(String name){
        try {
            Connection con = DriverManager.getConnection(getUrl());
            con.setAutoCommit(false);
            String str = "DELETE FROM Students WHERE nome = ?";
            PreparedStatement preparedStatement = con.prepareStatement(str);
            preparedStatement.setString(1,name);
            preparedStatement.executeUpdate();
            con.commit();
            preparedStatement.close();
            con.close();
            System.out.println("Done deleting!!");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static void printResults(ResultSet dr)
    {

        try {
            ResultSetMetaData metaData=dr.getMetaData();
            int i = metaData.getColumnCount();

            while(dr.next()) {
                for (int j = 1; j <= i; ++j)
                    System.out.print(dr.getObject(j)+ " ");
                System.out.print("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
