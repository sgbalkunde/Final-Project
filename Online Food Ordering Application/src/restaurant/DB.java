package restaurant;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DB {

    public static Alert a = new Alert(Alert.AlertType.INFORMATION, "Succesful Add", ButtonType.OK);
    public static Alert E = new Alert(Alert.AlertType.ERROR, "Error", ButtonType.OK);

    private static Connection getConnection() {
        Connection con = null;
        try {
            
            final String DB_URL = "jdbc:mysql://www.papademas.net:3307/510labs?autoReconnect=true&useSSL=false";
            final String USER = "db510";
			final String PASS = "510";
			
            con = DriverManager.getConnection(DB_URL, USER, PASS);
            
        } catch (SQLException ex) {
            System.err.println("Exception: " + ex.getMessage());
            E.show();
        }
        return con;
    }

    public static int count(String col, String Table) throws SQLException {
        Connection con = getConnection();
        Statement s = con.createStatement();
        String sqlSelect = "select count(" + col + ") from " + Table;
        ResultSet result = s.executeQuery(sqlSelect);
        if (result.next()) {
            return Integer.parseInt(result.getString(1));
        }

        return 0;
    }

    public static boolean insertMeals(String table, String name, String type, double cost) {

        try {
            Connection con = getConnection();
            Statement s = con.createStatement();
            String SqlInsert = "INSERT INTO sunanda_meals(Mname,Mtype,Mcost) " + "VALUES('" + name + "','" + type + "'," + cost + ")";

            s.execute(SqlInsert);
            return true;
        } catch (Exception e) {
            E.show();
            return false;
        }
    }

    public static boolean insertDrinks(String name, String type, double cost) {
        try {
            Connection con = getConnection();
            Statement s = con.createStatement();
            String SqlInsert = "INSERT INTO sunanda_drinks(Dname,Dtype,Dcost) " + "VALUES('" + name + "','" + type + "'," + cost + ")";
            s.execute(SqlInsert);
            return true;
        } catch (Exception e) {
            E.show();
            return false;
        }
    }

    public static ObservableList<Meals> getMeals() throws SQLException {
        Connection con = getConnection();
        Statement s = con.createStatement();
        String sqlSelect = "select * from sunanda_meals";
        ResultSet result = s.executeQuery(sqlSelect);
        ObservableList<Meals> mealsList = FXCollections.observableArrayList();
        while (result.next()) {
            int id = result.getInt("Mid");
            String name = result.getString("Mname");
            String type = result.getString("Mtype");
            double cost = result.getDouble("Mcost");
            mealsList.add(new Meals(id, cost, type, name));
        }
        return mealsList;
    }

    public static boolean delMeals(int id) throws SQLException {
        Connection con = getConnection();
        Statement s = con.createStatement();
        String sqlDelete = "DELETE FROM sunanda_drinks WHERE Mid = " + id + " ";
        boolean execute = s.execute(sqlDelete);

        return execute;
    }

    public static boolean delDrinks(int id) throws SQLException {
        Connection con = getConnection();
        Statement s = con.createStatement();
        String sqlDelete = "DELETE FROM sunanda_drinks WHERE Did = " + id + " ";
        boolean execute = s.execute(sqlDelete);

        return execute;
    }

    public static ObservableList<Drinks> getDrinks() throws SQLException {
        Connection con = getConnection();
        Statement s = con.createStatement();
        String sqlSelect = "select * from sunanda_drinks";
        ResultSet result = s.executeQuery(sqlSelect);
        ObservableList<Drinks> drinkList = FXCollections.observableArrayList();
        while (result.next()) {
            int id = result.getInt("Did");
            String name = result.getString("Dname");
            String type = result.getString("Dtype");
            double cost = result.getDouble("Dcost");
            drinkList.add(new Drinks(id, cost, type, name));
        }
        return drinkList;
    }

    public static boolean Update(String table, int id, String name, String type, double cost) throws SQLException {
        Connection con = getConnection();
        Statement s = con.createStatement();
        String sqlSelect;
        if (table.equals("drinks")) {
             sqlSelect = "UPDATE sunanda_drinks SET Dname = '" + name + "' , Dtype = '" + type + "',Dcost = " + cost + " where Did="+id+"";
        } else {
             sqlSelect = "UPDATE sunanda_meals SET Mname = '" + name + "' , Mtype = '" + type + "',Mcost = " + cost + " where Mid=" + id + "";

        }
         s.execute(sqlSelect);
        return true;
    }
   
}
