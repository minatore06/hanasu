package com.mycompany.clientchat;

import java.io.*;
import java.net.Socket;
/**
 *
 * @author stei2
 */
public class Ricevi extends Thread{
    BufferedReader inputServer;
    Socket socket;
    String stringa;
    
    public void run(){
        for(;;){
            try {
                stringa = inputServer.readLine();
                if(stringa.equals("7586")){//non riceve questa stringa, fixxare
                    System.out.println("Disconnesso");
                    inputServer.close();
                    //socket.close();
                    System.exit(0);
                }
                System.out.println(stringa);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Comunicazione terminata");
                System.exit(1);
            }
        }
    }
    
    public Ricevi(BufferedReader input){
        inputServer= input;
    }
}
