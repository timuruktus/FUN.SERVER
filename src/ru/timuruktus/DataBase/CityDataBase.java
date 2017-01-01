package ru.timuruktus.DataBase;


import ru.timuruktus.WebModules.CitiesWeb;

import java.sql.*;

public class CityDataBase implements DataBaseHelper {
    Connection con;


    @Override
    public void createConnection(String database, String name, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loading success!");
            String url = "jdbc:mysql://localhost/" + database;
            Connection con = DriverManager.getConnection(url, name, password);
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

    @Override
    public Object getResult() {
        return getCitiesFromDB();
    }

    private String getCitiesFromDB(){
        createConnection("cities","timuruktus","3628022000Usateh");
        String result = null;
        try {
            Statement st = con.createStatement();
            String sqlCommand = "select * from cities";
            ResultSet rs = st.executeQuery(sqlCommand);
            result = "" + CitiesWeb.CITY_DB_VERSION + ".";
            while(rs.next()){
                result += rs.getString("city") + ".";
            }
        }catch (Exception ex){ ex.printStackTrace(); }
        closeConnection();
        return result;
    }


}
