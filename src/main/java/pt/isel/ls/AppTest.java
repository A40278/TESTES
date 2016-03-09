package pt.isel.ls;
import java.sql.*;
import java.util.Scanner;

/**
 * Created by EduW on 09/03/2016.
 */
public class AppTest {

    public static void main(String[] arg) {

        String url = "jdbc:sqlserver://localhost:1433;user=walter;password=lebronjames;databaseName=LSTest";
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
    public static void testInsert(){
        try {
            Connection con = DriverManager.getConnection(getUrl());
            String str = "INSERT INTO STUDENTS VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(str);
            Scanner s = new Scanner(System.in);
            System.out.println("Student number ? > ");
            int number = s.nextInt();

            System.out.println("Student name ? > ");
            String name = s.nextLine() + s.nextLine();
            System.out.println("Student age ? > ");
            int age = s.nextInt();
            System.out.println("Student gender ? > ");
            String gender = s.nextLine() + s.nextLine();
            preparedStatement.setInt(1,number);
            preparedStatement.setString(2,name);
            preparedStatement.setInt(3,age);
            preparedStatement.setString(4,gender);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();
            System.out.println("Done inserting!!");
        }catch (SQLException e){
            e.printStackTrace();
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
