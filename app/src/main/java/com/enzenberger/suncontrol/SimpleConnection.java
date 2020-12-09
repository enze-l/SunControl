package com.enzenberger.suncontrol;

import android.content.Context;
import android.database.Observable;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleConnection implements Runnable {

    private final String ip;
    private final int port;
    private final String message;
    private final Boolean isDupl;
    private MainActivity mainActivity = null;
    private Socket socket;

    private SimpleConnection(String ip, int port, String message, Boolean isDupl) {
        this.ip = ip;
        this.port = port;
        this.message = message;
        this.isDupl = isDupl;
    }

    public SimpleConnection(String ip, int port, String message, MainActivity mainActivity){
        this(ip, port, message, true);
        this.mainActivity = mainActivity;
    }

    public SimpleConnection(String ip, int port, String message) {
        this(ip, port, message, false);
    }

    @Override
    public void run() {
        try {
            this.socket = new Socket(ip, port);
            if (!isDupl) {
                sendText(message);
            } else {
                request(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void request(String message) throws IOException {
        PrintWriter writer
                = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader reader
                = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer.println(message);
        writer.flush();
        String input = reader.readLine();
        this.mainActivity.getHeader().set(input);
    }

    private void sendText(String message) throws IOException {
        PrintWriter printWriter
                = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        printWriter.println(message);
        printWriter.flush();
    }
}
