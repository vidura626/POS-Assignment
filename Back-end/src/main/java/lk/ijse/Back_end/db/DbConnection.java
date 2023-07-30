package lk.ijse.Back_end.db;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection dbConnection;
    private Connection connection;

    @Resource(name = "java:comp/env/jdbc/customer")
    private DataSource dataSource;

    private DbConnection() {
//        Using JDBC Connection
        /*try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/simplestudentform?createDatabaseIfNotExist=true",
                    "root",
                    "1234"
            );
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }*/

//        Using JNDI - with tomcat server
        /*try {
            InitialContext ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/customerdb");//java:comp/env/ + nameOfResource
            this.connection = pool.getConnection();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }*/
        try {
            connection = dataSource.getConnection();
            System.out.println(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static DbConnection getConnection() {
        return dbConnection == null ? dbConnection = new DbConnection() : dbConnection;
    }

    public Connection openSession() throws SQLException {
        return connection;
    }
}
