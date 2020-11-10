package com.mycompany.serverchat;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ServerThread extends Thread{
    ServerThread server = null;
    Socket client = null;
    String stringaClient = null;
    BufferedReader inputClient;
    DataOutputStream outputClient;
    String nick = null;
    MultiServer gf = null;

    public String getNick() {
        return nick;
    }
    
    public void run(){
        try{
            initialization();
        }catch(Exception e){
            e.printStackTrace(System.out);
        }
    }
    
    private void initialization() throws Exception{
        ArrayList<String> nomi = null;
        String listaNomi = "";
  
        nick = inputClient.readLine();
        System.out.println(nick);
        
        gf.addUtente(this);
        
        gf.sendAll(nick+" si è connesso alla chat!", this);
        nomi = gf.getAllNicks(this);
        
        for (int i = 0; i < nomi.size(); i++) {
            listaNomi += nomi.get(i);
            if(i!=(nomi.size()-1))listaNomi += ", ";
        }
        
        outputClient.writeBytes("Lista degli utenti già connessi: "+listaNomi+"\n");
        
        comunica();
    }
    
    private void comunica()throws Exception{
        String cmd = "";
        for(;;){
            
            stringaClient = inputClient.readLine();
            if(stringaClient.indexOf('/')!=-1){
                cmd = stringaClient.substring(1, 3);
                System.out.println(cmd);
                switch(cmd){
                    case "dm":
                        if(stringaClient.indexOf(' ', 4)!=-1)/*carattere da rivedere, per indicare fine nome(magari fare con spazio)*/{
                            //prendi nome, cerca thread manda messagggio
                            String destinatario = stringaClient.substring(4, stringaClient.indexOf(' '));
                            System.out.println("destinatario: "+destinatario);
                            ArrayList<String> nomi = gf.getAllNicks(this);
                            
                            if(nomi.indexOf(destinatario)!=-1){
                                //trovato
                                gf.privateMSG(destinatario, nick, stringaClient.substring(stringaClient.indexOf(' ', 4)+2));
                            }else{
                                //errore, non esiste
                                outputClient.writeBytes("SISTEMA: L'utente non e' stato trovato\n");
                            }
                        }else{
                            //errore, non ha messo nessun nome
                            outputClient.writeBytes("SISTEMA: Non e' stato inserito nessun nome o e' stato omesso il carattere speciale\n");
                        }
                        break;
                }
            }else{
                //Chiusura connessione con client
                if(stringaClient.equals("Logout")){//rivedere
                    System.out.println(nick+" disconnesso!");
                    outputClient.writeBytes("Logout\n");//Invio killer line
                    inputClient.close();
                    outputClient.close();
                    client.close();
                    gf.sendAll(nick+" disconnesso!", this);
                    gf.removeUtente(this);
                    return;
                }
                try {   
                    //outputChat.writeBytes(nome+": "+stringaClient+"\n");
                    gf.sendAll(nick+": "+stringaClient, this);

                } catch (Exception e) {
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
