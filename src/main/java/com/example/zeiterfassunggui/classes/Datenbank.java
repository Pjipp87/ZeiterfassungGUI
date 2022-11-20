package com.example.zeiterfassunggui.classes;
import java.sql.*;
import java.time.LocalDateTime;

public class Datenbank {
    String url = "jdbc:mysql://localhost:3306/Zeiterfassung";
    String user = "root";
    String pass = "";
    //private static Connection con = null;
    private static Statement stmt = null;
    private static Connection connection;



    private static final String DB_PATH = System.getProperty("user.home") + "/" + "testdb.db";

    public Datenbank() throws SQLException {
        try {
            if (connection != null)
                return;
            System.out.println("Creating Connection to Database...");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            if (!connection.isClosed())
                System.out.println("...Connection established");
            System.out.println(DB_PATH);
            stmt = connection.createStatement();

/*
            String debugSql = "DROP TABLE anfangszeit";
            stmt.execute(debugSql);
            debugSql = "DROP TABLE endzeit";
            stmt.execute(debugSql);
*/

            String sql = "CREATE TABLE IF NOT EXISTS User (\n" +
                    "  ID integer NOT NULL,\n" +
                    "  Vorname text NOT NULL,\n" +
                    "  Nachname text NOT NULL,\n" +
                    "  arbeitet integer DEFAULT NULL\n" +
                    " );";
            stmt.execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS anfangszeit (\n"+
                    " id_anfang integer PRIMARY KEY AUTOINCREMENT,\n"+
                    " id_user integer NOT NULL, \n"+
                    " kommen TIME DEFAULT CURRENT_TIMESTAMP,\n"+
                    " datum DATE DEFAULT CURRENT_TIMESTAMP, \n"+
                    " FOREIGN KEY(id_user) REFERENCES user(ID)\n"+
                    "  );";
            stmt.execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS endzeit (\n"+
                    " id_ende integer PRIMARY KEY AUTOINCREMENT,\n"+
                    " id_user integer NOT NULL, \n"+
                   // " zeit DATETIME DEFAULT CURRENT_TIMESTAMP,\n"+
                    " gehen TIME DEFAULT CURRENT_TIMESTAMP,\n"+
                    " datum DATE DEFAULT CURRENT_TIMESTAMP, \n"+
                    " FOREIGN KEY(id_user) REFERENCES user(id_user) \n"+
                    " );";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Fehler SQLite: "+ e.getLocalizedMessage());
        }
    }


    public String getTimeDifference(Worker w){
        long time = 0;
        try {

            stmt = connection.createStatement();
            StringBuilder sb =new StringBuilder();
            sb.append("select (strftime('%s', endzeit.gehen)-strftime('%s',anfangszeit.kommen))*1000  AS DIfferenz FROM endzeit JOIN anfangszeit ON endzeit.id_user = anfangszeit.id_user WHERE anfangszeit.id_user=");
            sb.append(w.getDbid());
            sb.append(" AND endzeit.id_ende=(select max(endzeit.id_ende) from endzeit) AND anfangszeit.id_anfang=(select max(anfangszeit.id_anfang) from anfangszeit)");
            ResultSet rs = stmt.executeQuery(String.valueOf(sb));
            while (rs.next()){
                System.out.println(rs.getLong(1));
                time = rs.getLong(1);
            }
        }catch (SQLException e){
            System.err.println("Zeitdifferenz :"+e.getLocalizedMessage());
        }

        Time t = new Time(time);
        return t.getHours()-1+":"+t.getMinutes()+":"+t.getSeconds();

    }

    public void DELETEALLYOUSERFROMTABLER(String table){
        try {
            stmt = connection.createStatement();
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ");
            sb.append(table);
            stmt.execute(String.valueOf(sb));
        } catch (SQLException e){
            System.err.println("Fehler3 "+e.getLocalizedMessage());
        }
    }

    public void SHOWALLUSER(){
        try{
            stmt = connection.createStatement();
            String st = "SELECT * FROM User";


            ResultSet rs = stmt.executeQuery(st);

            while (rs.next()){
                System.out.println(rs.getString(1)+" - "+ rs.getString(2)+" - "+rs.getString(3)+" - "+rs.getString(4));
            }

        } catch (SQLException e){
            System.err.println("Fehler Showalleuser: "+e.getLocalizedMessage());
        }
    }
    public void SETNEWUSER(int num, String vorname, String nachname, int arbeitet) throws SQLException {
        try {
            stmt = connection.createStatement();
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO User (ID, Vorname, Nachname, arbeitet) VALUES (");
            sb.append(num).append(", '");
            sb.append(vorname).append("', '");
            sb.append(nachname).append("', ");
            sb.append(arbeitet).append(");");
            System.out.println(sb);
            stmt.execute(String.valueOf(sb));
        }catch (SQLException e){
            System.err.println("Fehler: "+e.getLocalizedMessage());
        }
    }

    public Worker getUser(int persNum) throws SQLException {
        Worker user = null;
      try {
            stmt = connection.createStatement();
            StringBuilder sb = new StringBuilder();
            sb.append("Select * From User WHERE ID=");
            sb.append(persNum);
            ResultSet rs = stmt.executeQuery(String.valueOf(sb));
            while(rs.next()){
                user = new Worker(rs.getString(2),rs.getString(3), rs.getInt(4),rs.getInt(1));
            }
        } catch (Exception e){
            System.out.println("Fehler in getUser()");
        }
        return user;
    }

    public String startDay(Worker w){
        try {

            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO Anfangszeit (id_user) ");
            sb.append("VALUES ('");
            sb.append(w.getDbid());
            sb.append("');");
            connection.prepareStatement(String.valueOf(sb)).execute();

            StringBuilder sb2 = new StringBuilder();
            sb2.append("UPDATE User SET arbeitet = '1' WHERE ID =");
            sb2.append(w.getDbid());
            sb2.append(";");
            connection.prepareStatement(String.valueOf(sb2)).execute();

        } catch (Exception e){
            System.out.println("Fehler in getUser()");
            System.err.println(e.getLocalizedMessage());
        }

        return "Arbeitsbeginn: "+LocalDateTime.now().getHour() +":"+LocalDateTime.now().getMinute()+" Uhr";
    }

    public String stopDay(Worker w){
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO Endzeit (id_user) ");
            sb.append("VALUES ('");
            sb.append(w.getDbid());
            sb.append("');");
            connection.prepareStatement(String.valueOf(sb)).execute();

            StringBuilder sb2 = new StringBuilder();
            sb2.append("UPDATE User SET arbeitet = '0' WHERE ID =");
            sb2.append(w.getDbid());
            sb2.append(";");
            connection.prepareStatement(String.valueOf(sb2)).execute();

        } catch (Exception e){
            System.out.println("Fehler in getUser()");
            System.err.println(e.getLocalizedMessage());

        }

        return "Arbeitsende: "+LocalDateTime.now().getHour() +":"+LocalDateTime.now().getMinute()+" Uhr";
    }


    public String getTime(Worker w){
        String starttime;
/*        try {
            stm = con.createStatement();
            StringBuilder sb = new StringBuilder();
            sb.append("Select * From User WHERE ID=");

          "SELECT `User`.`ID`, `Anfangszeit`.`zeit`, `Endzeit`.`zeit` FROM `User` , `Anfangszeit` , `Endzeit` WHERE User.Nummer = 4711  ORDER BY Endzeit.id_ende, Anfangszeit.id_anfang DESC Limit 1"


            sb.append(persNum);
            ResultSet rs = stm.executeQuery(String.valueOf(sb));
            while(rs.next()){
                user = new Worker(rs.getString(2),rs.getString(3), rs.getInt(4),rs.getInt(1));
/           }
        } catch (Exception e){
            System.out.println("Fehler in getUser()");
        }*/


        return "Test";
    }

}
