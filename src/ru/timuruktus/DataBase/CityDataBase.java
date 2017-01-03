package ru.timuruktus.DataBase;


import java.sql.*;
import java.util.ArrayList;

public class CityDataBase implements DataBaseHelper {
    Connection con;

    @Override
    public void createConnection(String database, String name, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loading success!");
            String url = "jdbc:mysql://localhost/" + database;
            con = DriverManager.getConnection(url, name, password);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void closeConnection() {
        try {
            con.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public String[] getCitiesFromDB(){
        ArrayList<String> citiesArray = new ArrayList<>();
        createConnection("cities", "timuruktus", "admin");
        try {
            Statement st = con.createStatement();
            String sqlCommand = "select * from cities";
            ResultSet rs = st.executeQuery(sqlCommand);
            while (rs.next()){
                System.out.println(rs.getString("city"));
                citiesArray.add(rs.getString("city"));
            }
            String[] cities = new String[citiesArray.size()];
            for(int i = 0; i < citiesArray.size(); i++){
                cities[i] = citiesArray.get(i);
            }
            closeConnection();
            return cities;
        } catch (Exception ex) {ex.printStackTrace();}
        System.out.println("NULL");
        return null;

    }


}
