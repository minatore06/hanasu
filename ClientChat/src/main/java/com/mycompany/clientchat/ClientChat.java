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
        txtIP = new JTextField("127.0.0.1");
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

    private void connetti() throws IOException
    { 
        utente = new Client(txtIP.getText(), Integer.parseInt(txtPorta.getText()), txtNome.getText(), this);
        System.out.println("sei in connetti");
        /*ou = socket.getOutputStream();
        ouw = new OutputStreamWriter(ou);
        bfw = new BufferedWriter(ouw);
        bfw.write(txtNome.getText()+"\r\n");
        bfw.flush();*/
    }
    
    public void addMsg(String msg){
        testo.append(msg);
    }
    
    //non più necessario
    /*public void inviaMessaggio(String msg) throws IOException
    { 
        if(msg.equals("Logout"))
        { 
            bfw.write("Disconnesso \r\n"); 
            testo.append("Diconnesso \r\n"); 
        }
        else
        { 
            bfw.write(msg+"\r\n"); 
            testo.append( txtNome.getText() + " <tu> -> " + txtMessaggio.getText()+"\r\n");
        } 
        bfw.flush();
        txtMessaggio.setText(""); 
    }*/

    //non più necessario
    /*
    public void ascolta() throws IOException
    { 
        InputStream in = socket.getInputStream();
        InputStreamReader inr = new InputStreamReader(in); 
        BufferedReader bfr = new BufferedReader(inr);
        String msg = ""; 
        while(!"Logout".equalsIgnoreCase(msg)) {
            if(bfr.ready()){
                msg = bfr.readLine(); 
                if(msg.equals("Logout")) 
                {
                    testo.append("Server out! \r\n");
                } 
                else
                {
                    testo.append(msg+"\r\n");
                } 
            } 
        }
    }*/
    
    //Non più necessario
    /*
    public void logout() throws IOException
    {
        utente.getTrasmetti().invia("Logout");
        bfw.close(); 
        ouw.close(); 
        ou.close(); 
        socket.close();
    }*/
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getActionCommand().equals(btnInvio.getActionCommand()))
        { 
            utente.getTrasmetti().invia(txtMessaggio.getText());
            txtMessaggio.setText("");
        }
        else if(e.getActionCommand().equals(btnLogout.getActionCommand())) 
        {
            utente.logout();
        }
    }
    
    @Override 
    public void keyPressed(KeyEvent e) 
    { 
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        { 
            utente.getTrasmetti().invia(txtMessaggio.getText());/*inviaMessaggio(txtMessaggio.getText())*/;
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
        //client.connetti();
        //client.ascolta();
    }

}
