package cashadvance_multicompany;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

/*import java.util.Properties;*/
//import javax.swing.*;
public class ConnectDB2 {

    
    public static String ErrorLogs;

    public static Connection ConnectionDB() throws Exception {

        String jdbcClassName = "com.ibm.jtopenlite.database.jdbc.JDBCDriver";
        String url = "jdbc:jtopenlite://192.200.9.190";

        Class.forName(jdbcClassName);
        return DriverManager.getConnection(url, "M3SRVICT", "ICT12345");
    }

    public static Connection LoginDB(String user, String pass) throws Exception {

        try {
            String jdbcClassName = "com.ibm.jtopenlite.database.jdbc.JDBCDriver";
            String url = "jdbc:jtopenlite://192.200.9.190";

            Class.forName(jdbcClassName);
            return DriverManager.getConnection(url, user, pass);

        } catch (ClassNotFoundException | SQLException e) {
            ErrorLogs = e.toString();
            return null;
        }
    }

    public static Connection ConnectionDBAS400() throws Exception {

        String jdbcClassName = "com.ibm.as400.access.AS400JDBCDriver";
        String url = "jdbc:as400://192.200.9.190;";
        //String url = "jdbc:jtopenlite://192.200.9.190;data truncation=false;";

        Class.forName(jdbcClassName);
        return DriverManager.getConnection(url, "M3SRVICT", "ICT12345");
    }

   

}
