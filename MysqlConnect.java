import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlConnect implements Closeable, Drivers {

    private Connection conn;

    public MysqlConnect(String host, String username, String password, String db){

        try {

            Class.forName(MYSQL_CLASS_DRIVER);

            String url = MYSQL_CONN_DRIVER
                        + host +"/" + db + "?"
                        + "user=" + username
                        + "&password=" + password;

            conn = DriverManager.getConnection(url);

        } catch(ClassNotFoundException e){
            System.err.println(e);
            System.exit(1);
        } catch(SQLException e){
            System.err.println(e);
            System.exit(1);
        }
    }

    public void insert(String query){

    }

    public void update(String query){

    }

    public void select(String query, String... columns){

        try {

            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(query);

            while(rset.next()){

                for(String column : columns){
                    System.out.print(rset.getString(column) + "\t");
                }

                System.out.println();
            }

            rset.close();
            stmt.close();

        } catch(SQLException ex){
            System.err.println(ex);
            close();
            System.exit(1);
        }
    }

    public void close(){

        try {
            if(conn != null){
                conn.close();
                conn = null;    // Set this to null in case if the user press close twice
            }
        } catch(SQLException ex){
            System.err.println(ex);
            System.exit(1);
        }
    }

    public static void main(String[] args){

        String query = "SELECT * FROM Users";
        String[] columns = {"username", "email"};

        MysqlConnect mysql = new MysqlConnect("localhost", "root", "welcome", "NextSemester");
        mysql.select(query, columns);
        mysql.close();
    }

}
