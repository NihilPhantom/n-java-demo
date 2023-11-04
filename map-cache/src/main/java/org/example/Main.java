package org.example;

import org.example.util.THasMap;
import org.example.util.THasMap2;

public class Main{
    public static void main(String[] args) throws InterruptedException {

        String mode = "H2"; // H2

        if("H1".equals(mode)){
            THasMap<String, String>  map = new THasMap<>();

            map.put("123", "123", 1000);

            Thread.sleep(500);
            System.out.println(map.get("123"));

            Thread.sleep(510);
            System.out.println(map.get("123"));

            map.stop();
        }
        else if("H2".equals(mode)){
            THasMap2<String, String> map = new THasMap2<>();

            for (int i=0;i<11 ;i++){
                map.put("" + i, "123", 1000 * i);
            }

            map.put("3", "123", 10000);


            Thread.sleep(500);
            System.out.println(map.get("1"));

            Thread.sleep(510);
            System.out.println(map.get("1"));

        }
    }
}