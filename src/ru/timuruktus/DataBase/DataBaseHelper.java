package ru.timuruktus.DataBase;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface DataBaseHelper {

    abstract public void createConnection(String database, String name, String password);
    abstract public void closeConnection();



}
