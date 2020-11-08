package com.mycompany.clientchat;

import java.io.*;
import java.net.*;

/**
 *
 * @author stei2
 */
public class Trasmetti extends Thread{
    BufferedReader tastiera;
    DataOutputStream outputServer;
    Socket socket;
    String stringa;
    
    public void run(){
        tastiera = new BufferedReader(new InputStreamReader(System.in));
        for(;;){
            try {    
                stringa = tastiera.readLine();
                outputServer.writeBytes(stringa+"\n");
                if(stringa.equals("FINE")){
                    System.out.println("Chiusura connessione");
                    outputServer.close();
                    socket.close();
                    System.exit(0);
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Errore durante la comunicazione col server");
                System.exit(1);
            }
        }
        
    }

    public Trasmetti(DataOutputStream server, Socket mioSocket){
        outputServer = server;
        socket = mioSocket;
    }
}
