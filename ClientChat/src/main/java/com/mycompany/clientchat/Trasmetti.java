package com.mycompany.clientchat;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;

public class Trasmetti{
    BufferedReader tastiera;
    DataOutputStream outputServer;
    Socket socket;
    ClientChat gui = null;
    
    //this is no more a thread per via dell'action listener
    public void invia(String stringa){
        try {
            outputServer.writeBytes(stringa+"\n");//invia messaggio al server
            //controllo del logout
            if(stringa.equals("Logout")){
                gui.addMsg("Disconnesso\r\n");//mostra a schermo il messaggio di disconnessione
                //chiusura connessione
                outputServer.close();
                socket.close();
                System.exit(0);
            }else{
                gui.addMsg("IO: " + stringa+"\r\n");//mostra il messaggio su schermo
            }
        }
        catch (Exception e) {//errore, la comunicazione viene considerata interrotta
            System.out.println(e.getMessage());
            gui.addMsg("Errore durante la comunicazione col server");
            System.exit(1);
        }
        
    }

    public Trasmetti(DataOutputStream server, Socket mioSocket, ClientChat gui){
        outputServer = server;
        socket = mioSocket;
        this.gui = gui;
    }
}
