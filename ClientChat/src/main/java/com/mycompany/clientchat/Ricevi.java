package com.mycompany.clientchat;

import java.io.*;
import java.net.Socket;
public class Ricevi extends Thread{
    BufferedReader inputServer;
    Socket socket;
    String stringa;
    ClientChat gui = null;
    
    public void run(){
        for(;;){
            try {
                stringa = inputServer.readLine();
                if(stringa.equals("Logout")){
                    gui.addMsg("Disconnesso\r\n");
                    inputServer.close();                  
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
