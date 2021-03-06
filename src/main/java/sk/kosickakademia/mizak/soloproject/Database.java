package sk.kosickakademia.mizak.soloproject;


import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private String url="jdbc:mysql://localhost:3306/games";
    private String username="root";
    private String password="";

    private final String insertNewGame="INSERT INTO gamedata (GameName, GameGenre) VALUES ( ?, ?)";
    private final String allGames="SELECT * FROM gamedata";
    private final String changeGame="UPDATE gamedata SET GameName = ? WHERE GameID = ?";
    private final String gameByID="SELECT * FROM gamedata WHERE GameID = ?";
    private final String deleteGame="DELETE FROM gamedata WHERE GameID = ?";
    Log log=new Log();
    private Connection getConn() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn= DriverManager.getConnection(url,username,password);
        return conn;
    }
//----------------------------------------------------------------------------------------------
    public boolean insertNewGame(Game game) throws SQLException, ClassNotFoundException {
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
    //----------------------------------------------------------------------------
    private List<Game> executeSelect(PreparedStatement ps) {
        List<Game> userList=new ArrayList<>();
        int result = 0;
        try{
            ResultSet rs = ps.executeQuery();
            if(rs!=null){
                while(rs.next()) {
                    result++;
                    int id=rs.getInt("GameID");
                    String game=rs.getString("GameName");
                    String genre=rs.getString("GameGenre");
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
    //-------------------------------------------------------------------------------
    public List<Game> getAllGames(){
        try (Connection conn = getConn()) {
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(allGames);
                return  executeSelect(ps);
            }
        }catch(Exception ex) {
        }
        return null;
    }
    //--------------------------------------------------------------------------------
    public boolean changeGame(int id,String newGame){
        if (id < 0)
            return false;
        try (Connection conn=getConn()){
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(changeGame);
                ps.setString(1, newGame);
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
    //-----------------------------------------------------------------------------------
    public Game getGameById(int id){
        try (Connection conn = getConn()) {
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(gameByID);
                ps.setInt(1, id);
                return (Game) executeSelect(ps);
            }
        }catch(Exception ex) {
        }
        return null;
    }
    //---------------------------------------------------------------------------------------
    public boolean deleteGame(int id){
        if (getGameById(id) == null){
            return false;
        }
        try (Connection conn=getConn()) {
            PreparedStatement ps=conn.prepareStatement(deleteGame);
            ps.setInt(1, id);
            if (ps.executeUpdate()==1){
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }
    //------------------------------------------------------------------------
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
