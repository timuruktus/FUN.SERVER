package ru.timuruktus.WebModules;



import ru.timuruktus.ClassesToSend.Cities;

import java.io.*;
import java.net.Socket;

public class Session implements Runnable {
    protected Socket socket = null;
    private boolean isActive = true;
    Cities cities;

    public Session(Socket socket, Cities cities) throws IOException {
        this.socket = socket;
        this.cities = cities;
    }

    @Override
    public void run() {
        while(isActive) {
            try {
                System.out.println("New Session Thread was created!");
                ObjectInputStream  input = new ObjectInputStream (socket.getInputStream());
                System.out.println("FIRST STEP");
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("SECOND STEP");
                if(input.readUTF().equals("CITIES")){
                    System.out.println("input == CITIES");
                    output.writeObject(cities);
                    output.flush();
                } else if(input.readUTF().equals("EXIT")){
                    stopThread();
                }
                output.close();
                input.close();
                socket.close();
                cities = null;
                stopThread();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("THIS IS IOException");
                stopThread();
            }
        }
    }

    public void stopThread(){
        this.isActive = false;
    }

}
