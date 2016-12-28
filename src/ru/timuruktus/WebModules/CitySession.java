package ru.timuruktus.WebModules;


import java.io.*;
import java.net.Socket;

public class CitySession implements Runnable {

    final int LOAD_CITIES = 1;

    protected Socket socket = null;

    public CitySession(Socket socket) throws IOException {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            long time = System.currentTimeMillis();
            input.readLine();
            output.write();
            output.close();
            input.close();
            System.out.println("Request processed: " + time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
