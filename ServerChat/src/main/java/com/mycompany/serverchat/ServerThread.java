package com.mycompany.serverchat;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author stei2
 */
public class ServerThread extends Thread{
    ServerThread server = null;
    Socket client = null;
    String stringaClient = null;
    String stringaMod = null;
    BufferedReader inputClient;
    DataOutputStream outputClient;
    DataOutputStream outputChat;
    String nome = null;
    MultiServer gf = null;//Grande Fratello

    public String getNome() {
        return nome;
    }
    
    public void run(){
        try{
            initialization();
        }catch(Exception e){
            e.printStackTrace(System.out);
        }
    }
    
    public void initialization() throws Exception{
        ArrayList<String> nomi = null;
        String listaNomi = "";
        
        outputClient.writeBytes("Inserisci il tuo nickname\n");
        nome = inputClient.readLine();
        System.out.println(nome);
        
        gf.addUtente(this);
        
        gf.sendAll(nome+" connected to the chat!", this);
        nomi = gf.getAllNicks(this);
        
        for (int i = 0; i < nomi.size(); i++) {
            listaNomi += nomi.get(i)+", ";
        }
        
        outputClient.writeBytes("Lista degli utenti già connessi: "+listaNomi+"\n");
        
        comunica();
    }
    
    public void comunica()throws Exception{
        for(;;){
            
            stringaClient = inputClient.readLine();
            //Chiusura connessione con client
            if(stringaClient.equals("FINE")){//rivedere
                System.out.println(nome+" left the chat!");
                outputClient.writeBytes("7586\n");//Invio killer line
                inputClient.close();
                outputClient.close();
                client.close();
                gf.sendAll(nome+" disconnected!", this);
                gf.removeUtente(this);
                return;
            }
            try {   
                //outputChat.writeBytes(nome+": "+stringaClient+"\n");
                gf.sendAll(nome+": "+stringaClient, this);
                
            } catch (Exception e) {
                outputClient.writeBytes("Non ci sono utenti rimasti in chat, disconnessione automatica!\n");
                outputClient.writeBytes("7586\n");
                inputClient.close();
                outputClient.close();
                client.close();
                return;
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
