package net.wimpi.modbus.cmd;

import net.wimpi.modbus.util.ModbusUtil;

import javax.xml.bind.DatatypeConverter;

public class CRCTest {
    public static void main(String[] args) {
        String hexString = "02040000000C"; // 输入的十六进制字符串
        byte[] byteArray = DatatypeConverter.parseHexBinary(hexString);
        int crc[] = ModbusUtil.calculateCRC(byteArray, 0, byteArray.length);
        for (int i=0; i<crc.length; i++){
            System.out.println(crc[i]);
        }
    }
}
