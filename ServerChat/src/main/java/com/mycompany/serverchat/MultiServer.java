package com.mycompany.serverchat;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author stei2
 */
public class MultiServer {
    Socket socket = null;
    ArrayList<ServerThread> utenti = new ArrayList();
    
    public void start(){
        try{
            ServerSocket serverSocket = new ServerSocket(5678);
            System.out.println("Server aperto!");
            System.out.println("Server in attesa...");
            
            while(true){
                socket = serverSocket.accept();
                System.out.println("Nuovo socket "+socket);
                ServerThread client = new ServerThread(socket, this);
                client.start();
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'instanza del server");
            System.exit(1);
        }
    }
    
    public void addUtente(ServerThread me){
        utenti.add(me);
    }
    
    public void removeUtente(ServerThread me){
        utenti.remove(me);
    }
    
    public void sendAll(String message, ServerThread me){
        for (int i = 0; i < utenti.size(); i++) {
            if(utenti.get(i) == me)continue;
            try {
                utenti.get(i).invia(message);
            } catch (IOException ex) {
                //qualcosa
            }
            
        }
    }
    
    public boolean privateMSG(String dest, String mit, String message){
        for (int i = 0; i < utenti.size(); i++) {
            if(utenti.get(i).getNick().equals(dest)){
                try {
                    utenti.get(i).invia("DM from->"+mit+": "+message+'\n');
                    return true;
                } catch (IOException ex) {
                    return false;
                }
            }
        }
        return false;
    }
    
    public ArrayList<String> getAllNicks(ServerThread me){
        ArrayList<String> nomi = new ArrayList();
        for (int i = 0; i < utenti.size(); i++) {
            if(utenti.get(i) == me)continue;
            nomi.add(utenti.get(i).getNick());
        }
        return nomi;
    }
    
    public static void connection(){
    }
}
