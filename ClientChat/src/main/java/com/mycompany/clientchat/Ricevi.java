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
    ClientChat gui = null;
    
    public void run(){
        for(;;){
            try {
                stringa = inputServer.readLine();
                if(stringa.equals("Logout")){//non riceve questa stringa, fixxare
                    gui.addMsg("Disconnesso\r\n");
                    inputServer.close();
                    //socket.close();
                    System.exit(0);
                }
                gui.addMsg(stringa+"\r\n");

            } catch (Exception e) {
                System.out.println(e.getMessage());
                gui.addMsg("Comunicazione terminata\r\n");
                System.exit(1);
            }
        }
    }
    
    public Ricevi(BufferedReader input, ClientChat gui){
        inputServer= input;
        this.gui = gui;
    }
}
