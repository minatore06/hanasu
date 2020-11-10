package com.mycompany.serverchat;

import java.io.*;
import java.net.*;
import java.util.ArrayList; 
import javax.swing.JLabel; 
import javax.swing.JOptionPane; 
import javax.swing.JTextField;


public class ServerChat{
/*
    private static ArrayList<BufferedWriter> clients; 
    private static ServerSocket server; 
    private String nome; 
    private Socket socket; 
    private InputStream in; 
    private InputStreamReader inr; 
    private BufferedReader bfr;*/

    /*
    public void run()
    { 
        try{
            String msg; 
            OutputStream ou = this.socket.getOutputStream(); 
            Writer ouw = new OutputStreamWriter(ou); 
            BufferedWriter bfw = new BufferedWriter(ouw); 
            clients.add(bfw); 
            nome = msg = bfr.readLine(); 
            while(!"Logout".equalsIgnoreCase(msg) && msg != null) 
            { 
                msg = bfr.readLine(); inviaATutti(bfw, msg); 
                System.out.println(msg); 
            } 
        }catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
    }

    public void inviaATutti(BufferedWriter bwOutput, String msg) throws IOException 
    { 
        BufferedWriter bwS; 
        for(BufferedWriter bw : clients)
        { 
            bwS = (BufferedWriter)bw; 
            if(!(bwOutput == bwS))
            { 
                bw.write(nome + " -> " + msg+"\r\n"); 
                bw.flush(); 
            } 
        } 
    }*/

    public static void main(String[] args) {
        try{ 
            JLabel lblMessage = new JLabel("Server Port:"); 
            JTextField txtPort = new JTextField("12345"); 
            Object[] texts = {lblMessage, txtPort }; 
            JOptionPane.showMessageDialog(null, texts);
            MultiServer master = new MultiServer(Integer.parseInt(txtPort.getText())); 
            JOptionPane.showMessageDialog(null,"Server attivo nella porta: "+ txtPort.getText()); 
            /*while(true)
            { 
                System.out.println("Attendendo una connessione...");
                Socket socket = server.accept();
                System.out.println("Client connesso ...");
            }*/
        }catch (Exception e) 
        { 
            e.printStackTrace(); 
        } 
    }
}
