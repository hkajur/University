import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class MysqlConnect implements Closeable, Drivers {

    private Connection conn;

    public MysqlConnect(String filename){

        try {

            Class.forName(MYSQL_CLASS_DRIVER);

            File file = new File(filename);

            if(!file.exists())
                throw new FileNotFoundException();

            Properties prop = new Properties();
            prop.load(new FileInputStream(file));

            String host = prop.getProperty("host");
            String username = prop.getProperty("user");
            String password = prop.getProperty("pass");
            String db = prop.getProperty("db");


            String url = MYSQL_CONN_DRIVER
                        + host +"/" + db + "?"
                        + "user=" + username
                        + "&password=" + password;

            conn = DriverManager.getConnection(url);

        } catch(FileNotFoundException fe){
            System.err.println("File: " + filename + " not found");
            System.exit(1);
        } catch(IOException ex){
            System.err.println(ex);
            System.exit(1);
        } catch(ClassNotFoundException ce){
            System.err.println(ce);
            System.exit(1);
        } catch(SQLException ce){
            System.err.println("Unable to connect to the MYSQL, check if its running");
            System.exit(1);
        }

    }

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

    public String select(String query, String... columns){

        String result = "";
        try {

            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(query);

            while(rset.next()){

                for(String column : columns){
                    //System.out.print(rset.getString(column) + "\t");
                    result = result + rset.getString(column);
                }

                //System.out.println();
            }

            rset.close();
            stmt.close();

        } catch(SQLException ex){
            System.err.println(ex);
            close();
            System.exit(1);
        }

        return result;
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

        String query = "SELECT * FROM CS434_Student";
        String[] columns = {"username", "password"};

        MysqlConnect mysql = new MysqlConnect("database.prop");
        mysql.select(query, columns);
        mysql.close();
    }

}
