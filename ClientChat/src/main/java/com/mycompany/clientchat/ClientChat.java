package com.mycompany.clientchat;

import java.awt.Color; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.KeyEvent; 
import java.awt.event.KeyListener; 
import java.io.BufferedReader; 
import java.io.BufferedWriter; 
import java.io.IOException; 
import java.io.InputStream; 
import java.io.InputStreamReader; 
import java.io.OutputStream; 
import java.io.OutputStreamWriter; 
import java.io.Writer; 
import java.net.Socket; 
import javax.swing.*;

public class ClientChat extends JFrame implements ActionListener, KeyListener{

    private static final long serialVersionUID = 1L; 
    private JTextArea testo; 
    private JTextField txtMessaggio; 
    private JButton btnInvio; 
    private JButton btnLogout; 
    private JLabel lblChat; 
    private JLabel lblMessaggio; 
    private JPanel pnlContenuto; 
    private Socket socket; 
    private OutputStream ou; 
    private Writer ouw;
    private BufferedWriter bfw;
    private JTextField txtIP; 
    private JTextField txtPorta; 
    private JTextField txtNome;
    private Client utente;

    public ClientChat() throws IOException
    { 
        JLabel lblMessage = new JLabel("Convalidazione"); 
        txtIP = new JTextField("87.19.188.88");
        txtPorta = new JTextField("12345");
        txtNome = new JTextField("Client"); 
        Object[] texts = {lblMessage, txtIP, txtPorta, txtNome }; 
        JOptionPane.showMessageDialog(null, texts);
        pnlContenuto = new JPanel(); 
        testo = new JTextArea(10,20);
        testo.setEditable(false); 
        testo.setBackground(new Color(240,240,240)); 
        txtMessaggio = new JTextField(20); 
        lblChat = new JLabel("Chat"); 
        lblMessaggio = new JLabel("Messaggio"); 
        btnInvio = new JButton("Invia"); 
        btnInvio.setToolTipText("Invia messaggio"); 
        btnLogout = new JButton("Logout");
        btnLogout.setToolTipText("Logout of Chat"); 
        btnInvio.addActionListener(this); 
        btnLogout.addActionListener(this);
        btnInvio.addKeyListener(this); 
        txtMessaggio.addKeyListener(this); 
        JScrollPane scroll = new JScrollPane(testo);
        testo.setLineWrap(true);
        pnlContenuto.add(lblChat);
        pnlContenuto.add(scroll); 
        pnlContenuto.add(lblMessaggio); 
        pnlContenuto.add(txtMessaggio); 
        pnlContenuto.add(btnLogout);
        pnlContenuto.add(btnInvio); 
        pnlContenuto.setBackground(Color.LIGHT_GRAY); 
        testo.setBorder(BorderFactory.createEtchedBorder(Color.BLUE,Color.BLUE)); 
        txtMessaggio.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));
        setTitle(txtNome.getText());
        setContentPane(pnlContenuto);
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(250,310);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        connetti();
    }

    //connessione
    private void connetti() throws IOException
    {
        System.out.println(utente);
        utente = new Client(txtIP.getText(), Integer.parseInt(txtPorta.getText()), txtNome.getText(), this);
        System.out.println(utente);
        utente.connetti();
        System.out.println(utente);
    }
    
    //aggiunge il messaggio all'interfaccia
    public void addMsg(String msg){
        testo.append(msg);
    }
       
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        //invio del messaggio tramite tasto INVIA
        if(e.getActionCommand().equals(btnInvio.getActionCommand()))
        {
            if(utente.getENEx()){//se il nickname è già esistente il messaggio viene considerato come il nuovo nickname
                utente.setNick(txtMessaggio.getText());//cambia il nickname
                utente.setENEx(false);//informa che il nickname è stato cambiato
            }else{
                utente.getTrasmetti().invia(txtMessaggio.getText());
            }
            txtMessaggio.setText("");
        }
        //logout tramite pulsante LOGOUT
        else if(e.getActionCommand().equals(btnLogout.getActionCommand())) 
        {
            utente.logout();
        }
    }
    
    @Override 
    public void keyPressed(KeyEvent e) 
    {   //invio del messaggio tramite tasto ENTER
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        { 
            if(utente.getENEx()){//se il nickname è già esistente il messaggio viene considerato come il nuovo nickname
                System.out.println("Nickname modificato");
                utente.setNick(txtMessaggio.getText());//cambia il nickname
                utente.setENEx(false);//informa che il nickname è stato cambiato ed il problema potrebbe essere risoldo
                System.out.println(txtMessaggio.getText());
            }else{
                utente.getTrasmetti().invia(txtMessaggio.getText());
            }
            txtMessaggio.setText("");
        } 
    } 
    
    @Override 
    public void keyReleased(KeyEvent arg0) 
    { 
    }
    
    @Override 
    public void keyTyped(KeyEvent arg0) 
    {    
    }

    public static void main(String[] args) throws IOException {
        ClientChat client = new ClientChat();
    }

}
