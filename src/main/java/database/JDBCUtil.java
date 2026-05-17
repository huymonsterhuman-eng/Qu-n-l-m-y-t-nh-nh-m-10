
package database;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author huymo
 */
public class JDBCUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/quanlimaytinh";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    /**
     * Establishes a connection to the MySQL database.
     * @return A Connection object or null if connection fails.
     */
    public static Connection getConnection() {
        Connection c = null;
        try {
            // Register MySQL Driver (Modern CJ Driver)
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Create connection
            c = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
        }
        return c;
    }

    /**
     * Safely closes the database connection.
     * @param c The connection to close.
     */
    public static void closeConnection(Connection c) {
        try {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mở connection với autoCommit=false để bắt đầu transaction.
     * Caller phải gọi commitTransaction hoặc rollbackTransaction sau khi dùng xong.
     */
    public static Connection beginTransaction() throws Exception {
        Connection con = getConnection();
        if (con == null) throw new Exception("Không thể kết nối database");
        con.setAutoCommit(false);
        return con;
    }

    /**
     * Commit transaction và đóng connection.
     */
    public static void commitTransaction(Connection con) throws Exception {
        con.commit();
        closeConnection(con);
    }

    /**
     * Rollback transaction và đóng connection. Không ném exception để dùng an toàn trong catch/finally.
     */
    public static void rollbackTransaction(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.rollback();
                closeConnection(con);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints metadata about the database connection.
     * @param c The connection to inspect.
     */
    public static void printInfo(Connection c) {
        try {
            if (c != null) {
                DatabaseMetaData mtdt = c.getMetaData();
                System.out.println("Database Product Name: " + mtdt.getDatabaseProductName());
                System.out.println("Database Product Version: " + mtdt.getDatabaseProductVersion());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
