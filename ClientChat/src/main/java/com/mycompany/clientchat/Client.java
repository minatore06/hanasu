 package com.mycompany.clientchat;
import java.io.*;
import java.net.*;
public class Client {
    String nomeServer = "localhost";
    int portaServer = 12345;
    Socket mioSocket;
    BufferedReader tastiera;
    String stringaClient;
    String stringaServer;
    DataOutputStream outputServer;
    BufferedReader inputServer;
    Trasmetti trasmetti = null;
    Ricevi ricevi = null;
    String nick = "";
    ClientChat gui = null;
    boolean ENEx = false; //Error Name Exist
    
    public Client(String ip, int porta, String nickname, ClientChat gui){
        nomeServer = ip;
        portaServer = porta;
        nick = nickname;
        this.gui = gui;      
        connetti();
    }

    public Trasmetti getTrasmetti(){
        return trasmetti;
    }
    
    public boolean getENEx(){
        return ENEx;
    }
    
    public void setENEx(boolean status){
        ENEx = status;
    }
    
    public void setNick(String nick){
        this.nick = nick;
    }
    
    //connessione col server
    private void connetti(){
        System.out.println("Client in esecuzione!");
        try {      
            mioSocket = new Socket(nomeServer,portaServer);           
            outputServer = new DataOutputStream((mioSocket.getOutputStream()));
            inputServer = new BufferedReader(new InputStreamReader(mioSocket.getInputStream()));           
            comunica();
        } catch(UnknownHostException e){
            System.err.println("Host sconosciuto");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione");
            System.exit(1);
        }
    }
    
    private void comunica(){
        try {   
            outputServer.writeBytes(nick+'\n');//invio del nickname
            stringaServer = inputServer.readLine();
            
            while(stringaServer.equals("0XiTruffa")){//nickname già esistente
                if(!ENEx)gui.addMsg("Nickname già esistente\r\n");//deve essere eseguito la prima volta
                ENEx = true;//setta errore nickname già esistente
                if(!ENEx){//se il nickname è stato cambiato
                    outputServer.writeBytes(nick+'\n');//invio del nickname
                    stringaServer = inputServer.readLine();//ricezione messaggio dal server
                }
            }
            gui.addMsg(stringaServer+"\r\n");//mostra l'elenco degli utenti connessi in chat
            trasmetti = new Trasmetti(outputServer, mioSocket, gui);//istanzia trasmetti per inviare i messaggi al server
            ricevi = new Ricevi(inputServer, gui);//istanzia ricevi per ricevere i messaggi inviati dal server
            ricevi.start();
        } catch (Exception e) {//errore durante la comunicazione col server
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione col server");
            System.exit(1);
        }        
    }
    
    public void logout(){//metodo alternativo per fare il logout tramite pulsante senza dover fare modifiche particolari
        trasmetti.invia("Logout");
    }
    
}
