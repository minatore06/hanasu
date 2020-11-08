 package com.mycompany.clientchat;
import java.io.*;
import java.net.*;
/**
 *
 * @author stei2
 */
public class Client {
    String nomeServer = "localhost";
    int portaServer = 5678;
    Socket mioSocket;
    BufferedReader tastiera;
    String stringaClient;
    String stringaServer;
    DataOutputStream outputServer;
    BufferedReader inputServer;
    
    public static void main(String[] args) {
        Client cliente = new Client();
        cliente.connetti();
    }
    
    public Socket connetti(){
        System.out.println("Client in esecuzione!");
        try {
            tastiera = new BufferedReader(new InputStreamReader(System.in));
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
        return mioSocket;
    }
    
    public void comunica(){
        try {
            System.out.println(inputServer.readLine());
            
            stringaClient = tastiera.readLine();
            outputServer.writeBytes(stringaClient+'\n');

            System.out.println(inputServer.readLine()+"\n");

            Trasmetti trasmetti = new Trasmetti(new DataOutputStream(outputServer), mioSocket);
            Ricevi ricevi = new Ricevi(new BufferedReader(new InputStreamReader(mioSocket.getInputStream())));
            trasmetti.start();
            ricevi.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione col server");
            System.exit(1);
        }
        
    }
    
}
