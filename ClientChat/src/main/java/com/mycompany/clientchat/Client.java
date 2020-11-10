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
    boolean ENEx = false; //Error Name Exist TRUE = Il nickname scelto è già esistente nel server | FALSE = Problema probabilmente risolto, il nuovo nick verrà inviato al server
    boolean WCN = false;//Wait change nickname TRUE = in attesa di nuovo nickname | FALSE = non è prevista la modifica del nickname
    
    public Client(String ip, int porta, String nickname, ClientChat gui){
        nomeServer = ip;
        portaServer = porta;
        nick = nickname;
        this.gui = gui;
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
        if(WCN)this.nick = nick;//cambia il nickname solo se è prevista una sua modifica
    }
    
    //connessione col server
    public void connetti(){
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
            System.out.println(stringaServer);
            while(stringaServer.equals("0XiTruffa")){//finchè il nickname è già esistente
                if(!WCN){//è già in attesa del cambio di nickname?
                    ENEx = true;//setta errore nickname già esistente informando la gui che ha bisogno di un nuovo input
                    WCN = true;//il nickname deve essere cambiato
                    gui.addMsg("Nickname già esistente\r\n");//informa il client
                    System.out.println(nick);
                }
                System.out.println(nick+ENEx+WCN);//operazione neccessaria, altrimenti si blocca l'esecuzione del while
                if(!ENEx){//è stato cambiato il nickname? (comportando la possibile soluzione dell'errore)
                    System.out.println(nick);
                    outputServer.writeBytes(nick+'\n');//invio del nickname al server
                    stringaServer = inputServer.readLine();//ricezione messaggio dal server
                    System.out.println(stringaServer);
                    WCN = false;//il nickname è stato cambiato
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
