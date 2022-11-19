package com.example.zeiterfassunggui.classes;
import java.sql.*;
public class Database {
    String url = "jdbc:mysql://localhost:3306/Zeiterfassung";
    String user = "root";
    String pass = "";
    private static Connection con = null;
    private static Statement stm= null;

    public Database() {
        try {
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Verbindung erfolgreich hergestellt");



            String abfrage = "SELECT * FROM staedteUSA";


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Worker getUser(int persNum) throws SQLException {
        Worker user = null;
        try {
            stm = con.createStatement();
            StringBuilder sb = new StringBuilder();
            sb.append("Select * From User WHERE Nummer=");
            sb.append(persNum);
            ResultSet rs = stm.executeQuery(String.valueOf(sb));
            while(rs.next()){
                user = new Worker(rs.getString(2),rs.getString(3), rs.getInt(4),rs.getInt(1));
            }
        } catch (Exception e){
            System.out.println("Fehler in getUser()");
        }
        return user;
    }

    public void startDay(Worker w){
        try {

            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO Anfangszeit (id_user) ");
            sb.append("VALUES ('");
            sb.append(w.getDbid());
            sb.append("');");
            con.prepareStatement(String.valueOf(sb)).execute();

            StringBuilder sb2 = new StringBuilder();
            sb2.append("UPDATE User SET arbeitet = '1' WHERE ID =");
            sb2.append(w.getDbid());
            sb2.append(";");
            con.prepareStatement(String.valueOf(sb2)).execute();

        } catch (Exception e){
            System.out.println("Fehler in getUser()");
            System.err.println(e.getLocalizedMessage());

        }
    }

    public void stopDay(Worker w){
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO Endzeit (id_user) ");
            sb.append("VALUES ('");
            sb.append(w.getDbid());
            sb.append("');");
            con.prepareStatement(String.valueOf(sb)).execute();

            StringBuilder sb2 = new StringBuilder();
            sb2.append("UPDATE User SET arbeitet = '0' WHERE ID =");
            sb2.append(w.getDbid());
            sb2.append(";");
            con.prepareStatement(String.valueOf(sb2)).execute();

        } catch (Exception e){
            System.out.println("Fehler in getUser()");
            System.err.println(e.getLocalizedMessage());

        }
    }


    public String getTime(Worker w){
        String starttime;
        try {
            stm = con.createStatement();
            StringBuilder sb = new StringBuilder();
            sb.append("Select * From User WHERE ID=");

//            "SELECT `User`.`ID`, `Anfangszeit`.`zeit`, `Endzeit`.`zeit` FROM `User` , `Anfangszeit` , `Endzeit` WHERE User.Nummer = 4711  ORDER BY Endzeit.id_ende, Anfangszeit.id_anfang DESC Limit 1"


//            sb.append(persNum);
//            ResultSet rs = stm.executeQuery(String.valueOf(sb));
//            while(rs.next()){
//                user = new Worker(rs.getString(2),rs.getString(3), rs.getInt(4),rs.getInt(1));
//            }
        } catch (Exception e){
            System.out.println("Fehler in getUser()");
        }


        return "Test";
    }

}
