package com.mycompany.clientchat;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;

public class Trasmetti{
    BufferedReader tastiera;
    DataOutputStream outputServer;
    Socket socket;
    ClientChat gui = null;
    
    public void invia(String stringa){
        try {
            outputServer.writeBytes(stringa+"\n");
            if(stringa.equals("Logout")){
                gui.addMsg("Disconnesso\r\n");
                outputServer.close();
                socket.close();
                System.exit(0);
            }else{
                gui.addMsg("IO: " + stringa+"\r\n");
            }
        }
        catch (Exception e) {
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
