package sk.kosickakademia.mizak.soloproject;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    Log log=new Log();
    public Connection getConn(){
    try{
        Properties props=new Properties();
        InputStream loader=getClass().getClassLoader().getResourceAsStream("database.properties");
        props.load(loader);
        String url=props.getProperty("url");
        String username=props.getProperty("username");
        String password=props.getProperty("password");
        Connection conn= DriverManager.getConnection(url,username,password);
        log.print("Connection success.");
        return conn;
    }catch(SQLException | IOException throwables){
        log.error(throwables.toString());

    }
    return null;
}

    public void closeConn(Connection conn){
        if(conn!=null){
            try {
                conn.close();
                log.print("Connection closed.");
            }catch(SQLException e){
                log.error(e.toString());
            }
        }
    }
}
