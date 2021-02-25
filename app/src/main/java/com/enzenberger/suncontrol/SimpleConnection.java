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
    private final ObservableField<String> result;
    private Socket socket;

    /**
     * Enables sending out an request and receiving the response
     * @param ip the ip to which the request should be send
     * @param port the port on which the receiving device is listening
     * @param message the message that should be send
     * @param result the object to which the result should be transmitted
     */
    public SimpleConnection(String ip, int port, String message, ObservableField<String> result) {
        this.ip = ip;
        this.port = port;
        this.message = message;
        this.result = result;
    }

    @Override
    public void run() {
        try {
            this.socket = new Socket(ip, port);
            request(message);
            this.socket.close();
        } catch (IOException e) {
            result.set(FAIL_MESSAGE);
        }
    }

    private void request(String message) throws IOException {
        PrintWriter printWriter
                = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        printWriter.println(message);
        printWriter.flush();
        BufferedReader reader
                = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String input = reader.readLine();
        result.set(input);
    }
}
