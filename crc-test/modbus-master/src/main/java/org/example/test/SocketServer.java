package org.example.test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SocketServer {
    private ServerSocket serverSocket;
    private Map<Integer, Socket> clientSockets;

    public SocketServer(int portNumber) {
        try {
            serverSocket = new ServerSocket(portNumber);
            clientSockets = new HashMap<>();
            System.out.println("Socket 服务器启动，等待客户端连接...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                int socketId = clientSocket.hashCode();
                clientSockets.put(socketId, clientSocket);
                System.out.println("客户端连接成功，ID: " + socketId);

                ClientHandler clientHandler = new ClientHandler(socketId, clientSocket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(int socketId, String message) {
        Socket clientSocket = clientSockets.get(socketId);
        if (clientSocket != null && clientSocket.isConnected()) {
            try {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println(message);
                System.out.println("向客户端 " + socketId + " 发送消息: " + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ClientHandler implements Runnable {
        private int socketId;
        private Socket clientSocket;

        public ClientHandler(int socketId, Socket clientSocket) {
            this.socketId = socketId;
            this.clientSocket = clientSocket;
        }

        public void run() {
            try {
                InputStream inputStream = clientSocket.getInputStream();
                // 接收数据
                byte[] buffer = new byte[1024];
                int bytesRead;
                byte[] messageBytes = new byte[0]; // 初始为空数组

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    messageBytes = concatenateArrays(messageBytes, Arrays.copyOf(buffer, bytesRead));
                    // 转换为十六进制输出
                    String hexMessage = bytesToHex(messageBytes);
                    System.out.println("接收到消息: " + hexMessage);
                }

                // 关闭连接
                inputStream.close();
                clientSocket.close();
                clientSockets.remove(socketId);
                System.out.println("客户端断开连接，ID: " + socketId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private byte[] concatenateArrays(byte[] a, byte[] b) {
            byte[] result = new byte[a.length + b.length];
            System.arraycopy(a, 0, result, 0, a.length);
            System.arraycopy(b, 0, result, a.length, b.length);
            return result;
        }

        private String bytesToHex(byte[] bytes) {
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02X", b));
            }
            return sb.toString();
        }
    }
}