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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;

public class MysqlConnect implements Closeable, Drivers {

    private static final boolean traceOn = false;

    private static final String INSERT_ROW = "INSERT INTO";
    private static final String DELETE_ROW = "DELETE FROM";

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

    private int insert(String query, Object... columns){

        int status = -1;

        try{

            PreparedStatement pstmt = conn.prepareStatement(query);

            for(int index = 0; index < columns.length; index++){

                if(columns[index] instanceof Integer){
                    pstmt.setInt(index+1, (Integer)columns[index]);
                } else if(columns[index] instanceof Double){
                    pstmt.setDouble(index+1, ((Double)columns[index]).doubleValue());
                } else {
                    pstmt.setString(index+1, (String)columns[index]);
                }
            }

            status = pstmt.executeUpdate();
            pstmt.close();

        } catch(SQLException ex){
            if(traceOn)
                ex.printStackTrace();
            else
                System.err.println(ex);
        }

        return status;
    }

    public int insert(String tableName, String[] columnNames, Object... columnValues){

        if(columnNames.length != columnValues.length){
            System.err.println("Error: Column names and values array have to be same length");
            return -1;
        }

        int status = -1;
        String query = INSERT_ROW + " " + tableName + " ";

        StringBuffer cnames = new StringBuffer("(");
        StringBuffer cvalues = new StringBuffer("(");

        for(int i = 0; i < columnNames.length; i++){
            if(i != (columnNames.length - 1)){
                cnames.append(columnNames[i] + ",");
                cvalues.append("?,");
            } else {
                cnames.append(columnNames[i]);
                cvalues.append("?");
            }
        }

        cnames = cnames.append(")");
        cvalues = cvalues.append(")");

        query = query + cnames.toString() + " VALUES " + cvalues.toString();

        System.out.println(query);
        status = insert(query, columnValues);

        return status;
    }

    public int update(String query){

        int status = -1;

        try{

            Statement stmt = conn.createStatement();
            status = stmt.executeUpdate(query);
            stmt.close();

        } catch(SQLException ex){
            if(traceOn)
                ex.printStackTrace();
            else
                System.err.println(ex);
        }

        return status;
    }

    public int delete(String query){

        int status = -1;

        try{

            Statement stmt = conn.createStatement();
            status = stmt.executeUpdate(query);
            stmt.close();

        } catch(SQLException ex){
            if(traceOn)
                ex.printStackTrace();
            else
                System.err.println(ex);
        }

        return status;

    }

    public void delete(String query, Object... columns){

        try{

            PreparedStatement pstmt = conn.prepareStatement(query);

            for(int index = 0; index < columns.length; index++){

                if(columns[index] instanceof Integer){
                    pstmt.setInt(index+1, (Integer)columns[index]);
                } else if(columns[index] instanceof Double){
                    pstmt.setDouble(index+1, ((Double)columns[index]).doubleValue());
                } else {
                    pstmt.setString(index+1, (String)columns[index]);
                }

            }

            pstmt.executeUpdate();
            pstmt.close();

        } catch(SQLException ex){
            if(traceOn)
                ex.printStackTrace();
            else
                System.err.println(ex);
        }

    }

    public String select(String query, String delim){

        String result = "";

        try {

            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);

            ResultSetMetaData rsmd = resultSet.getMetaData();
            int numOfColumns = rsmd.getColumnCount();

            while(resultSet.next()){

                for(int i = 0; i < numOfColumns; i++){

                    if(i != (numOfColumns - 1))
                        result += resultSet.getString(rsmd.getColumnName(i+1)) + delim;
                    else
                        result += resultSet.getString(rsmd.getColumnName(i+1));

                }

                result += "\n";
            }

            resultSet.close();
            stmt.close();

        } catch(Exception ex) {
            if(traceOn)
                ex.printStackTrace();
            else
                System.out.println(ex);
        }

        return result;
    }

    public String select(String query, String delim, String... columns){

        String result = "";

        try {

            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(query);

            while(rset.next()){

                for(int index = 0; index < columns.length; index++){

                    if(index != (columns.length - 1))
                        result += rset.getString(columns[index]) + delim;
                    else
                        result += rset.getString(columns[index]);

                }

                result += "\n";
            }

            rset.close();
            stmt.close();

        } catch(SQLException ex){
            if(traceOn)
                ex.printStackTrace();
            else
                System.err.println(ex);
        }

        return result;
    }

    public void close() throws IOException {

        try {
            if(conn != null){
                conn.close();
                conn = null;    // Set this to null in case if the user press close twice
            }
        } catch(SQLException ex){
            System.err.println(ex);
        }
    }

    private static void testSelectQuery(){

        String query = "SELECT * FROM CS434_Student";
        String[] columns = {"username", "password"};
        String[] columns2 = {"DeptName", "College"};

        try {

            MysqlConnect mysql = new MysqlConnect("database.prop");
            mysql.select(query, ",", columns);
            mysql.insert("CS434_Department", columns2, "CSTest", "CCS");
            mysql.close();

        } catch(IOException ex){
            System.err.println(ex);
        }

    }

    public static void main(String[] args){

        testSelectQuery();
    }

}
