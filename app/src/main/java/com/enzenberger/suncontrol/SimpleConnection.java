package com.enzenberger.suncontrol;

import androidx.databinding.ObservableField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleConnection implements Runnable {

    public final static String FAIL_MESSAGE = "IP_INVALID";
    private final String ip;
    private final int port;
    private final String message;
    private final Boolean isDuplex;
    private final ObservableField<String> result;
    private Socket socket;

    private SimpleConnection(String ip, int port, String message,
                             Boolean isDuplex, ObservableField<String> result) {
        this.ip = ip;
        this.port = port;
        this.message = message;
        this.isDuplex = isDuplex;
        this.result = result;
    }

    public SimpleConnection(String ip, int port, String message, ObservableField<String> result){
        this(ip, port, message, true, result);
    }

    @Override
    public void run() {
        try {
            this.socket = new Socket(ip, port);
            if (!isDuplex) {
                sendText(message);
            } else {
                request(message);
            }
            this.socket.close();
        } catch (IOException e) {
            result.set(FAIL_MESSAGE);
        }
    }

    private void request(String message) throws IOException {
        sendText(message);
        BufferedReader reader
                = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String input = reader.readLine();
        result.set(input);
    }

    private void sendText(String message) throws IOException {
        PrintWriter printWriter
                = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        printWriter.println(message);
        printWriter.flush();
    }
}
