package ru.timuruktus.WebModules;

import ru.timuruktus.ClassesToSend.Cities;
import ru.timuruktus.DataBase.CityDataBase;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Starter {

    public static int CITY_DB_VERSION = 101;

    public static void main(String[] args) {
        int port = 7705;
        CityDataBase cdb = new CityDataBase();
        Cities cities = new Cities(cdb.getCitiesFromDB(), Starter.CITY_DB_VERSION);

      /* Создаем серверный сокет на полученном порту */
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port: "
                    + serverSocket.getLocalPort() + "\n");
        } catch (IOException e) {
            System.out.println("Port " + port + " is blocked.");
            System.exit(-1);
        }

        while (true) {
            try {
                Socket clientSocket = null;
                while(clientSocket == null) {
                    clientSocket = serverSocket.accept();
                }
            /* Для обработки запроса от каждого клиента создается
             * отдельный объект и отдельный поток */
                Session session = new Session(clientSocket, cities);
                new Thread(session).start();
            } catch (IOException e) {
                System.out.println("Failed to establish connection.");
                System.out.println(e.getMessage());
            }
        }
    }

}

