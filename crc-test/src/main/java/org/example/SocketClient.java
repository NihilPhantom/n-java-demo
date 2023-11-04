package org.example;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
public class SocketClient {

    /**
     * 测试类型
     * slave： 从者模式，当服务器发送消息过来之后，返回一条消息作为回复
     * sender: 发送者模式，连接上服务器之后，开启定时任务，向服务器发送消息
     */
    private final String TestType = "slave";

    private String serverAddress;
    private int serverPort;
    private Socket socket;
    private OutputStream outputStream;

    private InputStream inputStream;

    public SocketClient(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void connect() {
        try {
            socket = new Socket(serverAddress, serverPort);
            System.out.println("连接到服务器成功，地址: " + serverAddress + ", 端口: " + serverPort);

            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();

            if(TestType.equals("slave")){
                byte[] bytes = new byte[1024];
                int len = -1;
                while ((len = inputStream.read(bytes)) != -1){
                    String hexString = "";
                    for (int i=0; i<len; i++) {
                        hexString += String.format("%02X", bytes[i]);
                    }
                    System.out.println(hexString);
//                    byte[] byteArray = DatatypeConverter.parseHexBinary(hexString);
                    byte[] byteArray = {3, (byte)4, (byte)24, (byte)9, (byte)35, (byte)9, (byte)198, (byte)128, (byte)105, (byte)128, (byte)105, (byte)128, (byte)105, (byte)128, (byte)105, (byte)128, (byte)105, (byte)128, (byte)105, (byte)128, (byte)105, (byte)128, (byte)105, (byte)128, (byte)105, (byte)128, (byte)105, (byte)70, (byte)193};
                    outputStream.write(byteArray);
                    outputStream.flush();
                }

            }else if(TestType.equals("sender")){
                 // 启动定时器，每隔5秒发送一条消息
                 Timer timer = new Timer();
                 timer.schedule(new SendMessageTask(), 0, 5000);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class SendMessageTask extends TimerTask {

        public SendMessageTask() {}

        public void run() {
            String hexString = "40040061620C"; // 输入的十六进制字符串
            byte[] byteArray = DatatypeConverter.parseHexBinary(hexString);
            try {
                outputStream.write(byteArray);
//                outputStream.write('\n');
                outputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println("发送消息: " + hexString);
        }
    }
}