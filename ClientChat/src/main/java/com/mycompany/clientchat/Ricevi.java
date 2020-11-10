package com.mycompany.clientchat;

import java.io.*;
import java.net.Socket;
public class Ricevi extends Thread{
    BufferedReader inputServer;
    Socket socket;
    String stringa;
    ClientChat gui = null;
    
    public void run(){
        for(;;){//ripetizione infinita
            try {
                stringa = inputServer.readLine();//ricezione messaggio
                //controllo se è stata richiesta una disconnessione dal server
                if(stringa.equals("Logout")){
                    gui.addMsg("Disconnesso\r\n");//aggiunta a schermo del messaggio di disconnessione nella gui
                    inputServer.close();
                    System.exit(0);
                }
                gui.addMsg(stringa+"\r\n");//mostra messaggio tramite interfaccia all'utente

            } catch (Exception e) {//errore, la comunicazione viene considerata interrotta
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
