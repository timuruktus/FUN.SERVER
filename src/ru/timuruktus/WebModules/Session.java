package ru.timuruktus.WebModules;



import ru.timuruktus.ClassesToSend.Cities;

import java.io.*;
import java.net.Socket;

public class Session implements Runnable {
    protected Socket socket = null;
    private volatile boolean isActive = true;
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
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                long time = System.currentTimeMillis();
                while (input.readObject() == null){
                    System.out.println("null");
                }
                if((String)input.readObject() == "CITIES"){
                    System.out.println("input == CITIES");
                    output.writeObject(cities);
                    output.flush();
                }
                output.close();
                input.close();
                socket.close();
                System.out.println("Request processed: " + time);
                stopThread();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("IOException");
            }catch (ClassNotFoundException ex){
                ex.printStackTrace();
                System.err.println("ClassNotFoundException");
            }
        }
    }

    public void stopThread(){
        this.isActive = false;
    }

}
