package com.mycompany.serverchat;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiServer {
    Socket socket = null;
    int porta = 12345;
    ArrayList<ServerThread> utenti = new ArrayList();
    
    //costruttore
    public MultiServer(int porta){
        this.porta = porta;
        
        start();
    }
    
    public void start(){
        try{
            ServerSocket serverSocket = new ServerSocket(porta);
            System.out.println("Server aperto!");
            System.out.println("Server in attesa...");
            
            while(true){//Accettazione continua di nuove connessioni
                socket = serverSocket.accept();
                System.out.println("Nuovo socket "+socket);
                ServerThread client = new ServerThread(socket, this);//creazione thread
                client.start();
            }
        }
        catch(Exception e){//errori nell'istanziamento del server
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'instanza del server");
            System.exit(1);
        }
    }
    
    //aggiunta del thread nell'arraylist
    public void addUtente(ServerThread me){
        utenti.add(me);
    }
    
    //rimozione del thread nell'arraylist
    public void removeUtente(ServerThread me){
        utenti.remove(me);
    }
    
    //metodo che invia il messaggio a tutti i thread tranne al mittente
    public void sendAll(String message, ServerThread me){
        for (int i = 0; i < utenti.size(); i++) {
            if(utenti.get(i) == me)continue;
            try {
                utenti.get(i).invia(message);
            } catch (IOException ex) {
            }
            
        }
    }
    
    //metodo per inviare il messaggio ad uno specifico utente
    public boolean privateMSG(String dest, String mit, String message){
        for (int i = 0; i < utenti.size(); i++) {
            if(utenti.get(i).getNick().equals(dest)){//ricerca dell'utente tramite nickname
                try {
                    utenti.get(i).invia("DM from-> "+mit+": "+message);
                    return true;
                } catch (IOException ex) {
                    return false;
                }
            }
        }
        return false;
    }
    
    public boolean checkNick(String me){
        for (int i = 0; i < utenti.size(); i++) {
            if(utenti.get(i).getNick().equals(me)){
                return true;
            }
        }
        return false;
    }
    
    //metodo che invia tutti i nickname degli utenti in un arraylist
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
