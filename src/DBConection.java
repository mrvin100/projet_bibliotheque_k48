import java.sql.*;


public class DBConection {

    static String URL = "jdbc:postgresql://localhost:5432/bibliotheque48" ;
    static String user = "postgres";
    static String password = "1234" ;
    static Connection connection;

    public static Connection getConnection(){
        try {
            connection = DriverManager.getConnection(URL,user,password);
            Class.forName("org.postgresql.Driver");

            if (connection != null){
                System.out.println("Connection successful");
            }else {
                System.out.println("Connection error");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}