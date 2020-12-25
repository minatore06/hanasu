package com.mycompany.clientchat;

import java.io.*;
import java.net.Socket;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class Ricevi extends Thread{
    BufferedReader inputServer;
    Socket socket;
    String stringa;
    ClientChat gui = null;
    
    public void run(){
        for(;;){//ripetizione infinita
            try {
                stringa = inputServer.readLine();//ricezione messaggio
                playSound("/kiniro-mosaic-ayaya-ayaya.wav");
                //controllo se è stata richiesta una disconnessione dal server
                if(stringa.equals("Logout")){
                    gui.addMsg("Disconnesso\r\n");//aggiunta a schermo del messaggio di disconnessione nella gui
                    inputServer.close();
                    System.exit(0);
                }
                gui.addMsg(stringa+"\r\n");//mostra messaggio tramite interfaccia all'utente

            } catch (Exception e) {//errore, la comunicazione viene considerata interrotta
                System.out.println(e.getMessage());
                gui.addMsg("Comunicazione terminata\r\n");
                System.exit(1);
            }
        }
    }
    
    public static synchronized void playSound(final String url) {
        new Thread(new Runnable() {
        // The wrapper thread is unnecessary, unless it blocks on the
        // Clip finishing; see comments.
          public void run() {
            try {
              Clip clip = AudioSystem.getClip();
              AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getResourceAsStream("/sounds" + url));
              clip.open(inputStream);
              clip.start(); 
            } catch (Exception e) {
              System.err.println(e.getMessage());
            }
          }
        }).start();
      }

    public Ricevi(BufferedReader input, ClientChat gui){
        inputServer= input;
        this.gui = gui;
    }
}
