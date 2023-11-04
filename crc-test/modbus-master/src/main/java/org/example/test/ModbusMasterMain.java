package org.example.test;


import net.wimpi.modbus.util.ModbusUtil;

public class ModbusMasterMain {

    public static void main(String[] args) {
        int portNumber = 1234;
        SocketServer socketServer = new SocketServer(portNumber);
        socketServer.start();
    }
}
