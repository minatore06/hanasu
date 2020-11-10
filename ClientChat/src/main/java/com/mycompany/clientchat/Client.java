 package com.mycompany.clientchat;
import java.io.*;
import java.net.*;
/**
 *
 * @author stei2
 */
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
    
    /*public static void main(String[] args) {
        Client cliente = new Client();
        cliente.connetti();
    }*/

    public Trasmetti getTrasmetti(){
        return trasmetti;
    }
    
    public Client(String ip, int porta, String nickname, ClientChat gui){
        nomeServer = ip;
        portaServer = porta;
        nick = nickname;
        this.gui = gui;
        
        connetti();
    }
    
    public void connetti(){
        System.out.println("Client in esecuzione!");
        try {
            //tastiera = new BufferedReader(new InputStreamReader(System.in));
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
    
    public void comunica(){
        try {
            //System.out.println(inputServer.readLine());//non server più, togliere dal server
            
            outputServer.writeBytes(nick+'\n');

            gui.addMsg(inputServer.readLine()+"\r\n");//lista nomi, mettere su gui

            trasmetti = new Trasmetti(outputServer, mioSocket, gui);
            ricevi = new Ricevi(inputServer, gui);
            ricevi.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione col server");
            System.exit(1);
        }
        
    }
    
    public void logout(){
        trasmetti.invia("Logout");
    }
    
}
