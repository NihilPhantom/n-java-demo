package org.example;

public class ModbusClientMain {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int serverPort = 10000;

        SocketClient socketClient = new SocketClient(serverAddress, serverPort);
        socketClient.connect();
    }
}
