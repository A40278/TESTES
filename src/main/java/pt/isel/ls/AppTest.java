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

    }

}
