package sk.kosickakademia.mizak.soloproject;


import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Database {
    private String url="localhost:3306/games";
    private String username = "admin";
    private String password = "";
    private final  String insertNewGame="INSERT INTO gamedata (GameName, GameGenre, ) VALUES ( ?, ?)";
    private final  String allGames="SELECT * FROM gamedata";
    private final String changeGame="UPDATE gamedata SET game = ? WHERE id = ?";
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
    public boolean insertNewUser(Game game){
        Connection conn=getConn();
        if(conn!=null){
            try{
                PreparedStatement ps=conn.prepareStatement(insertNewGame);
                ps.setString(1, game.getGame());
                ps.setString(2,game.getGenre());
                int result=ps.executeUpdate();
                closeConn(conn);
                log.print("New Game was added to Database.");
                return result==1;
            }catch(SQLException ex){
                log.error(ex.toString());
            }
        }
        return false;
    }
    private List<Game> executeSelect(PreparedStatement ps) {
        List<Game> userList=new ArrayList<>();
        int result = 0;
        try{
            ResultSet rs = ps.executeQuery();
            if(rs!=null){
                while(rs.next()) {
                    result++;
                    int id=rs.getInt("id");
                    String game=rs.getString("game");
                    String genre=rs.getString("genre");
                    userList.add(new Game(id,game,genre));
                    System.out.println("Number of results: "+result);
                    System.out.println(id+" "+game+" "+genre);
                }
            }else{
                System.out.println("No games found.");
                return null;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        if(userList.size()!=0){
            return userList;
        }else{
            System.out.println("No games found.");
            return null;
        }
    }
    public List<Game> getAllGames(){
        try (Connection conn = getConn()) {
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(allGames);
                return executeSelect(ps);
            }
        }catch(Exception ex) {
        }
        return null;
    }
    public boolean changeGame(int id,int newGame){
        if (id < 0)
            return false;
        try (Connection conn=getConn()){
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(changeGame);
                ps.setInt(1, newGame);
                ps.setInt(2, id);
                int update=ps.executeUpdate();
                log.print("Updated game for id: " + id);
                return update==1;
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return false;
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
