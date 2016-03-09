package pt.isel.ls;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import java.sql.*;
/**
 * Created by EduW on 09/03/2016.
 */
public class SqlTests {

    @Test
    public void testCon(){
        boolean b = true;
        String url = AppTest.getUrl();
        try {
            Connection con = DriverManager.getConnection(url);
            con.close();
        } catch (SQLException e) {
            b = false;
        }
        assertTrue(b);
    }

}
