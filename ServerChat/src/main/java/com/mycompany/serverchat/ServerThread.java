package com.mycompany.serverchat;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThread extends Thread{
    ServerThread server = null;
    Socket client = null;
    String stringaClient = null;
    BufferedReader inputClient;
    DataOutputStream outputClient;
    String nick = null;
    MultiServer gf = null;//Grande Fratello

    public String getNick() {
        return nick;
    }
    
    public void run(){
        try{
            initialization();
        }
        catch(SocketException e){//chiusura inaspettata del client
            try {
                inputClient.close();
                outputClient.close();
                client.close();
                gf.sendAll("SISTEMA-> "+nick+" disconnesso!", this);//notifica a tutti gli utenti connessi la disconnessione
                gf.removeUtente(this);//rimuove thread dall'arraylist
                System.out.println(nick+" disconnected");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        catch(Exception e){
            e.printStackTrace(System.out);
        }
    }
    
    //metodo che esegue le operazioni necessarie a rendere il thread operativo da eseguire solamente all'inizio
    private void initialization() throws Exception{
        ArrayList<String> nomi = null;
        String listaNomi = "";
  
        nick = inputClient.readLine();//presa del nickname, al momento non è presente nessun controllo

        //controllo sul nickname
        while(gf.checkNick(nick)){//doppio nick
            System.out.println("nickname ripetuto - "+nick);
            outputClient.writeBytes("0XiTruffa\n");//invia errore nickname già esistente
            nick = inputClient.readLine();//presa del nickname, al momento non è presente nessun controllo
        }
        
        System.out.println(nick);
        
        gf.addUtente(this);//aggiunge il thread all'arraylist solo dopo che è venuto a conoscenza del nick
        
        gf.sendAll("SISTEMA-> "+nick+" si è connesso alla chat!", this);//mostra a tutti che si è connesso
        System.out.println(nick+" connected");
        nomi = gf.getAllNicks(this);
        
        for (int i = 0; i < nomi.size(); i++) {//rende l'array una singola stringa
            listaNomi += nomi.get(i);
            if(i!=(nomi.size()-1))listaNomi += ", ";
        }
        
        outputClient.writeBytes("Lista degli utenti già connessi: "+listaNomi+"\n");//mostra la lista degli utenti connessi in chat
        
        comunica();
    }
    
    private void comunica()throws Exception{
        String cmd = "";
        for(;;){
            
            stringaClient = inputClient.readLine();//ricezione messaggio client
            /*
            if(stringaClient == null){//in caso di chiusura non segnalata, la trasforma in una chiusura segnalata(non sembra funzionare, viene lanciata un'eccezione)
                stringaClient = "Logout";
            }*/
            
            if(stringaClient.indexOf('/')!=-1){
                cmd = stringaClient.substring(1, 3);
                System.out.println("comando - "+cmd);
                switch(cmd){
                    case "dm":
                        System.out.println(stringaClient);
                        if(stringaClient.indexOf(' ', 4)!=-1){//carattere da rivedere, per indicare fine nome(magari fare con spazio)[alla fine è stato utilizzato lo spazio, di conseguenza non è più possibile controllare se è stato inserito il nick o no(non necessario), sostituito dal non trovare il nome dell'utente]
                            //prendi nome, cerca thread, manda messagggio
                            String destinatario = stringaClient.substring(4, stringaClient.indexOf(' ', 4));
                            ArrayList<String> nomi = gf.getAllNicks(this);
                            
                            if(nomi.indexOf(destinatario)!=-1){
                                //trovato, invia messaggio tagliando il comando
                                gf.privateMSG(destinatario, nick, stringaClient.substring(stringaClient.indexOf(' ', 4)+1));
                                System.out.println("Messaggio privato tra "+nick+" e "+destinatario);
                            }else{
                                //errore, non esiste
                                System.out.println("Destinatario non trovato: "+destinatario);
                                outputClient.writeBytes("SISTEMA-> L'utente non e' stato trovato\n");
                            }
                        }else{
                            //errore, non ha messo nessun nome(non più valido) o almeno l'uso è diverso
                            outputClient.writeBytes("SISTEMA-> Non e' stato inserito nessun nome\n");
                        }
                        break;
                }
            }else{
                //Chiusura connessione con client
                if(stringaClient.equals("Logout")){//dovrebbe funzionare
                    System.out.println(nick+" disconnected");
                    outputClient.writeBytes("Logout\n");//Invio killer line
                    //chiusura connessione
                    inputClient.close();
                    outputClient.close();
                    client.close();
                    gf.sendAll("SISTEMA-> "+nick+" disconnesso!", this);//notifica a tutti gli utenti connessi la disconnessione
                    gf.removeUtente(this);//rimuove thread dall'arraylist
                    return;
                }
                try {   
                    //outputChat.writeBytes(nome+": "+stringaClient+"\n");
                    gf.sendAll(nick+": "+stringaClient, this);//invia a tutti il messaggio con l'aggiunta del nickname del mittente

                } catch (Exception e) {//teoricamente parte quando è rimasto 1 solo utente, non dovrebbe essere più valido
                    outputClient.writeBytes("Non ci sono utenti rimasti in chat, disconnessione automatica!\n");
                    outputClient.writeBytes("Logout\n");
                    inputClient.close();
                    outputClient.close();
                    client.close();
                    return;
                }
            }
        }
    }
    
    //inoltra al proprio client il messaggio
    public void invia(String message) throws IOException{
        outputClient.writeBytes(message+"\n");
    }
    
    public ServerThread(Socket socket, MultiServer fato)throws Exception{
        gf = fato;
        this.client = socket;
        inputClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outputClient = new DataOutputStream(client.getOutputStream());
    }
}
