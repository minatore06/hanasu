package com.mycompany.serverchat;

import java.io.*;
import java.net.*;
import java.util.ArrayList; 
import javax.swing.JLabel; 
import javax.swing.JOptionPane; 
import javax.swing.JTextField;


public class ServerChat{

    public static void main(String[] args) {
        try{ 
            JLabel lblMessage = new JLabel("Server Port:"); 
            JTextField txtPort = new JTextField("12345"); 
            Object[] texts = {lblMessage, txtPort }; 
            JOptionPane.showMessageDialog(null, texts);
            
            MultiServer master = new MultiServer(Integer.parseInt(txtPort.getText())); 
            JOptionPane.showMessageDialog(null,"Server attivo nella porta: "+ txtPort.getText()); 
        }catch (Exception e) 
        { 
            e.printStackTrace(); 
        } 
    }
}
