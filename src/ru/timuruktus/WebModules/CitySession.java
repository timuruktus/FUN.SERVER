package ru.timuruktus.WebModules;


import ru.timuruktus.DataBase.*;

import java.io.*;
import java.net.Socket;

public class CitySession implements Runnable {

    final int LOAD_CITIES = 1;

    protected Socket socket = null;
    private String inputText = null;

    public CitySession(Socket socket) throws IOException {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            long time = System.currentTimeMillis();
            String splittedText[];
            int i = 0;
            while(i == 0) {
                if (input.readLine() != null) {
                    inputText = input.readLine();
                    splittedText = inputText.split(" ");
                    if(splittedText[0] == "ADMINVERSION"){
                        CitiesWeb.CITY_DB_VERSION = Integer.valueOf(splittedText[1]);
                        i++;
                    }
                    else if(splittedText[0] == "1"){
                        if(Integer.valueOf(splittedText[1]) == CitiesWeb.CITY_DB_VERSION){
                            output.write("RECENT");
                            i++;
                        }
                        else{
                            DataBaseHelper cityDB = new CityDataBase();
                            cityDB.createConnection("cities", "timuruktus", "3628022000Usateh");
                            output.write(cityDB.getResult().toString());
                        }
                    }
                }

            }
            output.close();
            input.close();
            socket.close();
            System.out.println("Request processed: " + time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
